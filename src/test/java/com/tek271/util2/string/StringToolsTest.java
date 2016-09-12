package com.tek271.util2.string;

import org.junit.*;

import static org.junit.Assert.*;

public class StringToolsTest {
	public static final String TEXT = "abcdef";

	private StringTools sut;

	@Before
	public void setUp() {
		sut = new StringTools();
	}

	@Test
	public void testReplaceBetween() {
		assertEquals(TEXT, sut.replaceBetween(TEXT, "w","z", ""));
		assertEquals("abcd0", sut.replaceBetween(TEXT, "d","z", "0"));
		assertEquals("ab0ef", sut.replaceBetween(TEXT, "b","e", "0"));
		assertEquals("abef", sut.replaceBetween(TEXT, "b","e", null));
	}

	@Test
	public void testClearBetween() {
		assertEquals(TEXT, sut.clearBetween(TEXT, "w","z"));
		assertEquals("abef", sut.clearBetween(TEXT, "b","e"));
	}
}