package com.example.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dogedex.R
import com.example.dogedex.auth.LoginFragment.LoginFragmentActions
import com.example.dogedex.databinding.FragmentSignUpBinding
import com.example.dogedex.isValidEmail

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    interface SignUpFragmentActions {
        fun onSingUpFieldsValidated(email: String, pass: String, passConfirm: String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SignUpFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)

        setupSignUpButtons()

        return binding.root
    }

    private fun setupSignUpButtons() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

        val email = binding.emailEdit.text.toString()
        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }
        val pass = binding.passwordEdit.text.toString()
        if (pass.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        val passConfirm = binding.confirmPasswordEdit.text.toString()
        if (pass.isEmpty()) {
            binding.confirmPasswordInput.error =
                getString(R.string.password_confirm_must_not_be_empty)
            return
        }

        if (pass != passConfirm) {
            binding.confirmPasswordInput.error = getString(R.string.passwords_do_not_match)
            return
        }
        signUpFragmentActions.onSingUpFieldsValidated(email, pass, passConfirm)
    }


}