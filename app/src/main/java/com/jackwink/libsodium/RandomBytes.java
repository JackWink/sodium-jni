package com.jackwink.libsodium;

import com.jackwink.libsodium.jni.Sodium;

/**
 * Created by jackwink on 2/21/15.
 */
public class RandomBytes {

    public static void fillBuffer(byte[] buf) {
        Sodium.randombytes_buf(buf, buf.length);
    }

    public static int random() {
        return Sodium.randombytes_random();
    }

    public static int uniform(int upper_bound) {
        return Sodium.randombytes_uniform(upper_bound);
    }
}
