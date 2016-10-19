package com.tek271.util2.db;

import com.google.common.base.Splitter;
import org.sql2o.Connection;
import org.sql2o.Query;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DbWriter extends DbAccessor<DbWriter> {
	private static final long NO_KEY = Long.MIN_VALUE;
	private boolean isReturnKey;

	protected DbWriter getThis() {
		return this;
	}

	public DbWriter returnKeyAfterWrite(boolean isReturnKey) {
		this.isReturnKey = isReturnKey;
		return this;
	}

	public long write() {
		if (isNotBlank(script)) {
			writeScript();
			return NO_KEY;
		}
		return dbConnection.isLocal? writeAndClose() : write(dbConnection.sql2oConnection);
	}

	private long write(Connection con) {
		Query query = createQuery(con);
		query.executeUpdate();
		if (isReturnKey) {
			return con.getKey(long.class);
		}
		return NO_KEY;
	}

	private long writeAndClose() {
		try (Connection con= dbConnection.getSql2oConnection()) {
			return write(con);
		}
	}

	private void writeScript() {
		Iterable<String> queries = Splitter.on(";\n").trimResults().omitEmptyStrings().split(script);
		DbWriter dbWriter = new DbWriter().withDbConnection(dbConnection);

		try {
			for (String sql: queries) {
				dbWriter.sql(sql).write();
			}
		} finally {
			dbConnection.closeIfCreated();
		}
	}

}
