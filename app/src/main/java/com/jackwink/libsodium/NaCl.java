package com.jackwink.libsodium;

/**
 * Created by jackwink on 2/16/15.
 */
import com.jackwink.libsodium.jni.Sodium;

public class NaCl {
    public static String sodium_version_string() {
        return Sodium.sodium_version_string();
    }

    private static NaCl instance = null;

    protected NaCl() {
        // Exists to defeat instantiation and force the first created instance to call sodium_init
        if (Sodium.sodium_init() == -1) {
            throw new RuntimeException("Sodium could not be initialized.");
        }
    }

    public static NaCl getInstance() {
        if(instance == null) {
            instance = new NaCl();
        }
        return instance;
    }

    public int crypto_sign_keypair(byte[] pk, byte[] sk) {
        return Sodium.crypto_sign_keypair(pk, sk);
    }

    static {
        System.loadLibrary("sodiumjni");
    }
}
