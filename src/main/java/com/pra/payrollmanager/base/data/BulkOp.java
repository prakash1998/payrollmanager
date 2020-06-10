package com.pra.payrollmanager.base.data;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkOp<T> {

	@Builder.Default
	Collection<T> added = new ArrayList<>();

	@Builder.Default
	Collection<T> updated = new ArrayList<>();

	@Builder.Default
	Collection<T> removed = new ArrayList<>();

//	public static <V> BulkOp<V> ofAdded(Collection<V> addedItems) {
//		return BulkOp.<V>builder()
//				.added(addedItems)
//				.build();
//	}

}
