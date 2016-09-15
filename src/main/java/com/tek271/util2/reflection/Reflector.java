package com.tek271.util2.reflection;

public class Reflector<T> {
	public final FieldReflector<T> fieldReflector;
	public final MethodReflector<T> methodReflector;
	public final PropertyReflector<T> propertyReflector;


	public Reflector(T obj) {
		fieldReflector = new FieldReflector<>(obj);
		methodReflector = new MethodReflector<>(obj);
		propertyReflector = new PropertyReflector<>(fieldReflector, methodReflector);
	}


}
