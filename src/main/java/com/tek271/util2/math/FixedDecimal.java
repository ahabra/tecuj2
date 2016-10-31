package com.tek271.util2.math;

public class FixedDecimal {
	public final int sizeMajor, sizeMinor;
	public final long major, minor;

	public FixedDecimal(int sizeMajor, int sizeMinor, long major, long minor) {
		this.sizeMajor = sizeMajor;
		this.sizeMinor = sizeMinor;
		this.major = major;
		this.minor = minor;
	}

	public int toInt() {
		return (int) major;
	}

	public long toLong() {
		return major;
	}

}
