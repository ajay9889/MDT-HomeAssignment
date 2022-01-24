package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.databinding.TransferFragmentBinding
import org.koin.android.ext.android.inject

class TransferFragment : BaseFragment<TransferFragmentBinding>(TransferFragmentBinding::inflate) {
    val userAccountViewModel: UserAccountViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    fun setUpView(){
        with(viewBinding){
//            logOut.setOnClickListener {
//                DsAlert.showAlertLogout(requireActivity(),
//                    userAccountViewModel,
//                    dbInstance,
//                    getString(R.string.logout_warn),
//                    requireContext().resources.getString(R.string.logout_msg),"Yes")
//            }
        }
    }
}