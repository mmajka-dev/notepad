package com.mmajka.notatnik.main

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.mmajka.notatnik.database.Repository
import com.mmajka.notatnik.database.note

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)
    var insertedId: Long = 0L
    var selectedItems = arrayListOf<Int>()



    fun googleServices(context: Context){
        val bundle: Bundle = Bundle()
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun newNote(): note{
        val note = note(0, "","","",0,0,"","","",1)
        repository.insertNote(note)
        return note
    }

    fun selectItems(position: Int){
        
    }
}