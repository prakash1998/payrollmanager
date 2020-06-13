package com.pra.payrollmanager.user.root.company;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Location implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7359113855073299863L;

	@JsonProperty("longitude")
	Double lg;

	@JsonProperty("latitude")
	Double lt;

}
