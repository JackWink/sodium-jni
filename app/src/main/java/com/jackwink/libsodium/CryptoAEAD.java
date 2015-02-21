package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoAEAD {
    public final static int CRYPTO_AED_CHACHA20POLY1305_KEYBYTES = SodiumConstants.CRYPTO_AED_CHACHA20POLY1305_KEYBYTES;
    public final static int CRYPTO_AED_CHACHA20POLY1305_NONCEBYTES = SodiumConstants.CRYPTO_AED_CHACHA20POLY1305_NONCEBYTES;
    public final static int CRYPTO_AED_CHACHA20POLY1305_MACBYTES = SodiumConstants.CRYPTO_AED_CHACHA20POLY1305_MACBYTES;

    public static byte[] crypto_aead_chacha20poly1305_encrypt(byte[] message, byte[] additionData, byte[] nonce, byte[] key) {
        byte[] ciphertext = new byte[message.length + CRYPTO_AED_CHACHA20POLY1305_MACBYTES];
        if (Sodium.crypto_aead_chacha20poly1305_encrypt(ciphertext, null, message, message.length,
                                additionData, additionData.length, new byte[0], nonce, key) != 0) {
            return null;
        }
        return ciphertext;
    }

    public static byte[] crypto_aead_chacha20poly1305_decrypt(byte[] ciphertext, byte[] additionData, byte[] nonce, byte[] key) {
        byte[] message = new byte[ciphertext.length - CRYPTO_AED_CHACHA20POLY1305_MACBYTES];
        if (Sodium.crypto_aead_chacha20poly1305_decrypt(message, null, new byte[0], ciphertext,
                         ciphertext.length, additionData, additionData.length, nonce, key) != 0) {
            return null;
        }
        return message;
    }

}

