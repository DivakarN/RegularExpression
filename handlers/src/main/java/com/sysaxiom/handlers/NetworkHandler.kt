package com.sysaxiom.handlers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.util.HashSet

class NetworkConnectionHandler : BroadcastReceiver() {

    internal var connected: Boolean? = null
    internal var listeners: MutableSet<NetworkChangeListener>

    init {
        listeners = HashSet()
    }

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT > 22) {
                val activeNetwork = cm.activeNetwork
                if(activeNetwork!=null){
                    val capabilities = cm.getNetworkCapabilities(activeNetwork)
                    if(capabilities!=null){
                        connected = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    }else{
                        connected = false
                    }
                } else{
                    connected = false
                }
                notifyStateToAll()
            } else {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
                    if (activeNetwork.isConnected) {
                        connected = true
                    } else {
                        connected = false
                    }
                } else {
                    connected = false
                }
                notifyStateToAll()
            }
        } catch (e: Exception) {
            Log.d("NetworkHandler", e.toString())
        }

    }

    private fun notifyStateToAll() {
        try {
            for (networkChangeListener in listeners) {
                notifyState(networkChangeListener)
            }
        } catch (e: Exception) {
            Log.d("NetworkHandler", e.toString())
        }

    }

    private fun notifyState(networkChangeListener: NetworkChangeListener?) {
        try {
            if (networkChangeListener == null || connected == null) {
                return
            }
            if (connected!!) {
                networkChangeListener.networkAvailable()
            } else {
                networkChangeListener.networkUnavailable()
            }
        } catch (e: Exception) {
            Log.d("NetworkHandler", e.toString())
        }

    }

    fun addListener(l: NetworkChangeListener) {
        listeners.add(l)
        notifyState(l)
    }

    fun removeListener(l: NetworkChangeListener) {
        listeners.remove(l)
    }

    interface NetworkChangeListener {
        fun networkAvailable()
        fun networkUnavailable()
    }
}