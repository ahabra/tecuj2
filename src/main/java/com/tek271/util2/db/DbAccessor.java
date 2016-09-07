package com.tek271.util2.db;

import com.tek271.util2.files.YamlTools;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

public abstract class DbAccessor<T extends DbAccessor> {
	String url;
	String user;
	String password;
	final Map<String, Object> parameters = new HashMap<>();
	Map<String, String> namedQueries;
	private T thisObj;

	protected abstract T getThis();

	public DbAccessor() {
		thisObj = getThis();
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

	public T namedQueries(Map<String, String> namedQueries) {
		this.namedQueries = namedQueries;
		return thisObj;
	}

	public T namedQueries(String yamlFileName) {
		YamlTools yamlTools = new YamlTools();
		Map<String, String> map = yamlTools.readFile(yamlFileName);
		return namedQueries(map);
	}

	public T param(String name, Object value) {
		parameters.put(name, value);
		return thisObj;
	}

	public T params(Map<String, Object> params) {
		parameters.putAll(params);
		return thisObj;
	}

	protected Query createQuery(Connection con, String sql) {
		Query query = con.createQuery(sql);
		if (parameters != null) {
			parameters.forEach(query::addParameter);
		}
		return query;
	}

	protected String getQueryByName(String queryName) {
		return namedQueries.get(queryName);
	}

	private Sql2o sql2o() {
		return new Sql2o(url, user, password);
	}

	public Connection getSql2oConnection() {
		return sql2o().open();
	}

	public Connection transaction() {
		return sql2o().beginTransaction();
	}

}
