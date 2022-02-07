/**
 * Compiler.java 
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Compiler{

    // Create a new keyboard Scanner object.
    static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {

        //Declare and initialize variables 

		String fileName;
        String name;
		ArrayList < ArrayList <Character>>  program = new ArrayList < ArrayList <Character>>();
        Token theToken = new Token();
        Lexer theLexer = new Lexer();
		

        //ask the user for the path and name to the file
        System.out.print("Enter a filename: ");
        fileName = keyboard.next();        
    
        //create the reference to the file
        File myFile = new File(fileName);
			
		
		try
		{

            InputStream in = new FileInputStream(myFile);
            Reader reader = new InputStreamReader(in);

            int j = 0;


            for(int i = 0; i < program.size(); i++){

                //for( int j = 0; j < program.get(i).size(); j++){
                    
                    while(((i = reader.read()) != -1) && ((i = reader.read()) != '$'))
                    {	
                        //Input into array 
                        program.get(i).add(j, (char) i);		
                        j++;
                    }//while


                //}//for j


            }//for i

			

			
			in.close();	
            reader.close();

		}//try
		
		//error for file not found
		catch(FileNotFoundException ex)
	    {
	      System.out.println("Failed to find file: " + myFile.getAbsolutePath()); 
	    }//catch

		for(int i = 0; i < program.size(); i++){

            for( int j = 0; j < program.get(i).size(); j++){
                
                System.out.println(program.get(i).get(j));


            }//for j


        }//for i
		
		//Lexer.Lex(program);
		
		
		}//main

}//Compiler