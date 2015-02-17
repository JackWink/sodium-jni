package com.jackwink.libsodium;

/**
 * Created by jackwink on 2/16/15.
 */
import com.jackwink.libsodium.jni.Sodium;

public class NaCl {
    private static NaCl instance = null;

    protected NaCl() {
        // Exists only to defeat instantiation.
        if (Sodium.sodium_init() == -1) {
            throw new RuntimeException("Sodium could not initialize.");
        }
    }

    public static NaCl getInstance() {
        if(instance == null) {
            instance = new NaCl();
        }
        return instance;
    }

    public static String sodium_version_string() {
        return Sodium.sodium_version_string();
    }


    public int sodium_init() {
        return Sodium.sodium_init();
    }


    public static int crypto_sign_keypair(byte[] pk, byte[] sk) {
        return Sodium.crypto_sign_keypair(pk, sk);
    }

    static {
        System.loadLibrary("sodiumjni");
    }
}
