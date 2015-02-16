package com.jackwink;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.jackwink.libsodium.NaCl;

import junit.framework.TestCase;

/**
 * Created by jackwink on 2/16/15.
 */
public class TestSodium extends TestCase {

    @SmallTest
    public void testImport() {
        if (NaCl.sodium_init() == -1) {
            fail();
        }

        byte[] publicKey = new byte[64];
        byte[] privateKey = new byte[64];

        byte[] seed = new byte[64];

        NaCl.crypto_sign_ed25519_seed_keypair(publicKey, privateKey, seed);
        Log.d("Test", "" + publicKey);
    }
}
