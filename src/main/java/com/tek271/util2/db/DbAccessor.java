package com.tek271.util2.db;

import com.tek271.util2.file.ResourceTools;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

public abstract class DbAccessor<T extends DbAccessor> {
	protected DbQueries queryCache = DbQueries.QUERY_CACHE;

	protected String url, user, password;
	protected final Map<String, Object> parameters = new HashMap<>();
	protected String sql;
	protected String script;
	protected Connection connection;
	protected boolean isExternalConnection = false;

	protected ResourceTools resourceTools = new ResourceTools();

	private T thisObj; // used to simplify the builder pattern
	protected abstract T getThis();

	public DbAccessor() {
		thisObj = getThis();
	}

	public T withConnection(Connection connection) {
		isExternalConnection = connection != null;
		this.connection = connection;
		return thisObj;
	}

	public T url(String url) {
		this.url = url;
		return thisObj;
	}

	public T user(String user) {
		this.user = user;
		return thisObj;
	}

	public T password(String password) {
		this.password = password;
		return thisObj;
	}

	public T param(String name, Object value) {
		parameters.put(name, value);
		return thisObj;
	}

	public T params(Map<String, Object> params) {
		parameters.putAll(params);
		return thisObj;
	}

	public T sql(String sql) {
		this.sql = sql;
		this.script = null;
		return thisObj;
	}

	public T sqlFromCachedQuery(String queryName) {
		return sql(getCachedQueryByName(queryName));
	}

	public T script(String script) {
		this.script = script;
		this.sql = null;
		return thisObj;
	}

	public T scriptFromFile(String fileName) {
		String text = resourceTools.readAsString(fileName);
		return script(text);
	}

	protected Query createQuery(Connection con) {
		Query query = con.createQuery(sql);
		if (parameters != null) {
			parameters.forEach(query::addParameter);
		}
		return query;
	}

	protected String getCachedQueryByName(String queryName) {
		return queryCache.get(queryName);
	}

	private Sql2o sql2o() {
		return new Sql2o(url, user, password);
	}

	public Connection getSql2oConnection() {
		if (connection != null) {
			return connection;
		}
		return sql2o().open();
	}

	public Connection transaction() {
		return sql2o().beginTransaction();
	}

}
