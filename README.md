#Sodium-JNI [DEPRECATED]

[![Build Status](https://travis-ci.org/JackWink/sodium-jni.svg?branch=master)](https://travis-ci.org/JackWink/sodium-jni)

A minimal JNI wrapper over [libsodium](https://github.com/jedisct1/libsodium). This is now deprecated. Kalium-JNI (now called [libsodium-jni](https://github.com/joshjdevl/kalium-jni)) seems to have come up with a similar design to this repository with regards to function exports. Since libsodium-jni has both addressed my original complaints and is actively maintained (at least more than this repository), I would advise using it instead.

## Other Java Options For Libsodium

- [Kalium](https://github.com/abstractj/kalium)

## What's the difference?

Kalium is a very sensible Java wrapper over libsodium that depends on `jnr-ffi`. Kalium takes an OOP approach 
whereas Sodium-JNI provides a completely static API, and attempts to use Java primatives over library 
defined classes.

Kalium-JNI mimics the Kalium API, but wraps libsodium using SWIG and the JNI. Sodium-JNI exports more 
functions (`sodium_init`, etc.) and doesn't attempt to provide an OOP wrapper. Sodium-JNI started as a fork
of Kalium-JNI, but has diverged significantly in both the build process and wrapper API.

## Building Requirements

* SWIG
* Android SDK + Android NDK
* Autotools (for building libsodium at the moment)
* set `JAVA_HOME`, `ANDROID_HOME` and `ANDROID_NDK_HOME` environment variables

## Building

Make sure you clone this repo with the `--recursive` flag set, it depends on libsodium as a submodule at the moment.

Run `build-sodiumjni.sh` and a folder `sodiumjni-androidlib` will be generated with the debug and release version of the `.aar` 

The local `.aar` can be included in an [Android studio project](http://stackoverflow.com/questions/24506648/adding-local-aar-files-to-gradle-build-using-flatdirs-is-not-working?lq=1)

## Help

* View issues to see what's being worked on.
