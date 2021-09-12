package com.tek271.util2.string;

import static org.apache.commons.lang3.StringUtils.left;

public class StringTools {

	/** Replace text between <code>start</code> and <code>end</code> */
	public String replaceBetween(String text, String start, String end, String with) {
		with = with==null? "" : with;
		int i = text.indexOf(start);
		if (i < 0) return text;

		StringBuilder sb = new StringBuilder();
		sb.append(left(text, i+start.length())).append(with);

		i= text.indexOf(end, i);
		if (i<0) return sb.toString();
		sb.append(text.substring(i));

		return sb.toString();
	}

	/** Clear text between <code>start</code> and <code>end</code> */
	public String clearBetween(String text, String start, String end) {
		return replaceBetween(text, start, end, null);
	}

}
