package com.daulsoft.neotest.util.common;

import org.apache.commons.codec.binary.Hex;

public class ByteUtil {
	public static String byteToHexString(byte [] source) throws Exception {
		return new String(Hex.encodeHex(source));
	}

	public static byte [] hexStringToByte(String source) throws Exception {
		//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
		//decodeHex의 DecoderException을 처리하지 않음 
		return Hex.decodeHex(source.toCharArray());
	}
}
