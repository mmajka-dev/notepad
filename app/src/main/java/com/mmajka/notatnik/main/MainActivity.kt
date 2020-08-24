package com.mmajka.notatnik.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mmajka.notatnik.R
import com.mmajka.notatnik.bin.RecycleBin
import com.mmajka.notatnik.database.note
import com.mmajka.notatnik.favorites.Favorites
import com.mmajka.notatnik.home.Home
import com.mmajka.notatnik.note.NotesDetails
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var addNote: FloatingActionButton
    private lateinit var frameLayout: FrameLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var bottom_bar: AnimatedBottomBar


    //Fragments
    private var fm: FragmentManager = supportFragmentManager
    private var home: Home = Home()
    private var favorites: Favorites = Favorites()
    private var bin: RecycleBin = RecycleBin()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()
        googleServices()


        bottom_bar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0 -> fm.beginTransaction().replace(R.id.frameLayout, home).commit()
                    1 -> fm.beginTransaction().replace(R.id.frameLayout, favorites).commit()
                    2 -> fm.beginTransaction().replace(R.id.frameLayout, bin).commit()
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.add -> {
                val intent = Intent(this, NotesDetails::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun pushIntents(note: note){
        val intent = Intent(this, NotesDetails::class.java)
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

    private fun init(){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        addNote = findViewById(R.id.addNote)
        frameLayout = findViewById(R.id.frameLayout)
        bottom_bar = findViewById(R.id.bottom_bar)
        fm.beginTransaction().add(R.id.frameLayout, home).commit()
    }

    private fun googleServices(){
        viewModel.googleServices(applicationContext)
    }
}