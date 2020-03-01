package com.pra.payrollmanager.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
	public static void main(String[] args) {
		System.out.println(ZonedDateTime.now());
		System.out.println(LocalDateTime.now());
		System.out.println(OffsetDateTime.now());
		System.out.println(Instant.now().plus(2, ChronoUnit.DAYS));
	}
}
