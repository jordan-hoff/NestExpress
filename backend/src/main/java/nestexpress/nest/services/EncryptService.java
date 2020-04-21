package nestexpress.nest.services;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

@Service
public class EncryptService {

// Encrypts the users password.
    public String encryptPassword(String password)
                  throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iteration       = 65536;
        SecureRandom random = new SecureRandom();
        byte[] salt         = new byte[32];

        random.nextBytes(salt);

        KeySpec keySpec             = new PBEKeySpec(password.toCharArray(), salt, iteration, 128);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = keyFactory.generateSecret(keySpec).getEncoded();

        return iteration + ":" + toHex(salt) + ":" + toHex(hash);
    }

    // Validates that the password from db and password user entered.
    public boolean validatePassword(String userPassword, String storedPassword)
    throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt    = fromHex(parts[1]);
        byte[] hash    = fromHex(parts[2]);

        PBEKeySpec keySpec   = new PBEKeySpec(userPassword.toCharArray(), salt, iterations, 128);
        SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash      = key.generateSecret(keySpec).getEncoded();

        int diff = hash.length ^ testHash.length;

        for(int hashIndex = 0; hashIndex < hash.length &&
        hashIndex < testHash.length; hashIndex++)
        diff |= hash[hashIndex] ^ testHash[hashIndex];

        return diff == 0;
    }

    // Converts the user password to readable hex values.
    private static String toHex(byte[] convertArray) throws NoSuchAlgorithmException {
        BigInteger bigInt = new BigInteger(1, convertArray);
        String hexValues  = bigInt.toString(16);
        int paddingLength = (convertArray.length * 2) - hexValues.length();

        if(paddingLength > 0)
            return String.format("%0"  + paddingLength + "d", 0) + hexValues;
        else
            return hexValues;
    }

    // Converts the db returned password values.
    private static byte[] fromHex(String hexValue) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hexValue.length() / 2];

        for(int byteIndex = 0; byteIndex < bytes.length; byteIndex++)
            bytes[byteIndex] =
                (byte)Integer.parseInt(hexValue.substring(2 * byteIndex, 2 * byteIndex + 2), 16);

        return bytes;
    }
}