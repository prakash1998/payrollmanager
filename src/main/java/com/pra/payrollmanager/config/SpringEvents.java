package com.pra.payrollmanager.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pra.payrollmanager.entity.EntityUtils;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.filter.AuthorizationFilter;
import com.pra.payrollmanager.security.WebSecurityConfig;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyDAL;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.security.authorization.ScreenPermissions;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermissionDAL;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermissionDAL;
import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermissionDAL;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermissionDAL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile({"dev","prod"})
public class SpringEvents {

	@Autowired
	private SecurityPermissionDAL securityPermissionDAL;

	@Autowired
	private FeaturePermissionDAL apiPermissionDAL;

	@Autowired
	private EndpointPermissionDAL endpointPermissionDAL;

	@Autowired
	private ScreenPermissionDAL screenPermissionDAL;

	@Autowired
	private SecurityCompanyDAL securityCompanyDAL;

	@Autowired
	private RequestMappingHandlerMapping requestHandlerMapping;

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) throws DuplicateDataEx {
		// creating all common tables on boot if not exists
		EntityUtils.createTableForCommonEntities(securityPermissionDAL);
		// creating all tables for existing companies if not exists
		securityCompanyDAL._findAll().stream()
				.forEach(company -> {
					EntityUtils.createTableForCompanyEntities(securityPermissionDAL.mongoTemplate(),
							company.getTablePrefix());
				});

		SecurityPermissions.persistPermissionsIfNot(securityPermissionDAL);
		FeaturePermissions.persistApiPermissionsIfNot(apiPermissionDAL);
		ScreenPermissions.persistScreenPermissionsIfNot(screenPermissionDAL);
		persistEndpointsIfNot();
	}

	private void persistEndpointsIfNot() throws DuplicateDataEx {

		List<EndpointPermission> existingEndpoints = endpointPermissionDAL._findAll();

		Map<String, EndpointPermission> existingEndpointsMap = existingEndpoints.stream()
				.collect(Collectors.toMap(EndpointPermission::getId, Function.identity()));

//		int nextPossibleId = existingEndpoints.stream()
//				.reduce(BinaryOperator.maxBy(Comparator.comparingInt(EndpointPermission::getNumericId)))
//				.map(EndpointPermission::getNumericId)
//				.orElse(0);

		List<EndpointPermission> finalEndpoints = new ArrayList<>();

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

				if (!existingEndpointsMap.containsKey(endpointId)) {

					String[] uriParts = endpointPattern.split("/");
					String categoryPhrase = endpointPattern;
					String category = endpointPattern;

					boolean includeInCategoryPhrase = true;
					for (int i = uriParts.length - 1; i > 0; i--) {
						if (!uriParts[i].contains("{")) {
							if (includeInCategoryPhrase) {
								categoryPhrase = uriParts[i];
								category = categoryPhrase;
								includeInCategoryPhrase = false;
							} else {
								if (category == categoryPhrase){
									category = uriParts[i];
								}else {
									category = String.format("%s %s",uriParts[i],category);
								}
							}
						}
					}

					// int indexOfBackslash = endpointPattern.indexOf('/', 1);
					// String category = indexOfBackslash == -1 ? endpointPattern.substring(1)
					// : endpointPattern.substring(1, indexOfBackslash);

					String methodPhrase = httpMethod.toLowerCase();
					if (httpMethod.equals(HttpMethod.POST.name()))
						methodPhrase = "create";
					if (httpMethod.equals(HttpMethod.PUT.name()))
						methodPhrase = "update";

					EndpointPermission newEndpoint = EndpointPermission.builder()
//							.numericId(++nextPossibleId)
							.id(endpointId)
							.display(methodPhrase + " " + categoryPhrase)
							.category(category)
							.description("-")
							.build();
//					endpointPermissionDAL.create(newEndpoint);
//					AuthorizationFilter.universalEndpointsMap.put(endpointId, newEndpoint);
					finalEndpoints.add(newEndpoint);
				} else {
					finalEndpoints.add(existingEndpointsMap.get(endpointId));
				}
			}
		}
		endpointPermissionDAL.truncateTable();
		endpointPermissionDAL._insert(finalEndpoints);
		
		AuthorizationFilter.universalEndpointsMap.putAll(
				finalEndpoints.stream()
						.collect(Collectors.toMap(EndpointPermission::getId, Function.identity())));

	}
}
