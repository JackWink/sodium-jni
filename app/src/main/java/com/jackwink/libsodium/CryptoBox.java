package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoBox {
    public final static int CRYPTO_BOX_SEEDBYTES = SodiumConstants.CRYPTO_BOX_SEEDBYTES;
    public final static int CRYPTO_BOX_PUBLICKEYBYTES = SodiumConstants.CRYPTO_BOX_PUBLICKEYBYTES;
    public final static int CRYPTO_BOX_SECRETKEYBYTES = SodiumConstants.CRYPTO_BOX_SECRETKEYBYTES;
    public final static int CRYPTO_BOX_NONCEBYTES = SodiumConstants.CRYPTO_BOX_NONCEBYTES;
    public final static int CRYPTO_BOX_MACBYTES = SodiumConstants.CRYPTO_BOX_MACBYTES;

    public static int seed_keypair(byte[] publicKey, byte[] secretKey, byte[] seed) {
        return Sodium.crypto_box_seed_keypair(publicKey, secretKey, seed);
    }

    public static int keypair(byte[] publicKey, byte[] secretKey) {
        return Sodium.crypto_box_keypair(publicKey, secretKey);
    }

    public static byte[] create_easy(byte[] message, byte[] nonce, byte[] publicKey, byte[] secretKey) {
        byte[] ciphertext = new byte[CRYPTO_BOX_MACBYTES + message.length];
        if (Sodium.crypto_box_easy(ciphertext, message, message.length, nonce, publicKey, secretKey) != 0) {
            return null;
        }
        return ciphertext;
    }

    public static byte[] open_easy(byte[] ciphertext, byte[] nonce, byte[] publicKey, byte[] secretKey) {
        byte[] message = new byte[ciphertext.length - CRYPTO_BOX_MACBYTES];
        if (Sodium.crypto_box_open_easy(message, ciphertext, ciphertext.length, nonce, publicKey, secretKey) != 0) {
            return null;
        }
        return message;
    }

    public static byte[] create_detached(byte[] mac, byte[] message, byte[] nonce, byte[] publicKey, byte[] secretKey) {
        byte[] ciphertext = new byte[message.length];
        if (Sodium.crypto_box_detached(ciphertext, mac, message, message.length, nonce, publicKey, secretKey) != 0) {
            return null;
        }
        return ciphertext;
    }

    public static byte[] open_detached(byte[] ciphertext, byte[] mac, byte[] nonce, byte[] publicKey, byte[] secretKey) {
        byte[] message = new byte[ciphertext.length];
        if (Sodium.crypto_box_open_detached(message, ciphertext, mac, ciphertext.length, nonce, publicKey, secretKey) != 0) {
            return null;
        }
        return message;
    }
}
