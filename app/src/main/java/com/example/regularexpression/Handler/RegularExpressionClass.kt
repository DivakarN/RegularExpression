package com.example.regularexpression.Handler

import android.content.Context
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegularExpressionClass(var content:Context) {
    //region emptyValue()
    fun emptyValue(username: String):Boolean {
        if (!username.isEmpty())
        {
            return true
        }
        else{
            return false
        }
    }
    //rendregion

    //region getLength()
    fun getLength(username: String, i: Int):Boolean{
        if(username.length>=i)
        {
            return true
        }
        else
        {
            return false
        }
    }
    //endregion

    //region specialcharacterCheck()
    fun specialCharacterCheck(password:String):Boolean {
        var regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        if (regex.matcher(password).find()) {
            return true
        }
        else
        {
            return false
        }
    }
    //endregion

    //region passwoedtextAlteadtonenumber()
    fun passwordtextAtleastoneNumber(password: String):Boolean {
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            return false
        }
        else
        {
            return true
        }
    }
    //endregion

    //region isValidPassword()
    /**
     * Function description:

    (?=.*[0-9]) # a digit must occur at least once
    (?=.*[a-z]) # a lower case letter must occur at least once
    (?=.*[A-Z]) # an upper case letter must occur at least once
    (?=.[-@%[}+'!/#$^?:;,(")~`.=&{>]<_]) # a special character must occur at least once replace with your special characters
    (?=\S+$) # no whitespace allowed in the entire string .{8,} # anything, at least six places though
     */
    fun isValidPassword(password: String): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
    val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
    pattern = Pattern.compile(PASSWORD_REGEX)
    matcher = pattern.matcher(password)
    return matcher.matches()
   }
    //endregion

//    //region urlValid()
//    fun urlValid(urlId:String):Boolean {
//        val regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
//        val inputStr: CharSequence = urlId
//        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
//        val matcher: Matcher = pattern.matcher(inputStr)
//        if (matcher.matches()) {
//            return  true
//        }
//        return false
//    }
//    //endregion

    //region phoneNumberValidate()
    fun phoneNumberValidate(phoneNumber:String): Boolean {
        var regexpPhone:String="^[0-9]{10}\$"
        var pattern = Pattern.compile(regexpPhone)
        var  matcher = pattern.matcher(phoneNumber)
        if (!matcher.matches()) {
            return false
        }
        else
        {
            return true
        }
    }
    //endregion

    //region emailvalidator
    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }
    //endregion

    //region isValidUrl()
    fun isValidUrl(url: String): Boolean {
        val p = Patterns.WEB_URL
        val m = p.matcher(url.toLowerCase())
        return m.matches()
    }
    //endregion

}

