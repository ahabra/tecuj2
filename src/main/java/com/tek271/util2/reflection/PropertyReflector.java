package com.tek271.util2.reflection;

public class PropertyReflector<T> {
	private FieldReflector<T> fieldReflector;
	private MethodReflector<T> methodReflector;

	public PropertyReflector(FieldReflector<T> fieldReflector, MethodReflector<T> methodReflector) {
		this.fieldReflector = fieldReflector;
		this.methodReflector = methodReflector;
	}

	public <R> R get(String propertyName) {
		return null;
	}

}
