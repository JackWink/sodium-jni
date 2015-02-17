package com.jackwink.libsodium;

/**
 * Created by jackwink on 2/16/15.
 */
import com.jackwink.libsodium.jni.Sodium;

public class NaCl {

    public static int sodium_init() {
        return Sodium.sodium_init();
    }

    public static String sodium_version_string() {
        return Sodium.sodium_version_string();
    }

    public static int crypto_aead_chacha20poly1305_encrypt(byte[] c, int[] clen, byte[] m, int mlen, byte[] ad, int adlen, byte[] nsec, byte[] npub, byte[] k) {
        return Sodium.crypto_aead_chacha20poly1305_encrypt(c, clen, m, mlen, ad, adlen, nsec, npub, k);
    }

    public static int crypto_aead_chacha20poly1305_decrypt(byte[] m, int[] mlen, byte[] nsec, byte[] c, int clen, byte[] ad, int adlen, byte[] npub, byte[] k) {
        return Sodium.crypto_aead_chacha20poly1305_decrypt(m, mlen, nsec, c, clen, ad, adlen, npub, k);
    }

    public static int crypto_hash_sha256(byte[] out, byte[] in, int inlen) {
        return Sodium.crypto_hash_sha256(out, in, inlen);
    }

    public static int crypto_hash_sha512(byte[] out, byte[] in, int inlen) {
        return Sodium.crypto_hash_sha512(out, in, inlen);
    }

    public static int crypto_generichash_blake2b(byte[] out, long outlen, byte[] in, int inlen, byte[] key, long keylen) {
        return Sodium.crypto_generichash_blake2b(out, outlen, in, inlen, key, keylen);
    }

    public static int crypto_pwhash_scryptsalsa208sha256(byte[] out, int outlen, String passwd, int passwdlen, byte[] salt, int opslimit, long memlimit) {
        return Sodium.crypto_pwhash_scryptsalsa208sha256(out, outlen, passwd, passwdlen, salt, opslimit, memlimit);
    }

    public static int crypto_box_curve25519xsalsa20poly1305_keypair(byte[] pk, byte[] sk) {
        return Sodium.crypto_box_curve25519xsalsa20poly1305_keypair(pk, sk);
    }

    public static void randombytes(byte[] buf, int size) {
        Sodium.randombytes(buf, size);
    }

    public static int crypto_box_curve25519xsalsa20poly1305(byte[] c, byte[] m, int mlen, byte[] n, byte[] pk, byte[] sk) {
        return Sodium.crypto_box_curve25519xsalsa20poly1305(c, m, mlen, n, pk, sk);
    }

    public static int crypto_box_curve25519xsalsa20poly1305_open(byte[] m, byte[] c, int clen, byte[] n, byte[] pk, byte[] sk) {
        return Sodium.crypto_box_curve25519xsalsa20poly1305_open(m, c, clen, n, pk, sk);
    }

    public static int crypto_scalarmult_curve25519(byte[] q, byte[] n, byte[] p) {
        return Sodium.crypto_scalarmult_curve25519(q, n, p);
    }

    public static int crypto_secretbox_xsalsa20poly1305(byte[] c, byte[] m, int mlen, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_xsalsa20poly1305(c, m, mlen, n, k);
    }

    public static int crypto_secretbox_xsalsa20poly1305_open(byte[] m, byte[] c, int clen, byte[] n, byte[] k) {
        return Sodium.crypto_secretbox_xsalsa20poly1305_open(m, c, clen, n, k);
    }

    public static int crypto_sign_ed25519_seed_keypair(byte[] pk, byte[] sk, byte[] seed) {
        return Sodium.crypto_sign_ed25519_seed_keypair(pk, sk, seed);
    }

    public static int crypto_sign_ed25519(byte[] sm, int[] smlen, byte[] m, int mlen, byte[] sk) {
        return Sodium.crypto_sign_ed25519(sm, smlen, m, mlen, sk);
    }

    public static int crypto_sign_ed25519_open(byte[] m, int[] mlen, byte[] sm, int smlen, byte[] pk) {
        return Sodium.crypto_sign_ed25519_open(m, mlen, sm, smlen, pk);
    }

    public static int crypto_stream_xsalsa20(byte[] c, int clen, byte[] n, byte[] k) {
        return Sodium.crypto_stream_xsalsa20(c, clen, n, k);
    }

    public static int crypto_stream_xsalsa20_xor(byte[] c, byte[] m, int mlen, byte[] n, byte[] k) {
        return Sodium.crypto_stream_xsalsa20_xor(c, m, mlen, n, k);
    }

    static {
        System.loadLibrary("sodiumjni");
    }
}
