package com.tek271.util2.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class YamlToolsTest {
	private YamlTools sut;

	@BeforeEach
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