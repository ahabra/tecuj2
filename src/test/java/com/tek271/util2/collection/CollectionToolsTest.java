package com.tek271.util2.collection;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.*;

public class CollectionToolsTest {
	private static final List<Integer> LIST = newArrayList(0, 1, 2, 3);
	private CollectionTools sut;

	@Before
	public void setUp() {
		sut = new CollectionTools();
	}

	@Test
	public void testCopyTo() {
		List<Integer> copy = sut.copy(LIST, newArrayList());
		assertEquals(LIST, copy);
	}

	@Test
	public void testCopy() {
		assertEquals(null, sut.copy(null));
		assertEquals(LIST, sut.copy(LIST));
		assertEquals(newHashSet("1", "a"), sut.copy(newHashSet("a", "1")));

		List<String> list = unmodifiableList(newArrayList("a", "1"));
		assertEquals(newArrayList("a", "1"), sut.copy(list));
	}

	@Test
	public void testContainsWithBiPredicate() {
		assertTrue(sut.contains(LIST, 1, Integer::equals));
		assertFalse(sut.contains(LIST, 9, Integer::equals));

		List<String> list = newArrayList("ab", "cd");
		assertTrue(sut.contains(list, "c", String::startsWith));
	}

	@Test
	public void testContainsWithPredicate() {
		assertTrue(sut.contains(LIST, a-> a==1));
		assertFalse(sut.contains(LIST, a-> a==9));

		List<String> list = newArrayList("ab", "cd");
		assertTrue(sut.contains(list, a-> a.startsWith("c")));
	}

	@Test
	public void testToMapByKey() {
		assertTrue(sut.toMapByKey("k").isEmpty());
		Map<Integer, String> map = sut.toMapByKey("length", "a", "aa");
		Map<Integer, String> expected = ImmutableMap.of(
				1, "a",
				2, "aa"
		);
		assertEquals(expected, map);
	}

}