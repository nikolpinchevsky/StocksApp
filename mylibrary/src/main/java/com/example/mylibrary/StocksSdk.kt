package com.example.mylibrary

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StocksSdk(baseUrl: String) {

    @Volatile private var token: String? = null

    fun setToken(jwt: String?) {
        token = jwt
    }

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

    // ---- simple methods (what app will use) ----
    suspend fun login(email: String, password: String): String {
        val res = api.login(AuthBody(email, password))
        setToken(res.token)
        return res.token
    }

    suspend fun getStocks(): List<StockItem> = api.listStocks().items

    suspend fun seedStock(symbol: String, name: String?, price: Double, currency: String = "USD") {
        api.seedStock(SeedStockBody(symbol, name, price, currency))
    }

    suspend fun quote(symbol: String): QuoteResponse = api.quote(symbol)

    suspend fun getHistory(symbol: String): List<HistoryPoint> =
        api.history(symbol).points

}
