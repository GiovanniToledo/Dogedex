package com.example.dogedex

import android.widget.ImageView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation

fun ImageView.loadUrlImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun ImageView.loadUrlImageWithBlur(url: String) {
    Glide.with(this.context)
        .load(url)
        .transform(BlurTransformation(25, 3))
        .into(this)
}