package com.tek271.util2.db;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.tek271.util2.db.PlayEntity.create;
import static org.junit.Assert.assertEquals;

public class DbWriterTest {
	private DbReader<PlayEntity> dbReader;
	private DbWriter dbWriter;

	@Before
	public void setUp() {
		dbReader = new DbReader<>(PlayEntity.class);
		dbWriter = new DbWriter();

		DbHelper.configureDb(dbReader);
		DbHelper.configureDb(dbWriter);
		dbWriter.sql("drop table if exists PlayEntity;").write();
		dbWriter.scriptFromFile("DbReaderTest.sql").write();
	}

	@Test
	public void testTransaction() {
		List<PlayEntity> entities = newArrayList(create(1), create(2));
		try (Connection con = dbWriter.transaction()) {
			for (PlayEntity e: entities) {
				e.id = dbWriter.param("name", e.name)
						.param("date", e.date)
						.sql(PlayEntity.INSERT_SQL)
						.returnKeyAfterWrite(true)
						.withConnection(con)
						.write();
			}
			con.commit();
		}
		List<PlayEntity> found = dbReader.sql("select * from PlayEntity").read();
		assertEquals(entities, found);
	}

	@Test
	public void testWriteNamedQuery() {
		Map<String, String> map = ImmutableMap.of("k1", PlayEntity.INSERT_SQL);
		PlayEntity playEntity = create(1);
		dbWriter
				.namedQueries(map)
				.param("name", playEntity.name)
				.param("date", playEntity.date)
				.sqlFromNamedQuery("k1")
				.write();
		List<PlayEntity> found = dbReader.sql("select * from PlayEntity").read();
		assertEquals(1, found.size());
		assertEquals(playEntity, found.get(0));
	}

	@Test
	public void testWriteNamedQueryAndGetId() {
		Map<String, String> map = ImmutableMap.of("k1", PlayEntity.INSERT_SQL);
		PlayEntity playEntity = create(1);
		long id = dbWriter
				.namedQueries(map)
				.param("name", playEntity.name)
				.param("date", playEntity.date)
				.sqlFromNamedQuery("k1")
				.returnKeyAfterWrite(true)
				.write();
		List<PlayEntity> found = dbReader.sql("select * from PlayEntity").read();
		assertEquals(0, id);
		assertEquals(1, found.size());
		assertEquals(playEntity, found.get(0));
	}

}