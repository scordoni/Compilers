/*
* 
* This is the AST Parser component of the compiler
* Here we take in the token stream and go through each token to create the AST
* 
*/


import java.util.*; 

public class ASTParser {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static ArrayList <String> CST = new ArrayList <String>();

    static ArrayList <Token> globalTokens= new ArrayList <Token>();

    static int position = 0;

    static int programNumber = 1;

    static int lineNumber = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int j = 0;

    static String tempString;

    
    //This method is the main parse method that calls parseProgram()
    public static void ASTParse(ArrayList<Token> tokenInput){

        //reset error flag
        ErrorFlag = 0;

        //reset tree nodes
        ASTClass.root = null;
        ASTClass.current = null;
        ASTClass.traversalResult = "";

        tempString = "";

        //puts the tokens into a gloablly accessable arraylist
        for(int i = 0; i < tokenInput.size(); i++){
            
            //reset incase of two programs getting stuck together due to usage of resetted token stream
            if((tokenInput.get(i).getKind().compareToIgnoreCase("EOP") == 0) && (i + 1 < tokenInput.size())){
                
                globalTokens.clear();
                i = i + 1;

            }//if

            //System.out.println(i);
            //System.out.println(globalTokens.size());
            //System.out.println(tokenInput.size());

            globalTokens.add(tokenInput.get(i));
            //System.out.println(globalTokens.get(i).getKind());

        }//for

        j = 0;

        theToken = globalTokens.get(j);

        //call parse program to call the parse sequence
        parseProgram();

        //System.out.println(tempString);

        //increment program number
        programNumber++;

        //clear input so that the arrays do not get added together
        globalTokens.clear();

        tokenInput.clear();

    }//parse

    //parse program that calls parse block
    public static void parseProgram(){

        //From the tree class

        ASTClass.addNode("root", "Program", theToken.getSymbol());

        //call parse block
        parseBlock();

        //match EOP token
        match("EOP");

        //move back up the AST tree
        ASTClass.moveUp();

    }//parseProgram

    //parse block that calls parse statementlist
    //creates a block in the program
    public static void parseBlock(){

        //From the tree class
        ASTClass.addNode("branch", "Block", theToken.getSymbol());

        //System.out.println(theToken.getKind());

        //match the L_Brace token
        match("L_BRACE");

        //call parse statementlsit
        parseStatementList();

        //match the R_Brace token
        match("R_BRACE");

        //move back up the CST tree
        ASTClass.moveUp();

    }//parseBlock

    //parse Statementlist that recursively calls itself or the empty string
    //creates a statement list in the program
    public static void parseStatementList(){

        //System.out.println(theToken.getKind());

        //if we have a next token that matches one of these tokens then we call parse statement and parse statement list
        if((theToken.getKind().compareToIgnoreCase("PRINT") == 0) || (theToken.getKind().compareToIgnoreCase("ID") == 0)
            || (theToken.getKind().compareToIgnoreCase("INT") == 0) || (theToken.getKind().compareToIgnoreCase("STRING") == 0)
            || (theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0) || (theToken.getKind().compareToIgnoreCase("WHILE") == 0)
            || (theToken.getKind().compareToIgnoreCase("IF") == 0) || (theToken.getKind().compareToIgnoreCase("L_BRACE") == 0) ){

            //call parse statement
            parseStatement();

            //call parse statementlist
            parseStatementList();

        }//if

        //else we do nothing, insinuating the empty string, and thus ending the recursive call to statementlist
        //and sending the program back to parse block
        else{

            //left blank Empty string

        }//else

    }//parseStatementList

    //parse statement that calls different parse methods depending on the token
    //creates a statement in a program
    public static void parseStatement(){

        //if our token matches print then we call parse print statement
        if(theToken.getKind().compareToIgnoreCase("PRINT") == 0){
        
            //call parsePrintStatement
            parsePrintStatement();
        
        }//if

        //if our token mathes ID then we call parse Assignment Statement
        else if(theToken.getKind().compareToIgnoreCase("ID") == 0){
        
            //call parseAssignmentStatement
            parseAssignmentStatement();
        
        }//else if

        //if our token mathces INT, STRING, or BOOLEAN then we call parse VarDecl
        else if((theToken.getKind().compareToIgnoreCase("INT") == 0) || (theToken.getKind().compareToIgnoreCase("STRING") == 0) || (theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0)){
        
            //call parseVarDecl
            parseVarDecl();
        
        }//else if

        //if our token matches while then we call parseWhileStatement
        else if(theToken.getKind().compareToIgnoreCase("WHILE") == 0){
        
            //call parseWhileStatement
            parseWhileStatement();
        
        }//else if

        //if our token matches IF then we call parseIfStatement
        else if(theToken.getKind().compareToIgnoreCase("IF") == 0){
        
            //call parseIfStatement
            parseIfStatement();
        
        }//else if

        //if our token mathces L_Brace then we call parseBlock
        else if(theToken.getKind().compareToIgnoreCase("L_BRACE") == 0){
        
            //call parseBlock
            parseBlock();
        
        }//else if

    }//parseStatement

