/**
 * Compiler.java 
 * 
 * This is the main code for the compiler
 * Here we will read our text file and pass each program to the lexer, parser and so on
 */

import java.io.File;
import java.io.FileNotFoundException;
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

        
        int lineNumber = 1;

       

        int programNumber = 1;

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
                        //print out space for formating
                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println("Lexing Program: " + programNumber);

                        tokens = Lexer.Lex(sourceCode, lineNumber);

                        //System.out.println("Error Flag: " + Lexer.ErrorFlag);

                        if (Lexer.ErrorFlag == 1){
                            
                            System.out.println(" ");
                            System.out.println("Due to Lex Error: Parse Failed");
                            System.out.println("Due to Lex Error: CST Failed");

                        }//if

                        else{

                            //here we pass the code to the parser for project 2
                            System.out.println(" ");
                            System.out.println("Parsing Program: " + programNumber);

                            //call parse
                            Parser.Parse(tokens); 

                            //System.out.println("Error Flag: " + Parser.ErrorFlag);

                            if (Parser.ErrorFlag == 1){
                            
                                System.out.println(" ");
                                System.out.println("Due to Parse Error: CST Failed");
    
                            }//if

                            else{
                                System.out.println(" ");
                                System.out.println(" ");
                                System.out.println("CST for Program: " + programNumber);

                                //call CST
                                CSTClass.CST(CST);

                                System.out.println(" ");
                                System.out.println(" ");
                                System.out.println("AST for Program: " + programNumber);

                                //call AST
                                ASTClass.AST(CST);

                                //call Semantic Analysis
                                System.out.println(" ");
                                System.out.println(" ");
                                System.out.println("Semantic Analysis for Program: " + programNumber);
                                System.out.println(" ");
                                System.out.println("Symbol Table for Program: " + programNumber);
                                SemanticAnalysis.SAnalysis();


                            }//else

                        }//else         
        
                        //now we set the new sourcecode to be equal to the rest of the file
                        //that came after the last end program character
                        sourceCode = afterEOP;

                        //increment the line number to keep track
                        lineNumber++;

                        //increment the program number to keep track
                        programNumber++;

                        //reset token stream for each program
                        tokens.clear();

                    }//if

                    //we check to see if the last program in the file ends with the end program character
                    else if( (!(line .contains(EOP))) && (input.hasNext() == false)){

                        System.out.println(" ");
                        System.out.println(" ");
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