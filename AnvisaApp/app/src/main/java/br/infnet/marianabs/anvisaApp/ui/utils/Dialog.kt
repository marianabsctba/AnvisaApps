package br.infnet.marianabs.anvisaApp.ui.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import br.infnet.marianabs.anvisaApp.R

class Dialog(activity: Activity) {


    var activity : Activity = activity


    val builder: AlertDialog.Builder? = activity?.let {
        AlertDialog.Builder(it)
    }


    val dialog: AlertDialog? = builder?.create()


    fun startLoadingDialog(texto : String){
        var inflater : LayoutInflater = activity.layoutInflater
        dialog!!.setView(inflater.inflate(R.layout.loading_dialog,null))
        dialog.setMessage(texto)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun dismiss(){
        dialog!!.cancel()
    }
}