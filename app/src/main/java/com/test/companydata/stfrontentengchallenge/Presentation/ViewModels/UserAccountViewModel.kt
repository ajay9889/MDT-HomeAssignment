package com.test.companydata.stfrontentengchallenge.Presentation.ViewModels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Domain.repository.UserRepository
import com.test.companydata.stfrontentengchallenge.MainApplication
import kotlinx.coroutines.launch

class UserAccountViewModel (
       application: MainApplication,
       val mUserRepository: UserRepository
    ): AndroidViewModel(application){
    val registeredUserInfo = MutableLiveData<ViewState<UserInfoRemoteData>>()
    fun isUserLoggedIn()= viewModelScope.launch{
        mUserRepository.getUserLoggedInInformations().let {
            registeredUserInfo.value = it
        }
    }
    fun onLogOut()= viewModelScope.launch{
        mUserRepository.getUserLogout()
        registeredUserInfo.postValue(ViewState.Logout())
    }
    fun createUserAccount(userName: String, password: String) = viewModelScope.launch{
        registeredUserInfo.postValue(ViewState.Loading())
        mUserRepository.createNewAccount(userName ,password).let {
            registeredUserInfo.value = it
        }
    }
    fun getUserSignIn(userName: String, password: String) =viewModelScope.launch{
        registeredUserInfo.postValue(ViewState.Loading())
        mUserRepository.getUserLogin(userName ,password).let {
            registeredUserInfo.value = it
        }
    }
}