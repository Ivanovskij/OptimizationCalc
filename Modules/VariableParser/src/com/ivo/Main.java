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
        String in = "1.5 * pow(b1, 2) * exp(1 - pow(b1, 2)) - 20.25 * pow(b1 - b2, 2)";
        
        // парсить число с минусом пока что не поддерживает
        
        Lexer lexer = new Lexer(in);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        double b1 = 1;
        double b2 = 2;
        
        List<Expression> result = new Parser(tokens, b1, b2).parse();
        for (Expression expr : result) {
            System.out.println(expr.toString() + " = " + expr.eval());
        }
        
    }
    
    
}
