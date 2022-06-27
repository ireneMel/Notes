package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.notes.models.Note

//2

/*
By default, all queries must be executed on a separate thread.
Room has Kotlin coroutines support.
This allows your queries to be annotated with the suspend modifier
and then called from a coroutine or from another suspension function.
 */
@Dao
interface DAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(notes: Note)

    @Delete
    suspend fun delete(notes: Note)

    @Query(
        "UPDATE notes SET title = :title, " +
                "noteText = :noteText WHERE id = :id"
    )
    suspend fun update(id: Int, title: String, noteText: String)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title OR noteText LIKE :data")
    fun search(data: String): LiveData<List<Note>>

    @Query("UPDATE notes SET isPinned = :isPinned WHERE id =:id")
    fun pin(id: Int, isPinned: Boolean)

}