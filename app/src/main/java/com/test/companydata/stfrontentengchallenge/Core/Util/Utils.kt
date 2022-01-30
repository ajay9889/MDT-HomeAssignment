package com.test.companydata.stfrontentengchallenge.Core.Util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    const val ACTION_LOGOUT="com.intent.ACTION_LOGOUT"

    fun hideKeyboard(context: Context , view: View){
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.getWindowToken() ,0 )
    }
    fun getFormatedDate(inComingDate: String): String{
        val pos = ParsePosition(0)
        val simpledateformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val stringDate = simpledateformat.parse(inComingDate, pos)
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(stringDate)
    }

    fun getCurrentDate(): String{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
    }

    fun getNumberFormated(digits: Double): String{
        val currency = NumberFormat.getCurrencyInstance();
        return currency.format(digits)
    }
}