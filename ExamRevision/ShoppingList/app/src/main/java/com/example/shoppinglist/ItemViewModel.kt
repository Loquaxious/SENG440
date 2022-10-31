package com.example.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private var _items = MutableLiveData<MutableList<ShoppingItem>>(emptyList<ShoppingItem>().toMutableList())
    val items: LiveData<MutableList<ShoppingItem>>
        get() = _items

    private var _numItems = MutableLiveData<Int>(_items.value?.size)
    val numItems : LiveData<Int>
        get() = _numItems

    fun addItem(item: ShoppingItem) {
        _items.value?.add(item)
        _items.notifyObserver()
        _numItems.value = _items.value?.size
    }

    fun deleteItem(position: Int) {
        _items.value?.removeAt(position)
        _items.notifyObserver()
        _numItems.value = _items.value?.size
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}