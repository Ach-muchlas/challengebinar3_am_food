package com.am.amfood.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.source.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    private val currentUserFirebaseAuth: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun fetchDataCurrentUser() {
        repository.getDataUser()?.let { user ->
            currentUserFirebaseAuth.value = user
        }
    }

    fun getDataCurrentUser(): LiveData<FirebaseUser> {
        return currentUserFirebaseAuth
    }
}