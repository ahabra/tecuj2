package com.tek271.util2.reflection;

import org.joor.Reflect;

import static org.joor.Reflect.*;

public class MethodReflector<OBJ> {
	private final Reflect reflect;

	public MethodReflector(OBJ obj) {
		reflect = on(obj);
	}

	public <R> R callFunction(String methodName, Object... args) {
		return reflect.call(methodName, args).get();
	}

	public void callVoid(String methodName, Object... args) {
		reflect.call(methodName, args);
	}

}
