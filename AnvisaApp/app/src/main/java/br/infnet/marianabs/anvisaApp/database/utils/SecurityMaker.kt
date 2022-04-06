package br.infnet.marianabs.anvisaApp.database.utils

import android.util.Base64

class SecurityMaker {

    companion object{

        @JvmStatic

        val encrypting = SecurityHelper()
    }

    private var crypto: ByteArray? = null

    fun getCriptoBase64(): String?{
        return Base64.encodeToString(crypto,Base64.DEFAULT)
    }
    fun setCriptoBase64(value: String?){
        crypto = Base64.decode(value,Base64.DEFAULT)
    }

    fun getClearText(): String?{
        return encrypting.decipher(crypto!!)
    }
    fun setClearText(value: String?){
        crypto = encrypting.cipher(value!!)
    }
}