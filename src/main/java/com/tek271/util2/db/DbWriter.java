package com.tek271.util2.db;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.tek271.util2.files.ResourceTools;
import org.sql2o.Connection;
import org.sql2o.Query;

public class DbWriter extends DbAccessor<DbWriter> {
	@VisibleForTesting
	public ResourceTools resourceTools = new ResourceTools();

	protected DbWriter getThis() {
		return this;
	}

	public long write(Connection con, boolean isReturnKey, String sql) {
		Query query = createQuery(con, sql);
		query.executeUpdate();
		if (isReturnKey) {
			return con.getKey(long.class);
		}
		return Long.MIN_VALUE;
	}

	private long write(boolean isReturnKey, String sql) {
		try (Connection con= getSql2oConnection()) {
			return write(con, isReturnKey, sql);
		}
	}

	public void write(String sql) {
		write(false, sql);
	}

	public long writeAndGetId(String sql) {
		return write(true, sql);
	}

	public void writeNamedQuery(String queryName) {
		write(getQueryByName(queryName));
	}

	public long writeNamedQueryAndGetId(String queryName ) {
		return writeAndGetId(getQueryByName(queryName));
	}

	public void writeFromFile(String fileName) {
		Iterable<String> queries = readQueriesFromFile(fileName);
		try (Connection con= getSql2oConnection()) {
			for (String sql: queries) {
				write(con, false, sql);
			}
		}
	}

	private Iterable<String> readQueriesFromFile(String fileName) {
		String text = resourceTools.readAsString(fileName);
		return Splitter.on(";\n").trimResults().omitEmptyStrings().split(text);
	}

}
