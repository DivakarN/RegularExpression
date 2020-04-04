package com.sysaxiom.handlers

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.security.cert.CertificateException
import java.util.ArrayList
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object MQTTHandler {

    fun mqttConnectionDetails(context: Context, mqttUrl: String, mqttUsername: String, mqttPassword: String, clientId : String): ArrayList<MQTTModel> {
        val client = MqttAndroidClient(context, mqttUrl, clientId, MemoryPersistence())
        val options = MqttConnectOptions()
        options.userName = mqttUsername
        options.password = mqttPassword.toCharArray()
        options.isAutomaticReconnect = false
        options.isCleanSession = true
        options.keepAliveInterval = 60
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
                    return null
                }
            })
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("TLSv1.1")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            options.socketFactory = sslSocketFactory
        } catch (e: Exception) {
            Log.d("MqttHandler", e.toString())
        }

        val model = MQTTModel(client, options)
        val list = ArrayList<MQTTModel>()
        list.add(model)
        return list
    }

    fun connectToMqtt(client: MqttAndroidClient, options: MqttConnectOptions, mqttTopicName: ArrayList<String>) {
        try {
            val token = client.connect(options)
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    client.setBufferOpts(disconnectedBufferOptions)
                    Log.d("MqttHandler", "Connected Successfully")
                    //Subscribe all the topics
                    try {
                        for(i in 0 until mqttTopicName.size){
                            client.subscribe(mqttTopicName[i],0)
                        }
                    } catch (e: MqttException) {
                        Log.d("MqttHandler", e.toString())
                    }

                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MqttHandler", exception.toString())
                }
            }
        } catch (e: MqttException) {
            Log.d("MqttHandler", "Issue Occured while connecting from mqtt server")
        }
    }

}

data class MQTTModel (
    val client: MqttAndroidClient,
    val options: MqttConnectOptions
)