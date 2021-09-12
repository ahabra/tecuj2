package com.tek271.util2.http;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class Url {
	static final String Q = "?";

	public String base;
	public RequestParams requestParams = new RequestParams();

	public Url parse(String text) {
		base = substringBefore(text, Q);
		requestParams.parse(substringAfter(text, Q));
		return this;
	}

	public Url loggingExcludedParams(String... excluded) {
		requestParams.loggingExcludedParams(excluded);
		return this;
	}

	@Override
	public String toString() {
		return base + Q + requestParams.toString();
	}

	public String getText() {
		return base + Q + requestParams.getText();
	}

}
