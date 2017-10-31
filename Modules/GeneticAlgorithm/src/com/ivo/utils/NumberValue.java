package com.ivo.utils;

/**
 *
 * @author IOAdmin
 */
public class NumberValue implements Value {

    private final String value;

    public NumberValue(String value) {
        this.value = value;
    }
    
    @Override
    public double asDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public int asInteger() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
