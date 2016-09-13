package com.tek271.util2.time;

import com.tek271.util2.string.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.LocalDateTime.ofInstant;

public class Time {
	public final int year;
	public final int month;
	public final int day;
	public final int hour;
	public final int minute;
	public final int second;
	public final int nano;
	public final LocalDateTime localDateTime;


	public Time(LocalDateTime d) {
		localDateTime = d;
		year = d.getYear();
		month = d.getMonthValue();
		day = d.getDayOfMonth();
		hour = d.getHour();
		minute = d.getMinute();
		second = d.getSecond();
		nano = d.getNano();
	}

	/**
	 * Create a Time object
	 * @param year the year
	 * @param month 1 to 12
	 * @param day 1 to 31
	 * @param hour 0 to 23
	 * @param minute 0 to 59
	 * @param second 0 to 59
	 * @param nano 0 to 999,999,999
	 */
	public Time(int year, int month, int day, int hour, int minute, int second, int nano) {
		this(LocalDateTime.of(year, month, day, hour, minute, second, nano));
	}

	public Time(int year, int month, int day) {
		this(year, month, day, 0, 0, 0, 0);
	}

	public Time(LocalDate d) {
		this(d.atStartOfDay());
	}

	public Time(Date date, ZoneId zoneId) {
		this(ofInstant(date.toInstant(), zoneId));
	}

	public Time(Date date) {
		this(date, ZoneId.systemDefault());
	}

	public static Time now() {
		return new Time(LocalDateTime.now());
	}

	public static Time today() {
		return new Time(LocalDate.now());
	}

	public Date toDate(ZoneId zoneId) {
		Instant instant = localDateTime.atZone(zoneId).toInstant();
		return Date.from(instant);
	}

	public Date toDate() {
		return toDate(ZoneId.systemDefault());
	}

	@Override
	public String toString() {
		return new ToString().exclude("localDateTime").toString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Time) && isEquals((Time) obj, "localDateTime");
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "localDateTime");
	}

	public boolean isEquals(Time another, String... excludedFields) {
		return EqualsBuilder.reflectionEquals(this, another, excludedFields);
	}

	public long toEpochMilli(ZoneId zoneId) {
		return ZonedDateTime.of(localDateTime, zoneId).toInstant().toEpochMilli();
	}

	public long toEpochMilli() {
		return toEpochMilli(ZoneId.systemDefault());
	}

	public boolean isSameDay(Time time) {
		return year==time.year && month==time.month && day==time.day;
	}

	public boolean isToday() {
		return isSameDay(now());
	}

	public static Time parse(String text, String pattern) {
		Date date;
		try {
			date = DateUtils.parseDate(text, pattern);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new Time(date);
	}

	public String format(DateTimeFormatter formatter) {
		return localDateTime.format(formatter);
	}

	public String format(String pattern) {
		return format(DateTimeFormatter.ofPattern(pattern));
	}


}
