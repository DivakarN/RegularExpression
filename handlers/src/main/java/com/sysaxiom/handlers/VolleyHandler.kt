package com.sysaxiom.handlers

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.HashMap

object VolleyHandler {

    //region Getting Asynchoronous Get API Calls
    fun getAyncNetworkCall(url: String, context: Context, listener: ServiceNetworkListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    listener.onResponse(response)
                }, Response.ErrorListener { error ->
                    listener.onError(error.toString())
                })
            queue.add(jsonObjectRequest)

        } catch (e: Exception) {
            Log.d("VolleyHandler", e.toString())
        }
    }//endregion

    //region Getting Asynchrounous Post API Calls
    fun postAsyncNetworkCall(url: String, context: Context, requestBody: String?, listener: ServiceNetworkListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val jsonObjectRequest = object :
                JsonObjectRequest(Request.Method.POST, url, null,
                    Response.Listener { response ->
                        listener.onResponse(response)
                    }, Response.ErrorListener { error ->
                        listener.onError(error.toString())
                    }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    return headers
                }

                override fun getBody(): ByteArray? {
                    try {
                        return requestBody?.toByteArray(charset("utf-8"))
                    } catch (e: UnsupportedEncodingException) {
                        Log.d("VolleyHandler", e.toString())
                        return null
                    }
                }
            }
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            Log.d("VolleyHandler", e.toString())
        }
    }//endregion

}

interface ServiceNetworkListener {
    fun onError(message: String)
    fun onResponse(response: JSONObject)
}