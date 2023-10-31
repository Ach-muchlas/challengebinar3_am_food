package com.am.amfood.ui.auth.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.am.amfood.databinding.FragmentLoginBinding
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.auth.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpLoginOrSigIn()
        return binding.root
    }


    private fun setUpLoginOrSigIn() {
        val email = binding.edtLoginEmail.text.toString()
        val password = binding.edtLoginPassword.text.toString()

        binding.apply {
            buttonTextSigIn.setOnClickListener {
                viewModel.signInUser(resultLauncher)
            }

            btnLogin.setOnClickListener {
                viewModel.loginUserWithEmailPassword(email, password)
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(AuthActivity.TAG, "firebaseAuthWithGoogle : " + account.id)
                    viewModel.firebaseAuthWithGoogle(
                        account.idToken!!,
                        requireContext(),
                        account.displayName.toString(),
                        account.email.toString(),
                        account.idToken.toString(),
                        account.photoUrl.toString()
                    )
                } catch (e: Exception) {
                    Log.w(AuthActivity.TAG, "Google Sign In Failed ", e)
                }
            }
        }
}