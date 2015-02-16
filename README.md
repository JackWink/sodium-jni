# Building

## Requirements

* SWIG
* Android SDK + Android NDK
* Autotools (for building libsodium at the moment)
* set `JAVA_HOME`, `ANDROID_HOME` and `ANDROID_NDK_HOME` environment variables

## Building

Run `build-sodiumjni.sh` then compile with `./gradlew build.

The AAR packages will be in `app/build/outputs/aar/`, this can be included in an [Android studio project](http://stackoverflow.com/questions/24506648/adding-local-aar-files-to-gradle-build-using-flatdirs-is-not-working?lq=1)
