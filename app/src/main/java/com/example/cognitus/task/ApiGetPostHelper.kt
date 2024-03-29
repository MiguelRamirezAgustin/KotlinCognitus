package com.example.cognitus.task

import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.Key

object ApiGetPostHelper {

    // Send Parameters method
    fun SendParams(reqURL: String, postDataParams: HashMap<String, String>?): String {

        val gsUrl: URL
        var resultString = ""
        try {
            gsUrl = URL(reqURL)

            val conn = gsUrl.openConnection() as HttpURLConnection
            conn.readTimeout = 7000
            conn.connectTimeout = 7000
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true

            if (postDataParams != null) {
                // For Post encoded Parameters
                val os = conn.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(getPostDataString(postDataParams))
                writer.flush()
                writer.close()
                os.close()

            } else {
                conn.requestMethod = "GET"
            }


            val responseCode = conn.responseCode
            Log.i("responseCode: ", responseCode.toString() + "")
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val allTextResponse = conn.inputStream.bufferedReader().use(BufferedReader::readText)
                resultString = allTextResponse
            } else {
                resultString = ""
            }
        } catch (e: Exception) {
            resultString = ""
            e.printStackTrace()
        }

        return resultString

    }


    // Collect Params from HashMap and encode with url.
    @Throws(UnsupportedEncodingException::class)
    private fun getPostDataString(params: HashMap<String, String>): String {
        val result = StringBuilder()
        var first = true
        for ((key, value) in params) {
            if (first)
                first = false
            else
                result.append("&")

            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }

        return result.toString()
    }
}