package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityNotesTakerBinding
import com.example.notes.models.Notes
import java.text.SimpleDateFormat
import java.util.*

class NotesTakerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesTakerBinding
    private lateinit var notes: Notes
    private var isBeingEdited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesTakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            notes = intent.getSerializableExtra("old_note") as Notes
            binding.editTitle.setText(notes.title)
            binding.editNotes.setText(notes.noteText)
            isBeingEdited = true
        } catch (e: Exception) {
            e.stackTrace
        }

        binding.imageViewSave.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val textBody = binding.editNotes.text.toString()

            if (textBody.isEmpty())
                Toast.makeText(this, "Add notes", Toast.LENGTH_SHORT).show()

            val format = SimpleDateFormat("dd MM yyy HH:mm a", Locale.GERMANY)
            val date = Date()

            if (!isBeingEdited)
                notes = Notes()

            notes.title = title
            notes.noteText = textBody
            notes.date = format.format(date)

            //send data back to main activity
            val intent = Intent()
            intent.putExtra("note", notes)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}