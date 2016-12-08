/**
 * @(#)Blowfish.java
 *
 * COPYRIGHT (c)  2007 CHIeru CO., LTD. All Rights Reserved.
 * VERSION 1.00.00 2007/2/15 �V�K�쐬
 */

package com.daulsoft.neotest.util.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



/**
 * Blowfish
 *
 * @author kawazawa@chieru.co.jp
 * @version 1.00.00 2007/2/15
 */
public class Blowfish {

        /* 암호화 */
    public static byte[] encrypt(String text)
        throws javax.crypto.IllegalBlockSizeException,
                    java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException,
                    java.io.UnsupportedEncodingException,
                    javax.crypto.BadPaddingException,
                    javax.crypto.NoSuchPaddingException
    {
        return encrypt( "DAulSOft", text );
    }
    public static byte[] encrypt(String key, String text)
        throws javax.crypto.IllegalBlockSizeException,
                    java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException,
                    java.io.UnsupportedEncodingException,
                    javax.crypto.BadPaddingException,
                    javax.crypto.NoSuchPaddingException
    {
    	//오탐 - 	45110321	[SP] 하드코드된 암호화키 사용
    	//key.getBytes()는 상수가 아닌 외부 입력값이므로   
    	//룰 설명 및 RDL 상으로는 정탐으로 판단될 수 있으므로 개발자 확인 필요 
        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher        cipher  = Cipher.getInstance("Blowfish");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return encrypted;
    }

    /* 복호화 */
    public static String decrypt(byte[] encrypted)
        throws javax.crypto.IllegalBlockSizeException,
                    java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException,
                    java.io.UnsupportedEncodingException,
                    javax.crypto.BadPaddingException,
                    javax.crypto.NoSuchPaddingException
    {
        return decrypt("DAulSOft", encrypted);
    }
    public static String decrypt(String key, byte[] encrypted)
        throws javax.crypto.IllegalBlockSizeException,
                    java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException,
                    java.io.UnsupportedEncodingException,
                    javax.crypto.BadPaddingException,
                    javax.crypto.NoSuchPaddingException
    {
    	//오탐 - 	45110321	[SP] 하드코드된 암호화키 사용
    	//key.getBytes()는 상수가 아닌 외부 입력값이므로 .. 
        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher        cipher  = Cipher.getInstance("Blowfish");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sksSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }

    /* 암호화 : Hex */
    public static String encryptToHexString(String text)
        throws javax.crypto.IllegalBlockSizeException,
                        java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException,
                        java.io.UnsupportedEncodingException,
                        javax.crypto.BadPaddingException,
                        javax.crypto.NoSuchPaddingException,
                        Exception
    {
        return encryptToHexString("DAulSOft", text);
    }
    public static String encryptToHexString(String key, String text)
                throws javax.crypto.IllegalBlockSizeException,
                                java.security.InvalidKeyException,
                                java.security.NoSuchAlgorithmException,
                                java.io.UnsupportedEncodingException,
                                javax.crypto.BadPaddingException,
                                javax.crypto.NoSuchPaddingException,
                                Exception
        {
                byte [] ret = Blowfish.encrypt(key, text);
                return ByteUtil.byteToHexString(ret);
        }

    /* 복호화 : Hex */
    public static String decryptFromHexString(String encrypted)
        throws javax.crypto.IllegalBlockSizeException,
                        java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException,
                        java.io.UnsupportedEncodingException,
                        javax.crypto.BadPaddingException,
                        javax.crypto.NoSuchPaddingException,
                        Exception
    {
        return decryptFromHexString("DAulSOft", encrypted);
    }
    public static String decryptFromHexString(String key, String encrypted)
                throws javax.crypto.IllegalBlockSizeException,
                                java.security.InvalidKeyException,
                                java.security.NoSuchAlgorithmException,
                                java.io.UnsupportedEncodingException,
                                javax.crypto.BadPaddingException,
                                javax.crypto.NoSuchPaddingException,
                                Exception
        {
                byte [] ret = ByteUtil.hexStringToByte(encrypted);
                return Blowfish.decrypt(key, ret);
        }
    
    public static String encryptToBase64(String text)
        throws javax.crypto.IllegalBlockSizeException,
                        java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException,
                        java.io.UnsupportedEncodingException,
                        javax.crypto.BadPaddingException,
                        javax.crypto.NoSuchPaddingException,
                        Exception
        {
                byte [] ret = Blowfish.encrypt(text);
                //정탐 - 45110327	[SP] 취약한 암호화 알고리즘의 사용
                return new String(Base64.encode(ret));
        }
        
        public static String decryptFromBase64(String encrypted)
                throws javax.crypto.IllegalBlockSizeException,
                                java.security.InvalidKeyException,
                                java.security.NoSuchAlgorithmException,
                                java.io.UnsupportedEncodingException,
                                javax.crypto.BadPaddingException,
                                javax.crypto.NoSuchPaddingException,
                                Exception
        {
        	//정탐 - 45110327	[SP] 취약한 암호화 알고리즘의 사용
                byte [] ret = Base64.decode(encrypted);
                return Blowfish.decrypt(ret);
        }
}
