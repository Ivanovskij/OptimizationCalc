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
        String in = "21*x1 + 18*x2 + 16*x3 + 17.5*x4";

        Lexer lexer = new Lexer(in);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        double x1 = 1;
        double x2 = 2;
        double x3 = 3;
        double x4 = 4;
        
        List<Expression> expressions = new Parser(tokens, 
                x1, x2, x3, x4).parse();
        for (Expression expr : expressions) {
            System.out.println(expr + " = " + expr.eval());
        }
    }
    
    
}
