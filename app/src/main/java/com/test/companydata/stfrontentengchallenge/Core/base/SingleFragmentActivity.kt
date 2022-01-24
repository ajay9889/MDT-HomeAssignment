package com.test.companydata.stfrontentengchallenge.Core.base
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.test.companydata.Core.base.BaseAppActivity
import com.test.companydata.stfrontentengchallenge.Presentation.Fragments.HomeFragment
import com.test.companydata.stfrontentengchallenge.R
import com.test.companydata.stfrontentengchallenge.databinding.ActivitySingleFragmentBinding

class SingleFragmentActivity:  BaseAppActivity<ActivitySingleFragmentBinding>(ActivitySingleFragmentBinding::inflate){
    companion object{
        private const val EX_FRAGMENT_NAME = "ex_fragment_name"
        private const val EX_BUNDLE = "ex_bundle"
        fun launchFragment(mContext: Context , fragmentBundle: Pair<String, Bundle>){
            (Class.forName(fragmentBundle.first).newInstance() as? Fragment) ?.let {
                val mIntent = Intent(mContext, SingleFragmentActivity::class.java);
                mContext.startActivity(
                    mIntent.putExtra(EX_FRAGMENT_NAME, fragmentBundle.first)
                        .putExtra(EX_BUNDLE, fragmentBundle.second)
                )
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val mFragment= Class.forName(intent.getStringExtra(EX_FRAGMENT_NAME)).newInstance()
                if(mFragment is DetailsFragment)
                    add<DetailsFragment>(R.id.fragment_container_view,
                        args = intent.getBundleExtra(EX_BUNDLE))
                if(mFragment is HomeFragment)
                    add<HomeFragment>(R.id.fragment_container_view,
                        args = intent.getBundleExtra(EX_BUNDLE))
            }
            setupToolbar(viewBinding.toolbar,null,"")
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}