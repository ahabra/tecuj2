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

	public List<E> read(String sql) {

		try (Connection con = getSql2oConnection()) {
			Query query = createQuery(con, sql);
			return query.executeAndFetch(entityType);
		}
	}

	public List<E> readNamedQuery(String queryName) {
		return read(getQueryByName(queryName));
	}


}
