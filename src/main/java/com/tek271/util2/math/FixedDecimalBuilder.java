package com.tek271.util2.math;

public class FixedDecimalBuilder {
	int sizeMajor, sizeMinor;
	long major, minor;
	long minorMultiplier;
	boolean roundMinor;

	public FixedDecimalBuilder roundMinor(boolean roundMinor) {
		this.roundMinor = roundMinor;
		return this;
	}

	public FixedDecimalBuilder roundMinor() {
		return roundMinor(true);
	}

	public FixedDecimalBuilder sizeMajor(int sizeMajor) {
		this.sizeMajor = sizeMajor;
		return this;
	}

	public FixedDecimalBuilder sizeMinor(int sizeMinor) {
		this.sizeMinor = sizeMinor;
		minorMultiplier = (long) Math.pow(10, sizeMinor);
		return this;
	}

	public FixedDecimalBuilder major(long major) {
		this.major = major;
		return this;
	}

	public FixedDecimalBuilder minor(long minor) {
		this.minor = minor;
		return this;
	}

	public FixedDecimal build() {
		return new FixedDecimal(sizeMajor, sizeMinor, major, minor);
	}

	public FixedDecimalBuilder setValue(int value) {
		return setValue((long) value);
	}

	public FixedDecimalBuilder setValue(long value) {
		return major(value).minor(0);
	}

	public FixedDecimalBuilder setValue(double value) {
		long min = roundMinor? calcRoundedMinor(value) : calcTruncatedMinor(value);
		return major((long)value).minor(min);
	}

	private long calcTruncatedMinor(double value) {
		return (long) ((value - major) * minorMultiplier);
	}

	private long calcRoundedMinor(double value) {
		long min = (long) ((value - major) * minorMultiplier * 10);
		if (min >= 10) return min/10;
		if (min >=5) return 1;
		return 0;
	}

	public FixedDecimalBuilder setValue(float value) {
		return setValue((double) value);
	}

}
