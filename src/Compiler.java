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
		ArrayList <String> program = new ArrayList <String>();
        Token theToken = new Token();
        Lexer theLexer = new Lexer();
		

        //ask the user for the path and name to the file
        System.out.print("Enter a filename: ");
        fileName = keyboard.next();        
    
        //create the reference to the file
        File myFile = new File(fileName);
			
		
		try
		{

			//create scanner
			Scanner input = new Scanner(myFile);

            int i = 0;

			//while there are more lines in the file it inputs them into a word array
		    while(input.hasNext())
		    {	
			    //Input into array 
				program.add(input.next());		
				i++;
		    }//while

			
			input.close();	

		}//try
		
		//error for file not found
		catch(FileNotFoundException ex)
	    {
	      System.out.println("Failed to find file: " + myFile.getAbsolutePath()); 
	    }//catch

		
		
		Lexer.Lex(program);
		
		
		}//main

}//Compiler