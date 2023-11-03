package com.am.amfood.data.source.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.am.amfood.R
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.ui.profile.ProfileFragment
import com.am.amfood.utils.Utils.formatNameFromEmail
import com.am.amfood.utils.Utils.intentActivityUseFinish
import com.am.amfood.utils.Utils.toastMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

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
                    "password" to user?.uid,
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
                toastMessage(context, "Login Failed : ${task.exception?.message}")
                Log.e(AuthActivity.TAG, "Login Failed : ${task.exception?.message}")
            }
        }
    }

    /*function is used to fetch data user in profile fragment */
    /*function retrieves user data from firebase realtime database*/
    fun getDataUserWithDatabase(callback: (DataUser?) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid

        uid?.let {
            firebaseDatabaseReference.child(ProfileFragment.USERS).child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(DataUser::class.java)
                        user.let {
                            val dataUser = DataUser(
                                username = it?.username
                                    ?: formatNameFromEmail(it?.email.toString()),
                                email = it?.email,
                                passwordUid = uid,
                                phone = it?.phone,
                                imageUrl = it?.imageUrl
                            )
                            callback(dataUser)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(null)
                    }
                })
        }
    }

    fun sendEmailVerification(onComplete: (Boolean) -> Unit) {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun isEmailVerified(): Boolean {
        val user = firebaseAuth.currentUser
        return user?.isEmailVerified ?: false
    }
}