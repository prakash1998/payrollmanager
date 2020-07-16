package com.pra.payrollmanager.apputils.filemanager;

import java.net.URL;

import javax.persistence.Transient;

import org.bson.types.ObjectId;
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
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("d")
public class AppFile extends BaseAuditDAO<ObjectId> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4682728248992723534L;
	
	@Id
	private ObjectId id;
	
	private String type;
	
	@Builder.Default
	private Boolean secured = false;
	
	@Transient
	private URL url;

	@Override
	public ObjectId primaryKeyValue() {
		return this.id;
	};

}
