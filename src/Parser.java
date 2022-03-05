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

    static int commentFlag = 0;

    static int stringFlag = 0;

    static int stringWordFlag = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int intFlag = 0;

    static int braceFlag = 0;

    static int parenFlag = 0;

    static int whileFlag = 0;

    static int printFlag = 0;

    static int booleanFlag = 0;

    static int ifFlag = 0;

    static int trueFlag = 0;

    static int falseFlag = 0;

    static int j = 0;

    


    //This method pushes each letter of the array into the stack
    public static ArrayList<String> Parse(ArrayList<Token> tokenInput){

        //reset error flag
        ErrorFlag = 0;

        //print out space for formating
        System.out.println(" ");

        System.out.println("Parsing Program: " + programNumber);

        System.out.println("PARSER: parse()");

        for(int i = 0; i < tokenInput.size(); i++){
            
            globalTokens.add(tokenInput.get(i));

        }//for

        j = 0;

        theToken = globalTokens.get(j);

        //System.out.println(globalTokens.size());

        parseProgram();

        if (ErrorFlag == 0){

            System.out.println("PARSER: Parse completed successfully");

        }//if

        else{

            System.out.println("ERROR IN PARSER: Parse Failed");

        }//else

        //increment program number
        programNumber++;

        //clear input so that the arrays do not get added together
        globalTokens.clear();

        tokenInput.clear();

       
        //return cst
        return CST;

    }//parse

    public static void parseProgram(){

        System.out.println("PARSER: parseProgram()");

        //System.out.println(theToken.getKind());

        //From the tree class
        CSTClass.addNode("root", "Program");

        parseBlock();

        match("EOP");

        CSTClass.moveUp();

    }//parseProgram

    public static void parseBlock(){

        System.out.println("PARSER: parseBlock()");
        
        //System.out.println("Block " + theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "Block");

        match("L_BRACE");

        //System.out.println("Block " + theToken.getKind());

        parseStatementList();

        //System.out.println("Block " +  theToken.getKind());

        match("R_BRACE");

        CSTClass.moveUp();

    }//parseBlock

    public static void parseStatementList(){

        System.out.println("PARSER: parseStatementList()");

        //System.out.println("StatementList " +  theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "StatementList");

        if((theToken.getKind().compareToIgnoreCase("PRINT") == 0) || (theToken.getKind().compareToIgnoreCase("CHAR") == 0)
            || (theToken.getKind().compareToIgnoreCase("INT") == 0) || (theToken.getKind().compareToIgnoreCase("STRING") == 0)
            || (theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0) || (theToken.getKind().compareToIgnoreCase("WHILE") == 0)
            || (theToken.getKind().compareToIgnoreCase("IF") == 0) || (theToken.getKind().compareToIgnoreCase("L_BRACE") == 0) ){

            parseStatement();

            parseStatementList();

        }//if

        else{

            //left blank Empty string

        }//else

        CSTClass.moveUp();

    }//parseStatementList

    public static void parseStatement(){

        System.out.println("PARSER: parseStatement()");

        //System.out.println("Statement " +  theToken.getKind());

        //From the tree class
        CSTClass.addNode("branch", "Statement");

        if(theToken.getKind().compareToIgnoreCase("PRINT") == 0){
        
            parsePrintStatement();
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
        
            parseAssignmentStatement();
        
        }//else if

        else if((theToken.getKind().compareToIgnoreCase("INT") == 0) || (theToken.getKind().compareToIgnoreCase("STRING") == 0) || (theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0)){
        
            parseVarDecl();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("WHILE") == 0){
        
            parseWhileStatement();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("IF") == 0){
        
            parseIfStatement();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("L_BRACE") == 0){
        
            parseBlock();
        
        }//else if

        CSTClass.moveUp();

    }//parseStatement

    public static void parsePrintStatement(){

        System.out.println("PARSER: parsePrintStatement()");

        //From the tree class
        CSTClass.addNode("branch", "PrintStatement");

        match("PRINT");

        match("OPEN_PAREN");

        parseExpr();

        match("CLOSE_PAREN");

        CSTClass.moveUp();

    }//parsePrintStatement

    public static void parseAssignmentStatement(){

        System.out.println("PARSER: parseAssignmentStatement()");

        //From the tree class
        CSTClass.addNode("branch", "AssignmentStatement");

        parseId();

        match("ASSIGNMENT");

        parseExpr();

        CSTClass.moveUp();

    }//parseAssignmentStatement

    public static void parseVarDecl(){

        System.out.println("PARSER: parseVarDecl()");

        //From the tree class
        CSTClass.addNode("branch", "VarDecl");

        parseType();

        parseId();
        
        CSTClass.moveUp();

    }//parseVarDecl

    public static void parseWhileStatement(){

        System.out.println("PARSER: parseWhileStatement()");

        //From the tree class
        CSTClass.addNode("branch", "WhileStatement");

        match("WHILE");

        parseBooleanExpr();

        parseBlock();

        CSTClass.moveUp();

    }//parseWhileStatement

    public static void parseIfStatement(){

        System.out.println("PARSER: parseIfStatement()");

        //From the tree class
        CSTClass.addNode("branch", "IfStatement");

        match("IF");

        parseBooleanExpr();

        parseBlock();

        CSTClass.moveUp();

    }//parseIfStatement

    public static void parseExpr(){

        System.out.println("PARSER: parseExpr()");

        //From the tree class
        CSTClass.addNode("branch", "Expr");

        if(theToken.getKind().compareToIgnoreCase(" ") == 0){
        
            parseIntExpr();
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("\"") == 0){
        
            parseStringExpr();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase(" ") == 0){
        
            parseBooleanExpr();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
        
            parseId();
        
        }//else if

        CSTClass.moveUp();

    }//parseExpr

    public static void parseIntExpr(){

        System.out.println("PARSER: parseIntExpr()");

        //From the tree class
        CSTClass.addNode("branch", "IntExpr");

        //"DIGIT" "INTOP"
        if(theToken.getKind().compareToIgnoreCase(" ") == 0){
        
            parsedigit();

            parseintop();
    
            parseExpr();
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
        
            parsedigit();
        
        }//else if

        CSTClass.moveUp();

    }//parseIntExpr

    public static void parseStringExpr(){

        System.out.println("PARSER: parseStringExpr()");

        //From the tree class
        CSTClass.addNode("branch", "StringExpr");

        match("OPEN_QUOTE");

        parseCharList();

        match("CLOSE_QUOTE");
        
        CSTClass.moveUp();

    }//parseStringExpr

    public static void parseBooleanExpr(){

        System.out.println("PARSER: parseBooleanExpr()");

        //From the tree class
        CSTClass.addNode("branch", "BooleanExpr");

        if(theToken.getKind().compareToIgnoreCase("OPEN_PAREN") == 0){
        
            match("OPEN_PAREN");

            parseExpr();

            parseboolop();

            parseExpr();

            match("CLOSE_PAREN");
        
        }//if

        else if((theToken.getKind().compareToIgnoreCase("TRUE") == 0) || (theToken.getKind().compareToIgnoreCase("FALSE") == 0) ){
        
            parseboolval();
        
        }//else if

        CSTClass.moveUp();

    }//parseBooleanExpr

    public static void parseId(){

        System.out.println("PARSER: parseId()");

        //From the tree class
        CSTClass.addNode("branch", "Id");

        parsechar();

        CSTClass.moveUp();
        
    }//parseId

    public static void parseCharList(){

        System.out.println("PARSER: parseCharList()");

        //From the tree class
        CSTClass.addNode("branch", "CharList");

        if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
           
            parsechar();

            parseCharList();
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase(" ") == 0){

            parsespace();

            parseCharList();

        }//else if

        else{

            //leave blank for empty string

        }//else

        CSTClass.moveUp();

    }//parseCharList

    public static void parseType(){

        System.out.println("PARSER: parseType()");

        //From the tree class
        CSTClass.addNode("branch", "Type");

        if(theToken.getKind().compareToIgnoreCase("INT") == 0){
 
            match("INT");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("STRING") == 0){
 
            match("STRING"); 
        
        }//else if

        else if(theToken.getKind().compareToIgnoreCase("BOOLEAN") == 0){
 
            match("BOOLEAN");
        
        }//else if

        CSTClass.moveUp();

    }//parsetype

    public static void parsechar(){

        //a - z

        if(theToken.getKind().compareToIgnoreCase("CHAR") == 0){
 
            match("CHAR");
        
        }//if


    }//parsechar

    public static void parsespace(){

        //space character
        if(theToken.getKind().compareToIgnoreCase(" ") == 0){
 
            match(" ");
        
        }//if

    }//parsespace

    public static void parsedigit(){

        // 0 - 9

        if(theToken.getKind().compareToIgnoreCase("DIGIT") == 0){
 
            match("DIGIT");
        
        }//if

    }//parsedigit

    public static void parseboolop(){

        //== or !=

        if(theToken.getKind().compareToIgnoreCase("==") == 0){
 
            match("==");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("!=") == 0){
 
            match("!=");
        
        }//else if

    }//parseboolop

    public static void parseboolval(){

        //true or flase

        if(theToken.getKind().compareToIgnoreCase("TRUE") == 0){
 
            match("TRUE");
        
        }//if

        else if(theToken.getKind().compareToIgnoreCase("FALSE") == 0){
 
            match("FALSE");
        
        }//else if

    }//parseboolval

    public static void parseintop(){

        //+

        if(theToken.getKind().compareToIgnoreCase("+") == 0){
 
            match("+");
        
        }//if

    }//parseintop

    public static void match(String matchchar){

        //System.out.println("Match in" +  theToken.getKind());

        //System.out.println("j in " + j);
        
        if (theToken.getKind().compareToIgnoreCase(matchchar) == 0){

            if ( j != globalTokens.size() - 1){

                //From the tree class
                CSTClass.addNode("leaf", theToken.getKind());

                j = j + 1;

                theToken = globalTokens.get(j);

                //System.out.println("match out" +  theToken.getKind());

                //System.out.println("j out " + j);

            }//if

            else{
                //From the tree class
                CSTClass.addNode("leaf", theToken.getKind());
            }//else

        }//if

        else{

            System.out.println("Error: unexpected token. Expected " + matchchar + " but found " + theToken.getKind());

            ErrorFlag = 1;
                        
        }//else

    }//match

}//Parser