package com.example.notes

import com.example.notes.adapters.NotesListAdapter
import com.example.notes.models.Note

class SearchNotes {

    companion object {

        fun filter(
            newText: String?,
            notesList: List<Note>,
            noteListAdapter: NotesListAdapter
        ) {
            val filteredList = ArrayList<Note>()
            for (note in notesList) {
                if (note.title.lowercase().contains(newText?.lowercase() ?: "error")
                    || note.noteText.lowercase().contains(newText?.lowercase() ?: "error")
                )
                    filteredList.add(note)
            }
            noteListAdapter.filterList(filteredList)
        }

    }
}