package com.example.instagram_clone.utils

import android.content.Context
import com.example.instagram_clone.models.Tokens

class TokenHandler {

    companion object{

        fun saveTokens(context:Context,tokens:Tokens){
            val pref = context.getSharedPreferences("Instagram_Auth",Context.MODE_PRIVATE)
            pref.edit().putString(Constants.ACCESS_TOKEN,tokens.accessToken).apply()
            pref.edit().putString(Constants.REFRESH_TOKEN,tokens.refreshToken).apply()
        }

        fun getTokens(context:Context):Tokens{
            val pref = context.getSharedPreferences("Instagram_Auth",Context.MODE_PRIVATE)
            val accessToken = pref.getString(Constants.ACCESS_TOKEN,"")
            val refreshToken = pref.getString(Constants.REFRESH_TOKEN,"")
            return Tokens(accessToken!!,refreshToken!!)
        }
    }

}