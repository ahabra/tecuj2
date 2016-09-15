package com.tek271.util2.reflection;

import org.joor.Reflect;

import static org.joor.Reflect.on;

public class MethodReflector<T> {
	private final Reflect reflect;

	public MethodReflector(Reflect reflect) {
		this.reflect = reflect;
	}

	public MethodReflector(T obj) {
		this(on(obj));
	}

	public <R> R callFunction(String methodName, Object... args) {
		return reflect.call(methodName, args).get();
	}

	public void callVoid(String methodName, Object... args) {
		reflect.call(methodName, args);
	}

}
