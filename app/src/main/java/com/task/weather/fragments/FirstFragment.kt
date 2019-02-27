package com.task.weather.fragments

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.task.weather.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class FirstFragment : Fragment() {

    val test = activity?.findViewById<TextView>(R.id.action_search).toString()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_first, container, false)

//        val query = this.arguments!!.getString("query")!!.toString()
//        doMySearch(query)

        return rootView
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        if(Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                doMySearch(query)
//            }
//        }
//    }


    fun doMySearch(query: String) {
        val showSearchResult = getView()?.findViewById<TextView>(R.id.search_result)

        val queue = Volley.newRequestQueue(activity?.applicationContext)

        var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$query&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"

        var stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonObjInner: JSONObject = jsonObj.getJSONObject("main")
                    val jsonObjectWeather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)
                    var str_weather: String = jsonObj.get("name").toString()
                    var str_temp: String = jsonObjInner.get("temp").toString()
                    var str_icon: String = jsonObjectWeather.get("icon").toString()
                    showSearchResult!!.text = "City: $str_weather\nTemperature: $str_temp °C"

                    val frameLayout = getView()?.findViewById<ConstraintLayout>(R.id.constraintLayout)
                    val imageView = ImageView(activity?.applicationContext)
                    Glide.with(this).load("http://openweathermap.org/img/w/$str_icon.png").into(imageView)
                    constraintLayout.addView(imageView)
                },
                Response.ErrorListener { showSearchResult!!.text = "That didn't work!" })
        queue.add(stringReq)
    }

}