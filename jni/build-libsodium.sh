#!/bin/bash

SODIUM_HOME=$(pwd)/libsodium

cd $SODIUM_HOME && git pull 

# This can be removed once we pull from a release
if [ ! -e $SODIUM_HOME/configure ]; then
    ./autogen.sh
fi

LIBSODIUM_ARMV6=$SODIUM_HOME/libsodium-android-armv6
LIBSODIUM_ARMV7A=$SODIUM_HOME/libsodium-android-armv7-a
LIBSODIUM_I686=$SODIUM_HOME/libsodium-android-i686
LIBSODIUM_MIPS32=$SODIUM_HOME/libsodium-android-mips32

if [ "$1" = "clean" ]; then
    rm -rf $LIBSODIUM_ARMV6
    rm -rf $LIBSODIUM_ARMV7A
    rm -rf $LIBSODIUM_I686
    rm -rf $LIBSODIUM_MIPS32
fi

# Run the android builds 
if [ ! -d $LIBSODIUM_ARMV6 ]; then
    $SODIUM_HOME/dist-build/android-arm.sh
    echo "build-libsodium: built armv6!"
else
    echo "build-libsodium: skipping armv6, already built!"
fi

if [ ! -d $LIBSODIUM_ARMV7A ]; then
    $SODIUM_HOME/dist-build/android-armv7-a.sh
    echo "build-libsodium: built armv7-a!"
else
    echo "build-libsodium: skipping armv7-a, already built!"
fi

if [ ! -d $LIBSODIUM_MIPS32 ]; then
    $SODIUM_HOME/dist-build/android-mips32.sh
    echo "build-libsodium: built mips32!"
else
    echo "build-libsodium: skipping mips32, already built!"
fi

if [ ! -d $LIBSODIUM_I686 ]; then
    $SODIUM_HOME/dist-build/android-x86.sh
    echo "build-libsodium: built x86!"
else
    echo "build-libsodium: skipping x86, already built!"
fi

echo "libsodium built."
