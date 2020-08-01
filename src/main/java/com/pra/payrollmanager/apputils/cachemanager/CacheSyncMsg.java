package com.pra.payrollmanager.apputils.cachemanager;

import java.util.Collections;
import java.util.List;

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
public class CacheSyncMsg {

	@NonNull
	private List<CacheEvictionMsg> el;

	public static CacheSyncMsg of(CacheEvictionMsg eviction) {
		return CacheSyncMsg.builder()
				.el(Collections.singletonList(eviction))
				.build();
	}

	public static CacheSyncMsg of(List<CacheEvictionMsg> evictions) {
		return CacheSyncMsg.builder()
				.el(evictions)
				.build();
	}

}
