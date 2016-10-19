package com.tek271.util2.db;

import org.sql2o.Connection;
import org.sql2o.Query;

import java.util.List;

public class DbReader<E> extends DbAccessor<DbReader<E>> {
	private final Class<E> entityType;

	@Override
	protected DbReader<E> getThis() {
		return this;
	}

	public DbReader(Class<E> entityType) {
		this.entityType = entityType;
	}

	public List<E> read() {
		return dbConnection.isLocal? readAndClose() : read(dbConnection.sql2oConnection);
	}

	private List<E> read(Connection con) {
		Query query = createQuery(con);
		return query.executeAndFetch(entityType);
	}

	private List<E> readAndClose() {
		try (Connection con = dbConnection.getSql2oConnection()) {
			return read(con);
		}
	}


}
