package com.pra.payrollmanager.security.authentication.user;

import org.javatuples.Pair;

import com.pra.payrollmanager.security.authorization.AuthorityService;

public class UserIdConversionUtils {

	public static String userIdFromUserName(AuthorityService authService, String userName) {
		return String.format("%s-%s", authService.getSecurityCompany().getId(), userName);
	}

	public static String userNameFromUserId(String userId) {
		String[] companyUser = userId.split("-");
		return companyUser[1];
	}

	public static Pair<String, String> splitCompanyUser(String userId) {
		String[] companyUser = userId.split("-");
		return Pair.with(companyUser[0], companyUser[1]);
	}

}
