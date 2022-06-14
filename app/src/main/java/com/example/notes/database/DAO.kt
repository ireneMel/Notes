package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.notes.models.Notes

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
    suspend fun insert(notes: Notes)

    @Query(
        "UPDATE notes SET title = :title, " +
                "noteText = :noteText WHERE id = :id"
    )
    suspend fun update(id: Int, title: String, noteText: String)

    @Delete
    suspend fun delete(notes: Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    //TODO replace id with date?
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Notes>>

}