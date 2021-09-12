package com.tek271.util2.math;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

// Algorithm taken (and enhanced) from
// http://stackoverflow.com/questions/4753251/how-to-go-about-formatting-1200-to-1-2k-in-java
public enum HumanNumber {
	standard(true, "Thousand", "Million", "Billion", "Trillion", "Quadrillion", "Quintillion"),
	binary(true, "Kilo", "Mega", "Giga", "Tera", "Peta", "Exa"),
	binaryShort(false, "K", "M", "G", "T", "P", "E");

	private final NavigableMap<Long, String> suffixMap;

	HumanNumber(boolean isSpace, String... names) {
		if (names.length != 6) {
			throw new IllegalArgumentException("Number of names must be exactly 6.");
		}
		this.suffixMap = createSuffixes(isSpace, names);
	}

	private static NavigableMap<Long, String> createSuffixes(boolean isSpace, String[] names) {
		String space = isSpace? " " : "";
		NavigableMap<Long, String> map = new TreeMap<>();
		long mul = 1;
		for (String name : names) {
			mul *= 1000;
			map.put(mul, space + name);
		}
		return map;
	}

	public String format(long value) {
		if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
		if (value < 0) return "-" + format(-value);
		if (value < 1000) return Long.toString(value); //deal with easy case

		Map.Entry<Long, String> e = suffixMap.floorEntry(value);
		long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy/10); //the number part of the output times 10
		boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}

}
