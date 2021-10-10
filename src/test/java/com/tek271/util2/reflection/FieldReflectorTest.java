package com.tek271.util2.reflection;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldReflectorTest {
	public static class ClassA {
		private int a= 1;
		boolean b = true;
		final protected String c = "x";
		static public Date d = new Date();
	}

	private ClassA obj;
	private FieldReflector<ClassA> sut;

	@BeforeEach
	public void setUp() {
		obj = new ClassA();
		sut = new FieldReflector<>(obj);
	}

	@Test
	public void testGetFieldValue() {
		assertEquals(1, (int) sut.getFieldValue("a"));
	}

	@Test
	public void toMapWithoutExclusions() {
		Map<String, Object> map = sut.toMap();
		assertEquals(4, map.size());
		assertEquals(obj.a, map.get("a"));
		assertEquals(obj.b, map.get("b"));
		assertEquals(obj.c, map.get("c"));
		assertEquals(ClassA.d, map.get("d"));
	}

	@Test
	public void toMapWithExclusions() {
		Map<String, Object> map = sut.excludeField("d")
				.excludeScope(ScopeEnum.PRIVATE)
				.toMap();
		assertEquals(2, map.size());
		assertEquals(obj.b, map.get("b"));
		assertEquals(obj.c, map.get("c"));
	}

	@Test
	public void toPairsArray() {
		Pair<String, Object>[] pairs = sut.toPairsArray();
		assertEquals(4, pairs.length);
		assertEquals(Pair.of("a", obj.a), pairs[0]);
		assertEquals(Pair.of("b", obj.b), pairs[1]);
		assertEquals(Pair.of("c", obj.c), pairs[2]);
		assertEquals(Pair.of("d", ClassA.d), pairs[3]);
	}

	@Test
	public void testGetFields() {
		List<String> fields = sut.getFields();
		assertEquals(4, fields.size());
	}

}