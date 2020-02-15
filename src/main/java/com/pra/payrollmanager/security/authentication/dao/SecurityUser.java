package com.pra.payrollmanager.security.authentication.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "SECURITY_USERS")
public class SecurityUser implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// it is not in camel-case because of UserDetail implementation
	@Id
	private String username;
	private String password;
	@Builder.Default
	private boolean userEnabled = true;
	@Builder.Default
	private boolean accountLocked = false;
	@Builder.Default
	private boolean accountExpired = false;
	@Builder.Default
	private boolean passwordExpired = false;
	@Builder.Default
	private Collection<String> roles = new HashSet<>();
	@Builder.Default
	private boolean loggedIn = false;

	public SecurityUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public SecurityUser() {

	}
	
	public boolean hasAccessTo(SecurityPermission permission) {
		return roles.contains(permission.getId());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !passwordExpired;
	}

	@Override
	public boolean isEnabled() {
		return userEnabled;
	}

}