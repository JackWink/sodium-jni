package com.jackwink.libsodium;

/**
 * Created by jackwink on 2/16/15.
 */
import com.jackwink.libsodium.jni.Sodium;

public class NaCl {
    public static String sodium_version_string() {
        return Sodium.sodium_version_string();
    }
}
