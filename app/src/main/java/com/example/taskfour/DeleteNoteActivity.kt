package com.example.taskfour

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter

class DeleteNoteActivity : AppCompatActivity() {
    private lateinit var notes: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_note)

        // Load notes from SharedPreferences
        loadNotes()

        // Set up ListView
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes.map { it.name })
        listView.setOnItemClickListener { _, _, position, _ ->
            // Delete note
            deleteNote(notes[position])

            // Return to MainActivity
            finish()
        }
    }

    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val savedNotes = sharedPreferences.getStringSet("notes", emptySet())
        notes = savedNotes?.map { Note(it.split(",")[0], it.split(",")[1]) }?.toMutableList() ?: mutableListOf()
    }

    private fun deleteNote(note: Note) {
        val sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val savedNotes = sharedPreferences.getStringSet("notes", emptySet())?.toMutableSet() ?: mutableSetOf()
        savedNotes.remove("${note.name},${note.content}")
        editor.putStringSet("notes", savedNotes)
        editor.apply()
    }
}