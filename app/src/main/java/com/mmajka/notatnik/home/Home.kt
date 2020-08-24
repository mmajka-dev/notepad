package com.mmajka.notatnik.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmajka.notatnik.R
import com.mmajka.notatnik.database.note
import com.mmajka.notatnik.note.NotesDetails

class Home: Fragment(), OnClickListener {

    lateinit var viewModel: HomeViewModel
    lateinit var recycler: RecyclerView
    lateinit var adapter: HomeAdapter

    override fun onStart() {
        super.onStart()
        loadSelection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_home, null)

        //-------------------init
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        recycler = view.findViewById(R.id.homeRecycler)



        //-------------------METHODS
        setupRecycler()

        return view
    }

    private fun setupRecycler(){
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
        recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = HomeAdapter(it, this)
            Log.d("SIZE", "${it.size}")
        recycler.adapter = adapter
        })
    }

    override fun OnItemClick(note: note, position: Int, view: View) {
        pushIntents(note, position)
    }

    override fun OnItemLongClick(note: note, position: Int, view: View) {
        if(view.isSelected){
            unselectItems(view)
        }else {
            selectItems(view)
        }
    }

    private fun pushIntents(note: note, position: Int){
        val intent = Intent(context, NotesDetails::class.java)
        intent.putExtra("noteId", note.id)
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteContent", note.content)
        intent.putExtra("noteFavorite", note.isFavorite)
        intent.putExtra("noteTrash", note.isTrash)
        intent.putExtra("noteDate", note.date)
        intent.putExtra("noteReminderDate", note.reminderDate)
        intent.putExtra("noteReminderTime", note.reminderTime)
        intent.putExtra("noteIsSaved", note.isSaved)
        startActivity(intent)
    }

    private fun selectItems(view: View){
        viewModel.selectNotes(view)
    }

    private fun unselectItems(view: View){
        viewModel.unselectNotes(view)
    }

    private fun loadSelection(){
        viewModel._selectedItems.observe(viewLifecycleOwner, Observer {newSelectedItems ->
            if (newSelectedItems.size != 0){
                viewModel.loadSelection()
                Log.d("NEW SELECTION", "${newSelectedItems.size}")
            }
            else{
                Log.d("NEW SELECTION", "NULL")
            }
        })


    }
}