package com.tek271.util2.reflection;

import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public class FieldReflector<T> {
	private final T obj;
	private final Set<String> excludedFieldNames = new HashSet<>();
	private final Set<ScopeEnum> excludedScopes = new HashSet<>();


	public FieldReflector(T obj) {
		this.obj = obj;
	}

	@SuppressWarnings("unchecked")
	public <V> V getFieldValue(Field field) {
		boolean accessChanged = false;
		if (! field.isAccessible()) {
			field.setAccessible(true);
			accessChanged = true;
		}
		try {
			return (V) field.get(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(fieldAccessError(field.getName()), e);
		} finally {
			if (accessChanged) {
				field.setAccessible(false);
			}
		}
	}

	public <V> V getFieldValue(String fieldName) {
		Field field = findField(fieldName);
		return getFieldValue(field);
	}

	public Field findField(String fieldName) {
		try {
			return obj.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(fieldAccessError(fieldName), e);
		}
	}

	private String fieldAccessError(String fieldName) {
		return "Cannot access field " + fieldName +	" in " + obj.getClass().getName();
	}

	public FieldReflector<T> excludeScope(Set<ScopeEnum> scopes) {
		excludedScopes.addAll(scopes);
		return this;
	}

	public FieldReflector<T> excludeScope(ScopeEnum... scopes) {
		return excludeScope(newHashSet(scopes));
	}

	public FieldReflector<T> excludeField(Set<String> fieldNames) {
		excludedFieldNames.addAll(fieldNames);
		return this;
	}

	public FieldReflector<T> excludeField(String... fieldNames) {
		excludeField(newHashSet(fieldNames));
		return this;
	}

	private boolean isExcluded(Field field) {
		if (excludedFieldNames.contains(field.getName())) return true;

		ScopeEnum scope = ScopeEnum.scopeOf(field);
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

	@SuppressWarnings("unchecked")
	public Pair<String, Object>[] toPairsArray() {
		List<Pair<String, Object>> list = toPairs();
		return list.toArray( new Pair[list.size()] );
	}

	public List<String> getFields() {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> names= new ArrayList<>();
		for (Field f: fields) {
			names.add(f.getName());
		}
		return names;
	}

}
