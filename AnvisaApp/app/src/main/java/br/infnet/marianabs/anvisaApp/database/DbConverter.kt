package br.infnet.marianabs.anvisaApp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.infnet.marianabs.anvisaApp.database.utils.converter.Converter
import br.infnet.marianabs.anvisaApp.model.Quiz

@Database(entities = [Quiz::class], version = 1)
@TypeConverters(Converter::class)


abstract class Database: RoomDatabase() {
    abstract fun quizDao(): QuizDao
}