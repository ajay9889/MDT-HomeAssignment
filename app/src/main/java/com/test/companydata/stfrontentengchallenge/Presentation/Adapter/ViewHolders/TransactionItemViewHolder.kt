package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.databinding.ItemDsrowBinding

class TransactionItemViewHolder (viewGroup: ViewGroup,
                                 val clickItemList:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemDsrowBinding> (viewGroup ,ItemDsrowBinding::inflate ) {
    @SuppressLint("CheckResult")
    fun bindView( companyData: CompanyListData){
        with(viewBinding){
            gain.text = companyData?.gainloss
            name.text = companyData?.name
            symbol.text = companyData?.symbol
            date.text = "Trading Date: ${companyData?.trade_date}"
            linItem.setOnClickListener {
                clickItemList?.invoke(companyData!!)
            }
        }
    }
}