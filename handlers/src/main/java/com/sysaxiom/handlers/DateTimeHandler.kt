package com.sysaxiom.handlers

import android.util.Log
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHandler {

    fun convertMillisecondToDate(millisecond: Long, timezone: String): String {
        val date = DateTime(java.lang.Long.valueOf(millisecond), DateTimeZone.forID(timezone))
        return date.toString()
    }

    fun convertDateToMillisecond(date: String, format : String) : Long {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.parse(date).time
    }

    fun getCurrentDate(format: String) : String {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(Date())
    }

    //Getting today time in UTC format
    fun currentUTCTimeWithOffset(timezone: String): String {
        val zone = DateTimeZone.forID(timezone)
        return DateTime(zone).toString()
    }

    //Getting today time in UTC format
    fun returnOffset(timezone: String): String {
        var offset: String
        val zone = DateTimeZone.forID(timezone)
        val dateTime = DateTime(zone)
        try {
            offset = dateTime.toString().substring(23, 29)
        } catch (e: ArrayIndexOutOfBoundsException) {
            offset = "+00:00"
        } catch (e: Exception) {
            offset = "+00:00"
        }
        return offset
    }

    fun convertDateToRequiredFormat(inputString: String, userFormat : String, requiredFormat: String) : String {
        var date= String()
        val userSimpleDateFormat = SimpleDateFormat(userFormat)
        val requiredSimpleDateFormat = SimpleDateFormat(requiredFormat)
        try {
            date = requiredSimpleDateFormat.format(userSimpleDateFormat.parse(inputString))
        } catch (e: ParseException) {
            Log.d("DateTimeHandler", e.toString())
        }
        return date
    }

    fun checkDatePattern(date: String,format: String): Boolean {
        try {
            val format = SimpleDateFormat(format)
            format.parse(date)
            return true
        } catch (e: ParseException) {
            return false
        }
    }

}