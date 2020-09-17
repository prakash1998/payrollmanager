package com.pra.payrollmanager.base.data;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BulkOp<T> {

	@Builder.Default
	Collection<T> added = new ArrayList<>();

	@Builder.Default
	Collection<T> updated = new ArrayList<>();

	@Builder.Default
	Collection<T> removed = new ArrayList<>();

	public static <V> BulkOp<V> fromAdded(Collection<V> addedItems) {
		return BulkOp.<V>builder()
				.added(addedItems)
				.build();
	}

	public Collection<T> getAll() {
		ArrayList<T> collector = new ArrayList<>();
		if (!this.added.isEmpty()) {
			collector.addAll(this.added);
		}
		if (!this.updated.isEmpty()) {
			collector.addAll(this.updated);
		}
		if (!this.removed.isEmpty()) {
			collector.addAll(this.removed);
		}
		return collector;
	}

	public boolean isEmpty() {
		return this.added.isEmpty() && this.updated.isEmpty() && this.removed.isEmpty();
	}

}
