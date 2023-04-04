package com.nottes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class notteAdapter(context:Context, notteList:ArrayList<Notte>):ArrayAdapter<Notte>(context, 0, notteList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.notte_layout, parent, false)
        val notte:Notte= getItem(position)!!
        view.findViewById<TextView>(R.id.title_text_view).text=notte.title
        view.findViewById<TextView>(R.id.time_text_view).text=notte.timestamp.toString()
        return view
        }
}