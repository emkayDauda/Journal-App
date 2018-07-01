package com.alc.askemkay.journalapp.db

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.os.AsyncTask
import com.alc.askemkay.journalapp.models.JournalEntryModel
import java.text.SimpleDateFormat
import java.util.*


// Created by ask_emkay on 6/30/18.

@Database(entities = arrayOf(JournalEntryModel::class), version = 1)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {

        private var INSTANCE: JournalDatabase? = null


        internal fun getDatabase(context: Context): JournalDatabase {
            if (INSTANCE == null) {
                synchronized(JournalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                JournalDatabase::class.java, "word_database")
                                .fallbackToDestructiveMigration()
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }


}