package com.alc.askemkay.journalapp.models

import android.support.v7.widget.RecyclerView
import android.view.View

// Created by ask_emkay on 6/30/18.
class RowViewHolder(val v: View, listener: RecyclerViewClickListenerInterface):
        RecyclerView.ViewHolder(v), View.OnClickListener {

    private var mListenerInterface = listener

    init { v.setOnClickListener(this) }

    override fun onClick(v: View) {
        mListenerInterface.onClick(v, adapterPosition)
    }
}