/*
* 
* This is the Semantic Analysis component of the compiler
* Here we look at scope and type checking
* 
*/


import java.util.*; 

public class SemanticAnalysis {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static ArrayList <Token> globalTokens= new ArrayList <Token>();

    static int position = 0;

    static int programNumber = 1;

    static int lineNumber = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int j = 0;

    
    //This method is the main Semantic Analysis method
    public static void SAnalysis(){

        System.out.println("------------------------------------");
        System.out.println("    Name    Type    Scope   Line    ");
        System.out.println("------------------------------------");

    }//Semantic Analysis




}//Semantic Analysis Class