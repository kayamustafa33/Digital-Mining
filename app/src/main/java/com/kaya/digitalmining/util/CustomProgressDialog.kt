package com.kaya.digitalmining.util

import android.app.Dialog
import android.content.Context

class CustomProgressDialog(context: Context) : Dialog(context) {

    private var progressDialog =  Dialog(context)

    override fun show() {
        //progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.show()
    }

    override fun dismiss() {
        progressDialog.dismiss()
    }

}