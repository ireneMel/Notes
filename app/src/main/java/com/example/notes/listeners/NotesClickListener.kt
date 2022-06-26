package com.example.notes.listeners

import androidx.cardview.widget.CardView
import com.example.notes.models.Note

interface NotesClickListener {
    fun onClick(notes: Note)
    fun onLongClick(notes: Note, cardView: CardView)
}