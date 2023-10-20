package com.am.amfood.ui.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.ui.profile.ProfileFragment
import com.am.amfood.utils.Utils.firebaseAuth
import com.am.amfood.utils.Utils.intentActivityUseFinish
import com.am.amfood.utils.Utils.toastMessage
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

    fun register(
        auth: FirebaseAuth,
        email: String,
        password: String,
        phone: String,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                /*get Data user in database*/
                val user = auth.currentUser
                val userData = DataUser(email, phone)

                /*save object data to firebase*/
                val database = Firebase.database
                val ref = database.reference.child(ProfileFragment.USERS)

                /*store user data with uid*/
                ref.child(user!!.uid).setValue(userData)

                intentActivityUseFinish(context, MainActivity::class.java)
                toastMessage(context, "Registration Successful ")
            } else {
                toastMessage(context, "Registration Failed :  ${task.exception?.message}")
            }
        }
    }

    fun login(auth: FirebaseAuth, email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                intentActivityUseFinish(context, MainActivity::class.java)
                toastMessage(context, "Login Successful")
            } else {
                toastMessage(context, "Login Failed : ${task.exception}")
            }
        }
    }

    fun updateData(dataPath : String, newData : Map<String, Any>, callback : (Boolean) -> Unit){
        val database = Firebase.database
        val reference = database.reference.child(dataPath)

        reference.updateChildren(newData).addOnCompleteListener { task ->
            if (task.isSuccessful){
                callback(true)
            }else{
                callback(false)
            }
        }
    }


}