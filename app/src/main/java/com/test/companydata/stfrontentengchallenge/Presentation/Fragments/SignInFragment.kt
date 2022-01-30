package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.test.companydata.Core.apputils.DsAlert
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.Presentation.Activity.HomeActivity
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.SigninBinding
import org.koin.android.ext.android.inject

class SignInFragment : BaseFragment<SigninBinding>(SigninBinding::inflate) {
    val userAccountViewModel: UserAccountViewModel by inject()
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observData()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.cancel()
    }
    fun setUp(){
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.cancel()

        with(viewBinding){

            buttonSignIN.setOnClickListener {
                if(validateField())
                    userAccountViewModel.getUserSignIn(editTextUserName.text.toString(), editTextPassword.text.toString());
            }

            editTextPassword.setOnEditorActionListener(object :TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE &&  !editTextPassword.text.toString().isNullOrBlank()) {
                        if(validateField())
                            userAccountViewModel.getUserSignIn(editTextUserName.text.toString(),
                                editTextPassword.text.toString());
                        return true;
                    }
                    return false;
                }
            })
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_signin_to_register)
            }


        }
    }
    fun validateField(): Boolean{
        with(viewBinding){
            editTextUserName.setError(null)
            editTextPassword.setError(null)
            if(editTextUserName.text.toString().isNullOrBlank())
            {
                editTextUserName.setError("Username")
                editTextUserName.isFocusable =true
                return false
            }else if(editTextPassword.text.toString().isNullOrBlank())
            {
                editTextPassword.setError("Enter Password")
                editTextPassword.isFocusable =true
                return false
            }
        }
        return true
    }

    fun observData(){
        userAccountViewModel.registeredUserInfo.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Loading -> {
                    dialog?.show()
                }
                is ViewState.Content -> {
                    dialog?.cancel()
                    with(viewBinding){ Utils.hideKeyboard(requireContext() , editTextPassword)}
                    requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                    requireActivity().finish()
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message.let { it1 ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            it1,"Okay")
                    }
                }
                is ViewState.Error -> {
                    dialog?.cancel()
                    it.t.message?.let { it1 ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            it1,"Okay")
                    }
                }
            }
        })
    }
}