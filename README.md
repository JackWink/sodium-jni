# Building

## Requirements

* SWIG
* Android SDK + Android NDK
* Autotools (for building libsodium at the moment)
* set `JAVA_HOME`, `ANDROID_HOME` and `ANDROID_NDK_HOME` environment variables

## Building

Run `build-sodiumjni.sh` and a folder `sodiumjni-androidlib` will be generated with the debug and release version of the `.aar` 

The local `.aar` can be included in an [Android studio project](http://stackoverflow.com/questions/24506648/adding-local-aar-files-to-gradle-build-using-flatdirs-is-not-working?lq=1)

# TODO

* Either release the .aar to maven, or figure out how to generate a `.jar` instead
* Double check ABIs, I think there might be some missing platforms
* Add bootstrap script to install swig
* Add bootstrap script to install android NDK
