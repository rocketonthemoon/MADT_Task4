package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ViewNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        // Get the note name and content from the intent extras
        val noteName = intent.getStringExtra("noteName")
        val noteContent = intent.getStringExtra("noteContent")

        // Set the note name and content in the TextViews
        findViewById<TextView>(R.id.noteName).text = noteName
        findViewById<TextView>(R.id.noteContent).text = noteContent
    }
}