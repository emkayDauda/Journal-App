package com.alc.askemkay.journalapp.models

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alc.askemkay.journalapp.R
import kotlinx.android.synthetic.main.journal_entry.view.*

// Created by ask_emkay on 6/29/18.
open class JournalAdapter(val listener: RecyclerViewClickListenerInterface):
RecyclerView.Adapter<JournalAdapter.ViewHolder>(){

    val mListener = listener

    private var mEntries: List<JournalEntryModel>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.journal_entry, parent, false) as View

        return ViewHolder(view, mListener)
    }

    override fun getItemCount() = mEntries?.size ?: 0

    fun setWords(entries: List<JournalEntryModel>) {
        mEntries = entries
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.date_text_view.text = mEntries?.get(position)?.date
        holder.view.time_text_view.text = mEntries?.get(position)?.time
        holder.view.entry.text = mEntries?.get(position)?.entry
        holder.view.emotion.text = mEntries?.get(position)?.emotion
        holder.view.sn_text_view.text = mEntries?.get(position)?.SN?.toString()
    }

    class ViewHolder(val view: View, listener: RecyclerViewClickListenerInterface):
            RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {


        private var mListenerInterface = listener

        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            mListenerInterface.onClick(v, adapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            mListenerInterface.onLongClick(v, adapterPosition)
            return true
        }
    }



}