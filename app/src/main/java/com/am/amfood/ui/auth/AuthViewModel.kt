package com.am.amfood.ui.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.data.source.repository.AuthRepository
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.ui.profile.ProfileFragment
import com.am.amfood.utils.Utils.formatNameFromEmail
import com.am.amfood.utils.Utils.intentActivityUseFinish
import com.am.amfood.utils.Utils.toastMessage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    fun signInUser(resultLauncher: ActivityResultLauncher<Intent>) =
        repository.signIn(resultLauncher)

    fun loginUserWithEmailPassword(email: String, password: String) =
        repository.login(email, password)

    fun registerUserWithEmailPassword(email: String, password: String, phone: String) =
        repository.registerUser(email, password, phone) { success, message ->
            if (success) {
                intentActivityUseFinish(repository.context, MainActivity::class.java)
            } else {
                toastMessage(repository.context, message.toString())
            }
        }

    fun signOutUser() = repository.signOut()

    fun firebaseAuthWithGoogle(
        idToken: String,
        context: Context,
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        repository.firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AuthActivity.TAG, "SignInWithCredentialSuccess")
                    val user = repository.firebaseAuth.currentUser
                    user?.uid?.let { uid ->
                        val userData = DataUser(
                            username = user.displayName
                                ?: formatNameFromEmail(user.email.toString()),
                            email = user.email,
                            phone = user.phoneNumber,
                            passwordUid = uid,
                            imageUrl = user.photoUrl.toString()
                        )
                        /*save object data to firebase*/
                        val database = Firebase.database
                        val ref = database.reference.child(ProfileFragment.USERS).child(uid)
                        ref.setValue(userData).addOnSuccessListener {
                            Log.d(AuthActivity.TAG, "Data User Successfully stored")
                            updateUI(user, context)
                        }.addOnFailureListener { e ->
                            Log.w(AuthActivity.TAG, "Error storing user data : $e")
                        }
                    }
                } else {
                    Log.w(AuthActivity.TAG, "signInWithCredentialFailure ", task.exception)
                    updateUI(null, context)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?, context: Context) {
        if (currentUser != null) {
            intentActivityUseFinish(context, MainActivity::class.java)
        }
    }

}