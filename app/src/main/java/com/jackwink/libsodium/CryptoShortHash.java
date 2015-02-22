package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoShortHash {
    public final static int CRYPTO_SHORTHASH_BYTES = SodiumConstants.CRYPTO_SHORTHASH_BYTES;
    public final static int CRYPTO_SHORTHASH_KEYBYTES = SodiumConstants.CRYPTO_SHORTHASH_KEYBYTES;

    public static byte[] hash(byte[] value, byte[] key) {
        byte[] hash = new byte[CRYPTO_SHORTHASH_BYTES];
        if (Sodium.crypto_shorthash(hash, value, value.length, key) != 0) {
            return null;
        }
        return hash;
    }
}
