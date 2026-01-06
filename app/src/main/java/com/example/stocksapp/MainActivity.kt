package com.example.stocksapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnNike = findViewById<Button>(R.id.btnNike)
        val btnAmazon = findViewById<Button>(R.id.btnAmazon)
        val btnApple = findViewById<Button>(R.id.btnApple)

        btnNike.setOnClickListener {
            openStockScreen("NKE", "Nike")
        }

        btnAmazon.setOnClickListener {
            openStockScreen( "AMZN", "Amazon")
        }

        btnApple.setOnClickListener {
            openStockScreen("AAPL", "Apple")
        }
    }

    /**
     * Navigates to the StockActivity to display details for the selected stock.
     * @param symbol The stock ticker symbol (e.g., "AAPL").
     * @param companyName The full name of the company to display in the title.
     */
    private fun openStockScreen(symbol: String, companyName: String) {
        val intent = Intent(this, StockActivity::class.java)
        intent.putExtra("SYMBOL", symbol)
        intent.putExtra("NAME", companyName)
        startActivity(intent)
    }
}