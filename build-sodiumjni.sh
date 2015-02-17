#!/bin/bash

##
# Steps:
#
#   1. Build libsodium shared libraries for Android
#
#   2. Run swig to create sodium_wrap.c 
#
#   3. Run ndk-build to build our sodiumjni shared libraries
#
#   4. Run gradle build script

# Fail if anything returns non-zero
set -e

require_env_var() {
    if [ -z "$1" ]; then
        echo "build-libsodium.sh: ERROR: Required env variable '$2' not set."
        echo "build-libsodium.sh: Exiting!"
        exit 1
    fi
}

require_env_var "$JAVA_HOME" "JAVA_HOME"
require_env_var "$ANDROID_HOME" "ANDROID_HOME"
require_env_var "$ANDROID_NDK_HOME" "ANDROID_NDK_HOME"

# Root location of the sodium-jni project
SODIUMJNI_HOME=$(pwd)

# Location of the sodiumJNI java classes (where SWIG should put the generated files)
SODIUMJNI_SRC_ROOT="${SODIUMJNI_HOME}/app/src"

# libsodium build location
LIBSODIUM_JNI_HOME="${SODIUMJNI_HOME}/jni"

##
#   Step 1
#
cd $LIBSODIUM_JNI_HOME
./build-libsodium.sh

##
#   Step 2 
#
cd $LIBSODIUM_JNI_HOME

SODIUMJNI_PACKAGE=com.jackwink.libsodium.jni
SODIUMJNI_JAVA_PACKAGE_ROOT=$SODIUMJNI_SRC_ROOT/main/java/com/jackwink/libsodium/jni

rm -rf $SODIUMJNI_JAVA_PACKAGE_ROOT
mkdir -p $SODIUMJNI_JAVA_PACKAGE_ROOT
export C_INCLUDE_PATH="${JAVA_HOME}/include:${JAVA_HOME}/include/linux:/System/Library/Frameworks/JavaVM.framework/Headers"

rm -f *.c
swig -java -package $SODIUMJNI_PACKAGE -outdir $SODIUMJNI_JAVA_PACKAGE_ROOT sodium.i

##
#   Step 3
#

cd $LIBSODIUM_JNI_HOME
ndk-build

# do some cleanup
rm -rf $SODIUMJNI_HOME/obj
rm -rf $SODIUMJNI_SRC_ROOT/main/lib
mv $SODIUMJNI_HOME/libs $SODIUMJNI_SRC_ROOT/main/lib

##
#   Step 4
#

cd $SODIUMJNI_HOME
./gradlew build

rm -rf $SODIUMJNI_HOME/build 
rm -rf $SODIUMJNI_HOME/sodiumjni-androidlib
mv $SODIUMJNI_HOME/app/build/outputs/aar $SODIUMJNI_HOME/sodiumjni-androidlib
rm -rf $SODIUMJNI_HOME/app/build
