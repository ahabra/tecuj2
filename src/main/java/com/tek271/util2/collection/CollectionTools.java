package com.tek271.util2.collection;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CollectionTools {

	public <T, C extends Collection<T>> C copy(C from, C to) {
		if (from == null) return null;
		to.clear();
		to.addAll(from);
		return to;
	}

	public <T, C extends Collection<T>> C copy(C from) {
		return ObjectUtils.clone(from);
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

}
