package com.pra.payrollmanager.utils.trial;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

interface BaseDAL {
	default void create() {
		System.out.println("created from base");
	}
}

interface ABaseDAL extends BaseDAL {
	@Override
	default void create() {
		System.out.println("created from audit base");
	}
}

interface RBaseDAL extends BaseDAL {

	@Override
	default void create() {
		BaseDAL.super.create();
		System.out.println("created from base r ");
	}
}

abstract class RDAL implements ABaseDAL, RBaseDAL {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		RBaseDAL.super.create();
	}

}

class D extends RDAL {

}

public class Main {
	// public static void main(String[] args) {
	// System.out.println(ZonedDateTime.now());
	// System.out.println(LocalDateTime.now());
	// System.out.println(OffsetDateTime.now());

	// System.out.println(Instant.now());

	// Instant javaInstant = Instant.now();
	// long now = System.nanoTime();
	//
	// for(int i= 0 ; i< 1e8; i++) {
	// Instant.now().truncatedTo(ChronoUnit.MILLIS);
	// }
	//
	// System.out.println("time taken java -- "+ (System.nanoTime() - now));
	//
	// org.joda.time.Instant jodaInstant = org.joda.time.Instant.now();
	// now = System.nanoTime();
	// for(int i= 0 ; i< 1e8; i++) {
	//// jodaInstant.equals(jodaInstant.plus(123));
	//// Instant.now().truncatedTo(ChronoUnit.MILLIS);
	// org.joda.time.Instant.now();
	// }
	//
	// System.out.println("time taken java -- "+ (System.nanoTime() - now));

	// D temp = new D();
	// temp.create();
	// }

}
