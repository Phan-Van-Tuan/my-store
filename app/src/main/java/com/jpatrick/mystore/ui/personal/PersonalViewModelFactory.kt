package com.jpatrick.mystore.ui.personal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PersonalViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalViewModel::class.java)) {
            return PersonalViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}