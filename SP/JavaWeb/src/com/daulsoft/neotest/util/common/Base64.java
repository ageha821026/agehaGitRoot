/**
 * @(#)Base64.java
 *
 * @author  sujin jeong
 * @version 1.0, 2003/09/30
 *
 * Copyright 2003-2004 by Daul Soft, Inc. All rights reserved.
 *
 */

/**
 * BASE64 인코딩/디코딩 처리용 클래스
 */

package com.daulsoft.neotest.util.common;

public class Base64
{
	private static final byte[] encodingTable =
		{
		    (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F', (byte)'G',
            (byte)'H', (byte)'I', (byte)'J', (byte)'K', (byte)'L', (byte)'M', (byte)'N',
            (byte)'O', (byte)'P', (byte)'Q', (byte)'R', (byte)'S', (byte)'T', (byte)'U',
            (byte)'V', (byte)'W', (byte)'X', (byte)'Y', (byte)'Z',
		    (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f', (byte)'g',
            (byte)'h', (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m', (byte)'n',
            (byte)'o', (byte)'p', (byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u',
            (byte)'v',
		    (byte)'w', (byte)'x', (byte)'y', (byte)'z',
		    (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6',
            (byte)'7', (byte)'8', (byte)'9',
		    (byte)'+', (byte)'/'
		};

	/**
	 * 바이트배열을 base64 인코딩하여 바이트배열로 반환한다.
	 * @param data 인코딩할 대상 데이터
	 * @return byte[] 인코딩된 바이트배열
	 */
	public static byte[] encode(
		byte[]	data)
	{
		byte[]	bytes;

		int modulus = data.length % 3;
		if (modulus == 0)
		{
			bytes = new byte[4 * data.length / 3];
		}
		else
		{
			bytes = new byte[4 * ((data.length / 3) + 1)];
		}

        int dataLength = (data.length - modulus);
		int a1, a2, a3;
		for (int i = 0, j = 0; i < dataLength; i += 3, j += 4)
		{
			a1 = data[i] & 0xff;
			a2 = data[i + 1] & 0xff;
			a3 = data[i + 2] & 0xff;

			bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
			bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
			bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
			bytes[j + 3] = encodingTable[a3 & 0x3f];
		}

		/*
		 * process the tail end.
		 */
		int	b1, b2, b3;
		int	d1, d2;
//정탐 - 11777	Switch 구문은 Default 구문을 포함해야 함
		switch (modulus)
		{
		case 0:		/* nothing left to do */
			break;
		case 1:
			d1 = data[data.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = (d1 << 4) & 0x3f;

			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = (byte)'=';
			bytes[bytes.length - 1] = (byte)'=';
			break;
		case 2:
			d1 = data[data.length - 2] & 0xff;
			d2 = data[data.length - 1] & 0xff;

			b1 = (d1 >>> 2) & 0x3f;
			b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
			b3 = (d2 << 2) & 0x3f;

			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = encodingTable[b3];
			bytes[bytes.length - 1] = (byte)'=';
			break;
		}

		return bytes;
	}

	/*
	 * set up the decoding table.
	 */
	private static final byte[] decodingTable;

	static
	{
		decodingTable = new byte[128];

		for (int i = 'A'; i <= 'Z'; i++)
		{
			decodingTable[i] = (byte)(i - 'A');
		}

		for (int i = 'a'; i <= 'z'; i++)
		{
			decodingTable[i] = (byte)(i - 'a' + 26);
		}

		for (int i = '0'; i <= '9'; i++)
		{
			decodingTable[i] = (byte)(i - '0' + 52);
		}

		decodingTable['+'] = 62;
		decodingTable['/'] = 63;
	}

	/**
	 * 바이트배열을 base64 디코딩하여 바이트배열로 반환한다.
	 * @param data 디코딩할 대상 데이터
	 * @return byte[] 디코딩된 바이트배열
	 */
	public static byte[] decode(
		byte[]	data)
	{
		byte[]	bytes;
		byte	b1, b2, b3, b4;

		if (data[data.length - 2] == '=')
		{
			bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
		}
		else if (data[data.length - 1] == '=')
		{
			bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
		}
		else
		{
			bytes = new byte[((data.length / 4) * 3)];
		}

		for (int i = 0, j = 0; i < data.length - 4; i += 4, j += 3)
		{
			b1 = decodingTable[data[i]];
			b2 = decodingTable[data[i + 1]];
			b3 = decodingTable[data[i + 2]];
			b4 = decodingTable[data[i + 3]];

			bytes[j] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte)((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte)((b3 << 6) | b4);
		}

		if (data[data.length - 2] == '=')
		{
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];

			bytes[bytes.length - 1] = (byte)((b1 << 2) | (b2 >> 4));
		}
		else if (data[data.length - 1] == '=')
		{
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];
			b3 = decodingTable[data[data.length - 2]];

			bytes[bytes.length - 2] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte)((b2 << 4) | (b3 >> 2));
		}
		else
		{
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];
			b3 = decodingTable[data[data.length - 2]];
			b4 = decodingTable[data[data.length - 1]];

			bytes[bytes.length - 3] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte)((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte)((b3 << 6) | b4);
		}

		return bytes;
	}

	/**
	 * 문자열을 base64 디코딩하여 바이트배열로 반환한다.
	 * @param String data 디코딩할 대상 데이터
	 * @return 디코딩된 바이트배열
	 */
	public static byte[] decode(
		String	data)
	{
		byte[]	bytes;
		byte	b1, b2, b3, b4;

		if (data.charAt(data.length() - 2) == '=')
		{
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
		}
		else if (data.charAt(data.length() - 1) == '=')
		{
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
		}
		else
		{
			bytes = new byte[((data.length() / 4) * 3)];
		}

		for (int i = 0, j = 0; i < data.length() - 4; i += 4, j += 3)
		{
			b1 = decodingTable[data.charAt(i)];
			b2 = decodingTable[data.charAt(i + 1)];
			b3 = decodingTable[data.charAt(i + 2)];
			b4 = decodingTable[data.charAt(i + 3)];

			bytes[j] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte)((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte)((b3 << 6) | b4);
		}

		if (data.charAt(data.length() - 2) == '=')
		{
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];

			bytes[bytes.length - 1] = (byte)((b1 << 2) | (b2 >> 4));
		}
		else if (data.charAt(data.length() - 1) == '=')
		{
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];
			b3 = decodingTable[data.charAt(data.length() - 2)];

			bytes[bytes.length - 2] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte)((b2 << 4) | (b3 >> 2));
		}
		else
		{
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];
			b3 = decodingTable[data.charAt(data.length() - 2)];
			b4 = decodingTable[data.charAt(data.length() - 1)];

