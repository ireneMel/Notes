package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.notes.R
import com.example.notes.databinding.NotesListBinding
import com.example.notes.listeners.NotesClickListener
import com.example.notes.models.Note

class NotesListAdapter(
    private val listener: NotesClickListener,
    private var notesList: List<Note>
) : Adapter<NotesListAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NotesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = notesList[position]

        with(holder.binding) {
            title.text = item.title
            title.isSelected = true
            notesText.text = item.noteText
            dateText.text = item.date
            dateText.isSelected = true

            if (item.isPinned)
                imagePin.setImageResource(R.drawable.ic_pin_24)
            else
                imagePin.setImageResource(0)

            notesContainer.setOnClickListener {
                listener.onClick(
                    notesList[holder.bindingAdapterPosition]
                )
            }

            notesContainer.setOnLongClickListener {
                listener.onLongClick(notesList[holder.bindingAdapterPosition], notesContainer)
                return@setOnLongClickListener true
            }
        }
    }

    fun setList(noteList: ArrayList<Note>) {
        this.notesList = noteList;
        notifyDataSetChanged()
    }

    fun filterList(filterList: List<Note>) {
        notesList = filterList
        notifyDataSetChanged()
    }

    private fun getColor(): Int {
        val colors = listOf(R.color.pink, R.color.red, R.color.yellow, R.color.violet, R.color.blue)
        return colors.random()
    }

    class NotesViewHolder(val binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root)

}