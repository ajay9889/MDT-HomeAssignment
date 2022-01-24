package com.test.companydata.stfrontentengchallenge.Presentation.ViewModels

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.MainApplication

class UserAccountViewModel (val application: MainApplication): AndroidViewModel(application){
    private var TAG= "GCP Auth"
    public val registeredUserInfo =MutableLiveData<ViewState<UserInfoRemoteData>>()
//    public val registeredUserInfo =MutableLiveData<ViewState<UserInfoRemoteData>>()
    init {

    }
    fun onSignOut(){

    }
    fun onSignup(activity: Activity, userName: String, password: String){
        registeredUserInfo.postValue(ViewState.Loading())

    }
}