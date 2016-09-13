package com.tek271.util2.reflection;

import org.junit.*;

import static org.junit.Assert.*;

public class MethodReflectorTest {
	private MethodReflector sut;
	private MyClass myClass;

	public static class MyClass {
		int m1() { return 1;}
		void m2() {}
	}

	@Before
	public void before() {
		myClass = new MyClass();
		sut = new MethodReflector<MyClass>(myClass);
	}

	@Test
	public void testCallFunction() {
		assertEquals(1, sut.callFunction("m1"));
	}

	@Test
	public void testCallVoid() {
		sut.callVoid("m2");
	}
}