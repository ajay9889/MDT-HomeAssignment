package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.HomeViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.databinding.TransferFragmentBinding
import org.koin.android.ext.android.inject

class TransferFragment : BaseFragment<TransferFragmentBinding>(TransferFragmentBinding::inflate) {
    val mHomeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        loadData()
    }
    fun loadData(){
        mHomeViewModel.getPaggingSourceData()
    }
    fun validateField(): Boolean{
        with(viewBinding){
           /* editTextUserName.setError(null)
            editTextPassword.setError(null)
            if(editTextUserName.text.toString().isNullOrBlank())
            {
                editTextUserName.setError("Enter Username")
                editTextUserName.isFocusable =true
                return false
            }else if(editTextPassword.text.toString().isNullOrBlank())
            {
                editTextPassword.setError("Enter Password")
                editTextPassword.isFocusable =true
                return false
            }*/
        }
        return true
    }
    fun setUpView(){
        with(viewBinding){
            buttonTransfer.setOnClickListener {

            }
            selectPayees.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {

                }
            })

        }
    }
}