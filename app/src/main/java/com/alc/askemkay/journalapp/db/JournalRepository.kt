package com.alc.askemkay.journalapp.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.alc.askemkay.journalapp.models.JournalEntryModel


// Created by ask_emkay on 6/30/18.

class JournalRepository(val application: Application) {
    private lateinit var mEntryDao: EntryDao
    private lateinit var mAllEntries: LiveData<List<JournalEntryModel>>

    init {
        val journalDatabase = JournalDatabase.getDatabase(application)
        mEntryDao = journalDatabase.entryDao()
        mAllEntries = mEntryDao.getAllEntries()
    }

    fun getAllEntries() : LiveData<List<JournalEntryModel>> = mAllEntries

    fun insertEntry(journalEntryModel: JournalEntryModel){
        insertAsyncTask(mEntryDao).execute(journalEntryModel)
    }

    private class insertAsyncTask(private val mAsyncTaskDao: EntryDao) : AsyncTask<JournalEntryModel, Void, Void>() {

        override fun doInBackground(vararg params: JournalEntryModel): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {

        }

    }

}

