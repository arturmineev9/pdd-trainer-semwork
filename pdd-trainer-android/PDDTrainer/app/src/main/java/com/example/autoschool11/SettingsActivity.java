package com.example.autoschool11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.autoschool11.databinding.FragmentSettingsBinding;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.login_registration.LoginActivity;
import com.example.autoschool11.login_registration.RegistrationActivity;
import com.example.autoschool11.theme_changer.Methods;
import com.example.autoschool11.theme_changer.ThemeColor;
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences app_preferences;
    private SharedPreferences.Editor editor;
    private FragmentSettingsBinding binding;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int appColor = app_preferences.getInt("color", 0);
        int appTheme = app_preferences.getInt("theme", 0);
        ThemeColor.color = appColor;

        if (appColor == 0 || appTheme == 0) {
            setTheme(ThemeColor.theme);
        } else {
            setTheme(appTheme);
        }

        setContentView(view);

        methods = new Methods();

        editor = app_preferences.edit();

        colorize();

        // Новая логика выбора цвета
        binding.buttonColor.setOnClickListener(v -> {
            new MaterialColorPickerDialog
                    .Builder(this)
                    .setTitle("Выбор цвета")
                    .setColors(Arrays.asList(
                            "#F44336",
                            "#E91E63",
                            "#9C27B0",
                            "#673AB7",
                            "#3F51B5",
                            "#2196F3",
                            "#03A9F4",
                            "#4CAF50",
                            "#FF9800",
                            "#9E9E9E",
                            "#795548",
                            "#000000",
                            "#ffc107",
                            "#00BCD4",
                            "#009688",
                            "#8BC34A",
                            "#CDDC39",
                            "#FFEB3B",
                            "#FF5722",
                            "#607D8B"
                    ))

                    .setColorShape(ColorShape.CIRCLE)
                    .setColorSwatch(ColorSwatch._300)
                    .setDefaultColor(R.color.red_colorPrimary)
                    .setColorListener((color, colorHex) -> {
                        ThemeColor.color = color;
                        methods.setColorTheme();
                        editor.putInt("color", color);
                        editor.putInt("theme", ThemeColor.theme);
                        editor.commit();

                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    })
                    .show();
        });

        binding.statRestartCard.setOnClickListener(this);
        binding.loginButtonCard.setOnClickListener(view1 -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
        binding.registrationButtonCard.setOnClickListener(view12 -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });
    }

    private void colorize() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);
        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(ThemeColor.color == 0 ? 0xffF44336 : ThemeColor.color);
        binding.buttonColor.setBackground(d);
        binding.registrationButton.setBackgroundColor(ThemeColor.color);
        binding.loginButton.setBackgroundColor(ThemeColor.color);
    }

    @Override
    public void onClick(View view) {
        DataBaseHelper databaseHelper = new DataBaseHelper(getBaseContext());
        databaseHelper.restartTrainingData();
        databaseHelper.restartStatisticsDB();
        databaseHelper.restartMistakes();
        databaseHelper.restartDayStatisticsDB();
        databaseHelper.restartSuccessTable();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Статистика сброшена!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
