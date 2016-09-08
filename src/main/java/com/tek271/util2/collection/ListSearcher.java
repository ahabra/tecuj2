package com.tek271.util2.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.emptyList;

public class ListSearcher<T> {
	private final List<T> source;
	private final int sourceSize;
	private int startIndex;
	private int maxIndex;
	private Predicate<T> matcher;

	public ListSearcher(List<T> source) {
		this.source = source;
		this.sourceSize = source.size();
		this.maxIndex = sourceSize;
		this.matcher = Predicate.isEqual(null);
	}

	public ListSearcher<T> startIndex(int startIndex) {
		this.startIndex = putInRange(startIndex);
		return this;
	}

	public ListSearcher<T> maxIndex(int maxIndex) {
		this.maxIndex = putInRange(maxIndex);
		return this;
	}

	public ListSearcher<T> resetIndex() {
		startIndex = 0;
		maxIndex = sourceSize;
		return this;
	}

	private int putInRange(int i) {
		return min(max(i, 0), sourceSize);
	}

	public ListSearcher<T> matcher(Predicate<T> matcher) {
		this.matcher = matcher;
		return this;
	}

	public List<T> subList() {
		if (maxIndex < startIndex) return emptyList();
		return source.subList(startIndex, maxIndex);
	}

	public int indexOfAny(Collection<T> targets) {
		for (int i=startIndex; i<maxIndex; i++) {
			if (targets.contains( source.get(i) )) return i;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public int indexOfAny(T... targets) {
		return indexOfAny(newArrayList(targets) );
	}

	public int indexOfSubList(List<T> target) {
		int targetSize = target.size();
		if (targetSize == 0 || targetSize>sourceSize) return -1;

		List<T> sub = subList();
		int i = Collections.indexOfSubList(sub, target);
		return i<0 ? i : i+startIndex;
	}

	public boolean isPrefix(List<T> target) {
		return indexOfSubList(target) == startIndex;
	}

}
