package com.example.taskfour

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()

            if (name.isBlank() || content.isBlank()) {
                Toast.makeText(this, "Name and content cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Save note to SharedPreferences
                saveNote(Note(name, content))

                // Return to MainActivity
                finish()
            }
        }
    }

    private fun saveNote(note: Note) {
        val sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val savedNotes = sharedPreferences.getStringSet("notes", emptySet())?.toMutableSet() ?: mutableSetOf()
        savedNotes.add("${note.name},${note.content}")
        editor.putStringSet("notes", savedNotes)
        editor.apply()
    }
}