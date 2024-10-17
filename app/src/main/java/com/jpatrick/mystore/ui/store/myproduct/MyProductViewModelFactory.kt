package com.jpatrick.mystore.ui.store.myproduct

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyProductViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProductViewModel::class.java)) {
            return MyProductViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

