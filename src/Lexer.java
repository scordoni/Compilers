/*
* 
* This is the Lexer component of the compiler
* 
*/


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    //Declare Variables
    Token theToken = new Token();

    //This method pushes each letter of the array into the stack
	public static void Lex(ArrayList program){

		//goes through the arraylist
		for(int i = 0; i < program.size(); i++){
			
            //System.out.println(program.get(i));

            for(int j = 0; j < program.get(i).toString().length() ; j++){
                
                System.out.println(program.get(i).toString().charAt(j));

                //if(program.get(i).toString().charAt(j)){

                    
                    System.out.println("DEBUG Lexer - L_BRACE [ { ] found at ");
                    

                //}         

            }//for j

		}//for


        
			
	}//Lex
    
}//Lexer
