package com.algokelvin.visitorapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.visitorapps.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_visitor.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        this.rvItem.layoutManager = LinearLayoutManager(this@MainActivity)
        this.rvItem.adapter = DataAdapter(visitor.size, R.layout.item_visitor) { view, position ->
            view.date_visitor.text = visitor[position].date
            view.name_visitor.text = ("Name: ${visitor[position].name}")
            view.address_visitor.text = visitor[position].address
        }
    }

}