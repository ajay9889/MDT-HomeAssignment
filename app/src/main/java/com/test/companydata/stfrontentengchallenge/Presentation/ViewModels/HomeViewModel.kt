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
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Domain.repository.AccountDataRepository
import com.test.companydata.stfrontentengchallenge.MainApplication

class HomeViewModel(application: MainApplication, val accntRepository : AccountDataRepository): AndroidViewModel(application){
    val itemListDashaboardItems = MutableLiveData<ViewState<DashaboardItems>>()

    // fetching data paging source
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )

    fun getPaggingSourceData() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            ItemListPagingSource (accntRepository,itemListDashaboardItems)
        }
    ).flowable.cachedIn(viewModelScope)

class ItemListPagingSource(val accntRepository : AccountDataRepository ,
                           val itemListDashaboardItems : MutableLiveData<ViewState<DashaboardItems>> ): PagingSource<Int, DashaboardItems>() {
        override fun getRefreshKey(state: PagingState<Int, DashaboardItems>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashaboardItems> {
            val dashbboardData = ArrayList<DashaboardItems>()
            itemListDashaboardItems.postValue(ViewState.Loading())
            accntRepository.getAccountBalances().let {
               when(it){
                   is ViewState.Content->{
                       dashbboardData.add(DashaboardItems.Ballance(it.data))
                   }
                   is ViewState.Message->{
                       itemListDashaboardItems.postValue(ViewState.Message(it.message))
                   }
                   else -> {
                       // error
                       itemListDashaboardItems.postValue(ViewState.Message("Error occured!"))
                   }
               }
            }
            accntRepository.getTransactionsList().let {
                when(it){
                    is ViewState.Content->{
                        it.data?.data?.map {
                            dashbboardData.add(DashaboardItems.Transactions(it))
                        }
                    }is ViewState.Message->{
                        itemListDashaboardItems.postValue(ViewState.Message(it.message))
                    }else -> {
                        itemListDashaboardItems.postValue(ViewState.Message("Error occured!"))
                    }
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