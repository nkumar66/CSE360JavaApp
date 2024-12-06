package src.CSE360App;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptionHelper {

    // Encryption constants
    private static final String PROVIDER = "BC";                // BouncyCastle Provider
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // AES algorithm with CBC and PKCS5Padding
    private static final byte[] DEFAULT_KEY_BYTES = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /***
     * Encrypts the given plaintext using AES encryption in CBC mode with PKCS5 padding.
     * @param plainText
     * @param initializationVector
     * @return
     * @throws Exception
     */
    // Encryption and Decryption methods are thread-safe
    public static byte[] encrypt(byte[] plainText, byte[] initializationVector) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER); // Create a new Cipher instance
        SecretKey key = new SecretKeySpec(DEFAULT_KEY_BYTES, "AES");  // Create the key
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(initializationVector)); // Initialize Cipher
        return cipher.doFinal(plainText); // Encrypt and return the result
    }

    /***
     * Decrypts the given ciphertext using AES decryption in CBC mode with PKCS5 padding.
     * @param cipherText
     * @param initializationVector
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, byte[] initializationVector) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER); // Create a new Cipher instance
        SecretKey key = new SecretKeySpec(DEFAULT_KEY_BYTES, "AES");  // Create the key
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(initializationVector)); // Initialize Cipher
        return cipher.doFinal(cipherText); // Decrypt and return the result
    }
}
