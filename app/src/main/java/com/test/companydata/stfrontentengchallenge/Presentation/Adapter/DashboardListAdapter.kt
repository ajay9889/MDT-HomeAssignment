package com.test.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.BalanceItemViewHolder
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TitleItemViewHolder
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TransactionItemViewHolder
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TransactionRowViewHolder

class DashboardListAdapter : PagingDataAdapter<DashaboardItems, RecyclerView.ViewHolder> (DataDifferentiator){
    object DataDifferentiator: DiffUtil.ItemCallback<DashaboardItems>(){
        override fun areItemsTheSame(oldItem: DashaboardItems, newItem: DashaboardItems): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: DashaboardItems, newItem: DashaboardItems): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            if (it is DashaboardItems.Ballance) {
                return 0
            } else if (it is DashaboardItems.Title) {
                return 1
            }
        }
        return 2
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BalanceItemViewHolder ->{
                getItem(position)?.let {it->
                    if(it is DashaboardItems.Ballance )
                    holder.bindView(it)
                }
            }
            is TitleItemViewHolder ->{
                getItem(position)?.let {it->
                    if(it is DashaboardItems.Title )
                        holder.bindView(it)
                }
            }
            is TransactionRowViewHolder ->{
                getItem(position)?.let {
                    if(it is DashaboardItems.Transactions )
                        holder.bindView(it)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("viewType" ,"$viewType")
        return  when(viewType){
            0->{
                BalanceItemViewHolder(parent)
            }
            1->{
                TitleItemViewHolder(parent)
            }
            else->{
                TransactionRowViewHolder(parent)
            }
        }
    }
}