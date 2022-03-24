package com.bassem.musicstream.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.SignupFragmentBinding
import com.bassem.musicstream.entities.User

class SignupFragment : Fragment(R.layout.signup_fragment) {
    private var _binding: SignupFragmentBinding? = null
    private val binding get() = _binding
    private var viewModel: SignupViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignupFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        //listener
        binding?.signupButton?.setOnClickListener {
            viewModel?.auth(binding?.mail?.text.toString(), binding?.password?.text.toString())
        }

        //Observers
        viewModel?.userId?.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel?.addUsertoFirebase(getUser(it), it)
            }
        }

        viewModel?.succed?.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "succeded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUser(id: String): User {
        val user = User()
        return user.apply {
            name = binding?.fullname?.text?.toString()!!
            email = binding?.mail?.text?.toString()!!
            phone = binding?.phone?.text?.toString()!!
            password = binding?.password?.text?.toString()!!
            userid = id

        }

    }


}