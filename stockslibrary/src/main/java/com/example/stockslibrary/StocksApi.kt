package com.example.stockslibrary

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit interface defining the communication with the Stocks API backend.
 * This interface maps HTTP endpoints to Kotlin suspend functions.
 */
interface StocksApi {
    /**
     * Authenticates a user and retrieves a JWT token.
     * @param body The login credentials (email and password).
     * @return A [TokenResponse] containing the access token.
     */
    @POST("/auth/login")
    suspend fun login(@Body body: AuthBody): TokenResponse

    /**
     * Registers a new user in the system.
     * @param body The registration details.
     * @return A [TokenResponse] containing the new access token.
     */
    @POST("/auth/register")
    suspend fun register(@Body body: AuthBody): TokenResponse

    /**
     * Fetches a list of all available stocks.
     * @return A [StocksListResponse] containing the list of stocks.
     */
    @GET("/stocks")
    suspend fun listStocks(): StocksListResponse

    /**
     * Seeds (creates or updates) a stock in the database.
     * @param body The stock details to be seeded.
     * @return A map containing the response data.
     */
    @POST("/stocks/seed")
    suspend fun seedStock(@Body body: SeedStockBody): Map<String, Any>

    /**
     * Retrieves the detailed quote for a specific stock symbol.
     * @param symbol The stock symbol (e.g., "AAPL").
     * @return A [QuoteResponse] containing the stock quote details.
     */
    @GET("/stocks/{symbol}/quote")
    suspend fun quote(@Path("symbol") symbol: String): QuoteResponse

    /**
     * Retrieves the historical data for a specific stock symbol.
     * @param symbol The stock symbol (e.g., "AAPL").
     * @return A [StockHistoryResponse] containing the stock's history.
     */
    @GET("/stocks/{symbol}/history")
    suspend fun history(@Path("symbol") symbol: String): StockHistoryResponse

}
