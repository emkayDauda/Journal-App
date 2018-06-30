package com.alc.askemkay.journalapp.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.alc.askemkay.journalapp.models.JournalEntryModel

@Dao
interface EntryDao {

    @Insert
    fun insert(journalEntryModel: JournalEntryModel)

    @Query("Delete FROM entry_table")
    fun deleteEverything()

    @Query("Select * FROM entry_table order by sn")
    fun getAllEntries(): LiveData<List<JournalEntryModel>>
}