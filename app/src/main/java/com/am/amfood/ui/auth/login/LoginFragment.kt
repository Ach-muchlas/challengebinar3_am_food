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
import com.am.amfood.databinding.FragmentLoginBinding
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.ui.main.MainActivity
import com.am.amfood.utils.Utils.firebaseAuth
import com.am.amfood.utils.Utils.googleSignClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpLoginOrSigIn()
        return binding.root
    }


    private fun setUpLoginOrSigIn() {
        binding.apply {
            buttonTextSigIn.setOnClickListener {
                viewModel.signIn(googleSignClient, resultLauncher)
            }

            btnLogin.setOnClickListener {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val email = binding.edtLoginEmail.text
        val password = binding.edtLoginPassword.text
        val firebaseAuth = firebaseAuth
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
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AuthActivity.TAG, "SignInWithCredentialSuccess")
                    val user = firebaseAuth.currentUser
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