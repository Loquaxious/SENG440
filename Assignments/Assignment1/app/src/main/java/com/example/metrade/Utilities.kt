package com.example.metrade

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection


fun parameterizeUrl(url: String, parameters: Map<String, String>): URL {
    val builder = Uri.parse(url).buildUpon()
    parameters.forEach { (key, value) -> builder.appendQueryParameter(key, value) }
    val uri = builder.build()
    return URL(uri.toString())
}

suspend fun getJson(url: URL): JSONObject {
    val result = withContext(Dispatchers.IO) {
        val connection = url.openConnection() as HttpsURLConnection
        try {
            val json = BufferedInputStream(connection.inputStream).readBytes()
                .toString(Charset.defaultCharset())
            JSONObject(json)
        } finally {
            connection.disconnect()
        }
    }
    return result
}

suspend fun postJson(url: URL, body: JSONObject): JSONObject {
    val result = withContext(Dispatchers.IO) {
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        try {
            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(body.toString())
            outputStreamWriter.flush()

            val json = BufferedInputStream(connection.inputStream).readBytes()
                .toString(Charset.defaultCharset())
            JSONObject(json)
        } finally {
            connection.disconnect()
        }
    }
    println(result)
    return result
}
