package com.pra.payrollmanager.config;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.filter.AuthorizationFilter;
import com.pra.payrollmanager.security.WebSecurityConfig;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.security.authorization.ScreenPermissions;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermissionDAL;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermissionDAL;
import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermissionDAL;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermissionDAL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringEvents {

	@Autowired
	private SecurityPermissionDAL securityPermissionRepo;

	@Autowired
	private FeaturePermissionDAL apiPermissionDAL;

	@Autowired
	private EndpointPermissionDAL endpointPermissionDAL;
	
	@Autowired
	private ScreenPermissionDAL screenPermissionDAL;


	@Autowired
	private RequestMappingHandlerMapping requestHandlerMapping;

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) throws DuplicateDataEx {

		SecurityPermissions.persistPermissionsIfNot(securityPermissionRepo);
		ResourceFeaturePermissions.persistApiPermissionsIfNot(apiPermissionDAL);
		ScreenPermissions.persistScreenPermissionsIfNot(screenPermissionDAL);
		
		persistEndPointsIfNot();
	}

	private void persistEndPointsIfNot() throws DuplicateDataEx {

		List<EndpointPermission> existingEndpoints = endpointPermissionDAL.findAll();

		AuthorizationFilter.universalEndpointsMap.putAll(
				existingEndpoints.stream()
						.collect(Collectors.toMap(EndpointPermission::getId, Function.identity())));

		int nextPossibleId = existingEndpoints.stream()
				.reduce(BinaryOperator.maxBy(Comparator.comparingInt(EndpointPermission::getNumericId)))
				.map(EndpointPermission::getNumericId)
				.orElse(1);

		Map<RequestMappingInfo, HandlerMethod> requestHandlerMap = requestHandlerMapping
				.getHandlerMethods();

		for (RequestMappingInfo endpointInfo : requestHandlerMap.keySet()) {
			log.info("Active Endpoint {}", endpointInfo);
			Set<RequestMethod> httpMethods = endpointInfo.getMethodsCondition().getMethods();
			if (!httpMethods.isEmpty()) {
				String httpMethod = httpMethods.iterator().next().toString();
				Set<String> patterns = endpointInfo.getPatternsCondition().getPatterns();
				String endpointPattern = patterns.iterator().next();

				if (WebSecurityConfig.openEndpoints.contains(endpointPattern) ||
						AuthorizationFilter.endpointsWithoutAuthorization.contains(endpointPattern))
					continue;

				String endpointId = AuthorizationFilter.endpointId(endpointPattern, httpMethod);

				if (existingEndpoints.stream().noneMatch(ep -> ep.getId().equals(endpointId))) {

					int indexOfBackslash = endpointPattern.indexOf('/', 1);
					String category = indexOfBackslash == -1 ? endpointPattern.substring(1)
							: endpointPattern.substring(1, indexOfBackslash);

					String methodPhrase = httpMethod.toLowerCase();
					if(httpMethod.equals(HttpMethod.POST.name()))
						methodPhrase = "create";
					if(httpMethod.equals(HttpMethod.PUT.name()))
						methodPhrase = "update";

					EndpointPermission newEndpoint = EndpointPermission.builder()
							.numericId(nextPossibleId++)
							.id(endpointId)
							.display(methodPhrase + " " + category)
							.category(category)
							.description("-")
							.build();
					endpointPermissionDAL.create(newEndpoint);
					AuthorizationFilter.universalEndpointsMap.put(endpointId, newEndpoint);
				}
			}
		}

	}
}