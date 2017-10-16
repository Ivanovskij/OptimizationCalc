package com.ivo;

import com.ivo.parser.Lexer;
import com.ivo.parser.Token;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Main {

    public static void main(String[] args) {
        String in = "(x1 + 3) >= 22";
        
        Lexer lexer = new Lexer(in);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
    
    
}
