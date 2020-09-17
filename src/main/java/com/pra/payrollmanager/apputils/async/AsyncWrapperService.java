package com.pra.payrollmanager.apputils.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.exception.unchecked.AppException;

@Service
public class AsyncWrapperService {

	@Async("threadPoolTaskExecutor")
	public <T> CompletableFuture<T> async(Supplier<T> slowFunction) {
		return CompletableFuture.supplyAsync(slowFunction);
	}

	public <T> T await(CompletableFuture<T> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new AppException("Problem while running Async Task from AsyncUtils.await", e);
		}
	}

}
