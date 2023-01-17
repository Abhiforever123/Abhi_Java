package com.test.practice;

import java.util.Base64;

public class EncryptPassword {
	
	public static String encryptedString(String str)
	{
		
		byte[] encrpt= Base64.getEncoder().encode(str.getBytes());
		
		return new String(encrpt);
	}

	
	public static String decryptString(String str)
	{
		byte[] decrpt = Base64.getDecoder().decode(str);
		return new String(decrpt);
	}
}
