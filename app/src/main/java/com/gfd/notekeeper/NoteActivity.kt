package com.gfd.notekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class NoteActivity : AppCompatActivity() {

    // Must track notePosition with instance state since it persists which
    // note the user is looking at
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //val dm = DataManager()
        // params: context is typically 'this'
        // you can create custom layouts, but Android has built in ones
        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        // format available options
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // associate adapter with spinner
        spinnerCourses.adapter = adapterCourses

        // original implementation
        // notePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        // savedInstanceState is only present if the activity has been created and destroyed.
        // Otherwise, we need the value from the intent on the original create of this activity
        // Use Elvis Operator ?: to take first value if savedInstanceState is not null, otherwise 2nd value
        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            // create a new blank note and add it to the end of the note collection
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

//        fab.setOnClickListener { view ->
//            val originalValue = textDisplayedValue.text.toString().toInt()
//            val newValue = originalValue * 2
//            textDisplayedValue.text = newValue.toString()
//            Snackbar.make(view, "Value $originalValue changed to $newValue",
//                Snackbar.LENGTH_LONG)
//                .show()
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save state we want to track
        // tack it onto the bundle
        // have to name it, just like we do an 'extra'
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)

        // select the course in the spinner
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        // force onPrepareOptionsMenu
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = textNoteTitle.text.toString()
        note.text = textNoteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
    }
}
