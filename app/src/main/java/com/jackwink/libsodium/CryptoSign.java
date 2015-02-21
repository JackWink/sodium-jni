package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;
import com.jackwink.libsodium.jni.SodiumConstants;

/**
 * Created by jackwink on 2/21/15.
 */
public class CryptoSign {
    public final static int CRYPTO_SIGN_BYTES = SodiumConstants.CRYPTO_SIGN_BYTES;
    public final static int CRYPTO_SIGN_SEEDBYTES = SodiumConstants.CRYPTO_SIGN_SEEDBYTES;
    public final static int CRYPTO_SIGN_PUBLICKEYBYTES = SodiumConstants.CRYPTO_SIGN_PUBLICKEYBYTES;
    public final static int CRYPTO_SIGN_SECRETKEYBYTES = SodiumConstants.CRYPTO_SIGN_SECRETKEYBYTES;

    public static int keypair(byte[] publicKey, byte[] secretKey) {
        return Sodium.crypto_sign_keypair(publicKey, secretKey);
    }

    public static int seed_keypair(byte[] publicKey, byte[] secretKey, byte[] seed) {
        return Sodium.crypto_sign_seed_keypair(publicKey, secretKey, seed);
    }

    public static byte[] sign(byte[] message, byte[] secretKey) {
        byte[] signedMessage = new byte[CRYPTO_SIGN_BYTES + message.length];
        if (Sodium.crypto_sign(signedMessage, null, message, message.length, secretKey) != 0) {
            return null;
        }
        return signedMessage;
    }

    public static byte[] open(byte[] signedMessage, byte[] publicKey) {
        byte[] message = new byte[signedMessage.length - CRYPTO_SIGN_BYTES];
        if (Sodium.crypto_sign_open(message, null, signedMessage,
                signedMessage.length, publicKey) != 0) {
            return null;
        }
        return message;
    }

    public static byte[] sign_detached(byte[] message, byte[] secretKey) {
        byte[] signature = new byte[CRYPTO_SIGN_BYTES];
        if (Sodium.crypto_sign_detached(signature, null, message, message.length, secretKey) != 0) {
            return null;
        }
        return signature;
    }

    public static boolean verify_detached(byte[] signature, byte[] message, byte[] publicKey) {
        return Sodium.crypto_sign_verify_detached(signature, message, message.length, publicKey) == 0;
    }

    public static byte[] ed25519_secretkey_to_seed(byte[] secretKey) {
        byte[] seed = new byte[CRYPTO_SIGN_SEEDBYTES];
        if (Sodium.crypto_sign_ed25519_sk_to_seed(seed, secretKey) != 0) {
            return null;
        }
        return seed;
    }

    public static byte[] ed25519_secretkey_to_publickey(byte[] secretKey) {
        byte[] publicKey = new byte[CRYPTO_SIGN_PUBLICKEYBYTES];
        if (Sodium.crypto_sign_ed25519_sk_to_pk(publicKey, secretKey) != 0) {
            return null;
        }
        return publicKey;
    }
}
