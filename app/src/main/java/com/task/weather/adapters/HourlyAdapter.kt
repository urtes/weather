package com.task.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.task.weather.R

class HourlyAdapter (private val context: Context,
                     private val dataSource: Array<String>,
                     private val location: String) : BaseAdapter() {

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

        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView

        val subtitleTextView = rowView.findViewById(R.id.recipe_list_subtitle) as TextView

        val detailTextView = rowView.findViewById(R.id.recipe_list_detail) as TextView

        val thumbnailImageView = rowView.findViewById(R.id.recipe_list_thumbnail) as ImageView

        val smth = getItem(position) as String

        titleTextView.text = smth
        subtitleTextView.text = location
        detailTextView.text = "also testing"

        return rowView
    }
}
