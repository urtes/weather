package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.task.weather.R
import com.task.weather.adapters.HourlyAdapter

class SecondFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_second, container, false)

        listView = view.findViewById(R.id.list_view)
        var myDataset: Array<String> = arrayOf("green", "red", "blue", "katinas", "green", "red", "blue", "katinas")
        val adapter = HourlyAdapter(requireContext(), myDataset)
        listView.adapter = adapter

        val city: TextView = view.findViewById(R.id.city)
        val myStr = arguments?.getString("location")
        city.text = myStr

        return view
    }

}