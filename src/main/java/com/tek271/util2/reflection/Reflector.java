package com.tek271.util2.reflection;

public class Reflector<T> {
	public final FieldReflector<T> fieldReflector;
	public final MethodReflector methodReflector;


	public Reflector(T obj) {
		fieldReflector = new FieldReflector<>(obj);
		methodReflector = new MethodReflector<>(obj);
	}


}
