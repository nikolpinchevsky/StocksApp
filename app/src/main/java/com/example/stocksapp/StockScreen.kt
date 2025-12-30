package com.example.stocksapp

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stockslibrary.StocksSdk
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.launch

class StockActivity : AppCompatActivity() {

    private val sdk = StocksSdk("https://stocks-server-e1vy.onrender.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
        val symbol = intent.getStringExtra("SYMBOL") ?: return

        val tvSymbol = findViewById<TextView>(R.id.tvSymbol)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val barChart = findViewById<BarChart>(R.id.barChart)

        tvSymbol.text = symbol

        lifecycleScope.launch {
            try {
                sdk.login("niki@test.com", "123456")

                val quote = sdk.quote(symbol)
                val history = sdk.getHistory(symbol)

                tvPrice.text = "$${quote.c} USD"

                setupLineChart(lineChart, history)

                setupBarChart(barChart, history)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@StockActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupLineChart(chart: LineChart, points: List<com.example.stockslibrary.HistoryPoint>) {
        val entries = points.mapIndexed { index, point ->
            Entry(index.toFloat(), point.price.toFloat())
        }

        val dataSet = LineDataSet(entries, "Price Trend")
        dataSet.color = Color.BLUE
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 2f

        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.invalidate() // רענון
    }

    private fun setupBarChart(chart: BarChart, points: List<com.example.stockslibrary.HistoryPoint>) {
        val entries = points.mapIndexed { index, point ->
            BarEntry(index.toFloat(), point.volume.toFloat())
        }

        val dataSet = BarDataSet(entries, "Volume")
        dataSet.color = Color.GRAY

        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.invalidate()
    }
}
