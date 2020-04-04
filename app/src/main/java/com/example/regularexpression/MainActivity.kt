package com.example.regularexpression

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.regularexpression.Handler.RegExpHandler
import com.example.regularexpression.Handler.RegularExpressionClass

class MainActivity : AppCompatActivity() {
    //region variable declaration
    lateinit var editTextPassword:EditText
    lateinit var editTextUsername:EditText
    lateinit var loginButton:Button
    lateinit var txtEnterUsername:TextView
    lateinit var txtEnterPassword:TextView
    lateinit var rapidLogoImageview:ImageView
    lateinit var secureEntryImageview:ImageView
    lateinit var txtNetworkConnectionStatus:TextView
    var regularExpressionClassObj:RegularExpressionClass= RegularExpressionClass(this)
    lateinit var constraintLayoutMain:ConstraintLayout
    var regExpHandlerObj:RegExpHandler=RegExpHandler(this@MainActivity)
    //endregion

    //region onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            rapidLogoImageview = findViewById(R.id.imageview_tsi_logo)
            editTextUsername = findViewById(R.id.edittext_username)
            editTextPassword = findViewById(R.id.edittext_password)
            loginButton = findViewById(R.id.button_login)
            secureEntryImageview = findViewById(R.id.imageview_secure_entry)
            txtNetworkConnectionStatus = findViewById(R.id.networkConnectionStatusTextview)
            txtNetworkConnectionStatus.setVisibility(View.GONE)
            constraintLayoutMain = findViewById(R.id.constrainmain)

            txtEnterUsername = findViewById(R.id.textview_enter_username)
            txtEnterPassword = findViewById(R.id.textview_enter_password)

            val passwordBackground: Drawable = editTextPassword.getBackground()
            val usernameBackground: Drawable = editTextUsername.getBackground()
            var username: String = editTextUsername.getText().toString()
            var password: String = editTextPassword.getText().toString()
            regExpHandlerObj.checkFieldsForEmptyValues(username,password)


            editTextUsername.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus ->
                    try {
                        if (!hasFocus) {
                            var username: String = editTextUsername.getText().toString()
                            if (regularExpressionClassObj.emptyValue(username)==false) {
                                txtEnterUsername.visibility = View.VISIBLE
                            }
                            editTextUsername.setBackgroundColor(Color.TRANSPARENT)
                            editTextUsername.setTextColor(resources.getColor(R.color.flightCompletedTaskListColor))
                        } else {
                            editTextUsername.background =
                                resources.getDrawable(R.drawable.rounded_white_border_edittext)
                            editTextUsername.setTextColor(resources.getColor(R.color.black))
                        }
                    } catch (e: java.lang.Exception) {
                        Log.d("RapidTurnAround", e.toString())
                    }
                }

            editTextPassword.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus ->
                    try {
                        if (!hasFocus) {
                            if (editTextPassword.text.toString().isEmpty()) {
                                txtEnterPassword.visibility = View.VISIBLE
                            }
                            editTextPassword.setBackgroundColor(Color.TRANSPARENT)
                            editTextPassword.setTextColor(resources.getColor(R.color.flightCompletedTaskListColor))
                        } else {
                            editTextPassword.background =
                                resources.getDrawable(R.drawable.rounded_white_border_edittext)
                            editTextPassword.setTextColor(resources.getColor(R.color.black))
                        }
                    } catch (e: java.lang.Exception) {
                        Log.d("RapidTurnAround", e.toString())
                    }
                }

            loginButton.setOnClickListener {
                var username: String = editTextUsername.getText().toString()
                var password: String = editTextPassword.getText().toString()
                println("@@username"+username)
                println("@@password"+password)
                regExpHandlerObj.checkFieldsForEmptyValues(username,password)
                var paawordvalue:Boolean=regularExpressionClassObj.emailValidator(password)
                println("@@passwordValue"+paawordvalue)
                var passwordValue1:Boolean= regularExpressionClassObj.phoneNumberValidate(password)
                println("@@"+passwordValue1)
                var urlvalue:Boolean=regularExpressionClassObj.isValidUrl(username)
                println("@@"+urlvalue)
            }


        }catch (e:Exception)
        {
            e.printStackTrace()
            println("@@"+e.toString())
        }
    }
    //endregion

}
