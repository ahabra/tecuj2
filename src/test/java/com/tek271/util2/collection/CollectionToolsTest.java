package com.tek271.util2.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	}

	@Test
	public void testContains() {
		assertTrue(sut.contains(LIST, 1, Integer::equals));
		assertFalse(sut.contains(LIST, 9, Integer::equals));

		List<String> list = newArrayList("ab", "cd");
		assertTrue(sut.contains(list, "c", String::startsWith));
	}

}