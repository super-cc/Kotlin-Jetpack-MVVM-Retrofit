package love.isuper.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceCreator {

    fun createHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLogInterceptor())
            .build()
        return httpClient
    }

    fun createHttpClientWithHeader(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLogInterceptor())
            .build()
        return httpClient
    }

    fun <T> create(serviceClass: Class<T>): T {
        val httpClient = createHttpClientWithHeader()

        val builder = Retrofit.Builder()
            .baseUrl(NetworkManager.getInstance().getBaseUrl())
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }

}