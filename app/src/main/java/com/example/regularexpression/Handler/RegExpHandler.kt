package com.example.regularexpression.Handler

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast

class RegExpHandler(var context:Context) {
    fun checkFieldsForEmptyValues(username: String, password: String): Boolean {
        try {
            if (username == "" || password == "") {
                Toast.makeText(context, "Field is empty", Toast.LENGTH_SHORT).show()
                return false
            } else if (username.length > 2 && password.length >= 8) {
                return true
            } else {
                Toast.makeText(context, "username and password length is wrong", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!username.isEmpty()) {
                Toast.makeText(context, "Username is not empty", Toast.LENGTH_SHORT).show()
                return true
            }
            if (!password.isEmpty()) {
                Toast.makeText(context, "Username is not empty", Toast.LENGTH_SHORT).show()
                return true
            }
        } catch (e: Exception) {
            Log.d("RapidTurnAround", e.toString())
        }
        return false
    }
}
       