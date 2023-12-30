package com.example.taskfour

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskfour.ui.theme.TaskFourTheme
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    private var notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load notes from SharedPreferences
        loadNotes()

        // Set up ListView
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes.map { it.name })

        listView.setOnItemClickListener { _, _, position, _ ->
            val note = notes[position]
            val intent = Intent(this, ViewNoteActivity::class.java).apply {
                putExtra("noteName", note.name)
                putExtra("noteContent", note.content)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_delete_note -> {
                val intent = Intent(this, DeleteNoteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshNotes()
    }

    private fun refreshNotes() {
        // Reload notes from SharedPreferences
        loadNotes()

        // Update the ListView adapter
        if(::adapter.isInitialized) {
            adapter.clear()
            adapter.addAll(notes.map { it.name })
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val savedNotes = sharedPreferences.getStringSet("notes", emptySet())

        notes.clear()
        savedNotes?.forEach { note ->
            val (name, content) = note.split(",", limit = 2)
            notes.add(Note(name, content))
        }
    }

}