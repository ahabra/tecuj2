package com.tek271.util2.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResourceToolsTest {
	private ResourceTools sut;

	@BeforeEach
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