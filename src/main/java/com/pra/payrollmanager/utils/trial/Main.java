package com.pra.payrollmanager.utils.trial;

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
	public static void main(String[] args) {
		// System.out.println(ZonedDateTime.now());
		// System.out.println(LocalDateTime.now());
		// System.out.println(OffsetDateTime.now());
		// System.out.println(Instant.now().plus(2, ChronoUnit.DAYS));

		D temp = new D();
		temp.create();
	}
}
