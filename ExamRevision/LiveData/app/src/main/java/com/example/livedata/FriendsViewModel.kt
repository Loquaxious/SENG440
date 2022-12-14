package com.example.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.FieldPosition

class FriendsViewModel: ViewModel() {
    private var _friends = MutableLiveData<MutableList<Friend>>(arrayListOf(
        Friend(
            "Ben Adams",
            "U8U061XEY",
            "Christchurch, New Zealand",
            "benjamin.adams@canterbury.ac.nz",
            "123456789"
        ),
        Friend(
            "Homer Simpson",
            "U80000000",
            "Springfield, USA",
            "homer.simpson@example.org",
            "987654321"
        ),
        Friend(
            "Marge Simpson",
            "U70000000",
            "Springfield, USA",
            "marge.simpson@example.org",
            "987654320"
        )
    ))

    val friends: LiveData<MutableList<Friend>>
        get() = _friends

    private var _numFriends = MutableLiveData<Int>(friends.value!!.size)
    val numFriends: LiveData<Int>
        get() = _numFriends

    fun addFriend(friend: Friend) {
        _friends.value?.add(friend)
        _friends.notifyObserver()
        _numFriends.value = _friends.value!!.size
    }

    fun deleteFriend(friend: Friend) {
        _friends.value?.remove(friend)
        _friends.notifyObserver()
        _numFriends.value = _friends.value!!.size
    }

    fun deleteFriend(position: Int) {
        _friends.value?.removeAt(position)
        _friends.notifyObserver()
        _numFriends.value = _friends.value!!.size
    }

    fun editFriend(position: Int,  friend: Friend) {
        _friends.value?.set(position, friend)
        _friends.notifyObserver()
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}