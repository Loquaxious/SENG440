package com.example.a2021exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.FieldPosition

class ItemViewModel : ViewModel() {
    private var _items = MutableLiveData<MutableList<String>>(emptyList<String>().toMutableList())


    private var _fromText = MutableLiveData<String>()
    val fromText: LiveData<String>
        get() = _fromText

    private var _toText = MutableLiveData<String>()
    val toText: LiveData<String>
        get() = _toText

    val items: LiveData<MutableList<String>>
        get() = _items

    private var _numItems = MutableLiveData<Int>(_items.value!!.size)
    val numItems: LiveData<Int>
        get() = _numItems

    fun setFromText(text: String) {
        _fromText.value = text
        _fromText.notifyObserver()
    }

    fun setToText(text: String) {
        _toText.value = text
        _toText.notifyObserver()
    }

    fun setItems(items: MutableList<String>) {
        _items.value = items
        _items.notifyObserver()
        _numItems.value = _items.value!!.size
    }

    fun addItem(item: String) {
        _items.value?.add(item)
        _items.notifyObserver()
        _numItems.value = _items.value!!.size
    }

    fun deleteItem(position: Int) {
        _items.value?.removeAt(position)
        _items.notifyObserver()
        _numItems.value = _items.value!!.size
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}