package com.example.myapplication;

import static com.example.myapplication.RGB2HSL.SBR_RGB_VALUE_MAX;
import static com.example.myapplication.RGB2HSL.RGB_PRIME_R;
import static com.example.myapplication.RGB2HSL.RGB_PRIME_G;
import static com.example.myapplication.RGB2HSL.RGB_PRIME_B;
import static com.example.myapplication.RGB2HSL.RGB_PRIME_NONE;
import static com.example.myapplication.RGB2HSL.HSL_MULTIPLIER;
import static com.example.myapplication.RGB2HSL.HSL_ADDITIVE;
import static com.example.myapplication.RGB2HSL.HSL_S_INDEX_LE50;
import static com.example.myapplication.RGB2HSL.HSL_S_INDEX_GT50;
import static com.example.myapplication.RGB2HSL.DOUBLE_FORMAT_SHORT;

public class Calculations {

    static double rgbRPrimeInput;
    static double rgbGPrimeInput;
    static double rgbBPrimeInput;
    static double rgbPrimeDifference;
    static final int HSL_R_360 = 360;
    static final int HSL_R_300 = 300;
    static final int HSL_L_THRESHOLD = 50;
    static final double PERCENT_CONVERSION = 100.0;

    static double rgbPrimeValueGet(int rgbValue) {
        return rgbValue / (double)SBR_RGB_VALUE_MAX;
    }

    static int cMaxIndexGet(double[] rgbPrimeInput) {
        rgbRPrimeInput = rgbPrimeInput[RGB_PRIME_R];
        rgbGPrimeInput = rgbPrimeInput[RGB_PRIME_G];
        rgbBPrimeInput = rgbPrimeInput[RGB_PRIME_B];

        int cMaxIndexCurrent;

        if ((rgbRPrimeInput >= rgbGPrimeInput) && (rgbRPrimeInput >= rgbBPrimeInput)) {
            cMaxIndexCurrent = RGB_PRIME_R;
            rgbPrimeDifference = rgbGPrimeInput - rgbBPrimeInput;
        } else if ((rgbGPrimeInput >= rgbRPrimeInput) && (rgbGPrimeInput >= rgbBPrimeInput)) {
            cMaxIndexCurrent = RGB_PRIME_G;
            rgbPrimeDifference = rgbBPrimeInput - rgbRPrimeInput;
        } else if ((rgbBPrimeInput >= rgbRPrimeInput) && (rgbBPrimeInput >= rgbGPrimeInput)) {
            cMaxIndexCurrent = RGB_PRIME_B;
            rgbPrimeDifference = rgbRPrimeInput - rgbGPrimeInput;
        } else {
            cMaxIndexCurrent = RGB_PRIME_NONE;
            rgbPrimeDifference = 0.0;
        }

        return cMaxIndexCurrent;
    }

    static int cMinIndexGet(double[] rgbPrimeInput) {
        rgbRPrimeInput = rgbPrimeInput[RGB_PRIME_R];
        rgbGPrimeInput = rgbPrimeInput[RGB_PRIME_G];
        rgbBPrimeInput = rgbPrimeInput[RGB_PRIME_B];
        int cMinIndexCurrent;

        if ((rgbRPrimeInput <= rgbGPrimeInput) && (rgbRPrimeInput <= rgbBPrimeInput)) {
            cMinIndexCurrent = RGB_PRIME_R;
        } else if ((rgbGPrimeInput <= rgbRPrimeInput) && (rgbGPrimeInput <= rgbBPrimeInput)) {
            cMinIndexCurrent = RGB_PRIME_G;
        } else {
            cMinIndexCurrent = RGB_PRIME_B;
        }

        return cMinIndexCurrent;
    }

    //This changes depending on pMaxIndex (whichever prime is the chosen prime)
    static double rgbPrimeDifference() {
        return rgbPrimeDifference;
    }

    static int hslHCalculate(int pMaxIndexInput, double pDeltaInput) {
        int hslH;

        if(pDeltaInput == 0.0) {
            hslH = 0;
        } else {
            //Equation from https://www.niwa.nu/2013/05/math-behind-colorspace-conversions-rgb-hsl/
            hslH = (int)Math.round(((double)HSL_MULTIPLIER * ((HSL_ADDITIVE * pMaxIndexInput) + (rgbPrimeDifference / pDeltaInput))));

            if(hslH < 0) {
                hslH += HSL_R_360;
            }
        }

        return hslH;
    }

