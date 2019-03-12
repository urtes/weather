package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.task.weather.R

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_first, container, false)

        val city: TextView = view.findViewById(R.id.city)
        val myStr = arguments?.getString("my_key")
        city.text = myStr
//
//        val bundle = arguments?.getBundle("data")
//        Log.d("damn", "${arguments?.getString("damn")}")
//
//        Log.d("Bundle", "${bundle.toString()}")
//
//        city.text = bundle?.getString("location")
//
//        Log.d("Meow", "${bundle?.getString("location")}")

        return view
    }
}