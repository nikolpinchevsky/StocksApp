package com.example.stockslibrary

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit endpoints mapping for the Stocks backend API.
 * Each function corresponds to a REST endpoint and runs as a suspend network call.
 */
interface StocksApi {
    /** POST /auth/login -> returns JWT token. */
    @POST("/auth/login")
    suspend fun login(@Body body: AuthBody): TokenResponse

    /** POST /auth/register -> returns JWT token. */
    @POST("/auth/register")
    suspend fun register(@Body body: AuthBody): TokenResponse

    /** GET /stocks -> list of all stocks. */
    @GET("/stocks")
    suspend fun listStocks(): StocksListResponse

    /** POST /stocks/seed -> create/update stock (admin/seed). */
    @POST("/stocks/seed")
    suspend fun seedStock(@Body body: SeedStockBody): Map<String, Any>

    /** GET /stocks/{symbol}/quote -> detailed quote for a symbol. */
    @GET("/stocks/{symbol}/quote")
    suspend fun quote(@Path("symbol") symbol: String): QuoteResponse

    /** GET /stocks/{symbol}/history -> time series points for charts. */
    @GET("/stocks/{symbol}/history")
    suspend fun history(@Path("symbol") symbol: String): StockHistoryResponse

}
