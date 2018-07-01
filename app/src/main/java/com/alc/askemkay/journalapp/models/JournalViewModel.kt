package com.alc.askemkay.journalapp.models

import android.arch.lifecycle.LiveData
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.alc.askemkay.journalapp.db.JournalRepository


// Created by ask_emkay on 6/30/18.
open class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: JournalRepository

    internal val allEntries: LiveData<List<JournalEntryModel>>

    init {
        mRepository = JournalRepository(application)
        allEntries = mRepository.getAllEntries()
    }




    fun insert(entry: JournalEntryModel) {
        mRepository.insertEntry(entry)
    }

    fun getAllEntries() = allEntries

    fun deleteEntry(id: String){
        mRepository.deleteEntry(id)
    }

    fun deleteEverything(){
        mRepository.deleteEverything()
    }
}