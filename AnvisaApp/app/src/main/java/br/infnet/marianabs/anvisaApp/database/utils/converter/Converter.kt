package br.infnet.marianabs.anvisaApp.database.utils.converter

import androidx.room.TypeConverter
import br.infnet.marianabs.anvisaApp.database.utils.SecurityMaker

class Converter {
    @TypeConverter
    fun encrypt(value: SecurityMaker?): String? {
        return value?.getCriptoBase64()
    }

    @TypeConverter
    fun decrypt(value: String?): SecurityMaker? {
        val cryptonite = SecurityMaker()
        cryptonite.setCriptoBase64(value!!)
        return cryptonite
    }
}