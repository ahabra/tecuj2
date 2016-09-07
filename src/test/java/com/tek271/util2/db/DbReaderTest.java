package com.tek271.util2.db;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DbReaderTest {
	private DbReader<PlayEntity> sut;

	@Before
	public void setUp() {
		sut = new DbReader<>(PlayEntity.class);
		DbWriter dbWriter = new DbWriter();

		DbHelper.configureDb(sut);
		DbHelper.configureDb(dbWriter);
		dbWriter.sql("drop table if exists PlayEntity;").write();
		dbWriter.scriptFromFile("DbReaderTest.sql").write();
	}

	@Test
	public void testBuilderProperties() {
		Map<String, String> namedQueries = new HashMap<>();
		sut.url("url1")
				.user("u1")
				.password("p1")
				.namedQueries(namedQueries);

		assertEquals("url1", sut.url);
		assertEquals("u1", sut.user);
		assertEquals("p1", sut.password);
		assertEquals(namedQueries, sut.namedQueries);
	}

	@Test
	public void testParams() {
		Map<String, Object> map = new HashMap<>();
		map.put("k1", "v1");
		map.put("k2", 3);
		sut.params(map);

		assertEquals(map, sut.parameters);
	}

	@Test
	public void testWriteThenRead() {
		List<PlayEntity> inserted = DbHelper.insertPlayEntities(3);
		List<PlayEntity> found = sut
				.sql("select * from PlayEntity")
				.read();

		assertEquals(inserted, found);
	}

	@Test
	public void testReadNamedQuery() {
		List<PlayEntity> inserted = DbHelper.insertPlayEntities(3);
		Map<String, String> map = ImmutableMap.of("k1", "select * from PlayEntity");
		sut.namedQueries(map);

		List<PlayEntity> found = sut.sqlFromNamedQuery("k1").read();
		assertEquals(inserted, found);
	}

	@Test
	public void testNamedQueriesFromYamlFile() {
		sut.namedQueries("YamlToolsTest.yml");
		assertEquals(2, sut.namedQueries.size());
	}

}