/*
* 
* This is the Parser component of the compiler
* Here we take in the token stream and go through each token to parse
* and create the CST
* 
*/


import java.util.*; 

public class Parser {

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

    
    //This method is the main parse method that calls parseProgram()
    public static void Parse(ArrayList<Token> tokenInput){

        //reset error flag
        ErrorFlag = 0;

        //reset tree nodes
        CSTClass.root = null;
        CSTClass.current = null;
        CSTClass.traversalResult = "";

        //print out space for formating
        //System.out.println(" ");

        //System.out.println("Parsing Program: " + programNumber);

        System.out.println("PARSER: parse()");

        //puts the tokens into a gloablly accessable arraylist
        for(int i = 0; i < tokenInput.size(); i++){
            
            globalTokens.add(tokenInput.get(i));
            //System.out.println(globalTokens.get(i).getKind());

        }//for

        j = 0;

        theToken = globalTokens.get(j);

        //System.out.println(globalTokens.size());

        //call parse program to call the parse sequence
        parseProgram();

        //if we have no errors then we have a completed parse
        if (ErrorFlag == 0){

            System.out.println("PARSER: Parse completed successfully");

        }//if

        //otherwise we have a parse error
        else{

            System.out.println("ERROR IN PARSER: Parse Failed");

        }//else

        //increment program number
        programNumber++;

        //clear input so that the arrays do not get added together
        globalTokens.clear();

        tokenInput.clear();

    }//parse

