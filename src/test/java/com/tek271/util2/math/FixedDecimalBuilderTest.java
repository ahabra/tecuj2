package com.tek271.util2.math;

import org.junit.*;

import static org.junit.Assert.*;

public class FixedDecimalBuilderTest {
	private FixedDecimalBuilder sut;

	@Before
	public void setUp() {
		sut = new FixedDecimalBuilder().sizeMajor(3).sizeMinor(2);
	}

	@Test
	public void testSetValue() {
		assertEquals(3, sut.setValue(3).major);
		assertEquals(3, sut.setValue(3.2).major);
		assertEquals(20, sut.setValue(3.2).minor);
		assertEquals(20, sut.setValue(3.2f).minor);
		assertEquals(2, sut.setValue(3.02).minor);
		assertEquals(0, sut.setValue(3.002).minor);
		assertEquals(0, sut.setValue(3.008).minor);
		assertEquals(1, sut.roundMinor().setValue(3.008).minor);
		assertEquals(0, sut.roundMinor().setValue(3.004).minor);
		assertEquals(123, sut.setValue(123.2).major);
		assertEquals(1234, sut.setValue(1234.2).major);
	}

	@Test
	public void settingParts() {
		assertEquals(3, sut.major(3).major);
		assertEquals(2, sut.minor(2).minor);
	}

}