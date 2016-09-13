package com.tek271.util2.time;

import java.time.format.DateTimeFormatter;

public enum TimeFormats {
	US_DATE("MM/dd/YYYY"),
	US_DATETIME("MM/dd/YYYY HH:mm:ss"),
	TS_SECONDS("yyyy.MM.dd-HH.mm.ss"),
	TS_NANOS("yyyy.MM.dd-HH.mm.ss.nnnnnnnnn");


	public final String pattern;
	public final DateTimeFormatter formatter;

	TimeFormats(String pattern) {
		this.pattern = pattern;
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

}
