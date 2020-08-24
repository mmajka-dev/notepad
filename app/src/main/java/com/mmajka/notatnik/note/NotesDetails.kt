package com.mmajka.notatnik.note

import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mmajka.notatnik.R
import com.mmajka.notatnik.database.note
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotesDetails: AppCompatActivity() {

    lateinit var viewModel: NoteDetailsViewModel
    lateinit var titleEditText: EditText
    lateinit var contentEditText: EditText
    lateinit var view: ConstraintLayout
    lateinit var noteDate: TextView
    lateinit var favoriteButton: MenuItem
    lateinit var favorite: ImageView

    //region ---------------note
    var id: Int = 0
    var title: String? = ""
    var content: String? = ""
    var date: String? = ""
    var isFavorite: Int = 0
    var isTrash: Int = 0
    var reminderDate: String? = null
    var reminderTime: String? = null
    var isSaved: Int = 0
    //endregion




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)


        initView()
        getIntents()

        //endregion
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.favorite ->{
                when(isFavorite){
                    0 -> {
                        item.icon.setTint(resources.getColor(R.color.colorAccent))
                        isFavorite = 1
                        viewModel.setAsFavorite(buildNote())
                    }
                    1 -> {
                        isFavorite = 0
                        item.icon.setTint(resources.getColor(R.color.colorPrimaryDark))
                        viewModel.unsetAsFavorite(buildNote())
                    }
                }
            }
            R.id.delete ->{
                when(isTrash){
                    0 ->{
                        val yesButton = "<font color='#919191'>Move</font>"
                        val noButton = "<font color='#919191'>No</font>"
                        MaterialAlertDialogBuilder(this)
                            .setMessage("Move to recycle bin?")
                            .setIcon(resources.getDrawable(R.drawable.ic_info))
                            .setBackground(resources.getDrawable(R.drawable.tab_item_shape))
                            .setNegativeButton(Html.fromHtml(noButton),null)
                            .setPositiveButton(Html.fromHtml(yesButton)){ _, _ ->
                                isTrash = 1
                                isFavorite = 0
                                viewModel.updateNote(buildNote())
                                onBackPressed()
                            }
                            .show()

                    }
                    1 ->{
                        val yesButton = "<font color='#919191'>Delete</font>"
                        val noButton = "<font color='#919191'>No</font>"
                        MaterialAlertDialogBuilder(this)
                            .setMessage("Delete note?")
                            .setIcon(resources.getDrawable(R.drawable.ic_info))
                            .setBackground(resources.getDrawable(R.drawable.tab_item_shape))
                            .setNegativeButton(Html.fromHtml(noButton),null)
                            .setPositiveButton(Html.fromHtml(yesButton)){ _, _ ->
                                viewModel.deleteNote(buildNote())
                                onBackPressed()
                            }
                            .show()
                    }
                }
            }
            R.id.save ->{
                when(isSaved){
                    0 -> viewModel.insertNote(buildNote())
                    1 -> viewModel.updateNote(buildNote())
                }
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buildNote(): note{
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()
        val date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        return note(id, title, content, date, isFavorite, isTrash, reminderDate, reminderTime,null, 1 )
    }

    private fun initView(){
        viewModel = ViewModelProvider(this).get(NoteDetailsViewModel::class.java)
        titleEditText = findViewById(R.id.noteTitle)
        contentEditText = findViewById(R.id.noteDetails)
        view = findViewById(R.id.noteView)
        noteDate = findViewById(R.id.noteDate)
        favorite = findViewById(R.id.fav)

        title = titleEditText.text.toString()
        content = contentEditText.text.toString()
    }

    private fun getIntents(){
        if (intent.hasExtra("noteId")) id = intent.getIntExtra("noteId", 0)
        if (intent.hasExtra("noteTitle")){
            titleEditText.setText(intent.getStringExtra("noteTitle"))
        }
        if (intent.hasExtra("noteContent")){
            contentEditText.setText(intent.getStringExtra("noteContent"))
        }
        if (intent.hasExtra("noteDate")){
            noteDate.text = intent.getStringExtra("noteDate")
            date = intent.getStringExtra("noteDate")
        }
        if (intent.hasExtra("noteFavorite")) isFavorite = intent.getIntExtra("noteFavorite", 0)
        when(isFavorite){
            0 -> favorite.visibility = View.GONE
            1 -> favorite.visibility = View.VISIBLE
        }

        if (intent.hasExtra("noteTrash")) isTrash = intent.getIntExtra("noteTrash", 0)
        if (intent.hasExtra("noteReminderDate")) reminderDate = intent.getStringExtra("noteReminderDate")
        if (intent.hasExtra("noteReminderTime")) reminderTime = intent.getStringExtra("noteReminderTime")
        if (intent.hasExtra("noteIsSaved")) isSaved = intent.getIntExtra("noteIsSaved", 0)
    }


}