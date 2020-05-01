package com.bekarys.tech_assignment.thecats.features.common.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(imagePath: String) {
    Glide.with(this.context).load(imagePath).into(this)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.toggleVisibility(visible: Boolean) {
    if(visible) visible() else gone()
}