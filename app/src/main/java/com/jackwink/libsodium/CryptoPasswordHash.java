package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

import java.util.Arrays;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoPasswordHash {
    public final static int CRYPTO_PWHASH_SALTBYTES = SodiumConstants.CRYPTO_PWHASH_SALTBYTES;
    public final static int CRYPTO_PWHASH_HASHBYTES = SodiumConstants.CRYPTO_PWHASH_HASHBYTES;
    public final static int CRYPTO_PWHASH_KEY_BYTES = SodiumConstants.CRYPTO_PWHASH_KEY_BYTES;

    public final static int CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE = SodiumConstants.CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE;
    public final static int CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE = SodiumConstants.CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE;
    public final static int CRYPTO_PWHASH_OPSLIMIT_SENSITIVE = SodiumConstants.CRYPTO_PWHASH_OPSLIMIT_SENSITIVE;
    public final static int CRYPTO_PWHASH_MEMLIMIT_SENSITIVE = SodiumConstants.CRYPTO_PWHASH_MEMLIMIT_SENSITIVE;

    public static byte[] deriveKey(String password, byte[] salt, long opslimit, int memlimit) {
        byte[] derivedKey = new byte[CRYPTO_PWHASH_KEY_BYTES];
        if (Sodium.crypto_pwhash_scryptsalsa208sha256(derivedKey, derivedKey.length, password,
                                                password.length(), salt, opslimit, memlimit) != 0) {
            return null;
        }
        return derivedKey;
    }


    public static String hash(String password) {
        return hash(password, CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE, CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE);
    }

    public static String hash(String password, long opslimit, int memlimit) {
        byte[] hash = new byte[CRYPTO_PWHASH_HASHBYTES];
        if (Sodium.crypto_pwhash_scryptsalsa208sha256_str(hash, password, password.length(),
                                                                         opslimit, memlimit) != 0) {
            return null;
        }
        return new String(hash);
    }

    public static boolean verify(String hash, String password) {
        return Sodium.crypto_pwhash_scryptsalsa208sha256_str_verify(hash.getBytes(), password, password.length()) == 0;
    }

}
