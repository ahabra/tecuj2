package com.tek271.util2.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class ListToolsTest {
	private static final List<Integer> LIST = newArrayList(1, 2, 3, 4, 5);
	private ListTools sut;

	@Before
	public void setUp() {
		sut = new ListTools();
	}

	@Test
	public void testSlice() {
		assertEquals(newArrayList(3, 4), sut.slice(LIST, 2, 2));
	}

	@Test
	public void testSubList() {
		assertEquals(newArrayList(4, 5), sut.subList(LIST, 3));
	}

	@Test
	public void testLeft() {
		assertEquals(newArrayList(1, 2), sut.left(LIST, 2));
		assertEquals(LIST, sut.left(LIST, 6));
		assertEquals(newArrayList(), sut.left(LIST, -1));
		assertEquals(newArrayList(), sut.left(LIST, 0));
	}

	@Test
	public void testRight() {
		assertEquals(newArrayList(4, 5), sut.right(LIST, 2));
		assertEquals(LIST, sut.right(LIST, 7));
		assertEquals(newArrayList(), sut.right(LIST, -3));
		assertEquals(newArrayList(), sut.right(LIST, 0));
	}

	@Test
	public void testSetLast() {
		List<Integer> list = newArrayList(LIST);
		assertEquals(5, (int)sut.getLast(list) );
		assertEquals(5, (int)sut.setLast(list, 9));
		assertEquals(9, (int)sut.getLast(list) );
	}

}