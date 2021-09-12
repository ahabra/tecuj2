package com.tek271.util2.string;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EscapeToolsTest {
	private EscapeTools sut;

	@Before
	public void setUp() {
		sut = new EscapeTools();
	}

	@Test
	public void testEscapeUrl() {
		assertEquals("", sut.escapeUrl(null));
		assertEquals("", sut.escapeUrl(""));
		assertEquals("", sut.escapeUrl("   "));
		assertEquals("ab", sut.escapeUrl("ab"));
		assertEquals("a+b", sut.escapeUrl("a b"));
		assertEquals("a%2Bb", sut.escapeUrl("a+b"));
	}

	@Test
	public void testUnescapeUrl() {
		assertEquals("", sut.unescapeUrl(null));
		assertEquals("", sut.unescapeUrl(""));
		assertEquals("", sut.unescapeUrl("   "));
		assertEquals("ab", sut.unescapeUrl("ab"));
		assertEquals("a b", sut.unescapeUrl("a+b"));
		assertEquals("a+b", sut.unescapeUrl("a%2Bb"));
	}

}