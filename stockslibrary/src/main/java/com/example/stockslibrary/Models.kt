package com.example.stockslibrary

/**
 * DTO models (request/response) used by the Stocks SDK.
 * These classes map directly to the backend REST API payloads.
 */
data class AuthBody(val email: String, val password: String)


data class TokenResponse(val token: String)

/**
 * One point in stock history (for charts).
 * Note: ts is a Unix timestamp (make sure the backend/client agree on ms vs seconds).
 */
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
