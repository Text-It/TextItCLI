package com.TextIt.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    public static String generateHashCode(String input) {
        try {

            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Perform the hash
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert a byte array into hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // Pad with leading zero
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported!", e);
        }
    }
}
