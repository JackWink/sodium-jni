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

    public static int crypto_secretbox_easy(byte[] c, byte[] m, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_easy(c, m, m.length, n, k);
    }

    public static int crypto_secretbox_open_easy(byte[] m, byte[] c, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_open_easy(m, c, c.length, n, k);
    }

    public static int crypto_secretbox_detached(byte[] c, byte[] mac, byte[] m, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_detached(c, mac, m, m.length, n, k);
    }

    public static int crypto_secretbox_open_detached(byte[] m, byte[] c, byte[] mac, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_open_detached(m, c, mac, c.length, n, k);
    }
}
