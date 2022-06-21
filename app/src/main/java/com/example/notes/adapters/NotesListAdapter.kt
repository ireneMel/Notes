package com.example.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.notes.NotesClickListener
import com.example.notes.R
import com.example.notes.models.Notes

class NotesListAdapter() : Adapter<NotesViewHolder>() {

    private lateinit var context: Context
    private lateinit var listener: NotesClickListener

    private var data = listOf<Notes>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(context: Context, listener: NotesClickListener, data: List<Notes>) : this() {
        this.listener = listener
        this.data = data
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = data[position]

        holder.title.text = item.title
        holder.title.isSelected = true

        holder.notesText.text = item.noteText

        holder.date.text = item.date
        holder.date.isSelected = true


        if (item.isPinned)
            holder.imagePin.setImageResource(R.drawable.ic_pin_24)
        else
            holder.imagePin.setImageResource(0)

        holder.cardView.setBackgroundColor(holder.itemView.resources.getColor(R.color.violet, null))

        holder.cardView.setOnClickListener {
            listener.onClick(
                data[holder.bindingAdapterPosition]
            )
        }

        holder.cardView.setOnLongClickListener {
            listener.onLongClick(data[holder.bindingAdapterPosition], holder.cardView)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = data.size

    fun filterList(filterList: List<Notes>) {
        data = filterList
        notifyDataSetChanged()
    }

    private fun getColor(): Int {
        val colors = listOf(R.color.pink, R.color.red, R.color.yellow, R.color.violet, R.color.blue)
        return colors.random()
    }
}