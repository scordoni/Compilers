/**
 * Compiler.java 
 * 
 * This is the main code for the compiler
 * Here we will read our text file and pass each program to the lexer, parser and so on
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Compiler{

    // Create a new keyboard Scanner object.
    static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {

        //Declare and initialize variables 

		String fileName;
        String sourceCode = "";
        String EOP = "$";

        String beforeEOP;
        String afterEOP;
		
        String line;

        ArrayList < Token > tokens = new ArrayList < Token >();

        ArrayList < String > CST = new ArrayList < String >();

        ArrayList < String > AST = new ArrayList < String >();
        
        int lineNumber = 1;

        int ErrorFlag = 0;

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

                while(input.hasNext()){

                    //while there is a next line we set it to a temp variable
                    line = input.nextLine();
                    
                    //if the line contains the character to end the program then we split the txt file
                    if((line .contains(EOP))){

                        //here we set a variable to what comes before the end character
                        beforeEOP = line .substring(0, line .indexOf(EOP) + 1);

                        //here we set a variable to what comes after the end character
                        afterEOP = line .substring(line .indexOf(EOP) + 1, line .length());

                        sourceCode = sourceCode + beforeEOP;

                        //we then pass the code to the lexer
                        tokens = Lexer.Lex(sourceCode, lineNumber);

                        ErrorFlag = Lexer.ErrorFlag(Lexer.ErrorFlag);

                        System.out.println("Error Flag: " + ErrorFlag);

                        //here we pass the code to the parser for project 2
                        CST = Parser.Parse(tokens); 

                        AST = CSTClass.CST(CST);
                        
        
                        //now we set the new sourcecode to be equal to the rest of the file
                        //that came after the last end program character
                        sourceCode = afterEOP;

                        //increment the line number to keep track
                        lineNumber++;

                    }//if

                    //we check to see if the last program in the file ends with the end program character
                    else if( (!(line .contains(EOP))) && (input.hasNext() == false)){

                        System.out.println("The last program does not have a \"$\" to end the program, therefore it cannot be processed.");

                    }//else ig

                    //else we add the current line to the source code
                    else{
                
                        sourceCode = sourceCode + line ;

                        //increment line number to keep track
                        
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