package com.pra.payrollmanager.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermission;

public class MockUserDetailService implements UserDetailsService {

	private Map<String,SecurityUser> userMap;

	public MockUserDetailService() {
		userMap = new HashMap<>();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userMap.get(username);
	}

	public void addUsersInStore(SecurityUser ...users) {
		for(SecurityUser user : users)
			userMap.put(user.getUsername(), user);
	}
	
	public void addUserInStore(String userName , SecurityPermission ...permissions) {
		Set<Integer> permissionIds = new HashSet<>();
		for(SecurityPermission permission : permissions)
			permissionIds.add(permission.getNumericId());
		
		addUsersInStore(SecurityUser.builder()
				.username(userName)
				.company(SecurityCompany.builder()
						.id("TEST")
						.build())
				.build());
	}
	
	public void clearStore() {
		this.userMap.clear();
	}

}
