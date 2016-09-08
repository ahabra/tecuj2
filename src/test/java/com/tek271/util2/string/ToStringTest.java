package com.tek271.util2.string;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.*;

import static org.junit.Assert.*;

public class ToStringTest {
	private static class MyClass {
		int a=1;
		int b=2;
		boolean c=true;
	}

	private ToString sut;
	private MyClass obj;

	@Before
	public void setUp() {
		sut = new ToString();
		obj = new MyClass();
	}

	@Test
	public void testReflectionToStringWithExclusion() {
		String s = sut.exclude("a").reflectionToString(obj);
		assertEquals("ToStringTest.MyClass[b=2,c=true]", s);
	}

	@Test
	public void testReflectionToStringWithInclusion() {
		String s = sut.include("a").reflectionToString(obj);
		assertEquals("ToStringTest.MyClass[a=1]", s);
	}

	@Test
	public void testReflectionToStringWithInclusionAndExclusion() {
		String s = sut.include("a", "b").exclude("b").reflectionToString(obj);
		assertEquals("ToStringTest.MyClass[a=1]", s);
	}

	@Test
	public void testReflectionToStringWithStyle() {
		String s = sut.style(ToStringStyle.SIMPLE_STYLE).reflectionToString(obj);
		assertEquals("1,2,true", s);
	}

}