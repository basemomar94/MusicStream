package com.bassem.musicstream.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.AccountFragmentBinding
import com.bassem.musicstream.entities.User

class AccountFragment : Fragment(R.layout.account_fragment) {
    var binding: AccountFragmentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        viewModel.getUserInfo()
        //Listeners
        binding?.updateButton?.setOnClickListener {
            loading(true)
            viewModel.updateInfo(updatedHashMap())

        }
        //Observers
        viewModel.user.observe(viewLifecycleOwner) {
            upadteUi(it)
        }
        viewModel.isUpdated.observe(viewLifecycleOwner) {
            loading(false)
        }
    }

    private fun upadteUi(user: User) {
        binding?.apply {
            fullname.setText(user.name)
            mail.setText(user.email)
            phone.setText(user.phone)
        }


    }

    private fun loading(isloading: Boolean) {
        if (isloading) {
            binding?.apply {
                updateButton.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            binding?.apply {
                updateButton.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }


    }

    private fun updatedHashMap(): HashMap<String, Any> {
        val user: HashMap<String, Any> = hashMapOf()
        user["name"] = binding?.fullname?.text.toString()
        user["email"] = binding?.mail?.text.toString()
        user["phone"] = binding?.phone?.text.toString()
        return user
    }
}