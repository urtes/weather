package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.task.weather.Api
import com.task.weather.R
import org.json.JSONObject
import java.sql.Time
import java.sql.Timestamp
import kotlin.math.roundToInt

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_first, container, false)
        val location = arguments?.getString("location")

        if (location != null) {
            fetchAndRenderNowData(location, view)
        } else {
            fetchAndRenderNowData("Vilnius", view)
        }

        return view
    }

    private fun fetchAndRenderNowData(location: String, view: View) {

        val cityView: TextView  = view.findViewById(R.id.city)
        val conditionsView: TextView  = view.findViewById(R.id.weather_conditions)
        val tempView: TextView = view.findViewById(R.id.temp)
        val tempHLView: TextView = view.findViewById(R.id.temp_h_l)
        val detailConditionsView: TextView  = view.findViewById(R.id.detail_conditions)
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${Api.URL}weather?q=$location&units=metric&appid=${Api.API_KEY}"

        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val strResp = response.toString()
                    val jsonObj = JSONObject(strResp)
                    val jsonObjMain: JSONObject = jsonObj.getJSONObject("main")
                    val jsonObjWind: JSONObject = jsonObj.getJSONObject("wind")
                    val jsonObjectWeather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val jsonObjSys: JSONObject = jsonObj.getJSONObject("sys")

                    val city = jsonObj.get("name").toString()
                    val temp = jsonObjMain.getDouble("temp").roundToInt()
                    val tempHigh = jsonObjMain.getDouble("temp_max").roundToInt()
                    val tempLow = jsonObjMain.getDouble("temp_min").roundToInt()
                    val icon = jsonObjectWeather.get("icon").toString()
                    val conditions = jsonObjectWeather.get("main").toString().toUpperCase()
                    val pressure= jsonObjMain.get("pressure").toString()
                    val humidity = jsonObjMain.get("humidity").toString()
                    val visibility = jsonObj.get("visibility").toString()
                    val winds = jsonObjWind.get("speed").toString()
                    val sunrise = Time(Timestamp(jsonObjSys.getLong("sunrise") * 1000).time)
                    val sunset = Time(Timestamp(jsonObjSys.getLong("sunset") * 1000).time)

                    cityView?.text = city
                    tempView?.text = "$temp°"
                    tempHLView?.text = "H $tempHigh° / L $tempLow°"
                    conditionsView?.text = conditions
                    detailConditionsView?.text = "Pressure:    $pressure\n" +
                            "Humidity:    $humidity\n" +
                            "Visibility:     $visibility\n" +
                            "Winds:         $winds\n" +
                            "Sunrise:       $sunrise\n" +
                            "Sunset:        $sunset"

                    val imgUrl = "${Api.IMAGE_URL}$icon.png"
                    val imageView: ImageView = view.findViewById(R.id.weather_icon)
                    Glide.with(this).load(imgUrl).into(imageView)

                },
                Response.ErrorListener { cityView?.text = "Location not found" })
        queue.add(stringReq)
    }
}