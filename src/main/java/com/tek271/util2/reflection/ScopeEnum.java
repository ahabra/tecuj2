package com.tek271.util2.reflection;

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

	public static ScopeEnum find(int modifier) {
		for (ScopeEnum s: ScopeEnum.values()) {
			if ((modifier & s.modifier) == s.modifier) return s;
		}
		return PACKAGE;
	}

}
