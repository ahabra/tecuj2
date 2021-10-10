package com.tek271.util2.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSearcherTest {
	private StringSearcher sut;

	@BeforeEach
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