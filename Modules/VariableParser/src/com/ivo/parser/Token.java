package com.ivo.parser;

/**
 *
 * @author IOAdmin
 */
public class Token {

    private TokenType tokenType;
    private String text;

    public Token() {
    }
    
    public Token(TokenType tokenType, String text) {
        this.tokenType = tokenType;
        this.text = text;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenType=" + tokenType + ", text=" + text + '}';
    }
}
