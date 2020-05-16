package com.pra.payrollmanager.base.data;

import java.time.Instant;

public interface WithCreator {

	String getCreatedBy();

	void setCreatedBy(String createdBy);

	Instant getCreatedDate();

	void setCreatedDate(Instant createdDate);

}
