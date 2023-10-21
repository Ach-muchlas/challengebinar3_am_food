package com.am.amfood.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.am.amfood.R
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.databinding.FragmentLoginBinding
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.ui.profile.ProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpLoginOrSigIn()
        return binding.root
    }


    private fun setUpLoginOrSigIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requireContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.apply {
            buttonTextSigIn.setOnClickListener {
                viewModel.signIn(googleSignInClient, resultLauncher)
            }

            btnLogin.setOnClickListener {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val email = binding.edtLoginEmail.text
        val password = binding.edtLoginPassword.text
        firebaseAuth = Firebase.auth
        viewModel.login(
            firebaseAuth,
            email.toString(),
            password.toString(),
            requireContext()
        )
    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(AuthActivity.TAG, "firebaseAuthWithGoogle : " + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: Exception) {
                    Log.w(AuthActivity.TAG, "Google Sign In Failed ", e)
                }
            }
        }


    private fun firebaseAuthWithGoogle(idToken: String) {
        firebaseAuth = Firebase.auth
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AuthActivity.TAG, "SignInWithCredentialSuccess")
                    val user = firebaseAuth.currentUser
                    val userData = DataUser(user?.email, user?.phoneNumber)

                    /*save object data to firebase*/
                    val database = Firebase.database
                    val ref = database.reference.child(ProfileFragment.USERS)

                    /*store user data with uid*/
                    ref.child(user!!.uid).setValue(userData)
                    updateUI(user)
                } else {
                    Log.w(AuthActivity.TAG, "signInWithCredentialFailure ", task.exception)
                    updateUI(null)
                }
            }
    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
    }

}