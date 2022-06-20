package com.example.notes.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardView: CardView
    val title: TextView
    val notesText: TextView
    val date: TextView
    val imagePin: ImageView

    init {
        cardView = itemView.findViewById(R.id.notes_container)
        title = itemView.findViewById(R.id.title)
        notesText = itemView.findViewById(R.id.notes_text)
        date = itemView.findViewById(R.id.date_text)
        imagePin = itemView.findViewById(R.id.image_pin)
    }
}