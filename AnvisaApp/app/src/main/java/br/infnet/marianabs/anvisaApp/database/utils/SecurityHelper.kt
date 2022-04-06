package br.infnet.marianabs.anvisaApp.database.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class SecurityHelper {

    val keyStore: KeyStore =
        KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    fun getSecretKey(): SecretKey? {
        var key: SecretKey? = null
        if(keyStore.containsAlias("chaveCripto")) {
            val entrada = keyStore.getEntry("chaveCripto", null) as?
                    KeyStore.SecretKeyEntry
            key = entrada?.secretKey
        }

        else
        {
            val builder = KeyGenParameterSpec.Builder("chaveCripto",
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT)


            val keySpecifications = builder.setKeySize(256)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_PKCS7).build()

            val kg = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            kg.init(keySpecifications)

            key = kg.generateKey()
        }
        return key
    }

    fun cipher(original: String): ByteArray {
        var key = getSecretKey()
        return cipher(original,key)
    }

    fun cipher(original: String , key: SecretKey?): ByteArray {
        if (key != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                init(Cipher.ENCRYPT_MODE,key)
                var valueOfEncrypt = doFinal(original.toByteArray())
                var ivCripto = ByteArray(16)
                iv.copyInto(ivCripto,0,0,16)
                return ivCripto + valueOfEncrypt
            }
        } else return byteArrayOf()
    }

    fun decipher(crypto: ByteArray): String{
        var key = getSecretKey()
        return decipher(crypto,key)
    }

    fun decipher(crypto: ByteArray , key: SecretKey?): String{
        if (key != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                var ivCripto = ByteArray(16)
                var valueCrypto = ByteArray(crypto.size-16)

                crypto.copyInto(ivCripto,0,0,16)
                crypto.copyInto(valueCrypto,0,16,crypto.size)

                val ivParams = IvParameterSpec(ivCripto)

                init(Cipher.DECRYPT_MODE,key,ivParams)

                return String(doFinal(valueCrypto))
            }
        } else return ""
    }

    fun getHash(txt: String): String{
        var md = MessageDigest.getInstance("MD5")
        return Base64.encodeToString(
            md.digest(txt.toByteArray()),Base64.DEFAULT).trimEnd()
    }
}