package com.ivo.parser.ast;

/**
 *
 * @author IOAdmin
 */
public class Function {
    
    private String text;

    public Function(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Function{" + "text=" + text + '}';
    }
}
