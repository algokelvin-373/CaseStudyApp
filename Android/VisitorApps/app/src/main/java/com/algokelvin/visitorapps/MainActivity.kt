package com.algokelvin.visitorapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.visitorapps.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_visitor.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var header = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setRecyclerView()
        }

    }

    private fun ActivityMainBinding.setRecyclerView() {
        val visitor = DataSample.setData()
        val borderUI = BorderUI(this@MainActivity, color = R.color.white,
            colorStroke = R.color.black, sizeStroke = 2, radius = ConstFunc.getSizeDp(resources, 8))

        this.rvItem.layoutManager = LinearLayoutManager(this@MainActivity)
        this.rvItem.adapter = DataAdapter(visitor.size, R.layout.item_visitor) { view, position ->
            if (visitor[position].date != header) {
                view.date_visitor.text = visitor[position].date
                header = visitor[position].date
            } else {
                view.date_visitor.visibility = View.GONE
            }

            view.cl_data_visitor.background = borderUI.getBorder()
            view.name_visitor.text = ("Name: ${visitor[position].name}")
            view.address_visitor.text = visitor[position].address
        }
    }

}