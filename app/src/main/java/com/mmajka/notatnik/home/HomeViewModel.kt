package com.mmajka.notatnik.home

import android.app.Application
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmajka.notatnik.database.Repository
import com.mmajka.notatnik.database.note
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class HomeViewModel(application: Application): AndroidViewModel(application) {


    private var repository = Repository(application)
    private var allNotes: Deferred<LiveData<List<note>>> =repository.getAllNotes()

    var _selectedItems = MutableLiveData<ArrayList<View>>()
    val selectedItems: LiveData<ArrayList<View>>
    get() = _selectedItems


    fun getAllNotes(): LiveData<List<note>> = runBlocking {
        allNotes.await()
    }

    fun newNote(){
        val note = note(0, "","","",0,0,"","","",1)
        repository.insertNote(note)
    }

    fun selectNotes(view: View){
        selectedItems.value?.add(view)
        view.background.setTint(Color.GRAY)
        view.isSelected = true
    }

    fun unselectNotes(view: View){
        selectedItems.value?.remove(view)
        view.background.setTint(Color.WHITE)
        view.isSelected = false
    }

    fun loadSelection(){
        if (selectedItems.value!!.size != 0){
            for (i in 0..selectedItems.value!!.size){
                selectedItems.value?.get(i)?.background?.setTint(Color.GRAY)
            }
        }
        else{
            Log.d("TAG", "Empty list")
        }

    }

    fun clearSelection(){
        selectedItems.value!!.clear()
    }



}