    //parsePrintStatement calls ParseExpr
    //creates a print statement in the program
    public static void parsePrintStatement(){

        //From the tree class
        ASTClass.addNode("branch", "PrintStatement", theToken.getSymbol());

        //match the print token
        match("PRINT");

        //match the open paren token
        match("OPEN_PAREN");

        //call parseExpr
        parseExpr();

        //match the close paren token
        match("CLOSE_PAREN");

        //move back up the CST tree
        ASTClass.moveUp();

    }//parsePrintStatement

    //parseAssignmentStatement calls parseID and parseExpr
    //creates an assignment statement in the program
    public static void parseAssignmentStatement(){

        //From the tree class
        ASTClass.addNode("branch", "AssignmentStatement", theToken.getSymbol());

        //call parseID
        parseId();

        //match the close assignment token
        match("ASSIGNMENT");

        //call parseExpr
        parseExpr();

        //move back up the CST tree
        ASTClass.moveUp();

    }//parseAssignmentStatement

    //parseVarDecl calls parseType and parseID
    //creates a variable declaration in the program
    public static void parseVarDecl(){

        //From the tree class
        ASTClass.addNode("branch", "VarDecl", theToken.getSymbol());

        //call parseType
        parseType();

        //call parseID
        parseId();
        
        //move back up the CST tree
        ASTClass.moveUp();

    }//parseVarDecl

    //parseWhileStatement calls parseBooleanExpr and parseBlock
    //creates a while statement in the program
    public static void parseWhileStatement(){

        //From the tree class
        ASTClass.addNode("branch", "WhileStatement", theToken.getSymbol());

        //match the while token
        match("WHILE");

        //call parseBooleanExpr
        parseBooleanExpr();

        //call parseBLock
        parseBlock();

        //move back up the CST tree
        ASTClass.moveUp();

    }//parseWhileStatement

    //parseIfStatement calls parseBooleanExor and parseBlock
    //creates an if statement in the program
    public static void parseIfStatement(){

        //From the tree class
        ASTClass.addNode("branch", "IfStatement", theToken.getSymbol());

        //match the if token
        match("IF");

        //call parseBooleanExpr
        parseBooleanExpr();

        //call parseBlock
        parseBlock();

        //move back up the AST tree
        ASTClass.moveUp();

    }//parseIfStatement

    //parseExpr calls different parse statements depending on the token
    //creates an expression statment in the program
    public static void parseExpr(){

        //if our token matches DIGIT then we call parseIntExpr
        if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
        
            //call parseIntExpr
            parseIntExpr();
        
        }//if

        //if out token matches Open Quote then we call parseStringExpr
        else if(theToken.getKind().compareToIgnoreCase("OPEN_QUOTE") == 0){
        
            //call parseStringExpr
            parseStringExpr();
        
        }//else if

        //if our token matches Open Paren then we call parseBoolean Expr
        else if( (theToken.getKind().compareToIgnoreCase("OPEN_PAREN") == 0) || (theToken.getKind().compareToIgnoreCase("TRUE") == 0) || (theToken.getKind().compareToIgnoreCase("FALSE") == 0)){
        
            //call parseBooleanExpr
            parseBooleanExpr();
        
        }//else if

