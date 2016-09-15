package com.tek271.util2.reflection;

import org.joor.Reflect;

import static org.joor.Reflect.on;

public class Reflector<T> {
	public final FieldReflector<T> fieldReflector;
	public final MethodReflector methodReflector;


	public Reflector(T obj) {
		fieldReflector = new FieldReflector<>(obj);
		Reflect reflect = on(obj);
		methodReflector = new MethodReflector<>(obj, reflect);
	}


}
