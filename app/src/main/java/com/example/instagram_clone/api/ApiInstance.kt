package com.example.instagram_clone.api
import android.app.Application
import com.example.instagram_clone.api.auth.AuthInterface
import com.example.instagram_clone.api.users.UserInterface
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.TokenHandler
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp:Application(){
    companion object{
        private var instance:MyApp? = null

        fun getInstance():MyApp{
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

object ApiInstance {

    private val interceptor = Interceptor {
        chain ->
        val tokens = TokenHandler.getTokens(MyApp.getInstance())
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader(Constants.AUTHORIZATION, "Bearer ${tokens.accessToken}")
                    .addHeader(Constants.REFRESH_TOKEN, "Bearer ${tokens.refreshToken}")
                    .build()
            )
        }
    }

    private val retrofit:Retrofit by lazy{

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi:AuthInterface by lazy {
        retrofit.create(AuthInterface::class.java)
    }

    val userApi:UserInterface by lazy {
        retrofit.create(UserInterface::class.java)
    }
}