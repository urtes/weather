package com.task.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.task.weather.R
import com.task.weather.models.HourlyData

class HourlyAdapter (private val context: Context,
                     private val dataSource: MutableList<HourlyData>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.list_item_hourly, parent, false)

        val timeView = rowView.findViewById(R.id.time) as TextView

        val conditionsView = rowView.findViewById(R.id.conditions) as TextView

        val temperatureView = rowView.findViewById(R.id.temperature) as TextView

        val weatherIconView = rowView.findViewById(R.id.weather_icon) as ImageView

        val hourlyData = getItem(position) as HourlyData

        timeView.text = hourlyData.time
        conditionsView.text = hourlyData.conditions
        temperatureView.text = hourlyData.temp + "°"

        var icon = hourlyData.icon

        var imgUrl = "http://openweathermap.org/img/w/$icon.png"
        Glide.with(context).load(imgUrl).into(weatherIconView)

        return rowView
    }
}