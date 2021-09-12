package com.tek271.util2.collection;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.emptyList;

public class ListSearcher<T> {
	private final List<T> source;
	private final int sourceSize;
	private int startIndex;
	private int maxIndex;
	private BiPredicate<T,T> matcher;
	private CollectionTools collectionTools = new CollectionTools();

	public ListSearcher(List<T> source) {
		this.source = source;
		this.sourceSize = source.size();
		this.maxIndex = sourceSize;
	}

	public ListSearcher<T> startIndex(int startIndex) {
		this.startIndex = putInRange(startIndex);
		return this;
	}

	public ListSearcher<T> maxIndex(int maxIndex) {
		this.maxIndex = putInRange(maxIndex);
		return this;
	}

	public ListSearcher<T> reset() {
		startIndex = 0;
		maxIndex = sourceSize;
		matcher = null;
		return this;
	}

	private int putInRange(int i) {
		return min(max(i, 0), sourceSize);
	}

	public ListSearcher<T> matcher(BiPredicate<T,T> matcher) {
		this.matcher = matcher;
		return this;
	}

	public ListSearcher<T> equalsMatcher() {
		return matcher(Objects::equals);
	}

	public List<T> subList() {
		if (maxIndex < startIndex) return emptyList();
		return source.subList(startIndex, maxIndex);
	}

	public int indexOf(T target) {
		for (int i=startIndex; i<maxIndex; i++) {
			if (eq(source.get(i), target)) return i;
		}
		return -1;
	}

	public int lastIndexOf(T target) {
		for (int i=maxIndex-1; i>=startIndex; i--) {
			if (eq(source.get(i), target)) return i;
		}
		return -1;
	}

	public int indexOfAny(Collection<T> targets) {
		for (int i=startIndex; i<maxIndex; i++) {
			if (contains(targets, source.get(i))) return i;
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

		int originalStartIndex = startIndex;
		try {
			for (int i=startIndex; i<sourceSize-targetSize+1; i++) {
				if (isPrefix(target)) {
					return i;
				}
				startIndex++;
			}
		} finally {
			startIndex = originalStartIndex;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public int indexOfSubList(T... target) {
		return indexOfSubList(newArrayList(target));
	}

	public boolean isPrefix(List<T> target) {
		if (collectionTools.isEmpty(target)) return false;

		List<T> sub = subList();
		if (target.size() > sub.size()) return false;
		for (int i=0, n=target.size(); i<n; i++) {
			if (!eq(sub.get(i), target.get(i))) return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isPrefix(T... target) {
		return isPrefix(newArrayList(target));
	}

	private boolean eq(T a, T b) {
		if (matcher==null) {
			return Objects.equals(a, b);
		}

		return matcher.test(a, b);
	}

	private boolean contains(Collection<T> col, T target) {
		return collectionTools.contains(col, target, matcher);
	}

}
