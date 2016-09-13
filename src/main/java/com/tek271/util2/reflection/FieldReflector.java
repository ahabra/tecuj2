package com.tek271.util2.reflection;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class FieldReflector<T> {
	private final T obj;
	private final Set<String> excludedFieldNames = new HashSet<>();

	public FieldReflector(T obj) {
		this.obj = obj;
	}

	public <V> V getFieldValue(Field field) {
		boolean accessChanged = false;
		if (! field.isAccessible()) {
			field.setAccessible(true);
			accessChanged = true;
		}
		try {
			//noinspection unchecked
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

	public FieldReflector<T> excludeField(Set<String> fieldNames) {
		excludedFieldNames.addAll(fieldNames);
		return this;
	}

	public boolean isExcluded(String fieldName) {
		return excludedFieldNames.contains(fieldName);
	}

}
