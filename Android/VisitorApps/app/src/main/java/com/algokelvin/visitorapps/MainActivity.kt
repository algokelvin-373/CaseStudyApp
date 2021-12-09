package com.algokelvin.visitorapps

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.visitorapps.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_visitor.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBool: Array<Boolean?>
    private lateinit var borderUI: BorderUI
    private var header = "null"
    private var endShow = false
    private var xShow = false
    private var xStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val visitor = DataSample.setData2()
        dataBool = arrayOfNulls(visitor.size)
        Arrays.fill(dataBool, false)
        borderUI = BorderUI(this@MainActivity, color = R.color.white,
            colorStroke = R.color.black, sizeStroke = 2, radius = ConstFunc.getSizeDp(resources, 8))

        binding.apply {
            setRecyclerView(visitor)
        }

    }

    private fun ActivityMainBinding.setRecyclerView(visitor: ArrayList<Visitor>) {
        this.rvItem.layoutManager = LinearLayoutManager(this@MainActivity)
        val dataAdapter = DataAdapter(visitor.size, R.layout.item_visitor) { view, position ->
            header(position, dataBool[position], view.date_visitor, visitor[position].date)

            view.cl_data_visitor.background = borderUI.getBorder()
            view.name_visitor.text = ("${position + 1} Name: ${visitor[position].name}")
            view.address_visitor.text = (visitor[position].address + " " + visitor[position].date)

            scrollConditional(position, visitor.size)
        }
        this.rvItem.adapter = dataAdapter
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