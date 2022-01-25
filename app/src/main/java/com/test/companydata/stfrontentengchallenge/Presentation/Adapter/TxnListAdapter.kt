package com.test.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.companydata.stfrontentengchallenge.DataSource.module.DataX
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.BalanceItemViewHolder
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TitleItemViewHolder
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TransactionItemViewHolder

class TxnListAdapter(private val mDataX: List<DataX>
): RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    override fun getItemCount(): Int {
        return mDataX.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TransactionItemViewHolder ->{
                mDataX.get(position)?.let {
                    holder.bindView(it)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("viewType" ,"$viewType")
        return   TransactionItemViewHolder(parent)
    }


}