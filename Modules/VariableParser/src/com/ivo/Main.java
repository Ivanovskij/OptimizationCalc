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
        String function = "1.5 * pow(b1, 2) * exp(1 - pow(b1, 2)) - 20.25 * pow(b1 - b2, 2)";
        //String bound = "x1 + x2 >= 6";

        Lexer lexer = new Lexer(function);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        double x1 = 1;
        double x2 = 2;
        
//        List<Expression> result = new Parser(tokens, x1, x2).parse();
//        for (Expression expr : result) {
//            System.out.println(expr.toString() + " = " + expr.eval());
//        }

        double result = new Parser(function, x1, x2).parse();
        System.out.println(result);
    }
    
    
}
