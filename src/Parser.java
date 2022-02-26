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


    //This method pushes each letter of the array into the stack
    public static ArrayList<String> Parse(ArrayList<Token> tokenInput){

        //print out space for formating
        System.out.println(" ");

        System.out.println("Parsing Program: " + programNumber);

        programNumber++;

        return CST;

    }//parse

    public static void parseProgram(){

        //From the tree class
        //addNode(root,Program);

        parseBlock();

        //match(EOP);

    }//parseProgram

    public static void parseBlock(){

        //match(openBrace);
        parseStatementList();
        //match(closeBrace);

    }//parseBlock

    public static void parseStatementList(){

        //if(tokenInput[i].getKind().com)
        parseStatement();

        parseStatementList();

        //Empty string


    }//parseStatementList

    public static void parseStatement(){

        parsePrintStatement();

        parseAssignmentStatement();

        parseVarDecl();

        parseWhileStatement();

        parseIfStatement();

        parseBlock();

        

    }//parseStatement

    public static void parsePrintStatement(){

        //match(keyword print);
        //match(open paren);
        parseExpr();
        //match(close paren);

    }//parsePrintStatement

    public static void parseAssignmentStatement(){

        parseId();

        //match(assignment)

        parseExpr();

        

    }//parseAssignmentStatement

    public static void parseVarDecl(){

        parsetype();

        parseId();
        

    }//parseVarDecl

    public static void parseWhileStatement(){

        //match(while);

        parseBooleanExpr();

        parseBlock();

        

    }//parseWhileStatement

    public static void parseIfStatement(){

        //match(if);

        parseBooleanExpr();

        parseBlock();

    }//parseIfStatement

    public static void parseExpr(){

        parseIntExpr();

        parseStringExpr();

        parseBooleanExpr();

        parseId();
        

    }//parseExpr

    public static void parseIntExpr(){

        parsedigit();

        parseintop();

        parseExpr();

        //or

        parsedigit();
        

    }//parseIntExpr

    public static void parseStringExpr(){

        //match(open quote)

        parseCharList();

        //match(close quote)
        

    }//parseStringExpr

    public static void parseBooleanExpr(){

        //match(open paren)

        parseExpr();

        parseboolop();

        parseExpr();

        //match(close paren)

        //or

        parseboolval();

        

    }//parseBooleanExpr

    public static void parseId(){

        parsechar();

        
    }//parseId

    public static void parseCharList(){

        parsechar();

        parseCharList();

        //or

        parsespace();

        parseCharList();

        //or

        //empty string

    }//parseCharList

    public static void parsetype(){

        //int

        //string 

        //boolean
        

    }//parsetype

    public static void parsechar(){

        //a - z


    }//parsechar

    public static void parsespace(){

        //space character

    }//parsespace

    public static void parsedigit(){

        // 0 - 9

    }//parsedigit

    public static void parseboolop(){

        //== or !=

    }//parseboolop

    public static void parseboolval(){

        //true or flase

    }//parseboolval

    public static void parseintop(){

        //+

    }//parseintop

    public static void match(String matchchar){

        /*
        if (tokenInput(i).getKind == matchchar){

            consume token
            increment token pointer

        }//if

        else{

            error: expected token but found current token


        }//else

        
        */
        
    }//match


}//Parser