package com.ivo;

import com.ivo.parser.Lexer;
import com.ivo.parser.Parser;
import com.ivo.parser.Token;
import com.ivo.parser.ast.Expression;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class Main {

    public static void main(String[] args) {
        String in = "a + b + (c * d";

        Lexer lexer = new Lexer(in);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        double a = 1;
        double b = 2;
        double c = 3;
        double d = 4;
        
        List<Expression> expressions = new Parser(tokens, 
                a, b, c, d).parse();
        for (Expression expr : expressions) {
            System.out.println(expr + " = " + expr.eval());
        }
    }
    
    
}
