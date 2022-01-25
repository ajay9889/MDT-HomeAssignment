package com.test.companydata.stfrontentengchallenge.Presentation.ViewModels

sealed class ViewState<T>{
    data class Content<T>(val data:T) : ViewState<T>()
    class Loading<T>: ViewState<T>()
    class Logout<T>: ViewState<T>()
    data class Error<T>(val t: Throwable): ViewState<T>()
    class Finished<T>: ViewState<T>()
    data class Message<T>(val message: String): ViewState<T>()
}