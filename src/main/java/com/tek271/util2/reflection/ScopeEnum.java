package com.tek271.util2.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public enum ScopeEnum {
	PRIVATE(Modifier.PRIVATE),
	PROTECTED(Modifier.PROTECTED),
	PUBLIC(Modifier.PUBLIC),
	PACKAGE(0);


	public final int modifier;

	ScopeEnum(int modifier) {
		this.modifier = modifier;
	}

	private static ScopeEnum scopeOf(int modifier) {
		for (ScopeEnum s: ScopeEnum.values()) {
			if ((modifier & s.modifier) == s.modifier) return s;
		}
		return PACKAGE;
	}

	public static ScopeEnum scopeOf(Field field) {
		return scopeOf(field.getModifiers());
	}

	public static ScopeEnum scopeOf(Method method) {
		return scopeOf(method.getModifiers());
	}

}
