package com.example.autoschool11.ui.screens.login_registration

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.autoschool11.databinding.ActivityLoginBinding
import com.example.autoschool11.ui.screens.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.pass.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isLetter() }) {
                Toast.makeText(
                    this,
                    "Пароль должен быть не менее 8 символов и содержать буквы и цифры",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }


        lifecycleScope.launchWhenStarted {
            viewModel.authState.collectLatest { state ->
                when (state) {
                    is AuthState.Loading -> {
                    }
                    is AuthState.Success -> {
                        Toast.makeText(this@LoginActivity, "Успешно!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, SettingsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                        viewModel.resetState()
                    }
                    is AuthState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
        }
    }
} 
