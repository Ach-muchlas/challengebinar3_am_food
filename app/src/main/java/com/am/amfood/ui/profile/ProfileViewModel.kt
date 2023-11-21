package com.am.amfood.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.source.repository.AuthRepository
import com.am.amfood.utils.Utils.toastMessage
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _userData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userData: LiveData<FirebaseUser?>
        get() = _userData
    fun fetchDataUserWithDatabase() {
        _userData.postValue(repository.getDataCurrentUser())
    }

}