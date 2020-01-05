package com.bjtu.wanciwang.common

import android.annotation.SuppressLint
import android.os.Build
import android.os.StrictMode
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


object GetByURL {
    @SuppressLint("ObsoleteSdkInt")
    fun readParse(urlPath: String?): String {
        val outStream = ByteArrayOutputStream()
        val data = ByteArray(1024)
        var len = 0
        val url: URL
        try {
            url = URL(urlPath)
            val conn =
                url.openConnection() as HttpURLConnection
            if (Build.VERSION.SDK_INT > 9) {
                val policy =
                    StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
            }
            val inStream = conn.inputStream
            while (inStream.read(data).also { len = it } != -1) {
                outStream.write(data, 0, len)
            }
            inStream.close()
        } catch (e: IOException) { // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return String(outStream.toByteArray())
    }
}
