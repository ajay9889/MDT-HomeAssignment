package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.test.companydata.Core.apputils.DsAlert
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Core.base.SingleFragmentActivity
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.Presentation.Adapter.DashboardListAdapter
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.HomeViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.HomeFragmentBinding
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    var dialog: ProgressDialog? = null
    val homeViewModel: HomeViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUPView();
        observeLiveData()
        initRecyclerView()
    }
    fun setUPView(){
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.show()
        val adapterL= DashboardListAdapter(requireContext(),    this::listItemClicked)
        with(viewBinding.recyclerviewDashboardHome) {
            adapter = adapterL
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
    fun listItemClicked(companyListData: DashaboardItems){
//        if(NetworkConnectivity.isNetworkConnected(requireContext())){
//            SingleFragmentActivity.launchFragment(requireContext() ,DetailsFragment.getFragmentInstance(companyListData))
//        }else{
//            DsAlert.showAlert(requireActivity(), getString(R.string.net_error_warning), requireContext().resources.getString(R.string.net_error),"Okay")
//        }
    }
    fun observeLiveData(){
        homeViewModel.itemListDashaboardItems.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Loading->{
                    dialog?.show()
                }
                is ViewState.Message->{
                    dialog?.cancel()
                    DsAlert.showAlert(requireActivity(), getString(R.string.net_error_warning), it.message,"Okay")
                }else -> {
                    dialog?.cancel()
                }
            }
        })
       }

    @SuppressLint("CheckResult")
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