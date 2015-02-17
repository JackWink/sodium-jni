/* sodium.i */
%module Sodium
%include "typemaps.i"
%include "stdint.i"
%include "arrays_java.i"
%include "carrays.i"
%include "various.i"

/* SWIG uses BigInts for unsigned long longs, but BigInts are obnoxious
 * and so we'd like to use a java long. typemaps.i defines unsigned long as a java long,
 * so although we do lose 'unsigned', hopefully we can rely on the callers not to pass in
 * a negative size.
 */
%apply unsigned long { unsigned long long };

/* This looks pretty wrong, libsodium seems to be using 'unsigned long long *' when it wants to pass
 * back a length value, this was taken right from kalium-jni */
%apply long[] {unsigned long long *};

/* Not sure if this is used yet, but size_t is most definitely used in some places in libsodium */
%apply unsigned long { size_t };

/* Character/Byte Types (taken from kalium-jni) */
%typemap(jni) unsigned char *"jbyteArray"
%typemap(jni) char *BYTE "jbyteArray"

%typemap(jtype) unsigned char *"byte[]"
%typemap(jtype) char *BYTE "byte[]"

%typemap(jstype) unsigned char *"byte[]"
%typemap(jstype) char *BYTE "byte[]"

%typemap(in) unsigned char *{
    $1 = (unsigned char *) JCALL2(GetByteArrayElements, jenv, $input, 0);
}

%typemap(in) char *BYTE {
    $1 = (char *) JCALL2(GetByteArrayElements, jenv, $input, 0);
}

%typemap(argout) unsigned char *{
    JCALL3(ReleaseByteArrayElements, jenv, $input, (jbyte *) $1, 0);
}

%typemap(argout) char *BYTE {
    JCALL3(ReleaseByteArrayElements, jenv, $input, (jbyte *) $1, 0);
}

%typemap(javain) unsigned char *"$javainput"
%typemap(javain) char *BYTE "$javainput"

/* Prevent default freearg typemap from being used */
%typemap(freearg) unsigned char *""
%typemap(freearg) char *BYTE ""

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

int crypto_sign(unsigned char *sm, unsigned long long *smlen_p,
                const unsigned char *m, unsigned long long mlen,
                const unsigned char *sk);

int crypto_sign_open(unsigned char *m, unsigned long long *mlen_p,
                     const unsigned char *sm, unsigned long long smlen,
                     const unsigned char *pk);

int crypto_sign_detached(unsigned char *sig, unsigned long long *siglen_p,
                         const unsigned char *m, unsigned long long mlen,
                         const unsigned char *sk);

int crypto_sign_verify_detached(const unsigned char *sig,
                                const unsigned char *m,
                                unsigned long long mlen,
                                const unsigned char *pk);

/* Old */

int crypto_aead_chacha20poly1305_encrypt(unsigned char *c,
                                         unsigned long long *clen,
                                         const unsigned char *m,
                                         unsigned long long mlen,
                                         const unsigned char *ad,
                                         unsigned long long adlen,
                                         const unsigned char *nsec,
                                         const unsigned char *npub,
                                         const unsigned char *k);

int crypto_aead_chacha20poly1305_decrypt(unsigned char *m,
                                         unsigned long long *mlen,
                                         unsigned char *nsec,
                                         const unsigned char *c,
                                         unsigned long long clen,
                                         const unsigned char *ad,
                                         unsigned long long adlen,
                                         const unsigned char *npub,
                                         const unsigned char *k);

int crypto_hash_sha256(unsigned char *out, const unsigned char *in,
                       unsigned long long inlen);

int crypto_hash_sha512(unsigned char *out, const unsigned char *in,
                       unsigned long long inlen);

int crypto_generichash_blake2b(unsigned char *out, size_t outlen,
                               const unsigned char *in,
                               unsigned long long inlen,
                               const unsigned char *key, size_t keylen);
                           
int crypto_pwhash_scryptsalsa208sha256(unsigned char * const out,
                                    unsigned long long outlen,
                                    const char * const passwd,
                                    unsigned long long passwdlen,
                                    const unsigned char * const salt,
                                    unsigned long long opslimit,
                                    size_t memlimit);

int crypto_box_curve25519xsalsa20poly1305_keypair(unsigned char *pk,
                                                  unsigned char *sk);

void        randombytes(unsigned char *buf, unsigned long long size);
/* void        randombytes(unsigned char * const buf, const unsigned long long buf_len); */


int crypto_box_curve25519xsalsa20poly1305(unsigned char *c,
                                          const unsigned char *m,
                                          unsigned long long mlen,
                                          const unsigned char *n,
                                          const unsigned char *pk,
                                          const unsigned char *sk);


int crypto_box_curve25519xsalsa20poly1305_open(unsigned char *m,
                                               const unsigned char *c,
                                               unsigned long long clen,
                                               const unsigned char *n,
                                               const unsigned char *pk,
                                               const unsigned char *sk);


int crypto_scalarmult_curve25519(unsigned char *q, const unsigned char *n,
                                 const unsigned char *p);


int crypto_secretbox_xsalsa20poly1305(unsigned char *c,
                                      const unsigned char *m,
                                      unsigned long long mlen,
                                      const unsigned char *n,
                                      const unsigned char *k);

int crypto_secretbox_xsalsa20poly1305_open(unsigned char *m,
                                           const unsigned char *c,
                                           unsigned long long clen,
                                           const unsigned char *n,
                                           const unsigned char *k);

int crypto_sign_ed25519_seed_keypair(unsigned char *pk, unsigned char *sk,
                                     const unsigned char *seed);

int crypto_sign_ed25519(unsigned char *sm, unsigned long long *smlen,
                        const unsigned char *m, unsigned long long mlen,
                        const unsigned char *sk);

int crypto_sign_ed25519_open(unsigned char *m, unsigned long long *mlen,
                             const unsigned char *sm, unsigned long long smlen,
                             const unsigned char *pk);
                             
                             
int crypto_stream_xsalsa20(unsigned char *c, unsigned long long clen,
              const unsigned char *n, const unsigned char *k);


int crypto_stream_xsalsa20_xor(unsigned char *c, const unsigned char *m,
                  unsigned long long mlen, const unsigned char *n,
                  const unsigned char *k);
