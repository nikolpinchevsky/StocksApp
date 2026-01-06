package com.example.stockslibrary

/**
 * Represents the credentials required for user authentication.
 * @property email The user's email address.
 * @property password The user's password.
 */
data class AuthBody(val email: String, val password: String)

/**
 * Response object containing the authentication token.
 * @property token The JWT (JSON Web Token) returned by the server.
 */
data class TokenResponse(val token: String)

/**
 * Represents a single data point in a stock's history graph.
 * @property ts Unix timestamp of the data point (in milliseconds or seconds).
 * @property price The stock price at this specific time.
 * @property volume The trading volume at this specific time.
 */
data class HistoryPoint(val ts: Long, val price: Double, val volume: Int)

/**
 * Response object containing the full history of a specific stock.
 * @property symbol The stock symbol (e.g., "AAPL").
 * @property points A list of historical data points for charting.
 */
data class StockHistoryResponse(val symbol: String, val points: List<HistoryPoint>)


/**
 * Request body for seeding (creating or updating) a stock in the database.
 * @property symbol The stock ticker symbol.
 * @property name The full name of the company.
 * @property price The initial or current price.
 * @property currency The currency code (default is "USD").
 */
data class SeedStockBody(
    val symbol: String,
    val name: String?,
    val price: Double,
    val currency: String = "USD"
)

/**
 * Represents a stock item in a list.
 * @property updatedAt Timestamp of the last update.
 */
data class StockItem(
    val symbol: String,
    val name: String?,
    val price: Double,
    val currency: String,
    val updatedAt: Long?
)

/**
 * Detailed quote response for a specific stock.
 * @property c The Current price of the stock.
 * @property source The source of the data (e.g., "mongodb").
 */
data class QuoteResponse(
    val symbol: String,
    val name: String? = null,
    val c: Double = 0.0,
    val currency: String = "USD",
    val updatedAt: Long? = null,
    val source: String? = null
)

/**
 * A wrapper response for a list of stocks.
 */
data class StocksListResponse(val items: List<StockItem>)
