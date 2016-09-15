package com.tek271.util2.reflection;

import org.joor.Reflect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static org.joor.Reflect.on;

public class MethodReflector<T> {
	private final T obj;
	private final Class<?> objClass;
	private final Reflect reflect;
	private boolean excludeObjectMethods;
	private boolean excludeInheritedMethods;
	private final Set<ScopeEnum> excludedScopes = new HashSet<>();

	public MethodReflector(T obj) {
		this(obj, on(obj));
	}

	private MethodReflector(T obj, Reflect reflect) {
		this.obj = obj;
		this.reflect = reflect;
		this.objClass = obj.getClass();
	}

	public <R> R callFunction(String methodName, Object... args) {
		return reflect.call(methodName, args).get();
	}

	public void callVoid(String methodName, Object... args) {
		reflect.call(methodName, args);
	}

	public MethodReflector<T> excludeObjectMethods(boolean exclude) {
		this.excludeObjectMethods = exclude;
		return this;
	}

	public MethodReflector<T> excludeObjectMethods() {
		return excludeObjectMethods(true);
	}

	public MethodReflector<T> excludeInheritedMethods(boolean exclude) {
		this.excludeInheritedMethods = exclude;
		return this;
	}

	public MethodReflector<T> excludeInheritedMethods() {
		return excludeInheritedMethods(true);
	}

	public MethodReflector<T> excludeScope(Set<ScopeEnum> scopes) {
		excludedScopes.addAll(scopes);
		return this;
	}

	public MethodReflector<T> excludeScope(ScopeEnum... scopes) {
		return excludeScope(newHashSet(scopes));
	}

	private Set<Method> getAllMethods() {
		Class<?> cls = obj.getClass();
		Set<Method> methods = newHashSet(cls.getDeclaredMethods());
		if (!excludeInheritedMethods) {
			methods.addAll(newHashSet(cls.getMethods()));
		}
		return methods;
	}

	private boolean isExcluded(Method method) {
		if (excludedScopes.contains(ScopeEnum.scopeOf(method))) return true;
		if (excludeInheritedMethods && isInherited(method)) return true;
		//noinspection RedundantIfStatement
		if (excludeObjectMethods && isObjectMethod(method)) return true;
		return false;
	}

	private boolean isIncluded(Method method) {
		return !isExcluded(method);
	}

	private boolean isInherited(Method method) {
		return !method.getDeclaringClass().equals(objClass);
	}

	private boolean isObjectMethod(Method method) {
		return method.getDeclaringClass().equals(Object.class);
	}

	public Set<Method> getMethods() {
		return getAllMethods().stream().filter(this::isIncluded).collect(Collectors.toSet());
	}



}
