package com.tek271.util2.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {
	private Time sut;

	@BeforeEach
	public void setUp() {
		sut = new Time(2016, 1, 29, 13, 59, 58, 100000);
	}

	@Test
	public void constructorWithDateDataSetsTimeFieldsToZero() {
		sut = new Time(2016, 1, 30);
		assertEquals(0, sut.hour);
		assertEquals(0, sut.minute);
		assertEquals(0, sut.second);
		assertEquals(0, sut.nano);
	}

	@Test
	public void constructorWithLocalDateSetsTimeFieldsToZero() {
		sut = new Time(LocalDate.now());
		assertEquals(0, sut.hour);
		assertEquals(0, sut.minute);
		assertEquals(0, sut.second);
		assertEquals(0, sut.nano);
	}

	@Test
	public void testToDate() {
		Date date = sut.toDate();
		Time time = new Time(date);
		assertEquals(true, sut.isEqual(time, "localDateTime", "nano"));
	}

	@Test
	public void testToString() {
		String expected = "Time[year=2016,month=1,day=29,hour=13,minute=59,second=58,nano=100000]";
		assertEquals(expected, sut.toString());
	}

	@Test
	public void testEquals() {
		Time time = new Time(2016, 1, 29, 13, 59, 58, 100000);
		assertEquals(sut, time);
	}

	@Test
	public void testHashCode() {
		Time time = new Time(2016, 1, 29, 13, 59, 58, 100000);
		assertEquals(sut.hashCode(), time.hashCode());
	}

	@Test
	public void testToEpochMilli() {
		sut = new Time(1970, 1, 1, 0, 0, 1, 0);
		assertEquals(1000, sut.toEpochMilli(ZoneId.of("UTC")));
	}

	@Test
	public void toEpochMilliWithDefaultZone() {
		long c = System.currentTimeMillis();
		long milli = Time.now().toEpochMilli();
		assertTrue(milli >= c);
		assertTrue(milli-c < 1000);
	}

	@Test
	public void testIsSameDay() {
		assertTrue(sut.isSameDay(new Time(sut.year, sut.month, sut.day)));
		assertFalse(sut.isSameDay(new Time(sut.year, sut.month, sut.day+1)));
	}

	@Test
	public void testIsToday() {
		assertFalse(sut.isToday());
		assertTrue(Time.now().isToday());
	}

	@Test
	public void testParseDateAndTime() {
		Time time = Time.parse("2016/01/29-23.59.58", "yyyy/MM/dd-HH.mm.ss");
		assertEquals(2016, time.year);
		assertEquals(1, time.month);
		assertEquals(29, time.day);
	}

	@Test
	public void testParseDate() {
		Time time = Time.parse("2016/01/29", "yyyy/MM/dd");
		assertEquals(2016, time.year);
		assertEquals(1, time.month);
		assertEquals(29, time.day);
	}

	@Test
	public void testFormat() {
		assertEquals("2016.01.29", sut.format("yyyy.MM.dd"));
		assertEquals("2016.01.29-13.59.58.000100000", sut.format("yyyy.MM.dd-HH.mm.ss.nnnnnnnnn"));
	}

	@Test
	public void testToday() {
		assertTrue( Time.today().isToday() );
	}

	@Test
	public void testToLocalDate() {
		LocalDate localDate = sut.toLocalDate();
		assertEquals(sut.year, localDate.getYear());
		assertEquals(sut.month, localDate.getMonthValue());
		assertEquals(sut.day, localDate.getDayOfMonth());
	}

	@Test
	public void testToLocalTime() {
		LocalTime localTime = sut.toLocalTime();
		assertEquals(sut.hour, localTime.getHour());
		assertEquals(sut.minute, localTime.getMinute());
		assertEquals(sut.second, localTime.getSecond());
		assertEquals(sut.nano, localTime.getNano());
	}

	@Test
	public void testIsAfter() {
		Time time = new Time(2016, 2, 29, 13, 59, 58, 100000);
		assertTrue(time.isAfter(sut));
		assertTrue(sut.isBefore(time));
	}


}