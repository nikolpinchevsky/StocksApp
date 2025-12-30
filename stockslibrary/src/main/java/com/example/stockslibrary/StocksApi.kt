package com.example.stockslibrary

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StocksApi {
    @POST("/auth/login")
    suspend fun login(@Body body: AuthBody): TokenResponse

    @POST("/auth/register")
    suspend fun register(@Body body: AuthBody): TokenResponse

    @GET("/stocks")
    suspend fun listStocks(): StocksListResponse

    @POST("/stocks/seed")
    suspend fun seedStock(@Body body: SeedStockBody): Map<String, Any>

    @GET("/stocks/{symbol}/quote")
    suspend fun quote(@Path("symbol") symbol: String): QuoteResponse

    @GET("/stocks/{symbol}/history")
    suspend fun history(@Path("symbol") symbol: String): StockHistoryResponse

}
