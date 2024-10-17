package com.jpatrick.mystore.ui.order

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.source.remote.OrderRemote
import kotlinx.coroutines.launch

class OrderViewModel(context: Context) : ViewModel() {
    private val orderRemote = OrderRemote()

    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> get() = _data

    fun fetchData(refresh: Boolean = false) {
        if (!refresh && _data.value != null) {
            return
        }

        // Gọi API để lấy dữ liệu mới
        viewModelScope.launch {
            try {
                val result = orderRemote.getAllOrderById()
                _data.value = result
            } catch (e: Exception) {
            }
        }
    }
    fun getConfirm(): List<Product> {
        return data.value?.filter { it.quantity.toInt() > 0 } ?: emptyList()
    }

    fun getPickup(): List<Product> {
        return data.value?.filter { it.quantity.toInt() == 0 } ?: emptyList()
    }
    fun getFeedBack(): List<Product> {
        return data.value?.filter { it.quantity.toInt() > 0 } ?: emptyList()
    }

    fun getNotFeedback(): List<Product> {
        return data.value?.filter { it.quantity.toInt() == 0 } ?: emptyList()
    }
}