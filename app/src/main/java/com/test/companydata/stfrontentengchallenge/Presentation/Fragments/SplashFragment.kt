package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Presentation.Activity.HomeActivity
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.SplashfragmentBinding
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<SplashfragmentBinding>(SplashfragmentBinding::inflate) {
    val userAccountViewModel: UserAccountViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obserUserData()
        userAccountViewModel.isUserLoggedIn()
    }
     fun obserUserData() {
         userAccountViewModel.registeredUserInfo.observe(viewLifecycleOwner,{
             when(it){
                 is ViewState.Content->{
                     Log.d("TAG_2" , "${it.data}")
                     it.data.token.isNullOrBlank().let {
                         if(it){
                             findNavController().navigate(R.id.action_splashFragment_to_signinFrgmnt)
                         }else{
                             requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                             requireActivity().finish()
                         }
                     }
                 }else->{
                  Log.d("TAG_2" , "${it}")
                    findNavController().navigate(R.id.action_splashFragment_to_signinFrgmnt)
                }
             }
         })
    }
}