package com.pra.payrollmanager.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.scheduling.annotation.Async;

import com.pra.payrollmanager.exception.unchecked.AppException;

public class AsyncUtils {

	public static <T> CompletableFuture<T> async(Supplier<T> slowFunction) {
		return CompletableFuture.supplyAsync(slowFunction);
	}

	public static <T> T await(CompletableFuture<T> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new AppException("Problem while running Async Task from AsyncUtils.await", e);
		}
	}

	public static void main(String[] args) {
		var item1 = async(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("in first");
			return 1;
		});
		
		System.out.println("after first");

		var item2 = async(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("in second");
			return 2;
		});

		System.out.println("after second");
		
		System.out.println("Sum is : " + (await(item1) + await(item2)));

	}

}
