package com.test.companydata.Core.apputils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.test.companydata.stfrontentengchallenge.Presentation.Activity.MainActivity
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.UserAccountViewModel
import com.test.companydata.stfrontentengchallenge.R

object DsAlert {

    fun onCreateDialog(ctx: Context): ProgressDialog {
        val dialog = ProgressDialog.show(ctx, null, null)
        dialog.setContentView(R.layout.loader)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        return dialog
    }
    fun showAlert(context: Activity,
                  title: String,
                  message: String,
                  positiveButton: String
    ): AlertDialog {
        if(Build.VERSION.SDK_INT >= 26){
            return  AlertDialog.Builder(context,android.R.style.Theme_Material_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .apply { positiveButton.let{ positiveString ->
                    setPositiveButton(positiveString) { dialog, which ->
                        dialog.cancel()
                    }
                }}.show()
        }else{
            return  AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .apply { positiveButton.let{ positiveString ->
                    setPositiveButton(positiveString) { dialog, which ->
                        dialog.cancel()
                    }
                }}.show()
        }

    }

    fun showToastMessage(context: Context , message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }



    fun showAlertLogout(activity: Activity,
                        userAccountViewModel: UserAccountViewModel,
                        title: String,
                        message: String,
                        positiveButton: String): AlertDialog {
        return  AlertDialog.Builder(activity,android.R.style.Theme_Material_Dialog)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .apply { positiveButton.let { positiveString ->
                setPositiveButton(positiveString) { dialog, which ->
                    dialog.cancel()
                    userAccountViewModel.onLogOut();
                }
                setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
            }
            }.show()
    }
}