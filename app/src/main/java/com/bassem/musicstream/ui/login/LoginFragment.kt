package com.bassem.musicstream.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.login_fragment) {
    var binding: LoginFragmentBinding? = null
    var viewModel: LoginViewModel? = null
    var auth: FirebaseAuth? = null
    var bottomLayout: LinearLayout? = null

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth?.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        bottomLayout = activity?.findViewById(R.id.bottomLayout)
        bottomLayout?.visibility = View.GONE


        //Listeners
        binding?.signup?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding?.loginBtu?.setOnClickListener {
            viewModel?.login(
                binding?.mailSignin?.text.toString(),
                binding?.passSigin?.text.toString()
            )

        }

        //Observers
        viewModel?.isLogin?.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }


    }
}