    static int hslLCalculate(double cMax, double cMin) {
        double hslLDouble;
        hslLDouble = ((cMin + cMax) / (double)HSL_ADDITIVE) * 100.0;
        return (int)Math.round(hslLDouble);
    }

    static int hslSIndexGet(int hslL) {
        int hslSIndex = 0;

        if(hslL <= HSL_L_THRESHOLD) {
            hslSIndex = HSL_S_INDEX_LE50;
        } else if (hslL > HSL_L_THRESHOLD) {
            hslSIndex = HSL_S_INDEX_GT50;
        } else {
            hslSIndex = 0;
        }

        return hslSIndex;
    }

    static String hslSDenominatorGet(int hslS, double pMax, double pMin) {
        String hslSDenominator = "";

        if(hslS <= HSL_L_THRESHOLD) {
            hslSDenominator = String.format(DOUBLE_FORMAT_SHORT, pMax) + " + " + String.format(DOUBLE_FORMAT_SHORT, pMin);
        } else if(hslS > HSL_L_THRESHOLD) {
            hslSDenominator = "2 - " + String.format(DOUBLE_FORMAT_SHORT, pMax) + " - " + String.format(DOUBLE_FORMAT_SHORT, pMin);
        }

        return hslSDenominator;
    }

    static int hslSCalculate(int hslL, double cDelta, double cMax, double cMin) {
        double hslSDouble;

        if(hslL <= HSL_L_THRESHOLD) {
            hslSDouble = (cDelta / (cMax + cMin)) * PERCENT_CONVERSION;
        } else if(hslL > HSL_L_THRESHOLD) {
            hslSDouble = (cDelta / (2.0 - cMax - cMin)) * PERCENT_CONVERSION;
        } else {
            hslSDouble = 0.0;
        }

        return (int)Math.round(hslSDouble);
    }

    static String hslH360Get(int hslH) {
        String hslH360;

        if (hslH >= HSL_R_300) {
            hslH360 = " + 360";
        } else {
            hslH360 = "";
        }

        return hslH360;
    }

    static double hslChromaCalculate(int s, int l) {
        return (1.0 - Math.abs((2.0 * (double)l / PERCENT_CONVERSION) - 1.0)) * ((double)s / PERCENT_CONVERSION);
    }

    static String chromaCalc(int s, int l) {
        return "(1 - |(2 × " + l + "%) - 1|) × " + s  + "% =";
    }

    static double hslHPrimeCalculate(int hslH) {
        return (double)hslH / (double)HSL_MULTIPLIER;
    }

    static String hPrimeCalc(int h) {
        return h + " ÷ " + HSL_MULTIPLIER + " =";
    }

    static double xCalculate(double chroma, double hPrime) {
        return chroma * (1.0 - Math.abs((hPrime % 2)) - 1.0);
    }

    static String xCalc(double chroma, double hPrime) {
        return String.format(DOUBLE_FORMAT_SHORT, chroma) + " × (1 - |" + String.format(DOUBLE_FORMAT_SHORT, hPrime) + " % 2) - 1|) =";
    }

    public static String rgbPrimeCalc(double hPrime) {
        return "H\' = " + String.format(DOUBLE_FORMAT_SHORT, hPrime) + ", ∴ RGB Prime =";
    }

    public static String rgbPrimeValue(double rPrime, double gPrime, double bPrime) {
        return "(" + String.format(DOUBLE_FORMAT_SHORT, rPrime) + ", " + String.format(DOUBLE_FORMAT_SHORT, gPrime) + ", " + String.format(DOUBLE_FORMAT_SHORT, bPrime) + ")";
    }

    public static double mCalculate(int l, double chroma) {
        return l - (chroma / 2.0);
    }

    public static String mCalcWrite(int l, double chroma) {
        return String.format(DOUBLE_FORMAT_SHORT, l) + " - (" + String.format(DOUBLE_FORMAT_SHORT, chroma) + " / 2)";
    }
}