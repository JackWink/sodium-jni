package com.jackwink.libsodium;

/**
 * Created by jackwink on 2/16/15.
 */
import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

public class NaCl {
    public static String sodium_version_string() {
        return Sodium.sodium_version_string();
    }

    private static NaCl instance = null;

    protected NaCl() {
    }

    public static NaCl getInstance() {
        if(instance == null) {
            instance = new NaCl();
        }
        return instance;
    }

    public static void randombytes_buf(byte[] buf) {
        Sodium.randombytes_buf(buf, buf.length);
    }

    public static int randombytes_random() {
        return Sodium.randombytes_random();
    }

    public static int randombytes_uniform(int upper_bound) {
        return Sodium.randombytes_uniform(upper_bound);
    }

}