        //if our tken matches ID then we call parseID
        else if(theToken.getKind().compareToIgnoreCase("ID") == 0){
        
            //call parseID
            parseId();
        
        }//else if

    }//parseExpr

    //parseIntExpr calls different parse methods depending on the token
    //creates an integer expression in the program
    public static void parseIntExpr(){

        //if the next token matches ADDITION then we call parseDigit, parseIntop, and parseExpr
        if((globalTokens.get(j + 1).getKind().compareToIgnoreCase("ADDITION")) == 0){
        
            //call parseDigit
            parseDigit();

            //call parseIntop
            parseIntop();
    
            //call parseExpr
            parseExpr();
        
        }//else if

        //else if then token matches DIGIT then we call parseDigit
        else if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
        
            //call parseDigit
            parseDigit();
        
        }//else if

    }//parseIntExpr

    //parseStringExpr calls parseCharlist
    //creates a string expression for the program
    public static void parseStringExpr(){

        //match the open quote token
        match("OPEN_QUOTE");

        //call parseCharlist
        parseCharList();

        //match the close quote token
        match("CLOSE_QUOTE");


        
    }//parseStringExpr

    //parseBooleanExpr calls different parse methods depending on the token
    public static void parseBooleanExpr(){

        //if the token matches open paren then we call parseExpr, parseBoolop, and parseExpr
        if(theToken.getKind().compareToIgnoreCase("OPEN_PAREN") == 0){
        
            //match the open paren token
            match("OPEN_PAREN");

            //call parseExpr
            parseExpr();

            //call parseBoolop
            parseBoolop();

            //call parsExpr
            parseExpr();

            //match the close paren token
            match("CLOSE_PAREN");
        
        }//if

        //else if the token matched true or false then we call parseBoolVal
        else if((theToken.getKind().compareToIgnoreCase("TRUE") == 0) || (theToken.getKind().compareToIgnoreCase("FALSE") == 0) ){
        
            //call parseBoolval
            parseBoolval();
        
        }//else if

    }//parseBooleanExpr

    //ParseId calls match to match an id
    //creates an ID
    public static void parseId(){

        //match the ID token
        match("ID");
        
    }//parseId

    //parseCharlist calls different methods depending on the tokens
    //creates a charlist for the program
    public static void parseCharList(){

        //if the token matches char then we call parseChar, and parseCharlist recursivly
        if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
           
            parseChar();

            parseCharList();
        
        }//if

        //else if the token matches space then we call parseSpace and parseCharlist recursively
        else if(theToken.getKind().compareToIgnoreCase(" ") == 0){

            parseSpace();

            parseCharList();

        }//else if

        //else we leave this blank for the empty string and to break out of the recursive call
        else{

            //leave blank for empty string

        }//else

    }//parseCharList

    //parseType calls different things depending on the tokens
    //creates a type for an ID in a program
    public static void parseType(){

        //if the token matches INT then we match the INT token
        if(theToken.getKind().compareToIgnoreCase("INT") == 0){
 
            //match the INT token
            match("INT");
        
        }//if

        //if the token matches STIRNG then we match the STRING token
        else if(theToken.getKind().compareToIgnoreCase("STRING") == 0){
 
            //match the STRING token
            match("STRING"); 
        
        }//else if

        //else if the token matches BOOLEAN then we match the BOOLEAN token
        else if(theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0){
 
            //match the BOOLEAN token
            match("BOOLEAN");
        
        }//else if

    }//parsetype

    //parseChar matches the char token
    public static void parseChar(){

        //a - z

        if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
 

            tempString = tempString + theToken.getSymbol();

            match("CHAR");


        
        }//if


    }//parsechar

    //parse space matches the space token
    public static void parseSpace(){

        //space character
        if(theToken.getKind().compareToIgnoreCase(" ") == 0){
 
            match(" ");
        
        }//if

    }//parsespace

    //parse digit matches the DIGIT token
    public static void parseDigit(){

        // 0 - 9

        if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
 
            match("DIGIT");
        
        }//if

    }//parsedigit

    //parse Boolop matches the boolop token for either == or !=
    public static void parseBoolop(){

        //== or !=

        if(theToken.getKind().compareToIgnoreCase("EQUALITY") == 0){
 
            match("EQUALITY");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("NON_EQUALITY") == 0){
 
            match("NON_EQUALITY");
        
        }//else if

    }//parseboolop

    //parseBoolval matches the Boolval token for either True or False
    public static void parseBoolval(){

        //true or flase

        if(theToken.getKind().compareToIgnoreCase("TRUE") == 0){
 
            match("TRUE");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("FALSE") == 0){
 
            match("FALSE");
        
        }//else if

    }//parseboolval

    //parseIntop matches the Intop token for ADDITION
    public static void parseIntop(){

        //+

        if(theToken.getKind().compareToIgnoreCase("ADDITION") == 0){
 
            match("ADDITION");
        
        }//if

    }//parseintop

    //Instead of placeing an "add ast node" statement throughout the parser I realized I followed the CST
    //class with the match statement and upon calling match we add the necessary nodes for the AST

    
    //match takes in a string to be matched to the token from the token stream output from lex
    public static void match(String matchchar){
        
        //if the token is matched then we add a node to the CST and increment the token pointer to look at the next token
        if (theToken.getKind().compareToIgnoreCase(matchchar) == 0){

            //if we havent reached the size of global tokens then we add a leaf node and increment the token pointer
            if ( j != globalTokens.size() - 1){

                if( (theToken.getSymbol().compareToIgnoreCase("{") == 0) || (theToken.getSymbol().compareToIgnoreCase("}") == 0) || 
                (theToken.getSymbol().compareToIgnoreCase("(") == 0) || (theToken.getSymbol().compareToIgnoreCase(")") == 0) || 
                (theToken.getSymbol().compareToIgnoreCase("print") == 0) || (theToken.getSymbol().compareToIgnoreCase("=") == 0)|| 
                (theToken.getSymbol().compareToIgnoreCase("\"") == 0) ){

            
                }//if

                else{

                ASTClass.addNode("leaf", theToken.getKind(), theToken.getSymbol());

                }//else
                
                j = j + 1;

                theToken = globalTokens.get(j);

            }//if

            //else we just add a node to the AST tree
            else{

                //From the tree class
                ASTClass.addNode("leaf", theToken.getKind(), theToken.getSymbol());

            }//else

        }//if

        //else we have an error and create a parse error
        else{

            //System.out.println("Error: unexpected token. Expected " + matchchar + " but found " + theToken.getKind());

            ErrorFlag = 1;
                        
        }//else

    }//match

}//ASTParser