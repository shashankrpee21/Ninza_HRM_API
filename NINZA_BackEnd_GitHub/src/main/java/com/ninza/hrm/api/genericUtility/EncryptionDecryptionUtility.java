package com.ninza.hrm.api.genericUtility;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionUtility {     // Private key = Ac03tEam@j!tu_#1

	public String encryptString(String input, String secretKey) throws Exception, NoSuchPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec("4234567890123456".getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public String decryptString(String input, String secretKey) throws Exception, NoSuchPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec("4234567890123456".getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
		return new String(decrypted);
	}
}
