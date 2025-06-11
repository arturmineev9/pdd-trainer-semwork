package com.example.autoschool11.ui.screens.login_registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.autoschool11.databinding.ActivityRegistrationBinding
import com.example.autoschool11.ui.screens.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonRegistration.setOnClickListener {
            val firstName = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.pass.text.toString()
            if (firstName.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                viewModel.register(firstName, email, password)
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.authState.collectLatest { state ->
                when (state) {
                    is AuthState.Loading -> {
                        // показать прогресс
                    }
                    is AuthState.Success -> {
                        // сохранить токен, перейти на SettingsActivity
                        AuthTokenStorage.saveToken(this@RegistrationActivity, state.token)
                        Toast.makeText(this@RegistrationActivity, "Успешно!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegistrationActivity, SettingsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                        viewModel.resetState()
                    }
                    is AuthState.Error -> {
                        Toast.makeText(this@RegistrationActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
        }
    }
} 