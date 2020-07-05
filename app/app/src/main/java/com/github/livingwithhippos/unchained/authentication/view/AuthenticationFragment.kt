package com.github.livingwithhippos.unchained.authentication.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.livingwithhippos.unchained.R
import com.github.livingwithhippos.unchained.authentication.viewmodel.AuthenticationViewModel
import com.github.livingwithhippos.unchained.databinding.FragmentAuthenticationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthenticationFragment : Fragment(), ButtonListener {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val authBinding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        //todo: add loading gif

        authBinding.listener = this

        viewModel.fetchAuthenticationInfo()
        observeAuthentication(authBinding)


        return authBinding.root
    }

    private fun observeAuthentication(binding: FragmentAuthenticationBinding) {
        viewModel.authLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.auth = it
                observeSecrets(binding, it.deviceCode)
            }
        })
    }

    private fun observeSecrets(binding: FragmentAuthenticationBinding, deviceCode: String){
        // start checking for user confirmation
        viewModel.fetchSecrets(deviceCode)
        viewModel.secretLiveData.observe(viewLifecycleOwner, Observer {
            if (it?.clientId != null) {
                binding.secrets = it
                observeToken(binding, it.clientId, deviceCode, it.clientSecret)
            }
        })
    }

    private fun observeToken(binding: FragmentAuthenticationBinding, clientId: String, deviceCode: String, clientSecret: String){

        // start checking for user confirmation
        viewModel.fetchToken(clientId, deviceCode, clientSecret)
        viewModel.tokenLiveData.observe(viewLifecycleOwner, Observer {
            if (it?.accessToken != null) {
                binding.token = it
                //todo: navigate to main fragment
                Log.d("VALUE FOUND", "GOT TOKEN")
            }
        })
    }

    override fun onCopyClick(value: String) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("real-debrid authorization code", value)
        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(),getString(R.string.code_copied),Toast.LENGTH_SHORT).show()
    }
}