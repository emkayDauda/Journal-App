package com.alc.askemkay.journalapp.models

import android.view.View

interface RecyclerViewClickListenerInterface {

    fun onClick(v: View, position: Int)

    fun onLongClick(v: View, position: Int)
}