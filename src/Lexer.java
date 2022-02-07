/*
* 
* This is the Lexer component of the compiler
* 
*/


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.LineNumberReader;

public class Lexer {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    ArrayList <String> tokenOutput = new ArrayList <String>();

    static int position = 0;

    //This method pushes each letter of the array into the stack
	public static void Lex(ArrayList program){

		//goes through the arraylist
		for(int i = 0; i < program.size(); i++){
			
            //System.out.println(program.get(i));

            for(int j = 0; j < program.get(i).toString().length() ; j++){
                
                System.out.println(program.get(i).toString().charAt(j));

                

                
                switch(character){

                    case '{':

                        
                        System.out.println("DEBUG Lexer - L_BRACE [ { ] found at " + position);
                        break;
                    

                    case '}':

                        System.out.println("DEBUG Lexer - R_BRACE [ } ] found at " + position);
                        break;

                    case '$':

                        System.out.println("DEBUG Lexer - EOP [ $ ] found at " + position);
                        break;

                    case '/':

                        while((Character.compare((program.get(i).toString().charAt(j)), '/') != 0)){
                            System.out.println(j);
                            j++;
                        }//while
                    
                        break;

                    case '!':

                        if(Character.compare((program.get(i).toString().charAt(j++)), '=') == 0){
                                
                            System.out.println("DEBUG Lexer - NO_EQUALITY [ != ] found at " + position);

                        }//if

                    case '=':

                        if(Character.compare((program.get(i).toString().charAt(j++)), '=') == 0){
                            
                            System.out.println("DEBUG Lexer - EQUALITY [ == ] found at " + position);

                        }//if

                        else {
                            
                            System.out.println("DEBUG Lexer - ASSIGN_OP [ = ] found at " + position);

                        }//else

                        break;

                    
                    
                    

                    case '0':

                        System.out.println("DEBUG Lexer - DIGIT [ 0 ] found at " + position);
                        
                        break;

                    case '1':

                        System.out.println("DEBUG Lexer - DIGIT [ 1 ] found at " + position);
                        
                        break;

                    case '2':

                        System.out.println("DEBUG Lexer - DIGIT [ 2 ] found at " + position);
                        
                        break;

                    case '3':

                        System.out.println("DEBUG Lexer - DIGIT [ 3 ] found at " + position);
                        
                        break;

                    case '4':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case '5':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case '6':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case '7':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case '8':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case '9':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case 'a':

                        System.out.println("DEBUG Lexer - ID [ a ] found at " + position);
                        
                        break;
                        
                    case 'b':

                        System.out.println("DEBUG Lexer - ID [ b ] found at " + position);
                        
                        break;                          
                          
                    case 'c':

                        System.out.println("DEBUG Lexer - ID [ c ] found at " + position);
                        
                        break;

                    case 'd':

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case 'e':

                        System.out.println("DEBUG Lexer - ID [ e ] found at " + position);
                        
                        break;


                    case 'f':

                        System.out.println("DEBUG Lexer - ID [ f ] found at " + position);
                        
                        break;


                    case 'g':

                        System.out.println("DEBUG Lexer - ID [ g ] found at " + position);
                        
                        break;

                    case 'h':

                        System.out.println("DEBUG Lexer - ID [ h ] found at " + position);
                        
                        break;

                    case 'i':

                        if(Character.compare((program.get(i).toString().charAt(j++)), 'n') == 0){
                                    
                            System.out.println("DEBUG Lexer - INT [ int ] found at " + position);

                        }//if

                    case 'j':

                        System.out.println("DEBUG Lexer - ID [ j ] found at " + position);
                        
                        break;

                    case 'k':

                        System.out.println("DEBUG Lexer - ID [ k ] found at " + position);
                        
                        break;

                    case 'l':

                        System.out.println("DEBUG Lexer - ID [ l ] found at " + position);
                        
                        break;

                    case 'm':

                        System.out.println("DEBUG Lexer - ID [ m ] found at " + position);
                        
                        break;

                    case 'n':

                        System.out.println("DEBUG Lexer - ID [ n ] found at " + position);
                        
                        break;
                    
                    case 'o':

                        System.out.println("DEBUG Lexer - ID [ o ] found at " + position);
                        
                        break;

                    case 'p':

                        System.out.println("DEBUG Lexer - ID [ p ] found at " + position);
                        
                        break;

                    case 'q':

                        System.out.println("DEBUG Lexer - ID [ q ] found at " + position);
                        
                        break;

                    case 'r':

                        System.out.println("DEBUG Lexer - ID [ r ] found at " + position);
                        
                        break;

                    case 's':

                        System.out.println("DEBUG Lexer - ID [ s ] found at " + position);
                        
                        break;

                    case 't':

                        System.out.println("DEBUG Lexer - ID [ t ] found at " + position);
                        
                        break;

                    case 'u':

                        System.out.println("DEBUG Lexer - ID [ u ] found at " + position);
                        
                        break;

                    case 'v':

                        System.out.println("DEBUG Lexer - ID [ v ] found at " + position);
                        
                        break;

                    case 'w':

                        System.out.println("DEBUG Lexer - ID [ w ] found at " + position);
                        
                        break;

                    case 'x':

                        System.out.println("DEBUG Lexer - ID [ x ] found at " + position);
                        
                        break;

                    case 'y':

                        System.out.println("DEBUG Lexer - ID [ y ] found at " + position);
                        
                        break;

                    case 'z':

                        System.out.println("DEBUG Lexer - ID [ z ] found at " + position);
                        
                        break;
                        
                    default:

                        System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.get(i).toString().charAt(j));
                    
                
                }//switch


               
  
            }//for j

		}//for


        
			
	}//Lex



    /*
    //this method find the sum of the array passed in
    public static int commentMethod(int programcounter) {
        
        while((Character.compare((program.get(i).toString().charAt(j)), '/') != 0)){

            j++;
        }//while
    }//
    */

}//Lexer
