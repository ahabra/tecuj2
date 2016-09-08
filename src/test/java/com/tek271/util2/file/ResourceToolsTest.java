package com.tek271.util2.file;

import org.junit.*;

import java.util.Properties;

import static org.junit.Assert.*;

public class ResourceToolsTest {
	private ResourceTools sut;

	@Before
	public void setUp() {
		sut = new ResourceTools();
	}

	@Test
	public void testReadAsProperties() {
		Properties properties = sut.readAsProperties("ResourceToolsTest.properties");
		assertEquals(2, properties.size());
		assertEquals("v1", properties.getProperty("k1"));
		assertEquals("v2", properties.getProperty("k2"));
	}
}