package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.adapters.NotesListAdapter
import com.example.notes.database.NotesRoomDatabase
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.listeners.NotesClickListener
import com.example.notes.models.Note
import com.example.notes.view_models.NotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var notesList: MutableList<Note> = ArrayList()
    private lateinit var noteListAdapter: NotesListAdapter
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateRecycler(notesList)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        notesViewModel.getAllNotes(this)?.observe(this) {
            noteListAdapter.setList(it as ArrayList<Note>)
            notesList = it
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NotesTakerActivity::class.java)
            getResultAddNewNote.launch(intent)
        }

        val swipeDeletion = ItemTouchHelper(swipedItem)
        swipeDeletion.attachToRecyclerView(binding.recyclerHome)

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
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
                val newNotes: Note = data?.getSerializableExtra("note") as Note
                //database.notesDAO().insert(newNotes)
                notesViewModel.insert(this, newNotes)
                clearAddNotify()
            }
        }

    private val getResultEditNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newNotes = data?.getSerializableExtra("note") as Note
                notesViewModel.update(this, newNotes)
                //database.notesDAO().update(newNotes.id, newNotes.title, newNotes.noteText)
                clearAddNotify()
            }
        }

    private fun clearAddNotify() {
//        notesList.clear()
//        notesList.addAll(notesViewModel.getAllNotes())
        noteListAdapter.notifyDataSetChanged()
    }

    private fun updateRecycler(notesLocal: List<Note>) {
        with(binding.recyclerHome) {
            noteListAdapter = NotesListAdapter(notesClickListener, notesLocal)
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = noteListAdapter
        }
    }

    private lateinit var selectedNote: Note

    private val notesClickListener = object : NotesClickListener {
        override fun onLongClick(notes: Note, cardView: CardView) {
            selectedNote = notes

            if (selectedNote.isPinned) {
                notesViewModel.pin(this@MainActivity, selectedNote, false)
                Toast.makeText(this@MainActivity, "unpinned", Toast.LENGTH_SHORT).show()
            } else {
                notesViewModel.pin(this@MainActivity, selectedNote, true)
                Toast.makeText(this@MainActivity, "pinned", Toast.LENGTH_SHORT).show()
            }
            clearAddNotify()
        }

        override fun onClick(notes: Note) {
            val intent = Intent(this@MainActivity, NotesTakerActivity::class.java)
            intent.putExtra("old_note", notes)
            getResultEditNote.launch(intent)
        }
    }

    private val swipedItem = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val note = notesList[viewHolder.absoluteAdapterPosition]

            if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
                notesViewModel.delete(this@MainActivity, note)
                Toast.makeText(this@MainActivity, "Deleted successfully", Toast.LENGTH_SHORT)
                    .show()
                noteListAdapter.notifyDataSetChanged()
            }
        }

    }

}