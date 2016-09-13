package com.tek271.util2.reflection;

import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public class Reflector<T> {
	private final T obj;
	private final FieldReflector<T> fieldReflector;
	private final Set<ScopeEnum> excludedScopes = new HashSet<>();

	public Reflector(T obj) {
		this.obj = obj;
		fieldReflector = new FieldReflector<>(obj);
	}

	public <V> V getFieldValue(Field field) {
		return fieldReflector.getFieldValue(field);
	}

	public <V> V getFieldValue(String fieldName) {
		return fieldReflector.getFieldValue(fieldName);
	}

	public Reflector<T> excludeScope(Set<ScopeEnum> scopes) {
		excludedScopes.addAll(scopes);
		return this;
	}

	public Reflector<T> excludeScope(ScopeEnum... scopes) {
		return excludeScope(newHashSet(scopes));
	}

	public Reflector<T> excludeField(Set<String> fieldNames) {
		fieldReflector.excludeField(fieldNames);
		return this;
	}

	public Reflector<T> excludeField(String... fieldNames) {
		return excludeField(newHashSet(fieldNames));
	}

	private boolean isExcluded(Field field) {
		if (fieldReflector.isExcluded(field)) return true;

		ScopeEnum scope = fieldReflector.scope(field);
		return excludedScopes.contains(scope);
	}

	public Map<String, Object> toMap() {
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, Object> map = new LinkedHashMap<>();
		for (Field f: fields) {
			if (! isExcluded(f)) {
				map.put(f.getName(), getFieldValue(f));
			}
		}
		return map;
	}

	public List<Pair<String, Object>> toPairs() {
		Map<String, Object> map = toMap();
		return map.entrySet().stream()
				.map(e -> Pair.of(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	public Pair<String, Object>[] toPairsArray() {
		List<Pair<String, Object>> list = toPairs();
		//noinspection unchecked
		return list.toArray( new Pair[list.size()] );
	}

}
