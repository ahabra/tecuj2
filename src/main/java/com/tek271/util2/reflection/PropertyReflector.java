package com.tek271.util2.reflection;

import com.google.common.base.Splitter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.Character.toUpperCase;
import static org.apache.commons.lang3.StringUtils.substring;

public class PropertyReflector<T> {
	private FieldReflector<T> fieldReflector;
	private MethodReflector<T> methodReflector;

	public PropertyReflector(FieldReflector<T> fieldReflector, MethodReflector<T> methodReflector) {
		this.fieldReflector = fieldReflector;
		this.methodReflector = methodReflector;
	}

	public PropertyReflector(T obj) {
		this(new FieldReflector<>(obj), new MethodReflector<>(obj));
	}

	@SuppressWarnings("unchecked")
	public <R> R get(String propertyName) {
		if (!propertyName.contains(".")) return getSimpleProperty(propertyName);

		List<String> parts = Splitter.on('.').splitToList(propertyName);
		PropertyReflector<?> reflector = this;
		Object result = null;
		for (int i=0, n=parts.size(); i<n; i++) {
			result = reflector.getSimpleProperty(parts.get(i));
			if (result == null) break;
			if (i<n-1) {
				reflector = new PropertyReflector<>(result);
			}
		}
		return (R) result;
	}

	<R> R getSimpleProperty(String propertyName) {
		if (fieldReflector.getFields().contains(propertyName)) {
			return fieldReflector.getFieldValue(propertyName);
		}
		return getFromMethod(propertyName);
	}

	private <R> R getFromMethod(String propertyName) {
		Set<Method> methods = methodReflector.getMethods();
		String getterName = buildGetter(propertyName);
		if (findGetter(methods, getterName).isPresent()) {
			return methodReflector.callFunction(getterName);
		}
		if (findMethod(methods, propertyName).isPresent()) {
			return methodReflector.callFunction(propertyName);
		}

		return null;
	}

	private Optional<Method> findGetter(Set<Method> methods, String getterName) {
		return methods.stream()
				.filter(m -> isGetter(m, getterName))
				.findFirst();
	}

	private boolean isGetter(Method method, String getterName) {
		return method.getName().equals(getterName)
				&& method.getParameterCount() == 0
				&& !method.getReturnType().equals(Void.TYPE);
	}

	private String buildGetter(String propertyName) {
		return "get" + toUpperCase(propertyName.charAt(0)) + substring(propertyName, 1);
	}

	private Optional<Method> findMethod(Set<Method> methods, String name) {
		return methods.stream()
				.filter(m -> isGetter(m, name))
				.findFirst();
	}

}
