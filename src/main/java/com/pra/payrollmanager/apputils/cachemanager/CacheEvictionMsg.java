package com.pra.payrollmanager.apputils.cachemanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CacheEvictionMsg {

	@NonNull
	private String store;

	// empty_list = clear the store
	@Builder.Default
	private Collection<String> keys = new ArrayList<>();

	public static CacheEvictionMsg of(String store, String... keys) {
		return CacheEvictionMsg.builder()
				.store(store)
				.keys(Arrays.asList(keys))
				.build();
	}

	public static CacheEvictionMsg of(String store, Collection<String> keys) {
		return CacheEvictionMsg.builder()
				.store(store)
				.keys(keys)
				.build();
	}
}
