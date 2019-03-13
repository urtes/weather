package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.task.weather.R
import org.json.JSONObject

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_first, container, false)

        val myStr = arguments?.getString("location")
        if (myStr != null) {
            doMySearch(myStr, view)
        } else {
            doMySearch("Vilnius", view)
        }

        return view
    }

    fun doMySearch(query: String, view: View) {

        val temp: TextView = view.findViewById(R.id.temp)
        val city: TextView  = view.findViewById(R.id.city)
        val weatherConditions: TextView  = view.findViewById(R.id.weather_conditions)
        val detailConditions: TextView  = view.findViewById(R.id.detail_conditions)


        val queue = Volley.newRequestQueue(requireContext())

        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$query&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"

        var stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonObjInner: JSONObject = jsonObj.getJSONObject("main")
                    val jsonObjectWeather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)

                    var str_city: String = jsonObj.get("name").toString()
                    var str_temp: String = jsonObjInner.get("temp").toString()
                    var str_icon: String = jsonObjectWeather.get("icon").toString()
                    var str_conditions: String = jsonObjectWeather.get("main").toString().toUpperCase()
                    val str_description: String = jsonObjectWeather.get("description").toString()
                    val str_pressure: String = jsonObjInner.get("pressure").toString()
                    val str_humidity: String = jsonObjInner.get("humidity").toString()
                    val str_visibility: String = jsonObj.get("visibility").toString()

                    city?.text = str_city
                    temp?.text = "$str_temp C°"
                    weatherConditions?.text = str_conditions
                    detailConditions?.text = "$str_description\nPressure: $str_pressure\nHumidity: $str_humidity\nVisibility: $str_visibility"

                    var imgUrl = "http://openweathermap.org/img/w/$str_icon.png"
                    val imageView: ImageView = view.findViewById(R.id.weather_icon)
                    Glide.with(this).load(imgUrl).into(imageView)

                },
                Response.ErrorListener { temp?.text = "That didn't work!" })
        queue.add(stringReq)
    }
}