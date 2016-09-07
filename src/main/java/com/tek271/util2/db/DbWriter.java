package com.tek271.util2.db;

import com.google.common.base.Splitter;
import com.tek271.util2.files.ResourceTools;
import org.sql2o.Connection;
import org.sql2o.Query;

public class DbWriter extends DbAccessor<DbWriter> {
	private ResourceTools resourceTools = new ResourceTools();
	private boolean isReturnKey;

	protected DbWriter getThis() {
		return this;
	}

	public DbWriter returnKeyAfterWrite(boolean isReturnKey) {
		this.isReturnKey = isReturnKey;
		return this;
	}

	public long write() {
		return isExternalConnection? write(connection) : writeAndClose();
	}

	private long write(Connection con) {
		Query query = createQuery(con);
		query.executeUpdate();
		if (isReturnKey) {
			return con.getKey(long.class);
		}
		return Long.MIN_VALUE;
	}

	private long writeAndClose() {
		try (Connection con= getSql2oConnection()) {
			return write(con);
		}
	}

	public void writeFromFile(String fileName) {
		Iterable<String> queries = readQueriesFromFile(fileName);
		returnKeyAfterWrite(false);
		try (Connection con= getSql2oConnection()) {
			withConnection(con);
			for (String sql: queries) {
				sql(sql).write();
			}
		}
	}

	private Iterable<String> readQueriesFromFile(String fileName) {
		String text = resourceTools.readAsString(fileName);
		return Splitter.on(";\n").trimResults().omitEmptyStrings().split(text);
	}

}
