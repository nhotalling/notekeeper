package com.gfd.notekeeper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRecyclerAdapter(private val context: Context, private val notes: List<NoteInfo>) :
    RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    // Creates Views. Called multiple times to create a pool of Views the RV can use
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view to display an item template
        // 3rd param, attach to root would automatically attach the view to the parent
        // but we want to let the RecyclerView manage that for us, so we pass false
        val itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false)
        return ViewHolder(itemView)
    }

    // bind the data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.textCourse?.text = note.course?.title
        holder.textTitle?.text = note.title
        holder.notePosition = position
    }

    // how much data overall should be displayed
    // shorthand syntax:
    override fun getItemCount() = notes.size
    // which is the same as:
//    override fun getItemCount(): Int {
//        return notes.size
//    }

    // on demo, View was nullable (View?) but it seems our base class
    // RecyclerView.ViewHolder() does not like a nullable passed in as its param
    // Note that we've marked ViewHolder as an "inner" class.
    // This gives us access to the members of the containing class (NoteRecyclerAdapter)
    // so we don't have to pass the Context into the constructor
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // needs references to each of the TextViews we want to populate
        // Android Studio doesn't know how to get references to them
        // so we need to write code to find them.

        val textCourse = itemView.findViewById<TextView?>(R.id.textCourse)
        val textTitle = itemView.findViewById<TextView?>(R.id.textTitle)

        // Responsibility for handling the click falls to the View.
        // Remember that Views will hold different items at different times
        // so we need a way to keep track of which item we're on.
        // Set the position in onBindViewHolder
        var notePosition = 0

        init {
            // for single hotspot, we can set the listener here
            itemView?.setOnClickListener {
                // since ViewHolder is an inner class, we can access NoteRecyclerAdapter's context
                val intent = Intent(context, NoteActivity::class.java)
                // attach the position as an extra
                intent.putExtra(NOTE_POSITION, notePosition)
                context.startActivity(intent)
            }
        }
    }
}