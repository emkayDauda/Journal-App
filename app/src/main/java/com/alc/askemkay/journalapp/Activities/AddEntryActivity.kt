package com.alc.askemkay.journalapp.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.alc.askemkay.journalapp.R
import com.alc.askemkay.journalapp.models.JournalEntryModel
import java.text.SimpleDateFormat
import java.util.*


class AddEntryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "newEntryIsToBeAdded"
    }



    private lateinit var newEntryEditText: TextInputEditText
    private lateinit var emotionsSpinner: Spinner
    private lateinit var entryCompleteFab: FloatingActionButton
    private lateinit var editEntryFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        newEntryEditText = findViewById(R.id.new_entry_edit_text)

        emotionsSpinner = findViewById(R.id.emotionSpinner)



        val spinnerAdapter = ArrayAdapter.createFromResource(this@AddEntryActivity,
                R.array.emotions_array, android.R.layout.simple_spinner_item)


        emotionsSpinner.adapter = spinnerAdapter
        emotionsSpinner.setSelection(0)

        entryCompleteFab = findViewById(R.id.entry_done_fab)

        editEntryFab = findViewById(R.id.edit_entry_fab)

        entryCompleteFab.setOnClickListener {
            val currentDate = SimpleDateFormat("EEE, MMM d, ''yy", Locale.ENGLISH)
                    .format(Calendar.getInstance().time)

            val currentTime = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Calendar.getInstance().time)

            val entryBody = newEntryEditText.text.toString()

            val currentEmotion = emotionsSpinner.selectedItem.toString()

            val journalEntry = JournalEntryModel(null, currentDate, currentTime, entryBody, currentEmotion)

            val replyIntent = Intent()
            if (TextUtils.isEmpty(newEntryEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY, journalEntry)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        editEntryFab.setOnClickListener {
            newEntryEditText.isEnabled = true
            toggleFabs()
        }

        if (intent.hasExtra(MainActivity.JOURNAL_ENTRY_EXTRA) &&
                intent.hasExtra(MainActivity.ENTRY_EMOTION_EXTRA)){
            newEntryEditText.setText(intent.getStringExtra(MainActivity.JOURNAL_ENTRY_EXTRA))
            newEntryEditText.isEnabled = false
            toggleFabs()

        }

    }

    fun toggleFabs(){
        if(entryCompleteFab.visibility == View.VISIBLE){
            entryCompleteFab.visibility = View.GONE
            editEntryFab.visibility = View.VISIBLE
        } else{
            entryCompleteFab.visibility = View.VISIBLE
            editEntryFab.visibility = View.GONE
        }
    }
}