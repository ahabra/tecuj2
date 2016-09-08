package com.tek271.util2.string;

import org.junit.*;

import static org.junit.Assert.*;

public class StringSearcherTest {
	private StringSearcher sut;

	@Before
	public void setUp() {
		sut = new StringSearcher();
	}

	@Test
	public void testContains() {
		assertFalse(sut.text("abc").contains("Bc"));
		assertTrue(sut.text("abc").ignoreCase().contains("Bc"));
		assertFalse(sut.text("abc").ignoreCase(false).contains("Bc"));
	}

	@Test
	public void testContainsAny() {
		assertTrue(sut.text("abcd").containsAny("x", "cd"));
		assertFalse(sut.text("abcd").containsAny("x", "cde"));
	}

}