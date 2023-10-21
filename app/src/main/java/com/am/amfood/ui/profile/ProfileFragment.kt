package com.am.amfood.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.am.amfood.R
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.databinding.FragmentProfileBinding
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.utils.Utils.formatNameFromEmail
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.appbar.let {
            it.btnBack.visibility = View.GONE
            it.textViewAppbar.text = getString(R.string.profile)
        }
        getDataUser()
        signOut()

        return binding.root
    }

    private fun getDataUser() {
        firebaseAuth = Firebase.auth
        val dataUser = firebaseAuth.currentUser

        if (dataUser?.uid != null) {
            val database = Firebase.database
            val dataPath = "$USERS/${dataUser.uid}"
            val reference = database.reference.child(dataPath)

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userDataFromDatabase = snapshot.getValue(DataUser::class.java)
                    if (userDataFromDatabase != null) {
                        val phone = userDataFromDatabase.phone ?: "087859"
                        binding.textValuePhone.text = phone
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
            binding.apply {
                dataUser.let {
                    Glide.with(requireContext()).load(it.photoUrl).into(binding.imageViewAvatar)
                    binding.textValueUsername.text =
                        it.displayName ?: formatNameFromEmail(it.email.toString())
                    binding.textValueEmail.text = it.email
                    binding.textValuePassword.text = it.uid
                    binding.textValuePhone.text = it.phoneNumber ?: "0878669"
                }
            }
        }
    }

    private fun updateDataUser() {
        val uid = firebaseAuth.currentUser?.uid

        if (uid != null) {
            val dataPath = "$USERS/$uid"
            val newData = mapOf(
                EMAIL to "",
                PHONE to ""
            )

            authViewModel.updateData(dataPath, newData) { success ->
                if (success) {
                    toastMessage(requireContext(), "Data updated successfully.")
                } else {
                    toastMessage(requireContext(), "Failed to update data!!")
                }
            }
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requireContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        firebaseAuth = Firebase.auth

        binding.textLogout.setOnClickListener {
            authViewModel.signOut(firebaseAuth, googleSignInClient)
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    companion object {
        const val USERS = "users"
        const val EMAIL = "email"
        const val PHONE = "phone"
    }

}