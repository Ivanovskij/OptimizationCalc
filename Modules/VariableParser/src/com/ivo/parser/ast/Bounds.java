package com.ivo.parser.ast;

import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Bounds {

    private List<String> bounds;

    public Bounds(List<String> bounds) {
        this.bounds = bounds;
    }

    public List<String> getBounds() {
        return bounds;
    }

    public void setBounds(List<String> bounds) {
        this.bounds = bounds;
    }

    @Override
    public String toString() {
        return "Bounds{" + "bounds=" + bounds + '}';
    }
}
