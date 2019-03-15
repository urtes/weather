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
import com.task.weather.Api
import com.task.weather.R
import com.task.weather.adapters.HourlyAdapter
import com.task.weather.models.Forecast
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt


class HourlyFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_hourly, container, false)
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
        val url = "${Api.URL}forecast?q=$location&units=metric&appid=${Api.API_KEY}"
        val hourlyForecast = ArrayList<Forecast>()

        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val strResp = response.toString()
                    val jsonArray = JSONObject(strResp).getJSONArray("list")

                    for (i in 0..8) {

                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val jsonArrayWeather: JSONArray = jsonObject.getJSONArray("weather")
                        val jsonObjectMain: JSONObject = jsonObject.getJSONObject("main")

                        val hourlyData = Forecast(jsonObject.getString("dt_txt"),
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