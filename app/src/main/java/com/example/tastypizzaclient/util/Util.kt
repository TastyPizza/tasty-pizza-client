package com.example.tastypizzaclient.util

import android.app.AlertDialog
import android.content.Context

class Util {
    companion object {
        fun showErrorDialog(context: Context, errorMessage: String) {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Ошибка")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }

        fun showUpdateDialog(context: Context, message: String) {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Еще не доступно")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }
    }
}