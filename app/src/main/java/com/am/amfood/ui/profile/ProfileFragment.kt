package com.am.amfood.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.am.amfood.data.remote.firebase.DataUser
import com.am.amfood.databinding.FragmentProfileBinding
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.utils.Utils.firebaseAuth
import com.am.amfood.utils.Utils.formatNameFromEmail
import com.am.amfood.utils.Utils.toastMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        getDataUser()

        return binding.root
    }

    private fun getDataUser() {
        val authFirebase: FirebaseAuth = firebaseAuth
        val dataUser = authFirebase.currentUser

        if (dataUser?.uid != null) {
            val database = Firebase.database
            val dataPath = "$USERS/${dataUser.uid}"
            val reference = database.reference.child(dataPath)

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userDataFromDatabase = snapshot.getValue(DataUser::class.java)
                    if (userDataFromDatabase != null) {
                        val phone = userDataFromDatabase.phone ?: ""
                        binding.textValuePhone.text = phone
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
            binding.apply {
                dataUser.let {
                    binding.textValueUsername.text = formatNameFromEmail(it.email.toString())
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

    companion object {
        const val USERS = "users"
        const val EMAIL = "email"
        const val PHONE = "phone"
    }

}