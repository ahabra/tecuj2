package com.tek271.util2.collection;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ListOfPairs<K,V> extends ArrayList<Pair<K, V>> {

	public ListOfPairs<K,V> add(K k, V v) {
		add(Pair.of(k, v));
		return this;
	}

	public ListOfPairs<K,V> add(Map<K, V> map) {
		map.forEach(this::add);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Pair<K, V>[] toArray() {
		Pair<K, V>[] ar = new Pair[size()];
		return toArray(ar);
	}

	public Map<K, V> toMap() {
		Map<K, V> map = new LinkedHashMap<>();
		this.forEach((e -> map.put(e.getKey(), e.getValue())));
		return map;
	}

	public List<V> getValues(K k) {
		return stream()
				.filter(e-> e.getKey().equals(k))
				.map(Pair::getValue)
				.collect(Collectors.toList());
	}

	public K key(int i) {
		return get(i).getKey();
	}

	public V value(int i) {
		return get(i).getValue();
	}

	public ListOfPairs<K,V> forEach(BiConsumer<K,V> consumer) {
		forEach(e -> consumer.accept(e.getKey(), e.getValue()));
		return this;
	}


}
