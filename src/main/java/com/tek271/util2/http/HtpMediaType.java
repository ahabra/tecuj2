package com.tek271.util2.http;

public enum HtpMediaType {
	textPlain("text/plain"),
	textHtml("text/html");

	public final String text;

	HtpMediaType(String text) { this.text = text; }

}
