package com.tek271.util2.db;

import org.junit.*;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DbQueriesTest {
	private DbQueries sut;

	@Before
	public void setUp() {
		sut = new DbQueries();
		DbQueries.QUERY_CACHE.clear();
	}

	@Test(expected = NoSuchElementException.class)
	public void getFailsIfEmpty() {
		sut.get("whatever");
	}

	@Test(expected = NoSuchElementException.class)
	public void cacheGetFailsIfEmpty() {
		DbQueries.QUERY_CACHE.get("whatever");
	}

	@Test
	public void cachCanBeInitialized() {
		DbQueries.initCache("YamlToolsTest.yml");
		String q1 = DbQueries.QUERY_CACHE.get("q1");
		assertEquals("select * from t1", q1);
	}

}