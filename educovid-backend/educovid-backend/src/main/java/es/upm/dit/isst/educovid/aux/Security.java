package es.upm.dit.isst.educovid.aux;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Security {
	
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		System.out.println("New salt generated: " + salt);
		return bytesToHex(salt);
	}
	
	public static String getHash(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.reset();
			byte[] hashedPassword = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
			System.out.println("Setted password: '" + password + "'");
			System.out.println("Setted salt: '" + salt + "'");
			System.out.println("Setted hash: '" + hashedPassword + "'");
			return bytesToHex(hashedPassword); 
	    }
	    catch (NoSuchAlgorithmException e) {
	        System.err.println("SHA-512 is not a valid message digest algorithm");
	        System.err.println(e);
	        return null;
	    }
	}
	
	public static Boolean checkPassword(String passwordToCheck, String hash, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.reset();
			System.out.println("Salt: '" + salt + "'");
			byte[] hashedPasswordToCheck = md.digest((salt + passwordToCheck).getBytes(StandardCharsets.UTF_8));
			String hexHashToCheck = Security.bytesToHex(hashedPasswordToCheck);
			System.out.println("Introduced password: '" + passwordToCheck + "'");
			System.out.println("Computed hash: '" + hexHashToCheck + "'");
			System.out.println("Expected hash: '" + hash.toString() + "'");
			return hexHashToCheck.equals(hash);
	    }
	    catch (NoSuchAlgorithmException e) {
	    	System.err.println("SHA-512 is not a valid message digest algorithm");
	        System.err.println(e);
	        return false;
	    }
	}
}
