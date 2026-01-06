package com.example.stockslibrary

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * SDK for interacting with the Stocks API backend.
 * Handles authentication, stock retrieval, seeding, quoting, and history fetching.
 * @property baseUrl The base URL of the Stocks API.
 */
class StocksSdk(baseUrl: String) {

    @Volatile private var token: String? = null

    /**
     * Sets the JWT token for authenticated requests.
     * @param jwt The JWT token string.
     */
    fun setToken(jwt: String?) {
        token = jwt
    }

    /**
     * Retrieves the current JWT token.
     * @return The JWT token string, or null if not set.
     */
    private val authInterceptor = Interceptor { chain ->
        val req = chain.request().newBuilder()
        val t = token
        if (!t.isNullOrBlank()) {
            req.addHeader("Authorization", "Bearer $t")
        }
        chain.proceed(req.build())
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) // MUST end with /
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(StocksApi::class.java)

    /***
     * Logs in a user with the provided email and password.
     * @param email The user's email address.
     * @param password The user's password.
     * @return The JWT token string upon successful login.
     */
    suspend fun login(email: String, password: String): String {
        val res = api.login(AuthBody(email, password))
        setToken(res.token)
        return res.token
    }

    /**
     * Retrieves the list of all stocks available in the system.
     */
    suspend fun getStocks(): List<StockItem> = api.listStocks().items

    /**
     * Creates or updates a stock entry (Admin/Seed operation).
     */
    suspend fun seedStock(symbol: String, name: String?, price: Double, currency: String = "USD") {
        api.seedStock(SeedStockBody(symbol, name, price, currency))
    }

    /**
     * Fetches the current price quote for a given stock symbol.
     * @param symbol The stock symbol (e.g. "AAPL").
     */
    suspend fun quote(symbol: String): QuoteResponse = api.quote(symbol)

    /**
     * Fetches historical data points for plotting graphs.
     * @param symbol The stock symbol.
     * @return A list of [HistoryPoint] objects containing timestamp, price, and volume.
     */
    suspend fun getHistory(symbol: String): List<HistoryPoint> =
        api.history(symbol).points

}
