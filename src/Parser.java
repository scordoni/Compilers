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

        System.out.println("Lexing Program: " + programNumber);

        programNumber++;


        return CST;

    }//parse




}//Parser