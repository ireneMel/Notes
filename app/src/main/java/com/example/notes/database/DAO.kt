package com.example.notes.database

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

    //TODO add suspend

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Query(
        "UPDATE notes SET title = :title, " +
                "noteText = :noteText WHERE id = :id"
    )
    fun update(id: Int, title: String, noteText: String)

    @Delete
    fun delete(notes: Notes)


    @Query("DELETE FROM notes")
    fun deleteAll()

    //TODO replace id with date?
    //TODO LiveData<List<Notes>>
    @Query("SELECT * FROM notes ORDER BY id DESC")
    //suspend
    fun getAllNotes(): List<Notes>

    @Query("UPDATE notes SET isPinned = :isPinned WHERE id =:id")
    fun pin(id: Int, isPinned: Boolean) {

    }

}