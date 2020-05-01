package com.bekarys.tech_assignment.thecats.features.common.extensions

import android.app.Activity

fun Activity.alert(
    title: String,
    message: String,
    onPositiveButtonClick: () -> Unit
) {
    showAlert(
        this,
        title,
        message,
        onPositiveButtonClick
    )
}