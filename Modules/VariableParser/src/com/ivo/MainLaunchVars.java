package com.ivo;


/**
 *
 * @author IOAdmin
 */
public class MainLaunchVars {

    public static void main(String[] args) {
       /* String function = "b1 = 1"
                + " b2 = 2 "
                + "1.5 * pow(b1, 2) * exp(1 - pow(b1, 2)) - 20.25 * pow(b1 - b2, 2)";
        String func2 = "x1 + x2 + x2";
        String bound = "x1 + x2 >= 6";*/

        /*Lexer lexer = new Lexer(func2);
        List<Token> tokens = lexer.parse();
        for (Token token : tokens) {
            System.out.println(token);
        }*/

        
//        List<Expression> result = new Parser(tokens, x1, x2).parse();
//        for (Expression expr : result) {
//            System.out.println(expr.toString() + " = " + expr.eval());
//        }

//        double result = new Parser(func2, x1, x2).parse();
//        System.out.println(result);

        System.out.println();
        System.out.println("====================================");
        System.out.println();
        
        String task1 = "x1 = 200";
        double x1 = 199;
        double x2 = 3;
        double x3 = 3;

        double result = new ParserExecute(task1, x1, x2, x3).execute();
        System.out.println(result);
    }
    
    
}
