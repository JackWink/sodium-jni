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

    public int crypto_sign_keypair(byte[] publicKey, byte[] secretKey) {
        return Sodium.crypto_sign_keypair(publicKey, secretKey);
    }

    public int crypto_sign_seed_keypair(byte[] publicKey, byte[] secretKey, byte[] seed) {
        return Sodium.crypto_sign_seed_keypair(publicKey, secretKey, seed);
    }

    public byte[] crypto_sign(byte[] message, byte[] secretKey) {
        byte[] signedMessage = new byte[SodiumConstants.CRYPTO_SIGN_BYTES + message.length];
        if (Sodium.crypto_sign(signedMessage, null, message, message.length, secretKey) != 0) {
            return null;
        }
        return signedMessage;
    }

    public byte[] crypto_sign_open(byte[] signedMessage, byte[] publicKey) {
        byte[] message = new byte[signedMessage.length - SodiumConstants.CRYPTO_SIGN_BYTES];
        if (Sodium.crypto_sign_open(message, null, signedMessage,
                                    signedMessage.length, publicKey) != 0) {
            return null;
        }
        return message;
    }

    public byte[] crypto_sign_detached(byte[] message, byte[] secretKey) {
        byte[] signature = new byte[SodiumConstants.CRYPTO_SIGN_BYTES];
        if (Sodium.crypto_sign_detached(signature, null, message, message.length, secretKey) != 0) {
            return null;
        }
        return signature;
    }

    public boolean crypto_sign_verify_detached(byte[] signature, byte[] message, byte[] publicKey) {
        return Sodium.crypto_sign_verify_detached(signature, message, message.length, publicKey) == 0;
    }

    static {
        System.loadLibrary("sodiumjni");
    }
}
