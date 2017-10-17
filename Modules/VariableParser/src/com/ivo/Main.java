package com.ivo;

import com.ivo.parser.Lexer;
import com.ivo.parser.Parser;
import com.ivo.parser.Token;
import com.ivo.parser.ast.Expression;
import com.ivo.parser.ast.Statement;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Main {

    public static void main(String[] args) {
        String in = "var1 + var2";

        Lexer lexer = new Lexer(in);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        double a = 1;
        double b = 2;
        double c = 3;
        double d = 4;
        
        Statement result = new Parser(tokens, 
                a, b).parse();
        System.out.println(result.toString());
    }
    
    
}
