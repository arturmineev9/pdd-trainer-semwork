package com.example.autoschool11.ui.screens.settings

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.autoschool11.R
import com.example.autoschool11.core.data.local.DataBaseHelper
import com.example.autoschool11.databinding.FragmentSettingsBinding
import com.example.autoschool11.ui.screens.MainActivity
import com.example.autoschool11.ui.screens.login_registration.AuthTokenStorage
import com.example.autoschool11.ui.screens.login_registration.LoginActivity
import com.example.autoschool11.ui.screens.login_registration.RegistrationActivity
import com.example.autoschool11.ui.theme_changer.Methods
import com.example.autoschool11.ui.theme_changer.ThemeColor
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: UserStatsViewModel by viewModels()
    private lateinit var methods: Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        val appPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appColor = appPreferences.getInt("color", 0)
        val appTheme = appPreferences.getInt("theme", 0)
        ThemeColor.color = appColor

        if (appColor == 0 || appTheme == 0) {
            setTheme(ThemeColor.theme)
        } else {
            setTheme(appTheme)
        }

        setContentView(binding.root)
        methods = Methods()
        val editor = appPreferences.edit()

        colorize()
        setupClickListeners(editor)
        updateUiBasedOnAuth()
        observeViewModel()
    }

    private fun updateUiBasedOnAuth() {
        val token = AuthTokenStorage.getToken(this)
        if (token != null && token.isNotEmpty()) {
            binding.grid.visibility = View.VISIBLE
            binding.gridogin.visibility = View.INVISIBLE
        } else {
            binding.grid.visibility = View.INVISIBLE
            binding.gridogin.visibility = View.VISIBLE
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.userStatsState.collectLatest { state ->
                when (state) {
                    is UserStatsState.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is UserStatsState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@SettingsActivity, "Успешно!", Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    is UserStatsState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@SettingsActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    is UserStatsState.Idle -> binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupClickListeners(editor: android.content.SharedPreferences.Editor) {
        binding.loginButtonCard.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registrationButtonCard.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.saveProgressCard.setOnClickListener {
            viewModel.saveStats()
        }
        binding.loadProgressCard.setOnClickListener {
            viewModel.loadStats()
        }
        binding.statRestartCard.setOnClickListener {
            val databaseHelper = DataBaseHelper(baseContext)
            databaseHelper.restartTrainingData()
            databaseHelper.restartStatisticsDB()
            databaseHelper.restartMistakes()
            databaseHelper.restartDayStatisticsDB()
            databaseHelper.restartSuccessTable()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            Toast.makeText(baseContext, "Статистика сброшена!", Toast.LENGTH_SHORT).show()
        }

        binding.buttonColor.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Выбор цвета")
                .setColors(
                    listOf(
                        "#F44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#2196F3",
                        "#03A9F4", "#4CAF50", "#FF9800", "#9E9E9E", "#795548", "#000000",
                        "#ffc107", "#00BCD4", "#009688", "#8BC34A", "#CDDC39", "#FFEB3B",
                        "#FF5722", "#607D8B"
                    )
                )
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(R.color.red_colorPrimary)
                .setColorListener { color, _ ->
                    ThemeColor.color = color
                    methods.setColorTheme()
                    editor.putInt("color", color)
                    editor.putInt("theme", ThemeColor.theme)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                .show()
        }
    }

    private fun colorize() {
        val d = ShapeDrawable(OvalShape())
        d.setBounds(58, 58, 58, 58)
        d.paint.style = Paint.Style.FILL
        d.paint.color = if (ThemeColor.color == 0) 0xffF44336.toInt() else ThemeColor.color
        binding.buttonColor.background = d
        binding.registrationButton.setBackgroundColor(ThemeColor.color)
        binding.loginButton.setBackgroundColor(ThemeColor.color)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        updateUiBasedOnAuth() // Обновляем UI при возвращении на экран
    }
} 
