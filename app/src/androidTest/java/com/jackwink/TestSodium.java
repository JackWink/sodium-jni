package com.jackwink;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.jackwink.libsodium.NaCl;
import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jackwink on 2/16/15.
 */
public class TestSodium extends TestCase {
    final static String TAG = "sodiumjni";

    @SmallTest
    public void testSignature() {
        NaCl cryptoProvider = NaCl.getInstance();

        byte[] publicKey = new byte[SodiumConstants.CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] secretKey = new byte[SodiumConstants.CRYPTO_SIGN_SECRETKEYBYTES];
        cryptoProvider.crypto_sign_keypair(publicKey, secretKey);

        byte[] original = "test".getBytes();
        byte[] testSig = cryptoProvider.crypto_sign(original, secretKey);
        byte[] message = cryptoProvider.crypto_sign_open(testSig, publicKey);

        if (!Arrays.equals(original, message)) {
            fail("Message not verified.");
        }

        byte[] justSig = cryptoProvider.crypto_sign_detached(original, secretKey);
        boolean success = cryptoProvider.crypto_sign_verify_detached(justSig, original, publicKey);

        if (!success) {
            fail("Message detached not verified.");
        }

    }

    @SmallTest
    public void testSecretBox() {
        byte[] message = "hello!".getBytes();
        byte[] nonce = new byte[SodiumConstants.CRYPTO_SECRETBOX_NONCEBYTES];
        byte[] key = new byte[SodiumConstants.CRYPTO_SECRETBOX_KEYBYTES];
        byte[] ciphertext = new byte[SodiumConstants.CRYPTO_SECRETBOX_MACBYTES + message.length];

        NaCl.randombytes_buf(nonce);
        NaCl.randombytes_buf(key);
        NaCl.crypto_secretbox_easy(ciphertext, message, nonce, key);

        byte[] decrypted = new byte[ciphertext.length - SodiumConstants.CRYPTO_SECRETBOX_MACBYTES];
        if (NaCl.crypto_secretbox_open_easy(decrypted, ciphertext, nonce, key) != 0) {
            fail("Message forged");
        }

        Log.i(TestSodium.TAG, "Enc: " + Arrays.toString(ciphertext));
        Log.i(TestSodium.TAG, "Dec: " + Arrays.toString(decrypted));
        Log.i(TestSodium.TAG, "Org: " + Arrays.toString(message));

    }


}
