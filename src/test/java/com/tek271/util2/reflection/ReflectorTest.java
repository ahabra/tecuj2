package com.tek271.util2.reflection;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class ReflectorTest {
	public static class ClassA {
		private int a= 1;
		boolean b = true;
		final protected String c = "x";
		static public Date d = new Date();
	}

	private ClassA obj;
	private Reflector<ClassA> sut;

	@Before
	public void setUp() {
		obj = new ClassA();
		sut = new Reflector<>(obj);
	}


}