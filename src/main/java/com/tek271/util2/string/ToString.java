package com.tek271.util2.string;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ToString {

	private static class FieldFilter extends ReflectionToStringBuilder {
		private final Set<String> includedFields;
		private final Set<String> excludedFields;

		public FieldFilter(Object obj, ToStringStyle style, Set<String> includedFields, Set<String> excludedFields) {
			super(obj, style);
			this.includedFields = includedFields;
			this.excludedFields = excludedFields;
		}

		protected boolean accept(Field f) {
			if (! super.accept(f)) return false;
			String name = f.getName();
			if (excludedFields.contains(name)) return false;
			if (includedFields.isEmpty()) return true;
			return includedFields.contains(name);
		}
	}

	private ToStringStyle style = ToStringStyle.SHORT_PREFIX_STYLE;
	private final Set<String> includedFields = new HashSet<>();
	private final Set<String> excludedFields = new HashSet<>();

	public ToString style(ToStringStyle style) {
		this.style = style;
		return this;
	}

	public ToString include(String... fields) {
		Collections.addAll(includedFields, fields);
		return this;
	}

	public ToString exclude(String... fields) {
		Collections.addAll(excludedFields, fields);
		return this;
	}

	public String reflectionToString(Object obj) {
		return new FieldFilter(obj, style, includedFields, excludedFields).toString();
	}

}
