package com.pra.payrollmanager.security.authentication.user;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false )
public class SecurityUser extends BaseAuditDAO<String> implements UserDetails {
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
	private boolean loggedIn = false;

	@Transient
	private SecurityCompany company;
	@Transient
	private String userId;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>();
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

	@Override
	public String primaryKeyValue() {
		return username;
	}

}