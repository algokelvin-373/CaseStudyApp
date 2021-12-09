package com.algokelvin.visitorapps

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_visitor.view.*

class RecyclerViewController(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val size: Int,
    private val layoutItem: Int,
    private val dataBool: Array<Boolean?>,
    private val dataHeader: Array<String?>,
    private val bindViewHolder : (View, Int) -> Unit
) {
    private var header = "null"
    private var endShow = false
    private var xShow = false
    private var xStart = false

    init {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        val dataAdapter = DataAdapter(size, layoutItem) { view, position ->
            header(position, dataBool[position], view.date_visitor, dataHeader[position])
            bindViewHolder(view, position)
            scrollConditional(position, size)
        }
        recyclerView.adapter = dataAdapter
        dataAdapter.notifyDataSetChanged()
    }

    private fun setVisibilityHeader(textView: TextView, visibility: Int, data: String? = null) {
        textView.text = data
        textView.visibility = visibility
    }
    private fun setHeaderData(data: String?) {
        header = data!!
    }
    private fun setBoolData(position: Int, boolean: Boolean) {
        dataBool[position] = boolean
    }
    private fun scrollConditional(position: Int, max: Int) {
        if (position + 1 == max) {
            Log.i("datahistory-date", "${position} Position is End")
            endShow = true
            xStart = true
        }
        if (position == 0) {
            Log.i("datahistory-date", "${position} Position is TOP")
            endShow = false
            if (xStart)
                xShow = true
        }
    }
    private fun headerController(dataBool: Boolean?, textView: TextView, data: String?) {
        if (dataBool == true) {
            setVisibilityHeader(textView, View.VISIBLE, data)
            setHeaderData(data)
        } else {
            setVisibilityHeader(textView, View.GONE)
        }
    }
    private fun header(position: Int, dataBool: Boolean?, textView: TextView, data: String?) {
        if (endShow) {
            headerController(dataBool, textView, data)
        } else if (xShow) {
            headerController(dataBool, textView, data)
        } else {
            if (data != header) {
                setVisibilityHeader(textView, View.VISIBLE, data)
                setHeaderData(data)
                setBoolData(position, true)
            } else {
                setVisibilityHeader(textView, View.GONE)
            }
        }
    }

}