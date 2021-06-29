package com.darkan.cache.util;

import java.nio.ByteBuffer;

public class Utils {

	public static int toFilesystemHash(String string) {
		int size = string.length();
		char c = 0;
		for (int index = 0; index < size; index++)
			c = (char) ((c << 5) - c + charToCp1252(string.charAt(index)));
		return c;
	}

	private static char[] CP1252_VALS = new char[] { '\u20ac', '\u0000', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017d', '\u0000', '\u0000', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\u0000', '\u017e', '\u0178' };

	public static char cp1252ToChar(byte i) {
		int cp1252 = i & 0xff;
		if (cp1252 == 0)
			throw new IllegalArgumentException("Non cp1252 character provided");
		if (cp1252 >= 128 && cp1252 <= 159) {
			int translated = CP1252_VALS[cp1252 - 128];
			if (translated == 0) {
				translated = 63;
			}
			cp1252 = (char) translated;
		}
		return (char) cp1252;
	}

	public static byte charToCp1252(char c) {
		if (c > 0 && c < '\u0080' || c >= '\u00a0' && c <= '\u00ff')
			return (byte) c;

		switch (c) {
		case '\u20ac':
			return (-128);
		case '\u201a':
			return (-126);
		case '\u0192':
			return (-125);
		case '\u201e':
			return (-124);
		case '\u2026':
			return (-123);
		case '\u2020':
			return (-122);
		case '\u2021':
			return (-121);
		case '\u02c6':
			return (-120);
		case '\u2030':
			return (-119);
		case '\u0160':
			return (-118);
		case '\u2039':
			return (-117);
		case '\u0152':
			return (-116);
		case '\u017d':
			return (-114);
		case '\u2018':
			return (-111);
		case '\u2019':
			return (-110);
		case '\u201c':
			return (-109);
		case '\u201d':
			return (-108);
		case '\u2022':
			return (-107);
		case '\u2013':
			return (-106);
		case '\u2014':
			return (-105);
		case '\u02dc':
			return (-104);
		case '\u2122':
			return (-103);
		case '\u0161':
			return (-102);
		case '\u203a':
			return (-101);
		case '\u0153':
			return (-100);
		case '\u017e':
			return (-98);
		case '\u0178':
			return (-97);
		default:
			return 63;
		}
	}

	public static void putSmartInt(ByteBuffer buffer, int value) {
		if (value >= Short.MAX_VALUE) {
			buffer.putInt(value - Integer.MAX_VALUE - 1);
		} else {
			if (value >= 0)
				buffer.putShort((short) value);
			else
				buffer.putShort(Short.MAX_VALUE);
		}
	}

	public static int getSmartInt(ByteBuffer buffer) {
		if (buffer.get(buffer.position()) < 0)
			return buffer.getInt() & 0x7fffffff;
		return buffer.getShort() & 0xffff;
	}

	public static String getString(ByteBuffer buffer) {
		int origPos = buffer.position();
		int length = 0;
		while (buffer.get() != 0)
			length++;
		if (length == 0)
			return "";
		byte[] byteArray = new byte[length];
		buffer.position(origPos);
		buffer.get(byteArray);
		buffer.position(buffer.position() + 1);
		return new String(byteArray);
	}

	public static void skip(ByteBuffer buffer, int bytes) {
		buffer.position(buffer.position() + bytes);
	}

	public static int getSmallSmartInt(ByteBuffer buffer) {
		if ((buffer.get(buffer.position()) & 0xff) < 128)
			return buffer.get() & 0xff;
		return (buffer.getShort() & 0xffff) - 0x8000;
	}
}
