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

            //if the file is empty we let the user know there is nothing in the file
            if(input.hasNext() == false){

                System.out.println("This text file is empty.");

            }//if

            //else we can run through the file to create the programs
            else{

                System.out.println(" ");
                System.out.println("Lexing Program: " + programNumber);


                while(input.hasNext()){

                    //while there is a next line we set it to a temp variable
                    line = input.nextLine();

                    //if the line contains the character to end the program then we split the txt file
                    if(line .contains(EOP)){

                        //here we set a variable to what comes before the end character
                        beforeEOP = line .substring(0, line .indexOf(EOP) + 1);

                        //here we set a variable to what comes after the end character
                        afterEOP = line .substring(line .indexOf(EOP) + 1, line .length());

                        sourceCode = sourceCode + beforeEOP;

                        //we then pass the code to the lexer
                        tokens = Lexer.Lex(sourceCode);

                        //here we pass the code to the parser for project 2
                        //CST = parse(tokens)      // project 2
        
                        //now we set the new sourcecode to be equal to the rest of the file
                        //that came after the last end program character
                        sourceCode = afterEOP;

                        //increment the line number to keep track
                        lineNumber++;

                        //increment the program number to keep track
                        programNumber++;


                        System.out.println(" ");
                        System.out.println("Lexing Program: " + programNumber);

                    }//if

                    //we check to see if the last program in the file ends with the end program character
                    else if( (!(line .contains(EOP))) && (input.hasNext() == false)){

                        System.out.println("The last program does not have a \"$\" to end the program, therefore it cannot be processed.");

                    }//else ig

                    //else we add the current line to the source code
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