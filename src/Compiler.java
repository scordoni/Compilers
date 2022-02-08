/**
 * Compiler.java 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler{

    // Create a new keyboard Scanner object.
    static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {

        //Declare and initialize variables 

		String fileName;
        String sourceCode = "";
        String EOP = "$";

        String beforeEOP;
        String afterEOP;
		
        String line;

        ArrayList < Token > tokens = new ArrayList < Token >();
        ArrayList < String > program = new ArrayList < String >();


        Token theToken = new Token();
        Lexer theLexer = new Lexer();

        int programNumber = 1;

        int lineNumber = 0;
		

        //ask the user for the path and name to the file
        System.out.println("Welcome to the Compiler");
        
        fileName = args[0];        
    
        //create the reference to the file
        File myFile = new File(fileName);
		
		try
		{

			//create scanner
			Scanner input = new Scanner(myFile);
            
            //.useDelimiter("\\s*");

            fullbreak:

            if(input.hasNext() == false){

                System.out.println("This text file is empty.");

            }//if



            else{

                System.out.println(" ");
                System.out.println("Lexing Program: " + programNumber);

                while(input.hasNext()){

                    line = input.nextLine();

                    if(line .contains(EOP)){

                        beforeEOP = line .substring(0, line .indexOf(EOP) + 1);

                        afterEOP = line .substring(line .indexOf(EOP) + 1, line .length());

                        sourceCode = sourceCode + beforeEOP;

                        tokens = Lexer.Lex(sourceCode);

                        //CST = parse(tokens)      // project 2
        
                        sourceCode = afterEOP;

                        lineNumber++;

                        programNumber++;


                        System.out.println(" ");
                        System.out.println("Lexing Program: " + programNumber);

                    }//if

                    else if( (!(line .contains(EOP))) && (input.hasNext() == false)){

                        System.out.println("The last program does not have a \"$\" to end the program, therefore it cannot be processed.");

                    }//else ig

                    else{
                
                        sourceCode = sourceCode + line ;
                        lineNumber++;

                    }//else  

                }//while

            }//else

			input.close();	

		}//try
		
		//error for file not found
		catch(FileNotFoundException ex)
	    {
	      System.out.println("Failed to find file: " + myFile.getAbsolutePath()); 
	    }//catch


		}//main

}//Compiler