package com.mmajka.notatnik.bin

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
import com.mmajka.notatnik.home.HomeAdapter
import com.mmajka.notatnik.home.OnClickListener
import com.mmajka.notatnik.note.NotesDetails

class RecycleBin: Fragment(), OnClickListener {
    lateinit var viewModel: BinViewModel
    lateinit var recycler: RecyclerView
    lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_favorites, null)

        //-------------------init
        viewModel = ViewModelProvider(this).get(BinViewModel::class.java)
        recycler = view.findViewById(R.id.favoritesRecycler)


        //-------------------METHODS
        setupRecycler()

        return view
    }


    private fun setupRecycler(){
        viewModel = ViewModelProvider(this).get(BinViewModel::class.java)
        viewModel.getAllFromBin().observe(viewLifecycleOwner, Observer {
            recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = HomeAdapter(it, this)
            Log.d("SIZE", "${it.size}")
            recycler.adapter = adapter

        })
    }

    override fun OnItemClick(note: note, position: Int, view: View) {
        val intent = Intent(context, NotesDetails::class.java)
        intent.putExtra("noteId", note.id)
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteContent", note.content)
        intent.putExtra("noteFavorite", note.isFavorite)
        intent.putExtra("noteTrash", note.isTrash)
        intent.putExtra("noteDate", note.date)
        intent.putExtra("noteReminderDate", note.reminderDate)
        intent.putExtra("noteReminderTime", note.reminderTime)
        startActivity(intent)
    }

    override fun OnItemLongClick(note: note, position: Int, view: View) {
        TODO("Not yet implemented")
    }
}