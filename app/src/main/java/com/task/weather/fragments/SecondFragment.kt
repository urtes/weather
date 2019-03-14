package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.task.weather.R
import com.task.weather.adapters.HourlyAdapter
import com.task.weather.models.HourlyData
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt


class SecondFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_second, container, false)
        val location = arguments?.getString("location")

        if (location != null) {
            fetchAndRenderHourlyData(view, location)
        } else {
            fetchAndRenderHourlyData(view, "Vilnius")
        }

        return view
    }

    private fun fetchAndRenderHourlyData(view: View, location: String) {

        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$location&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"
        val hourlyForecast = ArrayList<HourlyData>()

        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val strResp = response.toString()
                    val jsonArray = JSONObject(strResp).getJSONArray("list")

                    for (i in 0..8) {

                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val jsonArrayWeather: JSONArray = jsonObject.getJSONArray("weather")
                        val jsonObjectMain: JSONObject = jsonObject.getJSONObject("main")

                        val hourlyData = HourlyData(jsonObject.get("dt_txt").toString(),
                                jsonArrayWeather.getJSONObject(0).get("main").toString(),
                                jsonObjectMain.getDouble("temp").roundToInt().toString(),
                                jsonArrayWeather.getJSONObject(0).get("icon").toString())

                        hourlyForecast.add(hourlyData)
                    }

                    listView = view.findViewById(R.id.list_view)
                    listView.adapter = HourlyAdapter(requireContext(), hourlyForecast)
                },
                Response.ErrorListener { "Location not found" })
        queue.add(stringReq)
    }
}