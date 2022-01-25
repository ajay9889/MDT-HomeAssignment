package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.databinding.BalanceItemBinding

class BalanceItemViewHolder (viewGroup: ViewGroup): BaseViewHolder <BalanceItemBinding> (viewGroup ,BalanceItemBinding::inflate ) {

    fun bindView( mBallance: DashaboardItems.Ballance){
        with(viewBinding){
            mBallance.mBalanceData.balance?.let{
                amount.text= Utils.getNumberFormated(it.toDouble())
            }
            txtaccountNo.text=mBallance.mBalanceData.accountNo
            txtaccntHolder.text=mBallance.mBalanceData.accountHolder
        }
    }

}