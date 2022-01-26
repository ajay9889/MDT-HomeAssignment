package com.test.companydata.stfrontentengchallenge.KoinDepInject

import com.test.companydata.Core.networkutils.CacheInterceptor
import com.test.companydata.Core.networkutils.OnlineCacheInterceptor
import com.test.companydata.stfrontentengchallenge.BuildConfig
import com.test.companydata.stfrontentengchallenge.Core.networkutils.HeaderIntercepter
import com.test.companydata.stfrontentengchallenge.Core.networkutils.ResponseIntercepter
import com.test.companydata.stfrontentengchallenge.DataSource.repository.AccountDataRepositoryImpl
import com.test.companydata.stfrontentengchallenge.DataSource.repository.LocalRepositoryImpl
import com.test.companydata.stfrontentengchallenge.DataSource.repository.UserRepositoryImpl
import com.test.companydata.stfrontentengchallenge.MainApplication
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.HomeViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        LocalRepositoryImpl(androidContext())
    }
    single {
        UserRepositoryImpl(androidContext() , get())
    }
    single {
        AccountDataRepositoryImpl(androidContext() , get())
    }
    single{
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .addInterceptor(CacheInterceptor(androidContext()))
            .addNetworkInterceptor(OnlineCacheInterceptor())
            .addInterceptor(ResponseIntercepter(androidContext()))
            .addInterceptor(HeaderIntercepter(androidContext()))
            .cache(Cache(File(androidContext().cacheDir, "api"), 1024 * 1024 * 1024L)).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_DOMAIN)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}
val viewModelModule = module {
    viewModel {
        UserAccountViewModel(androidApplication() as MainApplication,  UserRepositoryImpl(androidContext() , get()))
    }
    viewModel {
        HomeViewModel(androidApplication() as MainApplication, AccountDataRepositoryImpl(androidContext() , get()))
    }
}
