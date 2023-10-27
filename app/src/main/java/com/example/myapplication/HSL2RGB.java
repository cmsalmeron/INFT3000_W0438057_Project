package com.example.myapplication;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.myapplication.RGB2HSL.HSL_H_KEY;
import static com.example.myapplication.RGB2HSL.HSL_S_KEY;
import static com.example.myapplication.RGB2HSL.HSL_L_KEY;

import androidx.appcompat.app.AppCompatActivity;

public class HSL2RGB extends AppCompatActivity {
    // Declare variables
    public SeekBar sbrHSLH;
    public SeekBar sbrHSLS;
    public SeekBar sbrHSLL;

    public static final int SBR_HSL_H_MAX = 360;
    public static final int SBR_HSL_S_MAX = 100;
    public static final int SBR_HSL_L_MAX = 100;

    public TextView lblHSLHValue;
    public TextView lblHSLSValue;
    public TextView lblHSLLValue;

    // HSL Values
    int hslH = 0;
    int hslS = 0;
    int hslL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsl2rgb);

        hslH = getIntent().getIntExtra(HSL_H_KEY, 0);
        hslS = getIntent().getIntExtra(HSL_S_KEY, 0);
        hslL = getIntent().getIntExtra(HSL_L_KEY, 0);

        // Define variables
        sbrHSLH = findViewById(R.id.sbrHSLH);
        sbrHSLH.setMax(SBR_HSL_H_MAX);
        sbrHSLH.setProgress(hslH);
        sbrHSLH.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hslFieldsUpdate(progress, seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbrHSLS = findViewById(R.id.sbrHSLS);
        sbrHSLS.setMax(SBR_HSL_S_MAX);
        sbrHSLS.setProgress(hslS);

        sbrHSLS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hslFieldsUpdate(progress, seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbrHSLL = findViewById(R.id.sbrHSLL);
        sbrHSLL.setMax(SBR_HSL_L_MAX);
        sbrHSLL.setProgress(hslL);

        sbrHSLL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hslFieldsUpdate(progress, seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Set sbr values
        lblHSLHValue = findViewById(R.id.lblHSLHValue);
        lblHSLHValue.setText(Integer.toString(hslH));

        lblHSLSValue = findViewById(R.id.lblHSLSValue);
        lblHSLSValue.setText(Integer.toString(hslS));

        lblHSLLValue = findViewById(R.id.lblHSLLValue);
        lblHSLLValue.setText(Integer.toString(hslL));
    }

    void hslFieldsUpdate(int hslValue, SeekBar hslSeekBar) {

        if(hslSeekBar == sbrHSLH) {
            lblHSLHValue.setText(Integer.toString(hslValue));
        } else if(hslSeekBar == sbrHSLS) {
            lblHSLSValue.setText(Integer.toString(hslValue));
        } else if(hslSeekBar == sbrHSLL) {
            lblHSLLValue.setText(Integer.toString(hslValue));
        } else {

        }
    }
}