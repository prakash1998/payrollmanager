package com.pra.payrollmanager.base.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	private Collection<T> added = new ArrayList<>();

	@Builder.Default
	private Collection<T> updated = new ArrayList<>();

	@Builder.Default
	private Collection<T> removed = new ArrayList<>();

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

	public static <T> BulkOp<T> empty() {
		return new BulkOp<>();
	}

	public static <T extends BaseDAO<?>> List<T> applyOps(List<T> list, BulkOp<T> ops) {

		Map<Object, T> updatedMap = ops.getUpdated().stream()
				.collect(Collectors.toMap(item -> item.primaryKeyValue(), item -> item));

		Set<Object> removedSet = ops.getRemoved().stream()
				.map(item -> item.primaryKeyValue())
				.collect(Collectors.toSet());

		List<T> updatedList = list.stream()
				.filter(item -> !removedSet.contains(item.primaryKeyValue()))
				.map(item -> updatedMap.getOrDefault(item.primaryKeyValue(), item))
				.collect(Collectors.toList());

		updatedList.addAll(ops.getAdded());

		return updatedList;
	}

}
