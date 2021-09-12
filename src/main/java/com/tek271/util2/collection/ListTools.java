package com.tek271.util2.collection;

import java.util.List;

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

}
