package com.tek271.util2.math;

import org.junit.*;

import static com.tek271.util2.math.HumanNumber.binary;
import static com.tek271.util2.math.HumanNumber.binaryShort;
import static com.tek271.util2.math.HumanNumber.standard;
import static org.junit.Assert.*;

public class HumanNumberTest {

	@Test
	public void testFormat() {
		assertEquals("3", standard.format(3));
		assertEquals("10", standard.format(10));
		assertEquals("100", standard.format(100));
		assertEquals("1 Thousand", standard.format(1000));
		assertEquals("3 Thousand", standard.format(3000));
		assertEquals("3 Thousand", standard.format(3001));
		assertEquals("3 Thousand", standard.format(3010));
		assertEquals("3.1 Thousand", standard.format(3100));

		assertEquals("2.7 Mega", binary.format(2_765_321));
		assertEquals("2.7M", binaryShort.format(2_765_321));
	}

}
