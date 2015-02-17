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
    }
}
