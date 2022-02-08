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
		

        ArrayList < String > tokens = new ArrayList < String >();
        ArrayList < String > program = new ArrayList < String >();


        Token theToken = new Token();
        Lexer theLexer = new Lexer();

        int programNumber = 1;

        int lineNumber = 0;
		

        //ask the user for the path and name to the file
        System.out.println("Welcome to the Compiler");
        System.out.print("Enter a filename: ");
        fileName = keyboard.next();        
    
        //create the reference to the file
        File myFile = new File(fileName);
		
		try
		{

			//create scanner
			Scanner input = new Scanner(myFile);
            
            //.useDelimiter("\\s*");

            if(input.hasNext() == false){

                System.out.println("This text file is empty.");

            }//if


            while(input.hasNext()){

                //System.out.println(input.nextLine()); //prints line

                //System.out.println(input.nextLine().contains(EOP)); //prints true

                //System.out.println(input.nextLine().indexOf(EOP));  //prints 2

                System.out.println(input.nextLine().substring(0, input.nextLine().indexOf(EOP)));

                

                if(input.nextLine().contains(EOP)){

                    beforeEOP = input.nextLine().substring(0, input.nextLine().indexOf(EOP));

                    afterEOP = input.nextLine().substring(input.nextLine().indexOf(EOP) + 1, input.nextLine().length());

                    sourceCode = sourceCode + beforeEOP;

                    tokens = Lexer.Lex(program);

                    //CST = parse(tokens)      // project 2
      
                    sourceCode = afterEOP;

                    lineNumber++;

                }//if

                else if( (!(input.nextLine().contains(EOP))) && (input.hasNext() == false)){

                    System.out.println("The last program does not have a \"$\" to end the program, therefore it cannot be processed.");

                }//else ig

                else{
               
                    sourceCode = sourceCode + input.nextLine();
                    lineNumber++;

                }//else  

            }//while

			input.close();	

		}//try
		
		//error for file not found
		catch(FileNotFoundException ex)
	    {
	      System.out.println("Failed to find file: " + myFile.getAbsolutePath()); 
	    }//catch


		}//main

}//Compiler