package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.myapplication.RGB2HSL.DOUBLE_FORMAT_SHORT;
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

    // Colour Box
    View imgColour;

    // HSL Values
    int h = 0;
    int s = 0;
    int l = 0;

    // Chroma
    TextView lblChromaCalc;
    TextView lblChromaValue;
    double chroma = 0.0;

    // H Prime
    TextView lblHPrimeCalc;
    TextView lblHPrimeValue;
    double hPrime;

    private static int H_PRIME_INDEX_0 = 0;
    private static int H_PRIME_INDEX_1 = 1;
    private static int H_PRIME_INDEX_2 = 2;
    private static int H_PRIME_INDEX_3 = 3;
    private static int H_PRIME_INDEX_4 = 4;
    private static int H_PRIME_INDEX_5 = 5;

    int hPrimeIndex = H_PRIME_INDEX_0;

    // x
    TextView lblXCalc;
    TextView lblXValue;
    double x = 0.0;

    // m
    TextView lblMCalc;
    TextView lblMValue;
    double m = 0.0;

    // RGB Prime
    double rPrime = 0.0;
    double gPrime = 0.0;
    double bPrime = 0.0;

    TextView lblRGBPH0Calc;
    TextView lblRGBPH1Calc;
    TextView lblRGBPH2Calc;
    TextView lblRGBPH3Calc;
    TextView lblRGBPH4Calc;
    TextView lblRGBPH5Calc;

    TextView lblRGBPH0Value;
    TextView lblRGBPH1Value;
    TextView lblRGBPH2Value;
    TextView lblRGBPH3Value;
    TextView lblRGBPH4Value;
    TextView lblRGBPH5Value;

    TextView[] rgbPrimeCalcs;
    TextView[] rgbPrimeValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsl2rgb);

        // Chroma
        lblChromaCalc = findViewById(R.id.lblChromaCalc);
        lblChromaValue = findViewById(R.id.lblChromaValue);

        // H Prime
        lblHPrimeCalc = findViewById(R.id.lblHPrimeCalc);
        lblHPrimeValue = findViewById(R.id.lblHPrimeValue);

        // X
        lblXCalc = findViewById(R.id.lblXCalc);
        lblXValue = findViewById(R.id.lblXValue);

        // Initial HSL Values
        h = getIntent().getIntExtra(HSL_H_KEY, 0);
        s = getIntent().getIntExtra(HSL_S_KEY, 0);
        l = getIntent().getIntExtra(HSL_L_KEY, 0);

        // m
        lblMCalc = findViewById(R.id.lblMCalc);
        lblMValue = findViewById(R.id.lblMValue);

        // RGB Prime
        lblRGBPH0Calc = findViewById(R.id.lblRGBPH0Calc);
        lblRGBPH1Calc = findViewById(R.id.lblRGBPH1Calc);
        lblRGBPH2Calc = findViewById(R.id.lblRGBPH2Calc);
        lblRGBPH3Calc = findViewById(R.id.lblRGBPH3Calc);
        lblRGBPH4Calc = findViewById(R.id.lblRGBPH4Calc);
        lblRGBPH5Calc = findViewById(R.id.lblRGBPH5Calc);
        rgbPrimeCalcs = new TextView[]{lblRGBPH0Calc, lblRGBPH1Calc, lblRGBPH2Calc, lblRGBPH3Calc, lblRGBPH4Calc, lblRGBPH5Calc};

        lblRGBPH0Value = findViewById(R.id.lblRGBPH0Value);
        lblRGBPH1Value = findViewById(R.id.lblRGBPH1Value);
        lblRGBPH2Value = findViewById(R.id.lblRGBPH2Value);
        lblRGBPH3Value = findViewById(R.id.lblRGBPH3Value);
        lblRGBPH4Value = findViewById(R.id.lblRGBPH4Value);
        lblRGBPH5Value = findViewById(R.id.lblRGBPH5Value);
        rgbPrimeValues = new TextView[]{lblRGBPH0Value, lblRGBPH1Value, lblRGBPH2Value, lblRGBPH3Value, lblRGBPH4Value, lblRGBPH5Value};

        // Run initial calculation
        rgbCalculate();

        // Get HSL variables
        sbrHSLH = findViewById(R.id.sbrHSLH);
        sbrHSLH.setMax(SBR_HSL_H_MAX);
        sbrHSLH.setProgress(h);
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
        sbrHSLS.setProgress(s);

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
        sbrHSLL.setProgress(l);

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
        lblHSLHValue.setText(Integer.toString(h));

        lblHSLSValue = findViewById(R.id.lblHSLSValue);
        lblHSLSValue.setText(Integer.toString(s));

        lblHSLLValue = findViewById(R.id.lblHSLLValue);
        lblHSLLValue.setText(Integer.toString(l));
    }

    void hslFieldsUpdate(int hslValue, SeekBar hslSeekBar) {

        if(hslSeekBar == sbrHSLH) {
            lblHSLHValue.setText(Integer.toString(hslValue));
            h = hslValue;
        } else if(hslSeekBar == sbrHSLS) {
            lblHSLSValue.setText(Integer.toString(hslValue) + "%");
            s = hslValue;
        } else if(hslSeekBar == sbrHSLL) {
            lblHSLLValue.setText(Integer.toString(hslValue) + "%");
            l = hslValue;
        } else {

        }

        rgbCalculate();
    }

    void rgbCalculate() {
        // Chroma
        chroma = Calculations.hslChromaCalculate(s, l);

        // H Prime
        hPrime = Calculations.hslHPrimeCalculate(h);

        // X
        x = Calculations.xCalculate(chroma, hPrime);

        // m
        m = Calculations.mCalculate(l, chroma);

        // Determine which row of RGB Prime to show based on h Prime and add m to each

        // Update display fields
        calculationFieldsUpdate();
    }

    void calculationFieldsUpdate() {
        // Chroma
        lblChromaCalc.setText(Calculations.chromaCalc(s, l));
        lblChromaValue.setText(String.format(DOUBLE_FORMAT_SHORT, chroma));

        // h Prime
        lblHPrimeCalc.setText(Calculations.hPrimeCalc(h));
        lblHPrimeValue.setText(String.format(DOUBLE_FORMAT_SHORT, hPrime));

        // X
        lblXCalc.setText(Calculations.xCalc(chroma, hPrime));
        lblXValue.setText(String.format(DOUBLE_FORMAT_SHORT, x));

        // m
        lblMCalc.setText(Calculations.mCalcWrite(l, chroma));
        lblMValue.setText(String.format(DOUBLE_FORMAT_SHORT, m));

        // RGB Prime
        rgbPrimeFieldsUpdate();
    }

    void rgbPrimeFieldsUpdate() {
        // Get H Prime
        hPrimeIndex = (int)hPrime;

        // Set H Prime to 0 if it is over H_PRIME_LIMIT
        if(hPrimeIndex > H_PRIME_INDEX_5) {
            hPrimeIndex = 0;
        }

        // Set RGB Prime values
        if(hPrimeIndex == H_PRIME_INDEX_0) {
            rPrime = chroma;
            gPrime = x;
            bPrime = 0.0;
        } else if(hPrimeIndex == H_PRIME_INDEX_1) {
            rPrime = x;
            gPrime = chroma;
            bPrime = 0.0;
        } else if(hPrimeIndex == H_PRIME_INDEX_2) {
            rPrime = 0.0;
            gPrime = chroma;
            bPrime = x;
        } else if(hPrimeIndex == H_PRIME_INDEX_3) {
            rPrime = 0.0;
            gPrime = x;
            bPrime = chroma;
        } else if(hPrimeIndex == H_PRIME_INDEX_4) {
            rPrime = x;
            gPrime = 0.0;
            bPrime = chroma;
        } else if(hPrimeIndex == H_PRIME_INDEX_5) {
            rPrime = chroma;
            gPrime = 0.0;
            bPrime = x;
        }

        // Layout settings to collapse and expand Calcs and Values
        ViewGroup.LayoutParams layoutCollapsed = new ViewGroup.LayoutParams(0, 0);
        ViewGroup.LayoutParams layoutExpanded = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Reset Calc and Value fields
        for(int i = 0; i <= H_PRIME_INDEX_5; i++) {
            rgbPrimeCalcs[i].setText(null);
            // rgbPrimeCalcs[i].setLayoutParams(layoutCollapsed);
            rgbPrimeValues[i].setText(null);
            // rgbPrimeValues[i].setLayoutParams(layoutCollapsed);;
        }

        // Set appropriate Calc/Value fields
        rgbPrimeCalcs[hPrimeIndex].setText(Calculations.rgbPrimeCalc(hPrime));
        // rgbPrimeCalcs[hPrimeIndex].setLayoutParams(layoutExpanded);
        rgbPrimeValues[hPrimeIndex].setText(Calculations.rgbPrimeValue(rPrime, gPrime, bPrime));
        // rgbPrimeValues[hPrimeIndex].setLayoutParams(layoutExpanded);
    }
}