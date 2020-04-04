package com.sysaxiom.handlers

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, length : Int){
    Toast.makeText(this, message, length).show()
}

fun ProgressBar.show(activity: Activity){
    visibility = View.VISIBLE
    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun ProgressBar.hide(activity: Activity){
    visibility = View.GONE
    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun View.snackbar(message: String, length: Int){
    Snackbar.make(this, message, length).show()
}

fun Context.alertDialog(title: String, message: String, positiveButtonText: String, cancelable: Boolean, drawable: Drawable) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(drawable)
        builder.setTitle(title)
        builder.setMessage(message)
            .setCancelable(cancelable)
            .setPositiveButton(positiveButtonText, null)
        val alert : AlertDialog = builder.create()
        alert.show()
    } catch (e: Exception) {
        Log.d("ViewHandler", e.toString())
    }
}

