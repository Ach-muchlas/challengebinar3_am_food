package com.am.amfood.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.data.source.repository.AuthRepository
import com.am.amfood.utils.Utils.toastMessage

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _userData: MutableLiveData<DataUser> = MutableLiveData()
    val userData: LiveData<DataUser>
        get() = _userData

    private val _isEmailVerified = MutableLiveData<Boolean>()
    val isEmailVerified : LiveData<Boolean>
        get() = _isEmailVerified

    fun sendEmailVerification(context: Context) {
        repository.sendEmailVerification { succes ->
            if (succes) {
                toastMessage(context, "Terkirim")
                Log.e("SIMPLEVERIF", "verification : ")
            } else {
                toastMessage(context, "GAGAL")
                Log.e("SIMPLEVERIF", "Not verification : ")
            }
        }
    }

    fun checkEmailVerificationStatus(){
        _isEmailVerified.value = repository.isEmailVerified()
    }

    fun fetchDataUserWithDatabase() = repository.getDataUserWithDatabase { dataUser ->
        _userData.postValue(dataUser)
    }

}