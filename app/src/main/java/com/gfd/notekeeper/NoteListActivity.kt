package com.gfd.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // Android expects a java class, not a Kotlin class
            // use class.java to pass a java compatible instance
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        // remove old listview code
//        listNotes.adapter = ArrayAdapter(this,
//            android.R.layout.simple_list_item_1,
//            DataManager.notes
//        )
//
//        // curly braces indicate we're adding a lambda. I think.
//        listNotes.setOnItemClickListener {parent, view, position, id ->
//            val activityIntent = Intent(this, NoteActivity::class.java)
//            activityIntent.putExtra(NOTE_POSITION, position)
//            startActivity(activityIntent)
//        }
    }

    // This activity was created using an adapter that knows about a certain
    // number of notes. We need to create a new adapter if the number of items
    // in the list change
    override fun onResume() {
        super.onResume()
        //(listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}