/*
* 
* This is the CST component of the compiler
* Here we take in the token stream and go through each token to create the CST
* 
*/


import java.util.*; 

public class CST {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static int position = 0;

    static int programNumber = 1;


    //This method pushes each letter of the array into the stack
    public static void CST(ArrayList<Token> tokenInput){

        //print out space for formating
        System.out.println(" ");

        System.out.println("CST For Program: " + programNumber);

        programNumber++;



    }//CST




}//CST