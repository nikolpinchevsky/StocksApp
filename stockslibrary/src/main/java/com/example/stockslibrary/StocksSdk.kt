package com.example.stockslibrary

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Stocks SDK - a thin client around the backend REST API.
 *
 * Responsibilities:
 * - Adds Authorization header (Bearer token) when a token is set
 * - Exposes convenient suspend methods for the main API endpoints
 *
 * baseUrl MUST end with "/" (Retrofit requirement).
 */
class StocksSdk(baseUrl: String) {

    // Stored in-memory token used by the interceptor (not persisted).
    @Volatile private var token: String? = null

    /** Set/clear the JWT token used for authenticated requests. */
    fun setToken(jwt: String?) {
        token = jwt
    }

    // Adds Authorization header automatically (if token exists).
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

    /**
     * Login with email/password.
     * On success, the token is saved internally and reused automatically.
     */
    suspend fun login(email: String, password: String): String {
        val res = api.login(AuthBody(email, password))
        setToken(res.token)
        return res.token
    }

    /** Returns all stocks from /stocks endpoint. */
    suspend fun getStocks(): List<StockItem> = api.listStocks().items

    /** Admin/seed operation: creates or updates a stock entry. */
    suspend fun seedStock(symbol: String, name: String?, price: Double, currency: String = "USD") {
        api.seedStock(SeedStockBody(symbol, name, price, currency))
    }

    /** Returns the current quote for a symbol. */
    suspend fun quote(symbol: String): QuoteResponse = api.quote(symbol)

    /** Returns history points for charts. */
    suspend fun getHistory(symbol: String): List<HistoryPoint> =
        api.history(symbol).points

}
