package com.tek271.util2.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Map;
import java.util.function.Supplier;

public class DbConnection {
	private static final Logger LOGGER = LogManager.getLogger(DbConnection.class);
	String url, user, password;
	Connection sql2oConnection;
	boolean isLocal = true;

	public DbConnection url(String url) {
		this.url = url;
		return this;
	}

	public DbConnection user(String user) {
		this.user = user;
		return this;
	}

	public DbConnection password(String password) {
		this.password = password;
		return this;
	}

	public DbConnection params(String url, String user, String password) {
		return url(url).user(user).password(password);
	}

	public DbConnection params(Map<String, String> map) {
		return params(map.get("url"), map.get("user"), map.get("password"));
	}

	public DbConnection with(Connection sql2oConnection) {
		isLocal = sql2oConnection == null;
		this.sql2oConnection = sql2oConnection;
		return this;
	}

	public DbConnection with(Supplier<Connection> connectionSupplier) {
		return with(connectionSupplier.get());
	}

	private Sql2o sql2o() {
		LOGGER.debug("Connecting to " + url + " for user=" + user);
		return new Sql2o(url, user, password);
	}

	public Connection getSql2oConnection() {
		if (sql2oConnection != null) {
			return sql2oConnection;
		}
		return sql2o().open();
	}

	public DbConnection closeIfCreated() {
		if (isLocal && sql2oConnection != null) {
			sql2oConnection.close();
		}
		return this;
	}

	public Connection transaction() {
		return sql2o().beginTransaction();
	}


}
