package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.adapters.NotesListAdapter
import com.example.notes.database.NotesRoomDatabase
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.models.Notes
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NotesRoomDatabase
    private var notesList: MutableList<Notes> = ArrayList()
    private lateinit var noteListAdapter: NotesListAdapter

    private fun getNotes() {
        lifecycleScope.launch {
            notesList = database.notesDAO().getAllNotes().toMutableList()
            updateRecycler(notesList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = NotesRoomDatabase.getInstance(this)

        //initialize notesList
        getNotes()

        binding.fabAdd.setOnClickListener {
            //instead of startActivityForResult()
            val intent = Intent(this, NotesTakerActivity::class.java)
            getResultAddNewNote.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                SearchNotes.filter(newText, notesList, noteListAdapter)
                return true
            }
        })
    }

    private val getResultAddNewNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newNotes: Notes = data?.getSerializableExtra("note") as Notes
                database.notesDAO().insert(newNotes)
                clearAddNotify()
            }
        }

    private val getResultEditNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newNotes = data?.getSerializableExtra("note") as Notes
                database.notesDAO().update(newNotes.id, newNotes.title, newNotes.noteText)
                clearAddNotify()
            }
        }

    private fun clearAddNotify() {
        notesList.clear()
        notesList.addAll(database.notesDAO().getAllNotes())
        noteListAdapter.notifyDataSetChanged()
    }

    private fun updateRecycler(notesLocal: List<Notes>) {
        binding.recyclerHome.setHasFixedSize(true)
        binding.recyclerHome.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        noteListAdapter = NotesListAdapter(this, notesClickListener, notesLocal)
        binding.recyclerHome.adapter = noteListAdapter
    }

    private lateinit var selectedNote: Notes

    private val notesClickListener = object : NotesClickListener {
        override fun onLongClick(notes: Notes, cardView: CardView) {
            selectedNote = Notes()
            selectedNote = notes
            showPopup(cardView)
        }

        override fun onClick(notes: Notes) {
            val intent = Intent(this@MainActivity, NotesTakerActivity::class.java)
            intent.putExtra("old_note", notes)
            getResultEditNote.launch(intent)
        }
    }

    private fun showPopup(cardView: CardView) {
        val popupMenu = PopupMenu(this, cardView)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.pin -> {
                if (selectedNote.isPinned) {
                    database.notesDAO().pin(selectedNote.id, false)
                    Toast.makeText(this, "unpinned", Toast.LENGTH_SHORT).show()
                } else {
                    database.notesDAO().pin(selectedNote.id, true)
                    Toast.makeText(this, "pinned", Toast.LENGTH_SHORT).show()
                }
                clearAddNotify()
                return true
            }
            R.id.delete -> {
                database.notesDAO().delete(selectedNote)
                notesList.remove(selectedNote)
                noteListAdapter.notifyDataSetChanged()
                Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }
}