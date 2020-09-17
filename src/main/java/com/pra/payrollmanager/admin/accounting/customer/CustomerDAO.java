package com.pra.payrollmanager.admin.accounting.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

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
@TypeAlias("1")
public class CustomerDAO extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	@NotNull(message = "userName must not be null")
	private String userName;

	@NotNull(message = "Name must not be null")
	@NotBlank(message = "Name must not be blank")
	private String name;

	@NotNull(message = "company must not be null")
	@NotBlank(message = "company must not be blank")
	private String company;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "please enter valid phone Number")
	private String phone;

	@Email
	@NotNull(message = "email must not be null")
	private String email;

	@NotNull(message = "GST must not be null")
	@NotBlank(message = "GST must not be blank")
	@Size(min = 15, max = 15, message = "GSTIN must be {max} digit in length")
	private String gst;

	@NotNull(message = "Address Line 1 must not be null")
	@NotBlank(message = "Address Line 2 must not be blank")
	private String address1;

	private String address2;

	@Override
	public String primaryKeyValue() {
		return userName;
	}

}
