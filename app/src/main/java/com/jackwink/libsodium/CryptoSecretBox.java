package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoSecretBox {
    public final static int CRYPTO_SECRETBOX_KEYBYTES = SodiumConstants.CRYPTO_SECRETBOX_KEYBYTES;
    public final static int CRYPTO_SECRETBOX_MACBYTES = SodiumConstants.CRYPTO_SECRETBOX_MACBYTES;
    public final static int CRYPTO_SECRETBOX_NONCEBYTES = SodiumConstants.CRYPTO_SECRETBOX_NONCEBYTES;

    public static int easy(byte[] c, byte[] m, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_easy(c, m, m.length, n, k);
    }

    public static int open_easy(byte[] m, byte[] c, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_open_easy(m, c, c.length, n, k);
    }

    public static int detached(byte[] c, byte[] mac, byte[] m, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_detached(c, mac, m, m.length, n, k);
    }

    public static int open_detached(byte[] m, byte[] c, byte[] mac, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_open_detached(m, c, mac, c.length, n, k);
    }
}
