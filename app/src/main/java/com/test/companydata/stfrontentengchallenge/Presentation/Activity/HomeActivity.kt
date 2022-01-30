package com.test.companydata.stfrontentengchallenge.Presentation.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.test.companydata.Core.apputils.DsAlert
import com.test.companydata.Core.base.BaseAppActivity
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils.ACTION_LOGOUT
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.ActivityDashboardBinding
import org.koin.android.ext.android.inject

/*
* After login user will Navigate to the HomeActivity to load the bottom menu for dashboard and bank transfer sections.
*/
class HomeActivity : BaseAppActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate)
{

    val userAccountViewModel: UserAccountViewModel by inject()
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBinding.toolbar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigatin_view)
        val navController = findNavController(R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*
        * Setting here bottom navigation menu using navcontroller.
        */
        bottomNavigationView.setupWithNavController(navController)

       /*
       * To Get user logout when the user is logged and token is expired
       */
        registerReceiver(bReciver, IntentFilter(ACTION_LOGOUT).apply {addAction(ACTION_LOGOUT)})
        observData()
    }

    var bReciver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.action?.let{
                if(it.equals(ACTION_LOGOUT)){
                    Toast.makeText(applicationContext,"Session expired, please login again!",Toast.LENGTH_SHORT).show()
                    userAccountViewModel.onLogOut()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bReciver)
    }

    fun observData(){
        userAccountViewModel.registeredUserInfo.observe(this,{
            when(it){
                is ViewState.Logout->{
                    Log.d("action_2" ,"$it")
                    startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }
        })
    }

    /*
    * Logout menu added from where user can easily logout.
    */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logoutmenu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout ->{
                DsAlert.showAlertLogout(this@HomeActivity,
                    userAccountViewModel,
                    getString(R.string.logout_warn),
                    this@HomeActivity.resources.getString(R.string.logout_msg),"Yes")
            }
            else ->{
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}