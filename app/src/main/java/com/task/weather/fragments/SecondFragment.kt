package com.task.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.task.weather.R
import com.task.weather.adapters.HourlyAdapter
import com.task.weather.models.HourlyData
import org.json.JSONArray
import org.json.JSONObject


class SecondFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_second, container, false)

        val myStr = arguments?.getString("location")

        if (myStr != null) {
            fetchHourlyData(view, myStr)
        } else {
            fetchHourlyData(view, "vilnius")
        }

        return view
    }

    private fun fetchHourlyData(view: View, location: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$location&units=metric&appid=dd1cbb470e98079b87159f9a28b09359"
        val hourlyForecast = ArrayList<HourlyData>()

        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    var strResp = response.toString()
                    var jsonArray = JSONObject(strResp).getJSONArray("list")

                    for (i in 0..8) {

                        var jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        var jsonArrayWeather: JSONArray = jsonObject.getJSONArray("weather")
                        var jsonObjectMain: JSONObject = jsonObject.getJSONObject("main")

                        val hourlyData = HourlyData(jsonObject.get("dt_txt").toString(),
                                jsonArrayWeather.getJSONObject(0).get("main").toString(),
                                jsonObjectMain.get("temp").toString(),
                                jsonArrayWeather.getJSONObject(0).get("icon").toString())

                        Log.d("tagas", hourlyData.toString())

                        hourlyForecast.add(hourlyData)
                    }

                    listView = view.findViewById(R.id.list_view)
                    listView.adapter = HourlyAdapter(requireContext(), hourlyForecast)
                },
                Response.ErrorListener { "That didn't work!" })

        queue.add(stringReq)
    }
}