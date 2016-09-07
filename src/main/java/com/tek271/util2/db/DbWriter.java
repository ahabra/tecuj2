package com.tek271.util2.db;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.tek271.util2.files.ResourceTools;
import org.sql2o.Connection;
import org.sql2o.Query;

public class DbWriter extends DbAccessor<DbWriter> {
	@VisibleForTesting
	ResourceTools resourceTools = new ResourceTools();

	protected DbWriter getThis() {
		return this;
	}

	public long write(String sql, boolean isReturnKey, Connection con) {
		Query query = createQuery(con, sql);
		query.executeUpdate();
		if (isReturnKey) {
			return con.getKey(long.class);
		}
		return Long.MIN_VALUE;
	}

	public long write(String sql, boolean isReturnKey) {
		try (Connection con= getSql2oConnection()) {
			return write(sql, isReturnKey, con);
		}
	}

	public void write(String sql) {
		write(sql, false);
	}

	public long writeNamedQuery(String queryName, boolean isReturnKey) {
		return write(getQueryByName(queryName), isReturnKey);
	}

	public void writeNamedQuery(String queryName) {
		writeNamedQuery(queryName, false);
	}

	public void writeFromFile(String fileName) {
		Iterable<String> queries = readQueriesFromFile(fileName);
		try (Connection con= getSql2oConnection()) {
			for (String sql: queries) {
				write(sql, false, con);
			}
		}
	}

	private Iterable<String> readQueriesFromFile(String fileName) {
		String text = resourceTools.readAsString(fileName);
		return Splitter.on(";\n").trimResults().omitEmptyStrings().split(text);
	}

}
