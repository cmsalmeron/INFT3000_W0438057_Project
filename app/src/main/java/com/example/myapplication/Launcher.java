package com.example.myapplication;

import static com.example.myapplication.HSL2RGB.RGB_B_KEY;
import static com.example.myapplication.HSL2RGB.RGB_G_KEY;
import static com.example.myapplication.HSL2RGB.RGB_R_KEY;
import static com.example.myapplication.RGB2HSL.HSL_H_KEY;
import static com.example.myapplication.RGB2HSL.HSL_L_KEY;
import static com.example.myapplication.RGB2HSL.HSL_S_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {
    Button btnRGB2HSL;
    Button btnHSL2RGB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        btnRGB2HSL = findViewById(R.id.btnRGB2HSL);

        btnRGB2HSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToRGB2HSL = new Intent(Launcher.this, RGB2HSL.class);
                switchToRGB2HSL.putExtra(RGB_R_KEY, 0);
                switchToRGB2HSL.putExtra(RGB_G_KEY, 0);
                switchToRGB2HSL.putExtra(RGB_B_KEY, 0);
                startActivity(switchToRGB2HSL);
            }
        });

        btnHSL2RGB = findViewById(R.id.btnHSL2RGB);

        btnHSL2RGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToHSL2RGB = new Intent(Launcher.this, HSL2RGB.class);
                switchToHSL2RGB.putExtra(HSL_H_KEY, 0);
                switchToHSL2RGB.putExtra(HSL_S_KEY, 0);
                switchToHSL2RGB.putExtra(HSL_L_KEY, 0);
                startActivity(switchToHSL2RGB);
            }
        });
    }
}
