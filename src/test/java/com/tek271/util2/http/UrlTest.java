package com.tek271.util2.http;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.*;

import static org.junit.Assert.*;

public class UrlTest {
	private Url sut;

	@Before
	public void setUp() {
		sut = new Url();
	}

	@Test
	public void testParse() {
		sut.parse("a?b=1");
		assertEquals("a", sut.base);
		assertEquals(Pair.of("b", "1"), sut.requestParams.get(0));
	}

	@Test
	public void testGetText() {
		String text = "a?b=1";
		assertEquals(text, sut.parse(text).getText() );
	}

	@Test
	public void testLoggingExcludedParams() {
		String toString = sut.loggingExcludedParams("p").parse("a?p=secret").toString();
		assertEquals("a?p=*****", toString);
	}

}