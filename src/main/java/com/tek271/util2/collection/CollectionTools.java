package com.tek271.util2.collection;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

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

}
