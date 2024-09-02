package com.example.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.dogedex.main.MainActivity
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.databinding.ActivityLoginBinding
import com.example.dogedex.domain.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> {
                    binding.pbLoading.isVisible = true
                }

                is ApiResponseStatus.Error -> {
                    binding.pbLoading.isVisible = false
                    showErrorDialog(status.message)
                }

                is ApiResponseStatus.Success -> {
                    binding.pbLoading.isVisible = false
                }

                else -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this, "Status not implemented!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showErrorDialog(msgId: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.unknown_error)
            .setMessage(msgId)
            .setPositiveButton(android.R.string.ok) { _, _ ->

            }
            .create().show()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, pass: String) {
        viewModel.login(email, pass)
    }

    override fun onSingUpFieldsValidated(email: String, pass: String, passConfirm: String) {
        viewModel.signUp(email, pass, passConfirm)
    }
}