/**
 * Compiler.java 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Compiler{

    // Create a new keyboard Scanner object.
    static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {

        //Declare and initialize variables 

		String fileName;
        String name;
		ArrayList < Integer > program = new ArrayList < Integer >();
        ArrayList < String > testprogram = new ArrayList < String >();
        Token theToken = new Token();
        Lexer theLexer = new Lexer();
        int programNumber = 1;
		

        //ask the user for the path and name to the file
        System.out.println("Welcome to the Compiler");
        System.out.print("Enter a filename: ");
        fileName = keyboard.next();        
    
        //create the reference to the file
        File myFile = new File(fileName);
			
		
		try
		{

			//create scanner
			Scanner input = new Scanner(myFile).useDelimiter("\\s*");

            int i = 0;

            while(input.hasNext()){

                testprogram.add(input.next());
                


            }//while

        
			
			input.close();	

		}//try
		
		//error for file not found
		catch(FileNotFoundException ex)
	    {
	      System.out.println("Failed to find file: " + myFile.getAbsolutePath()); 
	    }//catch


        System.out.println("Welcome to the lexer");

        System.out.println("Lexing Program ...");
        
        Lexer.Lex(testprogram);

        


        
		
		}//main

}//Compiler