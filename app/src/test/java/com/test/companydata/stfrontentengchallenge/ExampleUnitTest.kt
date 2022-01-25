package com.test.companydata.stfrontentengchallenge

import android.content.Context
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils
import com.test.companydata.stfrontentengchallenge.Domain.repository.UserRepository
import com.test.companydata.stfrontentengchallenge.KoinDepInject.appModule
import com.test.companydata.stfrontentengchallenge.KoinDepInject.viewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(mockContext)
            modules(listOf(
                appModule, viewModelModule
            ))
        }
    }

    @Test
    fun checkUserAccount() {
        CoroutineScope(Dispatchers.IO).launch {
            val mUserRepository by KoinJavaComponent.inject(UserRepository::class.java)
            assertNotNull(mUserRepository.createNewAccount("xyz", "123456"))
            assertNotNull(mUserRepository.getUserLogin("xyz", "123456"))
            assertNotNull(mUserRepository.getUserLogout())
        }
    }

    @Test
    fun checkUtils() {
        assertNotNull(Utils.getCurrentDate())
        assertNotNull(Utils.getFormatedDate("2022-01-25T08:23:33.965Z"))
        assertNotNull(Utils.getNumberFormated(10.0))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}