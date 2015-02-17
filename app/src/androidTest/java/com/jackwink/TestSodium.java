package com.jackwink;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.jackwink.libsodium.NaCl;
import com.jackwink.libsodium.jni.SodiumConstants;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jackwink on 2/16/15.
 */
public class TestSodium extends TestCase {

    @SmallTest
    public void testImport() {
        NaCl cryptoProvider = NaCl.getInstance();

        byte[] publicKey = new byte[SodiumConstants.CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] secretKey = new byte[SodiumConstants.CRYPTO_SIGN_SECRETKEYBYTES];
        cryptoProvider.crypto_sign_keypair(publicKey, secretKey);


        byte[] original = "test".getBytes();
        byte[] testSig = cryptoProvider.crypto_sign(original, secretKey);
        byte[] message = cryptoProvider.crypto_sign_open(testSig, publicKey);
        Log.d("sodiumjni", "Original: " + Arrays.toString(original));
        Log.d("sodiumjni", "Signed: " + Arrays.toString(testSig));
        Log.d("sodiumjni", "Opened: " + Arrays.toString(message));
    }
}
