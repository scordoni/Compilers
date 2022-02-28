/*
* 
* This is the CST component of the compiler
* Here we take in the token stream and go through each token to create the CST
* 
*/


import java.util.*; 

public class CSTClass {

    //Declare Variables
    static Token theToken = new Token();

    static ArrayList <String> AST = new ArrayList <String>();

    static char character;

    static int position = 0;

    static int programNumber = 1;


    //This method pushes each letter of the array into the stack
    public static ArrayList<String> CST(ArrayList<String> parseInput){

        //print out space for formating
        System.out.println(" ");

        System.out.println("CST For Program: " + programNumber);

        programNumber++;

        return AST;



    }//CST




}//CST