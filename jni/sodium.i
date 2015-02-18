/* sodium.i */
%module Sodium
%include "typemaps.i"
/*%include "various.i"*/
/*%include "stdint.i"
%include "arrays_java.i"
%include "carrays.i"
*/

/**
 * Defines unsigned char * (C) -> byte[] (Java)
 *
 * Following is taken from SWIG's various.i and adapted for unsigned chars 
 */
%typemap(jni) unsigned char *BYTE "jbyteArray"
%typemap(jtype) unsigned char *BYTE "byte[]"
%typemap(jstype) unsigned char *BYTE "byte[]"
%typemap(in) unsigned char *BYTE {
  $1 = (unsigned char *) JCALL2(GetByteArrayElements, jenv, $input, 0); 
}

%typemap(argout) unsigned char *BYTE {
  JCALL3(ReleaseByteArrayElements, jenv, $input, (jbyte *) $1, 0); 
}

%typemap(javain) unsigned char *BYTE "$javainput"

/* Prevent default freearg typemap from being used */
%typemap(freearg) unsigned char *BYTE ""

%apply unsigned char *BYTE { unsigned char * }; 

/* See Above.
 * Previous is taken from SWIG's various.i and adapted for unsigned chars 
 **/


/* SWIG uses BigInts for unsigned long longs, but BigInts are obnoxious
 * and so we'd like to use a java long. typemaps.i defines unsigned long as a java long,
 * so although we do lose 'unsigned', hopefully we can rely on the callers not to pass in
 * a negative size.
 */
%apply unsigned long { unsigned long long };

%javaconst(1);

/* Start Sodium Definitions */
%{
#include "sodium.h"
%}

/* core.h */
int sodium_init(void);

const char *sodium_version_string(void);

/* crypto_sign.h & crypto_sign_*.h */

#define CRYPTO_SIGN_BYTES 64 
#define CRYPTO_SIGN_SEEDBYTES 32 
#define CRYPTO_SIGN_PUBLICKEYBYTES 32
#define CRYPTO_SIGN_SECRETKEYBYTES (32 + 32)

int crypto_sign_keypair(unsigned char *pk, unsigned char *sk);

int crypto_sign_seed_keypair(unsigned char *pk, unsigned char *sk,
                             const unsigned char *seed);

int crypto_sign(unsigned char *sm, unsigned long long *smlem,
                const unsigned char *m, unsigned long long mlen,
                const unsigned char *sk);

int crypto_sign_open(unsigned char *m, unsigned long long *mlen,
                     const unsigned char *sm, unsigned long long smlen,
                     const unsigned char *pk);

int crypto_sign_detached(unsigned char *sig, unsigned long long *slen,
                         const unsigned char *m, unsigned long long mlen,
                         const unsigned char *sk);

int crypto_sign_verify_detached(const unsigned char *sig,
                                const unsigned char *m,
                                unsigned long long mlen,
                                const unsigned char *pk);

int crypto_sign_ed25519_sk_to_seed(unsigned char *seed,
                                   const unsigned char *sk);

int crypto_sign_ed25519_sk_to_pk(unsigned char *pk, const unsigned char *sk);


