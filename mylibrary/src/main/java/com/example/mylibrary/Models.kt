package com.example.mylibrary

data class AuthBody(val email: String, val password: String)
data class TokenResponse(val token: String)

data class HistoryPoint(val ts: Long, val price: Double, val volume: Int)
data class StockHistoryResponse(val symbol: String, val points: List<HistoryPoint>)

data class SeedStockBody(
    val symbol: String,
    val name: String?,
    val price: Double,
    val currency: String = "USD"
)

data class StockItem(
    val symbol: String,
    val name: String?,
    val price: Double,
    val currency: String,
    val updatedAt: Long?
)

data class QuoteResponse(
    val symbol: String,
    val name: String? = null,
    val c: Double = 0.0,
    val currency: String = "USD",
    val updatedAt: Long? = null,
    val source: String? = null
)


data class StocksListResponse(val items: List<StockItem>)
