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
import com.task.weather.adapters.ForecastAdapter
import com.task.weather.models.Forecast
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt


class ThirdFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_third, container, false)
        val location = arguments?.getString("location")

        if (location != null) {
            fetchAndRenderForecastData(view, location)
        } else {
            fetchAndRenderForecastData(view, "Vilnius")
        }

        return view
    }

    private fun fetchAndRenderForecastData(view: View, location: String) {

        val queue = Volley.newRequestQueue(requireContext())
        val url = "${Api.URL}forecast?q=$location&units=metric&appid=${Api.API_KEY}"
        val dailyForecast = ArrayList<Forecast>()

        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val strResp = response.toString()
                    val jsonArray = JSONObject(strResp).getJSONArray("list")
                    var j = 0

                    for (i in 0..4) {

                        val jsonObject: JSONObject = jsonArray.getJSONObject(j)
                        val jsonArrayWeather: JSONArray = jsonObject.getJSONArray("weather")
                        val jsonObjectMain: JSONObject = jsonObject.getJSONObject("main")

                        val forecastData = Forecast(jsonObject.get("dt_txt").toString(),
                                jsonArrayWeather.getJSONObject(0).get("main").toString(),
                                jsonObjectMain.getDouble("temp").roundToInt().toString(),
                                jsonArrayWeather.getJSONObject(0).get("icon").toString())

                        dailyForecast.add(forecastData)
                        j += 8
                    }

                    listView = view.findViewById(R.id.list_view)
                    listView.adapter = ForecastAdapter(requireContext(), dailyForecast)
                },
                Response.ErrorListener { "Location not found" })
        queue.add(stringReq)
    }
}