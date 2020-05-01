package com.bekarys.tech_assignment.thecats.features.common.extensions

import android.R
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


fun Fragment.toast(message: String) {
    context.let {
        Toast.makeText(it, message, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.alert(
    title: String,
    message: String,
    onPositiveButtonClick: () -> Unit
) {
    context?.let {
        showAlert(
            it,
            title,
            message,
            onPositiveButtonClick
        )
    }
}

fun showAlert(
    context: Context,
    title: String,
    message: String,
    onPositiveButtonClick: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            R.string.yes
        ) { dialog, which ->
            onPositiveButtonClick()
        }
        .setNegativeButton(R.string.no, null)
        .setIcon(R.drawable.ic_dialog_alert)
        .show()
}