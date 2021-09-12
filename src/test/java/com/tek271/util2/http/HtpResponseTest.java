package com.tek271.util2.http;

import org.junit.*;

import static org.junit.Assert.*;

public class HtpResponseTest {
	private HtpResponse sut;

	@Before
	public void setUp() {
		sut = new HtpResponse("hi", "text", 200, "OK");
	}

	@Test
	public void testOk() {
		sut = HtpResponse.ok("hi");
		assertEquals("hi", sut.text);
		assertEquals("text/html", sut.type);
		assertEquals(200, sut.status);
		assertEquals("OK", sut.reason);
		assertEquals(true, sut.isSuccess);
	}

	@Test
	public void testToString() {
		String expected = "HtpResponse[isSuccess=true,reason=OK,status=200,text=hi,type=text]";
		assertEquals(expected, sut.toString());
	}

}