package com.gfd.notekeeper

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        // listitems = recyclerView
        listitemsOrig.layoutManager = LinearLayoutManager(this)
        listitemsOrig.adapter = NoteRecyclerAdapter(this, DataManager.notes)

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
    // in the list changes.
    override fun onResume() {
        super.onResume()
        // this was for original ListView
        //(listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
        // this is for recyclerview
        // This method is okay for small data sets
        // but there are more efficient ways, like telling it
        // which items were updated.
        listitemsOrig.adapter?.notifyDataSetChanged()
    }
}