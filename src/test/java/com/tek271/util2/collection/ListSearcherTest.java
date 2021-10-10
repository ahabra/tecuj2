package com.tek271.util2.collection;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

public class ListSearcherTest {
	private static final List<Integer> LIST = newArrayList(0, 1, 2, 3, 4);
	private ListSearcher<Integer> sut;

	@BeforeEach
	public void setUp() {
		sut = new ListSearcher<>(LIST);
	}

	@Test
	public void testSubList() {
		assertEquals(LIST, sut.subList());
		assertEquals(newArrayList(1, 2), sut.startIndex(1).maxIndex(3).subList());
		assertEquals(LIST, sut.startIndex(-2).maxIndex(9).subList());
		assertEquals(emptyList(), sut.startIndex(9).maxIndex(9).subList());
		assertEquals(emptyList(), sut.startIndex(9).maxIndex(-2).subList());
		assertEquals(emptyList(), sut.startIndex(-2).maxIndex(-2).subList());
		assertEquals(emptyList(), sut.startIndex(0).maxIndex(0).subList());
		assertEquals(emptyList(), sut.startIndex(1).maxIndex(1).subList());
	}

	@Test
	public void testIndexOf() {
		assertEquals(1, sut.indexOf(1));
		assertEquals(-1, sut.indexOf(9));
		assertEquals(4, sut.matcher((a,b) -> b==a*2).indexOf(8));
	}

	@Test
	public void testIndexOfStringPrefix() {
		List<String> list = newArrayList("ab", "cd", "ef");
		ListSearcher<String> searcher = new ListSearcher<>(list);
		assertEquals(1, searcher.indexOf("cd"));
		assertEquals(-1, searcher.indexOf("c"));
		assertEquals(1, searcher.matcher(String::startsWith).indexOf("c"));
	}

	@Test
	public void testLastIndexOf() {
		assertEquals(1, sut.lastIndexOf(1));
		assertEquals(-1, sut.lastIndexOf(9));
		assertEquals(4, sut.matcher((a,b) -> b==a*2).lastIndexOf(8));
	}

	@Test
	public void testIndexOfAny() {
		assertEquals(1, sut.indexOfAny(7, 3, 1));
		assertEquals(-1, sut.startIndex(2).maxIndex(3).indexOfAny(7, 3, 1));
		assertEquals(3, sut.startIndex(2).maxIndex(4).indexOfAny(7, 3, 1));
		assertEquals(2, sut.matcher((a,b)-> a+b==9).indexOfAny(7, 3, 1));
	}

	@Test
	public void testIndexOfSubList() {
		assertEquals(2, sut.indexOfSubList(newArrayList(2, 3)));
		assertEquals(3, sut.indexOfSubList(newArrayList(3, 4)));
		assertEquals(3, sut.matcher(Integer::equals).indexOfSubList(newArrayList(3, 4)));
		assertEquals(-1, sut.startIndex(2).indexOfSubList(newArrayList(1)));
		assertEquals(2, sut.startIndex(1).maxIndex(3).indexOfSubList(newArrayList(2)));
		assertEquals(-1, sut.startIndex(1).maxIndex(3).indexOfSubList(newArrayList(2, 3)));
	}

	@Test
	public void testIndexOfSubListArray() {
		assertEquals(2, sut.indexOfSubList(2, 3));
		assertEquals(3, sut.indexOfSubList(3, 4));
		assertEquals(3, sut.matcher(Integer::equals).indexOfSubList(3, 4));
		assertEquals(-1, sut.startIndex(2).indexOfSubList(1));
		assertEquals(2, sut.startIndex(1).maxIndex(3).indexOfSubList(2));
		assertEquals(-1, sut.startIndex(1).maxIndex(3).indexOfSubList(2, 3));
	}

	@Test
	public void testIsPrefix() {
		assertTrue(sut.isPrefix(newArrayList(0, 1)));
		assertTrue(sut.startIndex(1).maxIndex(3).isPrefix(newArrayList(1, 2)));
		assertFalse(sut.startIndex(1).maxIndex(2).isPrefix(newArrayList(1, 2)));
		assertTrue(sut.startIndex(1).maxIndex(2).reset().isPrefix(newArrayList(0, 1)));
		assertFalse(sut.isPrefix(newArrayList()));
	}

	@Test
	public void testIsPrefixArray() {
		assertTrue(sut.isPrefix(0, 1));
		assertTrue(sut.startIndex(1).maxIndex(3).isPrefix(1, 2));
		assertFalse(sut.startIndex(1).maxIndex(2).isPrefix(1, 2));
		assertTrue(sut.startIndex(1).maxIndex(2).reset().isPrefix(0, 1));
		assertFalse(sut.isPrefix());
	}

	@Test
	public void testMatcher() {
		assertEquals(1, sut.indexOf(1));
		assertEquals(3, sut.matcher((a,b)->a+b==7).indexOf(4));
		assertEquals(-1, sut.indexOf(1));
		assertEquals(1, sut.equalsMatcher().indexOf(1));
	}

}