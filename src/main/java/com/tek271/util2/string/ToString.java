package com.tek271.util2.string;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

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
			//noinspection SimplifiableIfStatement
			if (includedFields.isEmpty()) return true;
			return includedFields.contains(name);
		}
	}

	private ToStringStyle style = ToStringStyle.SHORT_PREFIX_STYLE;
	private final Set<String> includedFields = new HashSet<>();
	private final Set<String> excludedFields = new HashSet<>();
	private CharSequence collectionSeparator= ", ";
	private CharSequence collectionPrefix= "[";
	private CharSequence collectionSuffix= "]";

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

	public ToString collectionSeparator(CharSequence sep) {
		collectionSeparator = sep;
		return this;
	}

	public ToString collectionPrefix(CharSequence prefix) {
		collectionPrefix = prefix;
		return this;
	}
	public ToString collectionSuffix(CharSequence suffix) {
		collectionSuffix = suffix;
		return this;
	}

	public String toString(Object obj) {
		return new FieldFilter(obj, style, includedFields, excludedFields).toString();
	}

	public <T> String toString(Iterable<T> iterable) {
		StringJoiner sj = new StringJoiner(collectionSeparator, collectionPrefix, collectionSuffix);
		iterable.forEach(i -> sj.add( toString(i) ));
		return sj.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> String toString(T... items) {
		return toString(Lists.newArrayList(items) );
	}

}
