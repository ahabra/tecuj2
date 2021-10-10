package com.tek271.util2.reflection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodReflectorTest {
	private MethodReflector<ClassA> sut;
	private ClassA obj;

	public static class ClassA {
		int m1() { return 1;}
		void m2() { m4(); }
		int m3(int a, int b) { return a+b; }
		private boolean m4() { return true; }
	}

	@BeforeEach
	public void before() {
		obj = new ClassA();
		sut = new MethodReflector<>(obj);
	}

	@Test
	public void testCallFunction() {
		assertEquals(Integer.valueOf(obj.m1()), sut.callFunction("m1"));
	}

	@Test
	public void testCallVoid() {
		obj.m2();
		sut.callVoid("m2");
	}

	@Test
	public void callFunctionWithArgs() {
		assertEquals(Integer.valueOf(obj.m3(4,5)), sut.callFunction("m3", 4, 5));
	}

	@Test
	public void testGetMethods() {
		Set<Method> methods = sut.excludeObjectMethods().getMethods();
		assertEquals(4, methods.size());
	}

	@Test
	public void testGetMethodsWithExclusions() {
		Set<Method> methods = sut
				.excludeInheritedMethods()
				.excludeScope(ScopeEnum.PRIVATE)
				.getMethods();
		assertEquals(3, methods.size());
	}


}