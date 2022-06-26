package com.example.notes.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.database.Repository
import com.example.notes.models.Note

class NotesViewModel : ViewModel() {

    fun insert(context: Context, note: Note) {
        Repository.insert(context, note)
    }

    fun getAllNotes(context: Context): LiveData<List<Note>>? {
        return Repository.getAllNotes(context)
    }

    fun update(context: Context, note: Note) {
        Repository.update(context, note)
    }

    fun delete(context: Context, note: Note) {
        Repository.delete(context, note)
    }

    fun search(context: Context, data: String) {
        Repository.search(context, data)
    }

    fun pin(context: Context, note: Note, isPinned: Boolean) {
        Repository.pin(context, note, isPinned)
    }

}