package com.example.androidtestapikotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

public class CommonTask() {

    suspend fun getRemotoData(url: String?): String?=  withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        val inStr = StringBuilder()

        try {
            connection.doInput = true
//            connection.doOutput = true
            connection.setChunkedStreamingMode(0)
            connection.useCaches = false
//            connection.setRequestProperty("User-Agent", "Chrome 74 on Windows 10")
            connection.requestMethod = "GET"
            connection.setRequestProperty("charset", "UTF-8")
//            var bw = BufferedWriter(OutputStreamWriter(connection.outputStream))
//            bw.write(outStr)
//            Log.e("TAG", "output:" + outStr)
//            bw.close()

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val br = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String

                while (br.readLine().also { line = it } != null) {

                    inStr.append(line)

                }
            } else {
                Log.d("TAG", "response code:$responseCode")
            }
        } catch (e: Exception) {
            Log.d("TAG", "=" + e);


        } finally {
            connection.disconnect()
        }

        Log.e("inStr=", inStr.toString())
        inStr.toString()


    }


    suspend fun getRemoteImage(url: String): Bitmap? = withContext(Dispatchers.IO){
        var connection = URL(url).openConnection() as HttpURLConnection
        var bitmap: Bitmap? = null

        try {
            connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true // allow inputs
//            connection.doOutput = true // allow outputs
            connection.useCaches = false // do not use a cached copy
            connection.requestMethod = "GET"
            connection.setRequestProperty("charset", "UTF-8")
            connection.setRequestProperty("User-Agent", "Chrome 74 on Windows 10")
//            val bw =
//                BufferedWriter(OutputStreamWriter(connection.outputStream))
//            bw.close()
            val responseCode = connection.responseCode
            if (responseCode == 200) {
                bitmap = BitmapFactory.decodeStream(
                    BufferedInputStream(connection.inputStream)
                )
            } else {
                Log.e("responseCode=", responseCode.toString())

            }
        } catch (e: IOException) {

            Log.e("IOException", e.toString())

        } finally {
            connection?.disconnect()
        }
         bitmap

    }


}