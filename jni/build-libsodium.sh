#!/bin/bash

export SODIUM_HOME="./libsodium"

cd $SODIUM_HOME && git pull && ./autogen.sh

# Run the android builds 
./dist-build/android-arm.sh
./dist-build/android-armv7-a.sh
./dist-build/android-mips32.sh
./dist-build/android-x86.sh

echo "libsodium built."
