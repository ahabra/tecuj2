package com.tek271.util2.string;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class StringSearcher {
	private boolean ignoreCase;
	private String text;

	public StringSearcher text(String text) {
		this.text = text;
		return this;
	}

	public StringSearcher ignoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}

	public StringSearcher ignoreCase() {
		return ignoreCase(true);
	}

	public boolean contains(String target) {
		if (ignoreCase) {
			return StringUtils.containsIgnoreCase(text, target);
		}
		return text.contains(target);
	}

	public boolean containsAny(Iterable<String> targets) {
		for (String t: targets) {
			if (contains(t)) return true;
		}
		return false;
	}

	public boolean containsAny(String... targets) {
		Set<String> set = Sets.newHashSet(targets);
		return containsAny(set);
	}


}
