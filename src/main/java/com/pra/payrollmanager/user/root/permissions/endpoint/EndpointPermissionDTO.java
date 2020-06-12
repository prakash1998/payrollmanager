//package com.pra.payrollmanager.user.root.permissions.endpoint;
//
//import javax.validation.constraints.NotNull;
//
//import com.pra.payrollmanager.base.data.BaseAuditDTO;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
//public class EndpointPermissionDTO extends BaseAuditDTO<EndpointPermission> {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 75499830592266708L;
//	
//	@NotNull
//	private Integer numericId;
//	@NotNull
//	private String id;
//	private String display;
//	private String category;
//	private String description;
//
////	@Override
////	public EndpointPermission toPlainDAO() {
////		return EndpointPermission.builder()
////				.id(id)
////				.numericId(numericId)
////				.display(display)
////				.category(category)
////				.description(description)
////				.build();
////	}
//
//}
