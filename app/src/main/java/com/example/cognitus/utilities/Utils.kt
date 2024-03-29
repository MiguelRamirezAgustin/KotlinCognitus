package com.example.cognitus.utilities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitus.activities.MainActivity

class Utils {
    //companion para poder exportat y usarlo en otras clases
    companion object{
            val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

        //fun para validar formato de correo
        fun isEmailValid(email:String):Boolean{
            return EMAIL_REGEX.toRegex().matches(email)
        }

        //fun para validar internet
        fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }

    }

}