package com.am.amfood.ui.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.am.amfood.ui.auth.login.LoginFragment
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.utils.Utils.toastMessage
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

    fun register(auth: FirebaseAuth, email: String, password: String, context: Context) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                context.startActivity(Intent(context, LoginFragment::class.java))
                toastMessage(context, "Registration Successful ")
            } else {
                toastMessage(context, "Registration Failed :  ${task.exception}")
            }
        }
    }

    fun login(auth: FirebaseAuth, email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                context.startActivity(Intent(context, MainActivity::class.java))
                toastMessage(context, "Login Successful")
            } else {
                toastMessage(context, "Login Failed : ${task.exception}")
            }
        }
    }
}