package com.test.companydata.stfrontentengchallenge.Presentation.ViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.DataSource.module.DataX
import com.test.companydata.stfrontentengchallenge.DataSource.repository.LocalRepositoryImpl
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Domain.repository.AccountDataRepository
import com.test.companydata.stfrontentengchallenge.Domain.repository.LocalRepository
import com.test.companydata.stfrontentengchallenge.MainApplication
import com.test.companydata.stfrontentengchallenge.R
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel(val application: MainApplication,
                    val accntRepository : AccountDataRepository): AndroidViewModel(application){
    /**
     * Single Mutable live data to handle the response and viewstate
     * */

    val itemListDashaboardItems = MutableLiveData<ViewState<DashaboardItems>>()
    /**
     * Manage the
     * */



    /**
     * Load home screen data using pagging data source
     * **/


    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getPaggingSourceData() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            ItemListPagingSource (application,
                accntRepository,itemListDashaboardItems)
        }
    ).flowable.cachedIn(viewModelScope)


class ItemListPagingSource(val application : MainApplication ,
                           val accntRepository : AccountDataRepository ,
                           val itemListDashaboardItems : MutableLiveData<ViewState<DashaboardItems>> ): PagingSource<Int, DashaboardItems>() {
        override fun getRefreshKey(state: PagingState<Int, DashaboardItems>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashaboardItems> {
            val dashbboardData = ArrayList<DashaboardItems>()
            itemListDashaboardItems.postValue(ViewState.Loading())
            dashbboardData.add(DashaboardItems.Title(application.resources.getString(R.string.balance_accnt)))
            accntRepository.getAccountBalances().let {
               when(it){
                   is ViewState.Content->{
                       val items = it.data
                       items.accountHolder = LocalRepositoryImpl(application.applicationContext).getUserAccountHolder()
                       dashbboardData.add(DashaboardItems.Ballance(items))
                   }
                   is ViewState.Message->{
                       itemListDashaboardItems.postValue(ViewState.Message(it.message))
                   }
                   else -> { itemListDashaboardItems.postValue(ViewState.Message(application.resources.getString(R.string.some_error))) }
               }
            }
            dashbboardData.add(DashaboardItems.Title(application.resources.getString(R.string.trns_accnt)))

            val map = HashMap<String, List<DataX>>();
            accntRepository.getTransactionsList().let {
                when(it){
                    is ViewState.Content->{
                        it.data.data?.map {txnData->
                            val key = Utils.getFormatedDate(txnData.transactionDate!!)
                            if (!map.containsKey(key)){
                                map.put(key, ArrayList<DataX>().apply {
                                    this.add(txnData)
                                })
                            }else{
                                val singleItem = map.get(key)?.toMutableList()!!
                                singleItem.add(txnData);
                                map.put(key,singleItem )
                            }
                        }
                        // adding all items
                        map.entries.map {
                            dashbboardData.add(DashaboardItems.Transactions( Pair(it.key, it.value)))
                        }
                    }else -> {}
                }
            }
            return LoadResult.Page(
                data = dashbboardData ,
                prevKey = null,
                nextKey = null
            )
        }
    }
}