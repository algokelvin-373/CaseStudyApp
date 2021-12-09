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
            if (endShow) {
                if (dataBool[position]!!) {
                    Log.i("datahistory-date", "ENDSHOW - ${position + 1} If date")
                    setVisibilityHeader(view.date_visitor, View.VISIBLE, visitor[position].date)
                    setHeaderData(visitor[position].date)
                } else {
                    setVisibilityHeader(view.date_visitor, View.GONE)
                }
            } else if (xShow) {
                if (dataBool[position]!!) {
                    Log.i("datahistory-date", "XSHOW - ${position + 1} If date")
                    setVisibilityHeader(view.date_visitor, View.VISIBLE, visitor[position].date)
                    setHeaderData(visitor[position].date)
                } else {
                    setVisibilityHeader(view.date_visitor, View.GONE)
                }
            } else {
                if (visitor[position].date != header) {
                    Log.i("datahistory-date", "${position + 1} If date")
                    setVisibilityHeader(view.date_visitor, View.VISIBLE, visitor[position].date)
                    setHeaderData(visitor[position].date)
                    dataBool[position] = true
                } else {
                    setVisibilityHeader(view.date_visitor, View.GONE)
                }
            }

            view.cl_data_visitor.background = borderUI.getBorder()
            view.name_visitor.text = ("${position + 1} Name: ${visitor[position].name}")
            view.address_visitor.text = (visitor[position].address + " " + visitor[position].date)

            if (position + 1 == visitor.size) {
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
        this.rvItem.adapter = dataAdapter
        dataAdapter.notifyDataSetChanged()
    }

    private fun setVisibilityHeader(textView: TextView, visibility: Int, data: String? = null) {
        textView.text = data
        textView.visibility = visibility
    }
    private fun setHeaderData(data: String) {
        header = data
    }

}