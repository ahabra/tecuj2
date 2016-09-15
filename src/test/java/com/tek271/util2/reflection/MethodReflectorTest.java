package com.tek271.util2.reflection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodReflectorTest {
	private MethodReflector sut;
	private MyClass myClass;

	public static class MyClass {
		int m1() { return 1;}
		void m2() {}
		int m3(int a, int b) { return a+b;}
	}

	@Before
	public void before() {
		myClass = new MyClass();
		sut = new MethodReflector<>(myClass);
	}

	@Test
	public void testCallFunction() {
		assertEquals(myClass.m1(), sut.callFunction("m1"));
	}

	@Test
	public void testCallVoid() {
		myClass.m2();
		sut.callVoid("m2");
	}

	@Test
	public void callFunctionWithArgs() {
		assertEquals(myClass.m3(4,5), sut.callFunction("m3", 4, 5));
	}

}