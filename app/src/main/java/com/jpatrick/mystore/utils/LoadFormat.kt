package com.jpatrick.mystore.utils

import android.widget.ImageView
import android.widget.TextView
import com.jpatrick.mystore.R
import com.squareup.picasso.Picasso
import java.util.Currency
import java.util.Locale

class LoadFormat {
    fun loadImage(imgUrl: String?, img: ImageView) {
        if (imgUrl.isNullOrEmpty()) {
            Picasso.get().load(R.drawable.logo_larger).into(img)

        } else {
            Picasso.get().load(imgUrl).into(img)
        }
    }

    fun loadCurrency(price: Number, textView: TextView) {
        val locale = Locale.getDefault()  // Lấy locale hiện tại của thiết bị
        val currency = Currency.getInstance(locale)  // Lấy currency từ locale
        textView.text = "${currency.symbol}$price"
    }
}