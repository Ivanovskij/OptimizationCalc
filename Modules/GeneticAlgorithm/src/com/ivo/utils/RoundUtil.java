/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public static final float round(final float number, final int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++) {
            pow *= 10;
        }
        double tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }
    
}
