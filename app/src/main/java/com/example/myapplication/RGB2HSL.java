package com.example.myapplication;

import static com.example.myapplication.HSL2RGB.RGB_B_KEY;
import static com.example.myapplication.HSL2RGB.RGB_G_KEY;
import static com.example.myapplication.HSL2RGB.RGB_R_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class RGB2HSL extends AppCompatActivity {
    // Declarations

    // Page Switcher
    public Button btnSwitchToHSL2RGB;
    public static final String HSL_H_KEY = "hslH";
    public static final String HSL_S_KEY = "hslS";
    public static final String HSL_L_KEY = "hslL";

    // RGB SeekBars
    public SeekBar sbrRGBR;
    public SeekBar sbrRGBG;
    public SeekBar sbrRGBB;

    public TextView lblRGBRValue;
    public TextView lblRGBGValue;
    public TextView lblRGBBValue;

    private static final int SBR_RGB_VALUE_DEFAULT = 0;
    public static final String DOUBLE_FORMAT_LONG = "%.16f";
    static final String DOUBLE_FORMAT_SHORT = "%.4f";
    static final int SBR_RGB_VALUE_MAX = 255;

    // RGB Values
    int r = 0;
    int g = 0;
    int b = 0;

    // RGB Flags for updating other RGB-based fields and values
    static int RGB_NONE = -1;
    static int RGB_R = 0;
    static int RGB_G = 1;
    static int RGB_B = 2;

    // Colour Box
    View imgColour;

    // RGB Prime
    public TextView lblRGBRPValue;
    public TextView lblRGBGPValue;
    public TextView lblRGBBPValue;

    double rPime = 0.0;
    double gPrime = 0.0;
    double bPrime = 0.0;
    double[] rgbPrime;
    String[] rgbPrimeString = {"R'", "G'", "B'"};
    static int RGB_PRIME_NONE = -1;
    static int RGB_PRIME_R = 0;
    static int RGB_PRIME_G = 1;
    static int RGB_PRIME_B = 2;

    // P Values
    public TextView lblHSLPMaxValue;
    public TextView lblHSLPMinValue;
    public TextView lblHSLPDeltaValue;

    double pMax = 0.0;
    double pMin = 0.0;
    double pDelta = 0.0;

    int pMaxIndex;
    int pMinIndex;

    //HSL Values
    int hslH;
    String hslH360;
    int hslS;
    int hslL = 0;

    // HSL Values: H Fields
    TextView lblHSLH0Calc;
    TextView lblHSLHRCalc;
    TextView lblHSLHGCalc;
    TextView lblHSLHBCalc;
    TextView[] lblHSLHCalcFields;

    TextView lblHSLH0Value;
    TextView lblHSLHRValue;
    TextView lblHSLHGValue;
    TextView lblHSLHBValue;
    TextView[] lblHSLHValueFields;

    // HSL Values: S Fields
    TextView lblHSLS0Calc;
    TextView lblHSLSLLE50Calc;
    TextView lblHSLSLGT50Calc;
    TextView[] lblHSLSCalcFields;

    TextView lblHSLS0Value;
    TextView lblHSLSLLE50Value;
    TextView lblHSLSLGT50Value;
    TextView[] lblHSLSValueFields;

    // HSL Values: L Fields
    TextView lblHSLLCalc;
    TextView lblHSLLValue;

    // S Index
    int hslSIndex;
    static final int HSL_S_INDEX_LE50 = 1;
    static final int HSL_S_INDEX_GT50 = 2;

    // Constants for conversion equation
    static int HSL_MULTIPLIER = 60;
    static int HSL_ADDITIVE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb2hsl);

        // Set initial values for RGB
        // Initial HSL Values
        r = getIntent().getIntExtra(RGB_R_KEY, 0);
        g = getIntent().getIntExtra(RGB_G_KEY, 0);
        b = getIntent().getIntExtra(RGB_B_KEY, 0);

        // Colour Button
        imgColour = findViewById(R.id.imgColourRGB);

        // RGB Prime
        lblRGBRPValue = findViewById(R.id.lblRGBRPValue);
        lblRGBGPValue = findViewById(R.id.lblRGBGPValue);
        lblRGBBPValue = findViewById(R.id.lblRGBBPValue);

        // P Values
        lblHSLPMaxValue = findViewById(R.id.lblHSLPMaxValue);
        lblHSLPMinValue = findViewById(R.id.lblHSLPMinValue);
        lblHSLPDeltaValue = findViewById(R.id.lblHSLPDeltaValue);

        // HSL Values
        // HSL: H
        lblHSLH0Calc = findViewById(R.id.lblHSLH0Calc);
        lblHSLHRCalc = findViewById(R.id.lblHSLHRCalc);
        lblHSLHGCalc = findViewById(R.id.lblHSLHGCalc);
        lblHSLHBCalc = findViewById(R.id.lblHSLHBCalc);

        lblHSLHCalcFields = new TextView[]{lblHSLH0Calc, lblHSLHRCalc, lblHSLHGCalc, lblHSLHBCalc};

        lblHSLH0Value = findViewById(R.id.lblHSLH0Value);
        lblHSLHRValue = findViewById(R.id.lblHSLHRValue);
        lblHSLHGValue = findViewById(R.id.lblHSLHGValue);
        lblHSLHBValue = findViewById(R.id.lblHSLHBValue);

        lblHSLHValueFields = new TextView[]{lblHSLH0Value, lblHSLHRValue, lblHSLHGValue, lblHSLHBValue};

        // HSL: S
        lblHSLS0Calc = findViewById(R.id.lblHSLS0Calc);
        lblHSLSLLE50Calc = findViewById(R.id.lblHSLSLLE50Calc);
        lblHSLSLGT50Calc = findViewById(R.id.lblHSLSLGT50Calc);
        lblHSLSCalcFields = new TextView[]{lblHSLS0Calc, lblHSLSLLE50Calc, lblHSLSLGT50Calc};

        lblHSLS0Value = findViewById(R.id.lblHSLS0Value);
        lblHSLSLLE50Value = findViewById(R.id.lblHSLSLLE50Value);
        lblHSLSLGT50Value = findViewById(R.id.lblHSLSLGT50Value);
        lblHSLSValueFields = new TextView[]{lblHSLS0Value, lblHSLSLLE50Value, lblHSLSLGT50Value};

        // HSL: L
        lblHSLLCalc = findViewById(R.id.lblHSLLCalc);
        lblHSLLValue = findViewById(R.id.lblHSLLValue);

        // Definitions

        // Switcher
        btnSwitchToHSL2RGB = findViewById(R.id.btnSwitchToHSL2RGB);

        btnSwitchToHSL2RGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToHSL2RGB = new Intent(RGB2HSL.this, HSL2RGB.class);
                switchToHSL2RGB.putExtra(HSL_H_KEY, hslH);
                switchToHSL2RGB.putExtra(HSL_S_KEY, hslS);
                switchToHSL2RGB.putExtra(HSL_L_KEY, hslL);
                startActivity(switchToHSL2RGB);
            }
        });

        // RGB SeekBars
        sbrRGBR = findViewById(R.id.sbrRGBR);
        sbrRGBR.setMax(SBR_RGB_VALUE_MAX);
        lblRGBRValue = findViewById(R.id.lblRGBRValue);

        sbrRGBR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rgbFieldsUpdate(progress, RGB_R);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbrRGBG = findViewById(R.id.sbrRGBG);
        sbrRGBG.setMax(SBR_RGB_VALUE_MAX);
        lblRGBGValue = findViewById(R.id.lblRGBGValue);

        sbrRGBG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rgbFieldsUpdate(progress, RGB_G);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbrRGBB = findViewById(R.id.sbrRGBB);
        sbrRGBB.setMax(SBR_RGB_VALUE_MAX);
        lblRGBBValue = findViewById(R.id.lblRGBBValue);

        sbrRGBB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rgbFieldsUpdate(progress, RGB_B);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Set RGB and HSL fields to 0 by default
        rgbFieldsUpdate(0, RGB_NONE);
    }

    // Take input for RGB and update RGB and RGB Prime fields
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    void rgbFieldsUpdate(int rgbXValue, int rgbXFlag) {

        if(rgbXFlag == RGB_R) {
            r = rgbXValue;
            rPime = Calculations.rgbPrimeValueGet(r);
            lblRGBRValue.setText(Integer.toString(r));
            lblRGBRPValue.setText(String.format(DOUBLE_FORMAT_SHORT, rPime));
        } else if(rgbXFlag == RGB_G) {
            g = rgbXValue;
            gPrime = Calculations.rgbPrimeValueGet(g);
            lblRGBGValue.setText(Integer.toString(g));
            lblRGBGPValue.setText(String.format(DOUBLE_FORMAT_SHORT, gPrime));
        } else if(rgbXFlag == RGB_B) {
            b = rgbXValue;
            bPrime = Calculations.rgbPrimeValueGet(b);
            lblRGBBValue.setText(Integer.toString(b));
            lblRGBBPValue.setText(String.format(DOUBLE_FORMAT_SHORT, bPrime));
        } else {
            lblRGBRValue.setText(Integer.toString(r));
            lblRGBGValue.setText(Integer.toString(g));
            lblRGBBValue.setText(Integer.toString(b));
            lblRGBRPValue.setText(String.format(DOUBLE_FORMAT_SHORT, rPime));
            lblRGBGPValue.setText(String.format(DOUBLE_FORMAT_SHORT, gPrime));
            lblRGBBPValue.setText(String.format(DOUBLE_FORMAT_SHORT, bPrime));
            sbrRGBR.setProgress(r);
            sbrRGBG.setProgress(g);
            sbrRGBB.setProgress(b);
        }

        imgColour.setBackgroundColor(Color.rgb(r, g, b));

        rgbPrime = new double[]{rPime, gPrime, bPrime};

        hslCalculate();
    }

    // Take Inputs and process calculations
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    void hslCalculate() {
        // Source: https://www.calculatorology.com/rgb-to-hsv-conversion/
        hslPValuesUpdate();

        hslHUpdate();

        hslLUpdate();

        hslSUpdate();
    }

    void hslPValuesUpdate() {
        // Get Index for cMax (R, G, B, or NONE) from list; need this to pick proper equation later
        pMaxIndex = Calculations.cMaxIndexGet(rgbPrime);
        pMax = rgbPrime[pMaxIndex];
        lblHSLPMaxValue.setText(rgbPrimeString[pMaxIndex] + ": " + String.format(DOUBLE_FORMAT_SHORT, pMax));

        // Get Index for cMix (R, G, B, or None) from list
        pMinIndex = Calculations.cMinIndexGet(rgbPrime);
        pMin = rgbPrime[pMinIndex];
        lblHSLPMinValue.setText(rgbPrimeString[pMinIndex] + ": " + String.format(DOUBLE_FORMAT_SHORT, pMin));

        // Calculate cDelta
        pDelta = pMax - pMin;
        lblHSLPDeltaValue.setText(String.format(DOUBLE_FORMAT_SHORT, pDelta));
    }

    void hslHUpdate() {

        // Set all H fields to blank
        for(int i = 0; i < lblHSLHValueFields.length; i++) {
            lblHSLHValueFields[i].setText("");
            lblHSLHCalcFields[i].setText("");
        }

        hslH = Calculations.hslHCalculate(pMaxIndex, pDelta);

        // If cDelta is 0, populate value for 0; else show the equation and answer for H
        if(pDelta == 0.0) {
            lblHSLHValueFields[0].setText("0");
        } else {
            hslH360 = Calculations.hslH360Get(hslH);
            // Set proper H field
            lblHSLHCalcFields[pMaxIndex + 1].setText(
                    Integer.toString(HSL_MULTIPLIER)
                            + " * "
                            + "(" + Integer.toString(HSL_ADDITIVE * pMaxIndex)
                            + " + (" + String.format(DOUBLE_FORMAT_SHORT, Calculations.rgbPrimeDifference())
                            + " / (" + String.format(DOUBLE_FORMAT_SHORT, pDelta) + ")))"
                            + hslH360
                            + " = ");
            lblHSLHValueFields[pMaxIndex + 1].setText(Integer.toString(hslH));
        }
    }

    void hslLUpdate() {
        hslL = Calculations.hslLCalculate(pMax, pMin);

        lblHSLLCalc.setText("(" + String.format(DOUBLE_FORMAT_SHORT, pMax) + " - " + String.format(DOUBLE_FORMAT_SHORT, pMin) + ") / 2 = ");

        lblHSLLValue.setText(Integer.toString(hslL) + "%");
    }

    void hslSUpdate() {

        // Set all S fields to blank
        for (int i = 0; i < lblHSLSCalcFields.length; i++) {
            lblHSLSValueFields[i].setText("");
            lblHSLSCalcFields[i].setText("");
        }

        hslS = Calculations.hslSCalculate(hslL, pDelta, pMax, pMin);
        hslSIndex = Calculations.hslSIndexGet(hslL);
        String hslSDenominator = Calculations.hslSDenominatorGet(hslL, pMax, pMin);

        // Set proper S field
        // If S is 0, populate value for 0; else show the equation and answer for S
        if(hslS == 0) {
            lblHSLS0Value.setText("0%");
        } else {
            lblHSLSCalcFields[hslSIndex].setText(
                    String.format(DOUBLE_FORMAT_SHORT, pDelta)
                            + " / ("
                            + hslSDenominator
                            + ") = ");
            lblHSLSValueFields[hslSIndex].setText(Integer.toString(hslS) + "%");
        }
    }
}