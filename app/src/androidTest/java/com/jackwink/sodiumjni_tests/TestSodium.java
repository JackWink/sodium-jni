package com.jackwink.sodiumjni_tests;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.jackwink.libsodium.CryptoAEAD;
import com.jackwink.libsodium.CryptoAuth;
import com.jackwink.libsodium.CryptoBox;
import com.jackwink.libsodium.CryptoSecretBox;
import com.jackwink.libsodium.CryptoSign;
import com.jackwink.libsodium.RandomBytes;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jackwink on 2/16/15.
 */
public class TestSodium extends TestCase {
    final static String TAG = "sodiumjni";

    @SmallTest
    public void testSignature() {


        byte[] publicKey = new byte[CryptoSign.CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] secretKey = new byte[CryptoSign.CRYPTO_SIGN_SECRETKEYBYTES];
        CryptoSign.keypair(publicKey, secretKey);

        byte[] original = "test".getBytes();
        byte[] testSig = CryptoSign.sign(original, secretKey);
        byte[] message = CryptoSign.open(testSig, publicKey);

        if (!Arrays.equals(original, message)) {
            fail("Message not verified.");
        }

        byte[] justSig = CryptoSign.sign_detached(original, secretKey);
        boolean success = CryptoSign.verify_detached(justSig, original, publicKey);

        if (!success) {
            fail("Message detached not verified.");
        }

    }

    @SmallTest
    public void testSecretBoxEasy() {
        byte[] message = "hello!".getBytes();
        byte[] nonce = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_NONCEBYTES];
        byte[] key = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_KEYBYTES];

        RandomBytes.fillBuffer(nonce);
        RandomBytes.fillBuffer(key);
        byte[] ciphertext = CryptoSecretBox.create_easy(message, nonce, key);
        if (ciphertext == null) {
            fail("Could not create ciphertext");
        }

        byte[] decrypted = CryptoSecretBox.open_easy(ciphertext, nonce, key);
        if (decrypted == null) {
            fail("Message forged");
        }
    }

    @SmallTest
    public void testSecretBoxDetached() {
        byte[] message = "hello!".getBytes();
        byte[] mac = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_MACBYTES];
        byte[] nonce = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_NONCEBYTES];
        byte[] key = new byte[CryptoSecretBox.CRYPTO_SECRETBOX_KEYBYTES];

        RandomBytes.fillBuffer(nonce);
        RandomBytes.fillBuffer(key);
        byte[] ciphertext = CryptoSecretBox.create_detached(mac, message, nonce, key);
        if (ciphertext == null) {
            fail("Could not create ciphertext detached!");
        }

        byte[] decrypted = CryptoSecretBox.open_detached(ciphertext, mac, nonce, key);
        if (decrypted == null) {
            fail("Message forged");
        }
    }

    @SmallTest
    public void testCryptoAuth() {
        byte[] message = "hello!".getBytes();
        byte[] key = new byte[CryptoAuth.CRYPTO_AUTH_KEYBYTES];

        RandomBytes.fillBuffer(key);
        byte[] mac = CryptoAuth.create(message, key);
        if (mac == null) {
            fail("Could not create MAC.");
        }

        if (!CryptoAuth.verify(mac, message, key)) {
            fail("Could not verify valid MAC.");
        }

        if (CryptoAuth.verify(mac, "hell0!".getBytes(), key)) {
            fail("verified wrong message!");
        }
    }

    @SmallTest
    public void testCryptoBox() {

        byte[] message = "hello!".getBytes();
        byte[] alice_publickey = new byte[CryptoBox.CRYPTO_BOX_PUBLICKEYBYTES];
        byte[] alice_secretkey = new byte[CryptoBox.CRYPTO_BOX_SECRETKEYBYTES];
        CryptoBox.keypair(alice_publickey, alice_secretkey);

        byte[] bob_publickey = new byte[CryptoBox.CRYPTO_BOX_PUBLICKEYBYTES];
        byte[] bob_secretkey = new byte[CryptoBox.CRYPTO_BOX_SECRETKEYBYTES];
        CryptoBox.keypair(bob_publickey, bob_secretkey);

        byte[] nonce = new byte[CryptoBox.CRYPTO_BOX_NONCEBYTES];
        RandomBytes.fillBuffer(nonce);

        byte[] ciphertext = CryptoBox.create_easy(message, nonce, bob_publickey, alice_secretkey);
        if (ciphertext == null) {
            fail("Could not create box for bob and alice!");
        }

        byte[] decrypted = CryptoBox.open_easy(ciphertext, nonce, alice_publickey, bob_secretkey);
        if (decrypted == null) {
            fail("Could not open box for bob and alice!");
        }
    }

    @SmallTest
    public void testCryptoBoxDetached() {

        byte[] message = "hello!".getBytes();
        byte[] mac = new byte[CryptoBox.CRYPTO_BOX_MACBYTES];
        byte[] alice_publickey = new byte[CryptoBox.CRYPTO_BOX_PUBLICKEYBYTES];
        byte[] alice_secretkey = new byte[CryptoBox.CRYPTO_BOX_SECRETKEYBYTES];
        CryptoBox.keypair(alice_publickey, alice_secretkey);

        byte[] bob_publickey = new byte[CryptoBox.CRYPTO_BOX_PUBLICKEYBYTES];
        byte[] bob_secretkey = new byte[CryptoBox.CRYPTO_BOX_SECRETKEYBYTES];
        CryptoBox.keypair(bob_publickey, bob_secretkey);

        byte[] nonce = new byte[CryptoBox.CRYPTO_BOX_NONCEBYTES];
        RandomBytes.fillBuffer(nonce);

        byte[] ciphertext = CryptoBox.create_detached(mac, message, nonce, bob_publickey, alice_secretkey);
        if (ciphertext == null) {
            fail("Could not create box for bob and alice!");
        }

        byte[] decrypted = CryptoBox.open_detached(ciphertext, mac, nonce, alice_publickey, bob_secretkey);
        if (decrypted == null) {
            fail("Could not open box for bob and alice!");
        }
    }

    @SmallTest
    public void testCryptoAEAD() {
        byte[] message = "hello!".getBytes();
        byte[] additionalData = "goodbye!".getBytes();

        byte[] key = new byte[CryptoAEAD.CRYPTO_AED_CHACHA20POLY1305_KEYBYTES];
        byte[] nonce = new byte[CryptoAEAD.CRYPTO_AED_CHACHA20POLY1305_NONCEBYTES];

        RandomBytes.fillBuffer(nonce);
        RandomBytes.fillBuffer(key);

        byte[] ciphertext = CryptoAEAD.crypto_aead_chacha20poly1305_encrypt(message, additionalData, nonce, key);
        if (ciphertext == null) {
            fail("Could not create CryptoAEAD ciphertext!");
        }

        byte[] decrypted = CryptoAEAD.crypto_aead_chacha20poly1305_decrypt(ciphertext, additionalData, nonce, key);
        if (decrypted == null) {
            fail("Could not decrypt CryptoAEAD ciphertext!");
        }
    }
}
