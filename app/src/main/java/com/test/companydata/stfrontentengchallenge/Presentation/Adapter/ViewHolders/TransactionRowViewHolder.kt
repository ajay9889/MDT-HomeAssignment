package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.TxnListAdapter
import com.test.companydata.stfrontentengchallenge.databinding.TransactionRowBinding

class TransactionRowViewHolder (viewGroup: ViewGroup) : BaseViewHolder <TransactionRowBinding> (viewGroup ,TransactionRowBinding::inflate ) {
    fun bindView( mTransactions: DashaboardItems.Transactions){
        with(viewBinding){

            txtDate.text= mTransactions.trnsItem.first//Utils.getFormatedDate(mTransactions.trnsItem.first)
            with(viewBinding.recyclerviewDashboardHome) {
                adapter = TxnListAdapter(mTransactions.trnsItem.second)
                layoutManager = LinearLayoutManager(viewGroup.context, LinearLayoutManager.VERTICAL, false)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
    }
}