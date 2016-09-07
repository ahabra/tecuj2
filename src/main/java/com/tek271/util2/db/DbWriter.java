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
		return isExternalConnection? write(connection) : writeAndClose();
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
		try (Connection con= getSql2oConnection()) {
			return write(con);
		}
	}

	private void writeScript() {
		Iterable<String> queries = Splitter.on(";\n").trimResults().omitEmptyStrings().split(script);
		Connection con = getSql2oConnection();
		DbWriter dbWriter = new DbWriter().withConnection(con);

		try {
			for (String sql: queries) {
				dbWriter.sql(sql).write();
			}
		} finally {
			if (!isExternalConnection) con.close();
		}
	}

}
