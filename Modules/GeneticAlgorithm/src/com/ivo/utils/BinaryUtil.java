package com.ivo.utils;

import java.math.BigInteger;

/**
 *
 * @author IOAdmin
 */
public class BinaryUtil {

    /**
     * @param value - binary string to double
     * @return double value
     */
    public static double binaryToNumber(String value) {
        double result = Double.longBitsToDouble(new BigInteger(value, 2).longValue());
        result = RoundUtil.round(result, 9);
        return result;
    }
    
    /**
     * @param value - double to binary string
     * @return string value
     */
    public static String doubleToBinary(double value) {
        String binary = Long.toBinaryString(Double.doubleToRawLongBits(value));
        if (binary.length() < 64) {
            String tmp = binary;
            // add to start zerows
            for (int i = 0; i < 64 - binary.length(); i++) {
                binary += "0";
            }
            binary += tmp;
        }
        return binary;
    }
    
    /**
     *
     * @param values - binary arguments
     * @return - double arguments
     */
    public static Double[] binaryArrToNumberArr(String[] values) {
        if (values == null) {
            throw new RuntimeException("Invalid in arg values");
        }
        
        Double[] args = new Double[values.length];
        for (int i = 0; i < values.length; i++) {
            args[i] = binaryToNumber(values[i]);
        }
        
        return args;
    }
    
    /**
     *
     * @param values - double arguments
     * @return - binary string arguments
     */
    public static String[] doubleArrToBinaryArr(Double[] values) {
        if (values == null) {
            throw new RuntimeException("Invalid in arg values");
        }
        
        String[] args = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            args[i] = doubleToBinary(values[i]);
        }
        
        return args;
    }
    
}
