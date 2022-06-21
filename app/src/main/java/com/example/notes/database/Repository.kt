package com.example.notes.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notes.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

//4

class Repository {
    companion object {
        private var notesRoomDatabase: NotesRoomDatabase? = null


        private fun initDB(context: Context): NotesRoomDatabase? {
            return NotesRoomDatabase.getInstance(context)
        }

        fun insert(context: Context, note: Note) {
            notesRoomDatabase = initDB(context)

            CoroutineScope(IO).launch {
                notesRoomDatabase?.notesDAO()?.insert(note)
            }
        }

        fun getAllNotes(context: Context): LiveData<List<Note>>? {
            notesRoomDatabase = initDB(context)
            return notesRoomDatabase?.notesDAO()?.getAllNotes()
        }

        //TODO redo update?
        fun update(context: Context, note: Note) {
            notesRoomDatabase = initDB(context)
            CoroutineScope(IO).launch {
                notesRoomDatabase?.notesDAO()?.update(note.id, note.title, note.noteText)
            }
        }

        fun delete(context: Context, note: Note) {
            notesRoomDatabase = initDB(context)
            CoroutineScope(IO).launch {
                notesRoomDatabase?.notesDAO()?.delete(note)
            }
        }
    }
}