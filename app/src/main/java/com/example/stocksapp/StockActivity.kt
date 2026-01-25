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

    // Instance of the custom Stocks SDK to interact with the backend API
    private val sdk = StocksSdk("https://stocks-server-e1vy.onrender.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        // Setup Back Button
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Get data passed from MainActivity
        val symbol = intent.getStringExtra("SYMBOL") ?: return
        val companyName = intent.getStringExtra("NAME")

        val tvSymbol = findViewById<TextView>(R.id.tvSymbol)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val barChart = findViewById<BarChart>(R.id.barChart)

        tvSymbol.text = companyName ?: symbol

        // Fetch data asynchronously using Coroutines
        lifecycleScope.launch {
            try {
                //// 1. Authenticate with the server (using demo credentials)
                sdk.login("niki@test.com", "123456")

                // 2. Fetch current stock quote and historical data
                val quote = sdk.quote(symbol)
                val history = sdk.getHistory(symbol)

                // 3. Update UI with fetched data
                tvPrice.text = "$${quote.c} USD"

                setupLineChart(lineChart, history)

                setupBarChart(barChart, history)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@StockActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    /**
     * Configures and populates the LineChart to show price trends over time.
     * @param chart The LineChart view.
     * @param points List of history points containing price data.
     */
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
        chart.invalidate()
    }

    /**
     * Configures and populates the BarChart to show trading volume.
     * @param chart The BarChart view.
     * @param points List of history points containing volume data.
     */
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
