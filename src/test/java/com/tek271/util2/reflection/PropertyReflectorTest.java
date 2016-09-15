package com.tek271.util2.reflection;

import org.junit.*;

import static org.junit.Assert.*;

public class PropertyReflectorTest {
	public static class ClassA {
		int a= 1;
		String getName() {return "joe"; }
		String food() { return "egg"; }
	}

	private PropertyReflector<ClassA> sut;
	private ClassA obj;

	@Before
	public void setUp() {
		obj = new ClassA();
		sut = new PropertyReflector<>(obj);
	}

	@Test
	public void testGetField() {
		assertEquals(obj.a, intValue(sut.get("a")));
	}

	@Test
	public void testGetGetter() {
		assertEquals(obj.getName(), sut.get("name"));
		assertEquals(obj.food(), sut.get("food"));
		assertNull(sut.get("notThere"));
	}

	private int intValue(Integer i) {
		return i;
	}

}