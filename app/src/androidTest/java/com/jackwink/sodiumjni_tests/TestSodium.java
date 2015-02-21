package com.jackwink.sodiumjni_tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.jackwink.libsodium.CryptoSecretBox;
import com.jackwink.libsodium.CryptoSign;
import com.jackwink.libsodium.RandomBytes;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jackwink on 2/16/15.
 */
public class TestSodium extends TestCase {
    final static String TAG = "sodiumjni";

    @SmallTest
    public void testSignature() {


        byte[] publicKey = new byte[CryptoSign.CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] secretKey = new byte[CryptoSign.CRYPTO_SIGN_SECRETKEYBYTES];
        CryptoSign.keypair(publicKey, secretKey);

        byte[] original = "test".getBytes();
        byte[] testSig = CryptoSign.sign(original, secretKey);
        byte[] message = CryptoSign.open(testSig, publicKey);

        if (!Arrays.equals(original, message)) {
            fail("Message not verified.");
        }

        byte[] justSig = CryptoSign.sign_detached(original, secretKey);
        boolean success = CryptoSign.verify_detached(justSig, original, publicKey);

        if (!success) {
            fail("Message detached not verified.");
        }

    }

    @SmallTest
    public void testSecretBox() {
        byte[] message = "hello!".getBytes();
        byte[] nonce = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_NONCEBYTES];
        byte[] key = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_KEYBYTES];
        byte[] ciphertext = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_MACBYTES + message.length];

        RandomBytes.fillBuffer(nonce);
        RandomBytes.fillBuffer(key);
        CryptoSecretBox.easy(ciphertext, message, nonce, key);

        byte[] decrypted = new byte[ciphertext.length - CryptoSecretBox.CRYPTO_SECRETBOX_MACBYTES];
        if (CryptoSecretBox.open_easy(decrypted, ciphertext, nonce, key) != 0) {
            fail("Message forged");
        }
    }
}
