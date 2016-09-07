package com.tek271.util2.db;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DbReaderTest {
	private static final String DATE= "2016.09.07";
	private DbReader<PlayEntity> sut;
	private DbWriter dbWriter;
	private Map<String, String> dbConfig;


	public static class PlayEntity {
		public long id;
		public String name;
		public String date;

		public String toInsertSql() {
			return "insert into PlayEntity (name, date) values (:name, :date)";
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		public static PlayEntity create(int counter) {
			PlayEntity e = new PlayEntity();
			e.name = "name-" + counter;
			e.date = DATE;
			return e;
		}
	}

	@Before
	public void setUp() {
		sut = new DbReader<>(PlayEntity.class);
		dbWriter = new DbWriter();
		dbConfig = ImmutableMap.<String, String>builder()
				.put("url", "jdbc:hsqldb:mem:tecuj-test")
				.put("driver", "org.hsqldb.jdbc.JDBCDriver")
				.put("user", "SA")
				.put("password", "SA")
				.build();
		setDbConfig(sut);
		setDbConfig(dbWriter);
		dbWriter.writeFromFile("DbReaderTest.sql");
	}

	private void setDbConfig(DbAccessor dbAccessor) {
		dbAccessor.url(dbConfig.get("url"))
				.user(dbConfig.get("user"))
				.password(dbConfig.get("password"));
	}

	private void insert(PlayEntity entity) {
		long id = dbWriter.param("name", entity.name)
				.param("date", entity.date)
				.writeAndGetId(entity.toInsertSql());

	}

	@Test
	public void testRead() {
		PlayEntity entity = PlayEntity.create(1);
		insert(entity);
		List<PlayEntity> list = sut.read("select * from PlayEntity");

		assertEquals(1, list.size());
		assertEquals(entity, list.get(0));
	}

	@Test
	public void testReadNamedQuery() {

	}
}