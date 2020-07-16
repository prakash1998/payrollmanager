package com.pra.payrollmanager.user.root.permissions.resource;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceType;

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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("v")
public class ResourceDAO implements Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660975791165557095L;

	@Id
	private ObjectId id;

	private ResourceType type;

	private String display;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

	@Override
	public ResourceType resourceType() {
		return type;
	}

	@Override
	public String display() {
		return display;
	}

}
