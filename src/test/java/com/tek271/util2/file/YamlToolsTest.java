package com.tek271.util2.file;

import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

public class YamlToolsTest {
	private YamlTools sut;

	@Before
	public void setUp() {
		sut = new YamlTools();
	}

	@Test
	public void testReadFile() throws Exception {
		Map<String, String> map = sut.readFile("YamlToolsTest.yml");
		assertEquals(2, map.size());
		assertEquals("select * from t1", map.get("q1"));
		assertEquals("a b", map.get("q2"));
	}

}