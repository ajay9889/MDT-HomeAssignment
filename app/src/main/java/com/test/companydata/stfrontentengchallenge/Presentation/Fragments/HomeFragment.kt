package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.test.companydata.Core.apputils.DsAlert
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.DashboardListAdapter
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.HomeViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.HomeFragmentBinding
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    val homeViewModel: HomeViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUPView();
        observeLiveData()
        initRecyclerView()
    }
    fun setUPView(){
        val adapterL= DashboardListAdapter()
        with(viewBinding.recyclerviewDashboardHome) {
            adapter = adapterL
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    fun observeLiveData(){
        homeViewModel.itemListDashaboardItems.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Message->{
                    DsAlert.showAlert(requireActivity(), getString(R.string.net_error_warning), it.message,"Okay")
                }else -> {
                }
            }
        })
       }

    fun initRecyclerView(){
        with(viewBinding){
            recyclerviewDashboardHome.invalidate()
            recyclerviewDashboardHome.invalidateItemDecorations()
            homeViewModel.getPaggingSourceData().subscribe(
                { pagingdata ->
                    (recyclerviewDashboardHome.adapter as DashboardListAdapter).submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
        }
    }

}