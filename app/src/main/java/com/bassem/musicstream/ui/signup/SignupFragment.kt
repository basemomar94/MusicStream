package com.bassem.musicstream.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.SignupFragmentBinding
import com.bassem.musicstream.entities.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
            loading(true)
            checkEmpty()
            if (isFieldsFull() && isPasswordMatched()) {
                viewModel?.auth(binding?.mail?.text.toString(), binding?.password?.text.toString())
            } else {
                loading(false)
            }
        }
        binding?.login?.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }


        //Observers
        viewModel?.userId?.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel?.addUsertoFirebase(getUser(it), it)
            }
        }

        viewModel?.isSucceed?.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
            } else {
                loading(false)
            }
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

    private fun showErrorMsg(input: TextInputEditText, layout: TextInputLayout) {
        if (input.text.isNullOrEmpty()) {
            layout.error = "* required"
        } else {
            layout.isErrorEnabled = false
        }
    }

    private fun checkEmpty() {
        showErrorMsg(binding!!.fullname, binding!!.nameLayout)
        showErrorMsg(binding!!.mail, binding!!.mailLayout)
        showErrorMsg(binding!!.password, binding!!.passwordLayout)
        showErrorMsg(binding!!.passwordcheck, binding!!.passwordcheckLayout)
        showErrorMsg(binding!!.phone, binding!!.phoneLayout)

    }

    private fun isFieldsFull() =
        binding?.fullname?.text?.isNotEmpty()!! && binding?.mail?.text?.isNotEmpty()!! && binding?.password?.text?.isNotEmpty()!! && binding?.passwordcheck?.text?.isNotEmpty()!!
                && binding?.phone?.text?.isNotEmpty()!!

    private fun isPasswordMatched(): Boolean {
        var isMatched = false
        if (binding?.password?.text.toString() == binding?.passwordcheck?.text.toString()) {
            isMatched = true
        } else {
            isMatched = false
            binding?.passwordLayout?.error = "the password should be identical"
            binding?.passwordcheckLayout?.error = "the password should be identical"
        }
        return isMatched
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding?.signupButton?.visibility = View.GONE
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.signupButton?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
        }
    }


}