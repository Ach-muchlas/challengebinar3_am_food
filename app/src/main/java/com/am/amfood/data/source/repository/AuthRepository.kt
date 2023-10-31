package com.am.amfood.data.source.repository

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.am.amfood.R
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.ui.profile.ProfileFragment
import com.am.amfood.utils.Utils.intentActivityUseFinish
import com.am.amfood.utils.Utils.toastMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class AuthRepository(
    val firebaseAuth: FirebaseAuth,
    private val firebaseDatabaseReference: DatabaseReference,
    val context: Context
) {

    /*function for initializing google singIn client*/
    private fun provideSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    /*function for signOut user*/
    fun signOut() {
        firebaseAuth.signOut()
        provideSignInClient().signOut()
    }

    /*function for signIn user*/
    fun signIn(
        resultLauncher: ActivityResultLauncher<Intent>
    ) {
        val signInIntent = provideSignInClient().signInIntent
        resultLauncher.launch(signInIntent)
    }

    /*function to register users using email, password, and phone in layout register */
    /*and the user data received will later be stored in firebase auth and firebase realtime database*/
    fun registerUser(
        email: String,
        password: String,
        phone: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        /*Register with email and password*/
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {/*call firebase user */
                val user = firebaseAuth.currentUser
                val userData = hashMapOf(
                    "email" to email,
                    "phone" to phone
                )
                user?.uid?.let { uid ->
                    firebaseDatabaseReference.child(ProfileFragment.USERS).child(uid)
                        .setValue(userData)
                        .addOnSuccessListener {
                            onComplete(true, "Successfully saved user data register")
                        }
                        .addOnFailureListener { e ->
                            onComplete(false, "Failed To save user data register : ${e.message}")
                        }
                }
            } else {
                onComplete(false, "Failed to register : ${task.exception?.message} ")
            }
        }
    }

    /*function to login users using email, password*/
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                intentActivityUseFinish(context, MainActivity::class.java)
                toastMessage(context, "Login Successful")
            } else {
                toastMessage(context, "Login Failed : ${task.exception}")
            }
        }
    }


    /*function for get data users in firebase auth */
    fun getDataUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}