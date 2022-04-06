package br.infnet.marianabs.anvisaApp.database

import android.content.Context
import androidx.room.Room

class DbFab {
    companion object{
        private var database: Database? = null

        fun getInstance(context: Context?) : Database {
            if(database == null)
                database = Room.databaseBuilder(
                    context!!,
                    Database::class.java, "anvisareport"
                ).fallbackToDestructiveMigration().build()

            return database as Database
        }

    }
}