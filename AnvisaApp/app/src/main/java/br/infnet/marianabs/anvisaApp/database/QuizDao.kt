package br.infnet.marianabs.anvisaApp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.infnet.marianabs.anvisaApp.model.Quiz

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz")
    fun getAll(): List<Quiz>

    @Query("SELECT * FROM quiz")
    fun getAllData(): List<Quiz>?

    @Insert
    fun insertAll(vararg quiz: Quiz)

    @Delete
    fun delete(quiz: Quiz)

    @Query("DELETE FROM quiz")
    fun deleteAll()

    @Query("SELECT * FROM quiz WHERE id = :avaliacaoId")
    fun getById(avaliacaoId: Int): Quiz

    @Query("SELECT * FROM quiz WHERE id IN (:avaliacaoIds)")
    fun loadAllByIds(avaliacaoIds: IntArray): List<Quiz>

    @Query("SELECT * FROM quiz WHERE serie = :serieGerado")
    fun loadBySerie(serieGerado: String): List<Quiz>?
}