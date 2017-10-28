package com.ivo;

import com.ivo.parser.Lexer;
import com.ivo.parser.Parser;
import com.ivo.parser.Token;
import com.ivo.parser.ast.Statement;
import com.ivo.parser.util.CacheStringFunctions;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class ParserExecute {

    private final String inStr;
    private List<Token> tokens;
    
    private Parser parser;

    public ParserExecute(String inStr) {
        this.inStr = inStr;
    }
    
    public Statement execute() {
        if (!CacheStringFunctions.isExists(inStr)) {
            tokens = new Lexer(inStr).parse();
            CacheStringFunctions.add(inStr, tokens);
        } else {
            tokens = CacheStringFunctions.get(inStr);
        }
        
        parser = new Parser(tokens);
        
        Statement result = parser.parse();
        
        return result;
    }
    
}
