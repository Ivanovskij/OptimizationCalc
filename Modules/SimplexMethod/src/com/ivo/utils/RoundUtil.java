package com.ivo.utils;

/**
 *
 * @author IOAdmin
 */
public class RoundUtil {

    public static final double round(final double number, final int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++) {
            pow *= 10;
        }
        double tmp = number * pow;
        return (double) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }
    
}
