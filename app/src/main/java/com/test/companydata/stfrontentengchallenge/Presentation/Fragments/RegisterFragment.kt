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
import com.test.companydata.stfrontentengchallenge.databinding.RegisterBinding
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<RegisterBinding>(RegisterBinding::inflate) {
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
            signIn.setOnClickListener {
                if (findNavController().previousBackStackEntry?.destination?.id == R.id.signinFrgmnt){
                    findNavController().popBackStack()
                }else{
                    findNavController().navigate(R.id.action_registerFrgmnt_to_signinFrgmnt)
                }
            }
            buttonSignUP.setOnClickListener {
                if(validateField())
                    userAccountViewModel.createUserAccount(editTextUserName.text.toString(), editTextPassword.text.toString());
            }


            editTextPassword.setOnEditorActionListener(object : TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE &&  !editTextPassword.text.toString().isNullOrBlank()) {
                        if(validateField())
                            userAccountViewModel.createUserAccount(editTextUserName.text.toString(), editTextPassword.text.toString());
                        return true;
                    }
                    return false;
                }
            })
        }
    }

    fun validateField(): Boolean{

       with(viewBinding){
           editTextUserName.setError(null)
           editTextPassword.setError(null)
            if(editTextUserName.text.toString().isNullOrBlank())
            {
                editTextUserName.setError("Enter Username")
                editTextUserName.isFocusable =true
                return false
            }else  if(editTextPassword.text.toString().isNullOrBlank())
               {
                   editTextUserName.setError("Enter Password")
                   editTextUserName.isFocusable =true
                   return false
               }else  if(editTextPassword.text.toString().length<6)
               {
                   editTextPassword.setError("Password length at least 6 character")
                   editTextUserName.isFocusable =true
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
                    with(viewBinding){Utils.hideKeyboard(requireContext() , editTextPassword)}
                    requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                    requireActivity().finish()
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message.let { msg ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            msg,"Okay")
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