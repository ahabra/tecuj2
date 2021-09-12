package com.tek271.util2.db;

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
		DbQueries.QUERY_CACHE.put("k1", "select * from PlayEntity");
		List<PlayEntity> inserted = DbHelper.insertPlayEntities(3);

		List<PlayEntity> found = sut.sqlFromCachedQuery("k1").read();
		assertEquals(inserted, found);
	}

	@Test
	public void testReadMaps() {
		List<PlayEntity> inserted = DbHelper.insertPlayEntities(3);
		List<Map<String, Object>> found = sut
				.sql("select * from PlayEntity")
				.readMaps();

		assertEquals(inserted.size(), found.size());
		for(int i=0; i<inserted.size(); i++) {
			PlayEntity expected = inserted.get(i);
			Map<String, Object> actual = found.get(i);
			assertEquals(expected.id, ((Integer)actual.get("id")).intValue());
			assertEquals(expected.name, actual.get("name"));
			assertEquals(expected.date, actual.get("date"));
		}
	}

}