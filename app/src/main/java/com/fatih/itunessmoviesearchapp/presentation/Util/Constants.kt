package com.fatih.itunessmoviesearchapp.presentation.Util

import android.graphics.Color
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

object Constants {
    const val BASE_URL = "https://itunes.apple.com/"
}
fun ImageView.downloadFromUrl(url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        start()
    }
    Glide.with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .into(this)
}