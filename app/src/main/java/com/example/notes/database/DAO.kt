package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
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
    fun insert(notes: Note)

    @Delete
    fun delete(notes: Note)

    @Query(
        "UPDATE notes SET title = :title, " +
                "noteText = :noteText WHERE id = :id"
    )
    fun update(id: Int, title: String, noteText: String)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title OR noteText LIKE :data")
    fun search(data: String): LiveData<List<Note>>

    @Query("UPDATE notes SET isPinned = :isPinned WHERE id =:id")
    fun pin(id: Int, isPinned: Boolean)

}