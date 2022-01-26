package com.test.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.test.companydata.Core.apputils.DsAlert
import com.test.companydata.Core.base.BaseFragment
import com.test.companydata.Core.networkutils.NetworkConnectivity
import com.test.companydata.stfrontentengchallenge.Core.Util.AmountDigitsInputFilter
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.DataSource.module.PayeesData
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.HomeViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.TransferFragmentBinding
import org.koin.android.ext.android.inject


class TransferFragment : BaseFragment<TransferFragmentBinding>(TransferFragmentBinding::inflate) {
    val mHomeViewModel: HomeViewModel by inject()
    var dialog: ProgressDialog? = null
    var payeesData: PayeesData?=null;
    var payeesName = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        loadData()
        observeData()
    }
    fun loadData(){
        mHomeViewModel.getPayee()
    }
    fun observeData(){
        mHomeViewModel.balanceData.observe(viewLifecycleOwner,{
            when(it) {
                is ViewState.Content -> {
                    dialog?.cancel()
                    if(it.data.status.equals("success"))
                    {
                        with(viewBinding){
                            selectPayees.setText("")
                            editTextAmount.setText("")
                            editTextDesc.setText("")
                        }
                        DsAlert.showAlert(requireActivity(),
                            requireContext().resources.getString(R.string.transfer_success),
                            requireContext().resources.getString(R.string.transfer_msg),"Done")
                    }
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message.let { message ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            message,"Okay")
                    }
                }
            }
        })

        mHomeViewModel.payeList.observe(viewLifecycleOwner,{
            when(it){
                is ViewState.Content->{
                    payeesData = it.data
                    setPayeesListAdapter()
                }
                is ViewState.Message->{
                    DsAlert.showAlert(requireActivity(),
                        requireContext().resources.getString(R.string.warning),
                        it.message,"Done")
                }
            }
        })
    }

    fun setPayeesListAdapter(){
        payeesData?.data?.map {
            payeesName.add(it.name!!)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, payeesName.toTypedArray()
        )
        with(viewBinding){
            selectPayees.setAdapter(adapter)
            selectPayees.setOnItemClickListener(object : AdapterView.OnItemClickListener{
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {}
            })
        }
    }

   val mTextWatcher = object: TextWatcher{
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            with(viewBinding){
                if(!editTextAmount.text.toString().isNullOrBlank() && !editTextAmount.text.toString().contains("$")){
                    editTextAmount.setText(Utils.getNumberFormated(editTextAmount.text.toString().toDouble()))
                }
            }
        }
    }

    fun validateField(): Boolean{
        with(viewBinding){
            selectPayees.setError(null)
            editTextAmount.setError(null)
            editTextDesc.setError(null)
            if(selectPayees.text.toString().isNullOrBlank())
            {
                selectPayees.setError("Enter Payee Name")
                selectPayees.isFocusable =true
                return false
            }else if(editTextAmount.text.toString().isNullOrBlank())
            {
                editTextAmount.setError("Set Amount")
                editTextAmount.isFocusable =true
                return false
            }else if(editTextDesc.text.toString().isNullOrBlank())
            {
                editTextDesc.setError("Comment/Description")
                editTextDesc.isFocusable =true
                return false
            }
        }
        return true
    }
    fun setUpView(){
        with(viewBinding){
            dialog = DsAlert.onCreateDialog(requireContext())
            dialog?.cancel()
            editTextDesc.addTextChangedListener(mTextWatcher)
            selectPayees.addTextChangedListener(mTextWatcher)
            editTextAmount.setFilters(arrayOf<InputFilter>(AmountDigitsInputFilter()))
            buttonTransfer.setOnClickListener {
                if(validateField()){
                    if(NetworkConnectivity.isNetworkConnected(requireContext())){
                        val selectedItem=payeesData?.data?.filter {it.name.equals(selectPayees.text.toString().trim())}
                        if (selectedItem != null) {
                            if(selectedItem.size>0){
                                dialog?.show()
                                selectedItem.get(0).accountNo?.let { it1 ->
                                    mHomeViewModel.getAmountTransferToPayee(
                                        it1,editTextAmount.text.toString(),editTextDesc.text.toString())
                                }
                            }else{
                                DsAlert.showAlert(requireActivity(),
                                    requireContext().resources.getString(R.string.warning),
                                    requireContext().resources.getString(R.string.payeename),"Okay")
                            }
                        }
                    }else{
                        DsAlert.showAlert(requireActivity(),
                            requireContext().resources.getString(R.string.net_error_warning),
                            requireContext().resources.getString(R.string.net_error),"Okay")
                    }
                }
            }
        }
    }
}