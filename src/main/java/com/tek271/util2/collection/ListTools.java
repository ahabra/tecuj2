package com.tek271.util2.collection;

import java.util.List;
import java.util.function.Predicate;

public class ListTools {

	public <T> T getLast(List<T> list) {
		return list.get(list.size()-1);
	}

	public <T> T setLast(List<T> list, T value) {
		return list.set(list.size()-1, value);
	}

	public <T> List<T> slice(List<T> source, int startIndex, int count) {
		count = Math.max(0, count);
		int toIndex = Math.min(startIndex + count, source.size());
		return source.subList(startIndex, toIndex);
	}

	public <T> List<T> subList(List<T> source, int fromIndex) {
		fromIndex = Math.max(0, fromIndex);
		fromIndex = Math.min(fromIndex, source.size());
		return source.subList(fromIndex, source.size());
	}

	public <T> List<T> left(List<T> source, int count) {
		return slice(source, 0, count);
	}

	public <T> List<T> right(List<T> source, int count) {
		return subList(source, source.size()-count);
	}

	public <T> int indexOf(List<T> list, Predicate<T> matcher) {
		for (int i=0, n=list.size(); i<n; i++) {
			T item = list.get(i);
			if (matcher.test(item)) return i;
		}
		return -1;
	}

	public <T> int lastIndexOf(List<T> list, Predicate<T> matcher) {
		for (int i=list.size()-1; i>=0; i--) {
			T item = list.get(i);
			if (matcher.test(item)) return i;
		}
		return -1;
	}

}