    //parse program that calls parse block
    public static void parseProgram(){

        System.out.println("PARSER: parseProgram()");

        //System.out.println(theToken.getKind());

        //From the tree class
        CSTClass.addNode("root", "Program");

        //call parse block
        parseBlock();

        //match EOP token
        match("EOP");

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseProgram

    //parse block that calls parse statementlist
    //creates a block in the program
    public static void parseBlock(){

        System.out.println("PARSER: parseBlock()");
        
        //System.out.println("Block " + theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "Block");

        //match the L_Brace token
        match("L_BRACE");

        //call parse statementlsit
        parseStatementList();

        //match the R_Brace token
        match("R_BRACE");

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseBlock

    //parse Statementlist that recursively calls itself or the empty string
    //creates a statement list in the program
    public static void parseStatementList(){

        System.out.println("PARSER: parseStatementList()");

        //System.out.println("StatementList " +  theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "StatementList");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseStatementList

    //parse statement that calls different parse methods depending on the token
    //creates a statement in a program
    public static void parseStatement(){

        System.out.println("PARSER: parseStatement()");

        //System.out.println("Statement " +  theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "Statement");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseStatement

    //parsePrintStatement calls ParseExpr
    //creates a print statement in the program
    public static void parsePrintStatement(){

        System.out.println("PARSER: parsePrintStatement()");

        //From the tree class
        CSTClass.addNode("branch", "PrintStatement");

        //match the print token
        match("PRINT");

        //match the open paren token
        match("OPEN_PAREN");

        //call parseExpr
        parseExpr();

        //match the close paren token
        match("CLOSE_PAREN");

        //move back up the CST tree
        CSTClass.moveUp();

    }//parsePrintStatement

    //parseAssignmentStatement calls parseID and parseExpr
    //creates an assignment statement in the program
    public static void parseAssignmentStatement(){

        System.out.println("PARSER: parseAssignmentStatement()");

        //From the tree class
        CSTClass.addNode("branch", "AssignmentStatement");

        //call parseID
        parseId();

        //match the close assignment token
        match("ASSIGNMENT");

        //call parseExpr
        parseExpr();

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseAssignmentStatement

    //parseVarDecl calls parseType and parseID
    //creates a variable declaration in the program
    public static void parseVarDecl(){

        System.out.println("PARSER: parseVarDecl()");

        //From the tree class
        CSTClass.addNode("branch", "VarDecl");

        //call parseType
        parseType();

        //call parseID
        parseId();
        
        //move back up the CST tree
        CSTClass.moveUp();

    }//parseVarDecl

    //parseWhileStatement calls parseBooleanExpr and parseBlock
    //creates a while statement in the program
    public static void parseWhileStatement(){

        System.out.println("PARSER: parseWhileStatement()");

        //From the tree class
        CSTClass.addNode("branch", "WhileStatement");

        //match the while token
        match("WHILE");

        //call parseBooleanExpr
        parseBooleanExpr();

        //call parseBLock
        parseBlock();

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseWhileStatement

    //parseIfStatement calls parseBooleanExor and parseBlock
    //creates an if statement in the program
    public static void parseIfStatement(){

        System.out.println("PARSER: parseIfStatement()");

        //From the tree class
        CSTClass.addNode("branch", "IfStatement");

        //match the if token
        match("IF");

        //call parseBooleanExpr
        parseBooleanExpr();

        //call parseBlock
        parseBlock();

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseIfStatement

    //parseExpr calls different parse statements depending on the token
    //creates an expression statment in the program
    public static void parseExpr(){

        System.out.println("PARSER: parseExpr()");

        //From the tree class
        CSTClass.addNode("branch", "Expr");

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
        else if(theToken.getKind().compareToIgnoreCase("OPEN_PAREN") == 0){
        
            //call parseBooleanExpr
            parseBooleanExpr();
        
        }//else if

        //if our tken matches ID then we call parseID
        else if(theToken.getKind().compareToIgnoreCase("ID") == 0){
        
            //call parseID
            parseId();
        
        }//else if

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseExpr

    //parseIntExpr calls different parse methods depending on the token
    //creates an integer expression in the program
    public static void parseIntExpr(){

        System.out.println("PARSER: parseIntExpr()");

        //From the tree class
        CSTClass.addNode("branch", "IntExpr");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseIntExpr

    //parseStringExpr calls parseCharlist
    //creates a string expression for the program
    public static void parseStringExpr(){

        System.out.println("PARSER: parseStringExpr()");

        //From the tree class
        CSTClass.addNode("branch", "StringExpr");

        //match the open quote token
        match("OPEN_QUOTE");

        //call parseCharlist
        parseCharList();

        //match the close quote token
        match("CLOSE_QUOTE");
        
        //move back up the CST tree
        CSTClass.moveUp();

    }//parseStringExpr

    //parseBooleanExpr calls different parse methods depending on the token
    public static void parseBooleanExpr(){

        System.out.println("PARSER: parseBooleanExpr()");

        //From the tree class
        CSTClass.addNode("branch", "BooleanExpr");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseBooleanExpr

    //ParseId calls match to match an id
    //creates an ID
    public static void parseId(){

        System.out.println("PARSER: parseId()");

        //From the tree class
        CSTClass.addNode("branch", "Id");

        //match the ID token
        match("ID");

        //move back up the CST tree
        CSTClass.moveUp();
        
    }//parseId

    //parseCharlist calls different methods depending on the tokens
    //creates a charlist for the program
    public static void parseCharList(){

        System.out.println("PARSER: parseCharList()");

        //From the tree class
        CSTClass.addNode("branch", "CharList");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parseCharList

    //parseType calls different things depending on the tokens
    //creates a type for an ID in a program
    public static void parseType(){

        System.out.println("PARSER: parseType()");

        //From the tree class
        CSTClass.addNode("branch", "Type");

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

        //move back up the CST tree
        CSTClass.moveUp();

    }//parsetype

    //parseChar matches the char token
    public static void parseChar(){

        //a - z

        System.out.println("PARSER: parseChar()");

        if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
 
            match("CHAR");
        
        }//if


    }//parsechar

    //parse space matches the space token
    public static void parseSpace(){

        System.out.println("PARSER: parseSpace()");

        //space character
        if(theToken.getKind().compareToIgnoreCase(" ") == 0){
 
            match(" ");
        
        }//if

    }//parsespace

    //parse digit matches the DIGIT token
    public static void parseDigit(){

        // 0 - 9

        System.out.println("PARSER: parseDigit()");

        if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
 
            match("DIGIT");
        
        }//if

    }//parsedigit

    //parse Boolop matches the boolop token for either == or !=
    public static void parseBoolop(){

        //== or !=

        System.out.println("PARSER: parseBoolop()");

        if(theToken.getKind().compareToIgnoreCase("EQUALITY") == 0){
 
            match("EQUALITY");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("NON_EQUALITY") == 0){
 
            match("NON_EQUALITY");
        
        }//else if

    }//parseboolop

    //parseBoolval matches the Boolval token for either True or False
    public static void parseBoolval(){

        System.out.println("PARSER: parseBoolval()");

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

        System.out.println("PARSER: parseIntop()");

        //+

        if(theToken.getKind().compareToIgnoreCase("ADDITION") == 0){
 
            match("ADDITION");
        
        }//if

    }//parseintop

    //match takes in a string to be matched to the token from the token stream output from lex
    public static void match(String matchchar){

        //System.out.println("Match in" +  theToken.getKind());

        //System.out.println("j in " + j);
        
        //if the token is matched then we add a node to the CST and increment the token pointer to look at the next token
        if (theToken.getKind().compareToIgnoreCase(matchchar) == 0){

            //if we havent reached the sixe of global tokens then we add a leaf node and increment the token pointer
            if ( j != globalTokens.size() - 1){

                //From the tree class
                CSTClass.addNode("leaf", theToken.getKind());

                j = j + 1;

                theToken = globalTokens.get(j);

                //System.out.println("match out" +  theToken.getKind());

                //System.out.println("j out " + j);

            }//if

            //else we just add a node to the CST tree
            else{
                //From the tree class
                CSTClass.addNode("leaf", theToken.getKind());
            }//else

        }//if

        //else we have an error and create a parse error
        else{

            System.out.println("Error: unexpected token. Expected " + matchchar + " but found " + theToken.getKind());

            ErrorFlag = 1;
                        
        }//else

    }//match

}//Parser