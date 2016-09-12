package com.tek271.util2.string;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class EscapeTools {

	public String escapeUrl(String s) {
		if (StringUtils.isBlank(s)) return "";
		try {
			return URLEncoder.encode(s, UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not escape: " + s, e);
		}
	}

	public String unescapeUrl(String s) {
		if (StringUtils.isBlank(s)) return "";
		try {
			return URLDecoder.decode(s, UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not unescape: " + s, e);
		}
	}

}
