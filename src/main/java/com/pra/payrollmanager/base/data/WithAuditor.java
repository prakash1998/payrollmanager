package com.pra.payrollmanager.base.data;

import java.time.Instant;

public interface WithAuditor {
	
	String getCreatedBy();

	void setCreatedBy(String createdBy);

	Instant getCreatedDate();

	void setCreatedDate(Instant createdDate);

	String getModifier();

	void setModifier(String modifier);

	Instant getModifiedDate();

	void setModifiedDate(Instant modifiedDate);

	Boolean getDeleted();

	void setDeleted(Boolean deleted);

	String getDeletedBy();

	void setDeletedBy(String deletedBy);
}
