import java.util.*;

import java.security.*;
import java.security.cert.*;
import java.security.interfaces.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import java.io.*;

public class Main {

    // • Input a line of text from the console
    // • AES tests
        // – Generate a 128-bit AES key
        // – Encrypt the string that was read from the console using AES
        // – Decrypt the resulting AES ciphertext and print out the plaintext
    // • Blowfish tests
        // – Generate a 128-bit Blowfish key
        // – Encrypt the string that was read from the console using Blowfish
        // – Decrypt the resulting Blowfish ciphertext and print out the plaintext
    // • RSA tests
        // – Generate an RSA public/private key pair
        // – Encrypt the string that was read from the console using RSA
        // – Decrypt the resulting RSA ciphertext and print out the plaintext
        // – Generate an RSA signature over the string that was read from console
        // – Verify the resulting RSA signature and print out whether the verification succeeded
    // • Extra Credit (Up to 5 points)
        // – Generate an array of 100 different random strings
        // – Time how long it takes to encrypt all 100 strings using AES
        // – Time how long it takes to encrypt all 100 strings using Blowfish
        // – Time how long it takes to encrypt all 100 strings using RSA
        // – Output how many times faster AES encryption is than RSA encryption
        // – Output how many times faster Blowfish encryption is than RSA encryption
        // – Output how many times faster Blowfish encryption is than AES encryption

    public static void main(String args[]) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        System.out.print("Enter some text: ");
        Scanner console = new Scanner(System.in);
        String plaintext = console.nextLine();
        System.out.println("Your input: " + plaintext);

        byte[] encrypted;
        String decrypted;

        // ###############  AES  ###################
        // make a key
        KeyGenerator generator = KeyGenerator.getInstance("AES");   
        generator.init(128);
        SecretKey key = generator.generateKey();

        // then ecrypt/decerypt using that key
        encrypted = encrypt("AES", plaintext, key);
        decrypted = decrypt("AES", encrypted, key);
        System.out.println("AES: " + decrypted);

        // #############  Blowfish  ###############
        // make a key
        generator = KeyGenerator.getInstance("Blowfish");   
        generator.init(128);
        key = generator.generateKey();

        // then ecrypt/decerypt using that key
        encrypted = encrypt("Blowfish", plaintext, key);
        decrypted = decrypt("Blowfish", encrypted, key);
        System.out.println("Blowfish: " + decrypted);

        // ###############  RSA  ##################
        // make a key
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.genKeyPair();

        // then ecrypt/decerypt using that key
        encrypted = encrypt("RSA", plaintext, pair.getPublic());
        decrypted = decrypt("RSA", encrypted, pair.getPrivate());
        System.out.println("RSA: " + decrypted);

        // create and verify a signature
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(pair.getPrivate());
        sig.update(plaintext.getBytes());
        byte[] signatureBytes = sig.sign();
        System.out.println("Signature: " + new String(Base64.getEncoder().encode(signatureBytes)));

        sig.initVerify(pair.getPublic());
        sig.update(plaintext.getBytes());
        System.out.println("Signature authentic: " + sig.verify(signatureBytes));
    }

    public static byte[] encrypt(final String type, final String plaintext, final Key key) {
        byte[] encryptedVal = null;

        try {
            final Cipher c = Cipher.getInstance(type);
            c.init(Cipher.ENCRYPT_MODE, key);
            encryptedVal = c.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            System.out.println("The Exception is=" + e);
        }
        return encryptedVal;
    }

    public static String decrypt(final String type, final byte[] encrypted, final Key key) {
        String decryptedValue = null;

        try {
            final Cipher c = Cipher.getInstance(type);
            c.init(Cipher.DECRYPT_MODE, key);
            decryptedValue = new String(c.doFinal(encrypted));
        } catch (Exception e) {
            System.out.println("The Exception is=" + e);
            e.printStackTrace(System.err);
        }

        return decryptedValue;
    }
}