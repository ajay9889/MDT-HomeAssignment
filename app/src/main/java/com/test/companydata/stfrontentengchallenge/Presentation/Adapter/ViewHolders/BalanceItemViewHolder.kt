package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote.Companion.toGlobalQoutes
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.ItemGanerloaserBinding

class BalanceItemViewHolder (viewGroup: ViewGroup,
                             val clickItemList:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemGanerloaserBinding> (viewGroup ,ItemGanerloaserBinding::inflate ) {
    @SuppressLint("CheckResult")
    fun bindView( tbGlobalQuote: TbGlobalQuote){
        with(viewBinding){
            tbGlobalQuote.changePercent?.let {
                changesPerc.text = it
                if(it.contains("-".toRegex())){
                    changesPerc.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.red))
                }
            }
            date.text = "Last trading date on \n${tbGlobalQuote?.latestTradingDay}"
            title.text = tbGlobalQuote?.symbol
            mainItems.setOnClickListener {
                tbGlobalQuote?.let { it1 -> clickItemList?.invoke(it1.toGlobalQoutes()) }
            }
        }
    }

}