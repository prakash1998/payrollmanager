package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.mongodb.lang.NonNull;
import com.pra.payrollmanager.base.data.BaseDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("x")
public class SecurityPermission implements BaseDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660975791165557095L;
	
	@Id
	private String id;
	@NonNull
	private Integer numericId;
	private String display;
	private String category;
	private String description;

	public static SecurityPermission of(int numericId, String id) {
		String category = id.split("__")[0];
		return SecurityPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("__","_").replace("_", " "))
				.category(category)
				.description("-")
				.build();
	}
	
	public static SecurityPermission of(int numericId) {
		return SecurityPermission.builder()
				.numericId(numericId)
				.build();
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}
	
}
