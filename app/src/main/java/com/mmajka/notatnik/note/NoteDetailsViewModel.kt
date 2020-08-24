package com.mmajka.notatnik.note

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mmajka.notatnik.database.Repository
import com.mmajka.notatnik.database.note

class NoteDetailsViewModel(application: Application): AndroidViewModel(application) {

    private var repository =Repository(application)

    fun insertNote(note: note){
        repository.insertNote(note)
        Log.d("INSERT TEST", "MVVM RUNNING")
    }

    fun updateNote(note: note){
        repository.updateNote(note)
    }

    fun deleteNote(note: note){
        repository.deleteNote(note)
    }

    fun setAsFavorite(note: note){
        repository.updateNote(note)
    }

    fun unsetAsFavorite(note: note){
        repository.updateNote(note)
    }
}