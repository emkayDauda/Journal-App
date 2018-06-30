package com.alc.askemkay.journalapp.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.support.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.sql.Time

// Created by ask_emkay on 6/29/18.

@Parcelize
@Entity (tableName = "entry_table")
data class JournalEntryModel(@PrimaryKey(autoGenerate = true) @NonNull @ColumnInfo(name = "sn") val SN: Int?,
                             @NonNull @ColumnInfo(name = "date") val date: String,
                             @NonNull @ColumnInfo(name = "time") val time: String,
                             @NonNull @ColumnInfo(name = "entry") val entry: String,
                             @NonNull @ColumnInfo(name = "emotion") val emotion: String) : Parcelable {

}