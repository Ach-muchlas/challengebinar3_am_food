package com.am.amfood.ui.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    fun signOut(firebaseAuth: FirebaseAuth, googleSignInClient: GoogleSignInClient) {
        firebaseAuth.signOut()
        googleSignInClient.signOut()
    }

    fun signIn(
        googleSignInClient: GoogleSignInClient,
        resultLauncher: ActivityResultLauncher<Intent>
    ) {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }
}