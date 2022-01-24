package com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.data.usage.Database.Databasehelper
import com.test.companydata.stfrontentengchallenge.DataSource.module.BestMatche.Companion.getToCompanyDomain
import com.test.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toCompanyItemDomain
import com.test.companydata.stfrontentengchallenge.DataSource.repository.AccountDataReposityImpl
import com.test.companydata.stfrontentengchallenge.MainApplication
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SearchViewModel(val application: MainApplication,
                      val accntRepository : AccountDataReposityImpl,
                      ): AndroidViewModel(application){
    val dbInstance : Databasehelper by inject(Databasehelper::class.java)
    val getDefaultCompanyList = MutableLiveData<List<CompanyDataItemDomain>>()

    fun getSearchCompanyName(keyword: String?){
        viewModelScope.launch(Dispatchers.IO) {
            if (keyword.isNullOrBlank()) {
                val list=accntRepository.getCompanyList().map {
                    it.toCompanyItemDomain(
                        BehaviorSubject.create(),
                        dbInstance
                    )
                }
                Log.d("com: list" , "$list")
                getDefaultCompanyList.postValue(list)
            } else {
                val listItems = arrayListOf<CompanyDataItemDomain>()
                accntRepository.getSearchEndpoint(keyword)?.let {
                    it.data?.bestMatches?.map {
                        it?.getToCompanyDomain()?.let {it1->
                          listItems.add(it1.toCompanyItemDomain(
                              BehaviorSubject.create(),
                              dbInstance
                          ))
                        }
                    }
                }
                getDefaultCompanyList.postValue(listItems.toList()) ;
            }
        }
    }
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getPaggingSourceData(mCList: List<CompanyDataItemDomain>) = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            CompanyListPagingSource(mCList)
        }
    ).flowable.cachedIn(viewModelScope)
    class CompanyListPagingSource(val mCList: List<CompanyDataItemDomain>): PagingSource<Int, CompanyDataItemDomain>() {

        override fun getRefreshKey(state: PagingState<Int, CompanyDataItemDomain>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyDataItemDomain> {
            return LoadResult.Page(
                data = mCList ,
                prevKey = null,
                nextKey = null
            )
        }
    }
}