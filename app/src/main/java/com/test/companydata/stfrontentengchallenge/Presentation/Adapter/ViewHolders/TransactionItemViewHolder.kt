package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders
import android.graphics.Color
import android.view.ViewGroup
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.DataSource.module.DataX
import com.test.companydata.stfrontentengchallenge.databinding.TransactionItemBinding

class TransactionItemViewHolder (viewGroup: ViewGroup) : BaseViewHolder <TransactionItemBinding> (viewGroup ,TransactionItemBinding::inflate ) {
    fun bindView( mTransactions: DataX){
        with(viewBinding){

            if(mTransactions.transactionType?.equals("received") == true){
                ammount.setTextColor(Color.parseColor("#088E0D"))
                name.text = mTransactions.sender?.accountHolder
                accountNo.text =mTransactions.sender?.accountNo
                mTransactions.amount?.let {
                    ammount.text = Utils.getNumberFormated(it.toDouble())
                }
            }else{
                ammount.setTextColor(Color.parseColor("#000000"))
                name.text = mTransactions.receipient?.accountHolder
                accountNo.text =mTransactions.receipient?.accountNo
                mTransactions.amount?.let {
                    ammount.text = "-"+Utils.getNumberFormated(it.toDouble())
                }
            }

        }
    }
}