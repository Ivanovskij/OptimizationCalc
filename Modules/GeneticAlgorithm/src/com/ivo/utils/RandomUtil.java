package com.ivo.utils;

import java.util.Random;

/**
 *
 * @author IOAdmin
 */
public class RandomUtil {
    
    public static int getRandomRangeInt(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt(max + 1) + min;
    }
    
    public static double getRandomRangeDouble(double min, double max) {
        return Math.random() * max + min;
    }

}