			bytes[bytes.length - 3] = (byte)((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte)((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte)((b3 << 6) | b4);
		}

		return bytes;
	}
/*
	public static void main(String[] args)
	{
		String sData = "RGF1bFNvZnQgWEVkaXQgRG9jdW1lbnQAAAAAAEClEgB0ZXJpdXMAAwAAEwBspRIAjMj4d6fI+HfQfR8AAAATAHRlcml1cwB3p8j4dwgGEwCQAPYAjijmdwAAEwDQfR8AAMgfAI8IISkIcI8D8kf4d5Dx/HfOR/h36NUcbAAAAAAA1RxsWCnmd+jIHwBoiRoA1KQSAFCJGgAMphIAkYH5dwBJ+Hf/////sKUSAKNI+HcCAAAAZNUcbAOR+Heg1Rxs0wcLAAUABwAPABsANgAAANMHCwAFAAcADwAbADYAAAAMAJoBlgAAAAEAAAD///8AAAAAAAcAAABhAGIAYwBkAGUAZgBnAAAAAAAAAAAAAAAAAAEANABM////ZAAAAAAAAAAAAAAAAgAAALG8uLLDvAAAlLI0AAAAAAAAAAAACQAJAAABCAAAAAAAAAAAAAAAAAAAAAAAAAAAAP////8AAAAAAAAAABsAAAAAAAAAAAAAAGQAAAAAAAAAAAAAAA==";
		System.out.println( new String(decode(sData)) );
	}
*/
}
