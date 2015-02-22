package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;
import com.jackwink.libsodium.jni.SodiumJNI;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoGenericHash {
    public final static int CRYPTO_GENERICHASH_BYTES = SodiumConstants.CRYPTO_GENERICHASH_BYTES;
    public final static int CRYPTO_GENERICHASH_KEYBYTES = SodiumConstants.CRYPTO_GENERICHASH_KEYBYTES;

    public static byte[] hash(byte[] value, byte[] key) {
        byte[] hash = new byte[CRYPTO_GENERICHASH_BYTES];
        if(SodiumJNI.crypto_generichash(hash, hash.length, value, value.length, key, key.length) != 0) {
            return null;
        }
        return hash;
    }
}
