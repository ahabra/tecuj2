package com.tek271.util2.string;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class ToStringTest {
	@SuppressWarnings("unused")
	private static class ClassA {
		int a=1;
		int b=2;
		boolean c=true;
	}

	private ToString sut;
	private ClassA obj;

	@Before
	public void setUp() {
		sut = new ToString();
		obj = new ClassA();
	}

	@Test
	public void testToStringWithExclusion() {
		String s = sut.exclude("a").toString(obj);
		assertEquals("ToStringTest.ClassA[b=2,c=true]", s);
	}

	@Test
	public void testToStringWithInclusion() {
		String s = sut.include("a").toString(obj);
		assertEquals("ToStringTest.ClassA[a=1]", s);
	}

	@Test
	public void testToStringWithInclusionAndExclusion() {
		String s = sut.include("a", "b").exclude("b").toString(obj);
		assertEquals("ToStringTest.ClassA[a=1]", s);
	}

	@Test
	public void testToStringWithStyle() {
		String s = sut.style(ToStringStyle.SIMPLE_STYLE).toString(obj);
		assertEquals("1,2,true", s);
	}

	@Test
	public void testToStringWithCollection() {
		List<ClassA> list = Lists.newArrayList(obj, obj);
		String s = sut
				.style(ToStringStyle.NO_CLASS_NAME_STYLE)
				.exclude("c", "b")
				.collectionSeparator(" | ")
				.collectionPrefix("{")
				.collectionSuffix("}")
				.toString(list);
		assertEquals("{[a=1] | [a=1]}", s);
	}

	@Test
	public void testToStringWithArray() {
		ClassA[] ar = {obj, obj};
		String s = sut
				.style(ToStringStyle.NO_CLASS_NAME_STYLE)
				.include("a")
				.collectionSeparator(" | ")
				.collectionPrefix("{")
				.collectionSuffix("}")
				.toString(ar);
		assertEquals("{[a=1] | [a=1]}", s);
	}

}