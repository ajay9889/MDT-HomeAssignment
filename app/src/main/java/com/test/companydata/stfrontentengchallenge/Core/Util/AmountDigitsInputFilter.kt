package com.test.companydata.stfrontentengchallenge.Core.Util

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

class AmountDigitsInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,dest: Spanned?,dstart: Int,dend: Int): CharSequence? {
        val mPattern =  Pattern.compile("[0-9]*+((\\.[0-9]?)?)||(\\.)?")
        val matcher: Matcher = mPattern.matcher(dest)
        if (!matcher.matches())
            return "";
        return null;
    }
}