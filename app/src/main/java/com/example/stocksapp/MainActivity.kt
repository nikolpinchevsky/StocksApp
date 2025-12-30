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
            openStockScreen("NKE")
        }

        btnAmazon.setOnClickListener {
            openStockScreen("AMZN")
        }

        btnApple.setOnClickListener {
            openStockScreen("AAPL")
        }
    }

    private fun openStockScreen(symbol: String) {
        val intent = Intent(this, StockActivity::class.java)
        intent.putExtra("SYMBOL", symbol)
        startActivity(intent)
    }
}