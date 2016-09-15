package com.tek271.util2.reflection;

import org.junit.*;

import static org.junit.Assert.*;

public class PropertyReflectorTest {
	public static class ClassA {
		int a= 1;
		String getName() {return "joe"; }
		String food() { return "egg"; }
		ClassB b= new ClassB();
	}

	public static class ClassB {
		String a = "hi";
		String b() { return "hi2"; }
		String getC() { return "hi3"; }
	}

	private PropertyReflector<ClassA> sut;
	private ClassA objA;

	@Before
	public void setUp() {
		objA = new ClassA();
		sut = new PropertyReflector<>(objA);
	}

	@Test
	public void testGetField() {
		assertEquals(objA.a, intValue(sut.get("a")));
	}

	@Test
	public void testGetGetter() {
		assertEquals(objA.getName(), sut.get("name"));
		assertEquals(objA.food(), sut.get("food"));
		assertNull(sut.get("notThere"));
	}

	@Test
	public void testGetPropoertyChain() {
		assertEquals(objA.b.a, sut.get("b.a"));
		assertEquals(objA.b.b(), sut.get("b.b"));
		assertEquals(objA.b.getC(), sut.get("b.c"));
		assertEquals(objA.b.getC(), sut.get("b.getC"));
		assertNull(sut.get("b.z"));
		assertNull(sut.get("z.a"));
	}

	private int intValue(Integer i) {
		return i;
	}

}