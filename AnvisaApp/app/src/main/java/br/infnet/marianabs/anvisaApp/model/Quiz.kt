package br.infnet.marianabs.anvisaApp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.infnet.marianabs.anvisaApp.database.utils.SecurityMaker

@Entity
data class Quiz (
    @PrimaryKey(autoGenerate = true) val id: Int = 0 ,
    @ColumnInfo(name = "serie") val serie: String ,
    @ColumnInfo(name = "organization") val organization: SecurityMaker? ,
    @ColumnInfo(name = "location") val location: String? ,
    @ColumnInfo(name = "answers") val answers: String?
)