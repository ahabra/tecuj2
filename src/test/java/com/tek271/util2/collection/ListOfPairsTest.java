package com.tek271.util2.collection;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfPairsTest {
	private ListOfPairs<Integer, String> sut;

	@BeforeEach
	public void setUp() {
		sut = new ListOfPairs<>();
	}

	@Test
	public void testAdd() {
		sut.add(1, "a");
		assertEquals(Pair.of(1, "a"), sut.get(0));
	}

	@Test
	public void testAddMap() {
		Map<Integer, String> map = new LinkedHashMap<>();
		map.put(1, "a");
		map.put(2, "b");
		sut.add(map);

		assertEquals(2, sut.size());
		assertEquals(Pair.of(1, "a"), sut.get(0));
		assertEquals(Pair.of(2, "b"), sut.get(1));
	}

	@Test
	public void testToArray() {
		sut.add(1, "a");
		Pair<Integer, String>[] pairs = sut.toArray();
		assertEquals(1, pairs.length);
		assertEquals(Pair.of(1, "a"), pairs[0]);
	}

	@Test
	public void testToMap() {
		sut.add(1, "a");
		sut.add(2, "b");
		Map<Integer, String> map = sut.toMap();
		assertEquals(2, map.size());
		assertEquals("a", map.get(1));
		assertEquals("b", map.get(2));
	}

	@Test
	public void testGetValues() {
		sut.add(1, "a");
		sut.add(2, "b");
		sut.add(1, "c");

		assertEquals(newArrayList("a", "c"), sut.getValues(1));
		assertEquals(newArrayList("b"), sut.getValues(2));
	}

	@Test
	public void testKeyAndValue() {
		sut.add(1, "a");
		assertEquals(1, sut.key(0).intValue());
		assertEquals("a", sut.value(0));
	}

	@Test
	public void testForEach() {
		sut.add(1, "a");
		sut.add(2, "b");
		Map<Integer, String> map = new LinkedHashMap<>();
		sut.forEach(map::put);
		assertEquals(sut.size(), map.size());
		assertEquals(sut.value(0), map.get(1));
		assertEquals(sut.value(1), map.get(2));
	}

}