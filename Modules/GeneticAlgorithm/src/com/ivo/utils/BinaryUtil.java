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
        result = RoundUtil.round(result, 3);
        return result;
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
    
}
