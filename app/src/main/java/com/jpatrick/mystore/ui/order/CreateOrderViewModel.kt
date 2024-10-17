package com.jpatrick.mystore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpatrick.mystore.data.model.dto.CartDto


class CreateOrderViewModel : ViewModel() {
    private val _data = MutableLiveData<List<CartDto>>()
    val data: LiveData<List<CartDto>> get() = _data

    fun setItem( list: List<CartDto>) {
        _data.value = list
    }
}