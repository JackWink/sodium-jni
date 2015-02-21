package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoAuth {

    public final static int CRYPTO_AUTH_BYTES = SodiumConstants.CRYPTO_AUTH_BYTES;
    public final static int CRYPTO_AUTH_KEYBYTES = SodiumConstants.CRYPTO_AUTH_KEYBYTES;

    public static byte[] create(byte[] message, byte[] key) {
        byte[] hmac = new byte[CRYPTO_AUTH_BYTES];
        if (Sodium.crypto_auth(hmac, message, message.length, key) != 0) {
            return null;
        }
        return hmac;
    }

    public static boolean verify(byte[]mac, byte[] message, byte[] key) {
        return Sodium.crypto_auth_verify(mac, message, message.length, key) == 0;
    }

}
