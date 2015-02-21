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

    public static byte[] create_easy(byte[] message, byte[] nonce, byte[] key) {
        byte[] ciphertext = new byte[CRYPTO_SECRETBOX_MACBYTES + message.length];
        if (Sodium.crypto_secretbox_easy(ciphertext, message, message.length, nonce, key) != 0) {
            return null;
        }
        return ciphertext;
    }

    public static byte[] open_easy(byte[] ciphertext, byte[] nonce, byte[] key) {
        byte[] message = new byte[ciphertext.length - CRYPTO_SECRETBOX_MACBYTES];
        if (Sodium.crypto_secretbox_open_easy(message, ciphertext, ciphertext.length, nonce, key) != 0) {
            return null;
        }
        return message;
    }

    public static byte[] create_detached(byte[] mac, byte[] message, byte[] nonce, byte[] key) {
        byte[] ciphertext = new byte[message.length];
        if (Sodium.crypto_secretbox_detached(ciphertext, mac, message, message.length, nonce, key) != 0) {
            return null;
        }
        return ciphertext;
    }

    public static byte[] open_detached(byte[] ciphertext, byte[] mac, byte[] nonce, byte[] key) {
        byte[] message = new byte[ciphertext.length];
        if(Sodium.crypto_secretbox_open_detached(message, ciphertext, mac, ciphertext.length, nonce, key) != 0) {
            return null;
        }
        return message;
    }
}
