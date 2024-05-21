package de.hitec.nhplus.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Utility class for hashing and verifying passwords using SHA-256.
 */
public class PassHash {

    /**
     * Hashes the given password using SHA-256.
     *
     * @param password the password to hash
     * @return the hashed password as a hexadecimal string, or null if the hashing algorithm is not found
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifies if the given password matches the hashed password.
     *
     * @param password the plain text password to verify
     * @param hashedPassword the hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return Objects.equals(hashPassword(password), hashedPassword);
    }
}