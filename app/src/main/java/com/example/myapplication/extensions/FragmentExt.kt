package com.example.myapplication.extensions

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myapplication.R

fun Fragment.showErrorAlertDialog(message: String) {
    AlertDialog.Builder(context)
        .setTitle(R.string.title_error)
        .setMessage(message)
        .setPositiveButton(R.string.action_close, null)
        .show()
}