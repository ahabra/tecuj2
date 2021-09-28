package com.tek271.util2.collection;

import com.tek271.util2.reflection.PropertyReflector;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/** Useful collection related functions */
public class CollectionTools {

	/** Copy a collection to another of the same type */
	public <T, C extends Collection<T>> C copy(C from, C to) {
		if (from == null) return null;
		to.clear();
		to.addAll(from);
		return to;
	}

	public <T, C extends Collection<T>> C copy(C from) {
		if (from == null) return null;
		if (from instanceof Cloneable) {
			return ObjectUtils.clone(from);
		}

		C r = newSetOrList(from);
		r.addAll(from);
		return r;
	}

	@SuppressWarnings("unchecked")
	private <T, C extends Collection<T>> C newSetOrList(C c) {
		if (c instanceof Set) {
			return (C) newHashSet();
		}
		return (C) newArrayList();
	}

	public <T> boolean contains(Collection<T> col, T target, BiPredicate<T,T> matcher) {
		if (matcher==null) {
			return col.contains(target);
		}

		for (T c: col) {
			if (matcher.test(c, target)) return true;
		}
		return false;
	}

	public <T> boolean contains(Collection<T> col, Predicate<T> matcher) {
		for (T c: col) {
			if (matcher.test(c)) return true;
		}
		return false;
	}

	public <T> boolean isEmpty(Collection<T> c) {
		return c==null || c.isEmpty();
	}

	public <K, V> Map<K, V> toMapByKey(String key, Collection<V> col) {
		if (StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("The argument 'key' cannot be [" + key + "].");
		}
		final String keyName = key.trim();
		Map<K, V> map = new LinkedHashMap<>();
		if (isEmpty(col)) return map;
		for (V v: col) {
			K k = new PropertyReflector<>(v).get(keyName);
			map.put(k, v);
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> toMapByKey(String key, V... col) {
		return toMapByKey(key, newArrayList(col));
	}

}
