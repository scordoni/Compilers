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

    static String character;

    ArrayList <String> tokenOutput = new ArrayList <String>();

    static int position = 0;

    int programCounter = 2;

    int lastPosition = 0;

    int currentPosition =0;

    static int commentFlag = 0;

    static int stringFlag = 0;

    static int ErrorFlag = 0;

    //This method pushes each letter of the array into the stack
	public static void Lex(ArrayList program){

        System.out.println(" ");
        System.out.println("Welcome to the lexer");


        System.out.println("INFO  Lexer - Lexing program 1");

        
        
		//goes through the arraylist
		for(int i = 0; i < program.size(); i++){
			
            //System.out.println(program.get(i));


            character = program.get(i).toString();


            fullbreak:


                switch(character){

                    case "{":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if
                        
                        System.out.println("DEBUG Lexer - L_BRACE [ { ] found at " + position);
                        break;
                    

                    case "}":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - R_BRACE [ } ] found at " + position);
                        break;

                    case "$":

                        if(commentFlag == 1){


                            System.out.println("ERROR - Unclosed comment found at " + position);

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - EOP [ $ ] found at " + position);

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        
                        break;

                    case "\"":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        if (stringFlag == 0){

                            stringFlag = 1;
                        }//if

                        else if (stringFlag == 0){

                            stringFlag = 1;
                        }//if
                    
                        break;

                    case "/":


                        if(program.get(i + 1).toString().compareToIgnoreCase("*") == 0){

                            commentFlag = 1;

                        }//if

                        else if(program.get(i + 1).toString().compareToIgnoreCase("*") != 0){

                            commentFlag = 0;

                        }//if
                    
                        break;


                    case "*":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        break;

                    case "!":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        break;

                        

                    case "=":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        

                        break;

                    
                    
                    case "0":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - DIGIT [ 0 ] found at " + position);
                        
                        break;

                    case "1":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - DIGIT [ 1 ] found at " + position);
                        
                        break;

                    case "2":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - DIGIT [ 2 ] found at " + position);
                        
                        break;

                    case "3":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - DIGIT [ 3 ] found at " + position);
                        
                        break;

                    case "4":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 4 ] found at " + position);
                        
                        break;

                    case "5":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 5 ] found at " + position);
                        
                        break;

                    case "6":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 6 ] found at " + position);
                        
                        break;

                    case "7":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 7 ] found at " + position);
                        
                        break;

                    case "8":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 8 ] found at " + position);
                        
                        break;

                    case "9":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ 9 ] found at " + position);
                        
                        break;

                    case "a":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ a ] found at " + position);
                        
                        break;
                        
                    case "b":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ b ] found at " + position);
                        
                        break;                          
                          
                    case "c":
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ c ] found at " + position);
                        
                        break;

                    case "d":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ d ] found at " + position);
                        
                        break;

                    case "e":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ e ] found at " + position);
                        
                        break;


                    case "f":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ f ] found at " + position);
                        
                        break;


                    case "g":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ g ] found at " + position);
                        
                        break;

                    case "h":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ h ] found at " + position);
                        
                        break;

                    case "i":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        break;

                    case "j":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ j ] found at " + position);
                        
                        break;

                    case "k":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ k ] found at " + position);
                        
                        break;

                    case "l":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ l ] found at " + position);
                        
                        break;

                    case "m":
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ m ] found at " + position);
                        
                        break;

                    case "n":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ n ] found at " + position);
                        
                        break;
                    
                    case "o":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ o ] found at " + position);
                        
                        break;

                    case "p":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ p ] found at " + position);
                        
                        break;

                    case "q":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ q ] found at " + position);
                        
                        break;

                    case "r":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ r ] found at " + position);
                        
                        break;

                    case "s":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ s ] found at " + position);
                        
                        break;

                    case "t":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ t ] found at " + position);
                        
                        break;

                    case "u":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ u ] found at " + position);
                        
                        break;

                    case "v":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ v ] found at " + position);
                        
                        break;

                    case "w":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ w ] found at " + position);
                        
                        break;

                    case "x":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if
                    


                        System.out.println("DEBUG Lexer - ID [ x ] found at " + position);
                        
                        break;

                    case "y":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ y ] found at " + position);
                        
                        break;

                    case "z":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        System.out.println("DEBUG Lexer - ID [ z ] found at " + position);
                        
                        break;
                        
                    default:

                        ErrorFlag = 1;
                        System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.get(i).toString());
                    
                
                }//switch


		}//for

  
			
	}//Lex


}//Lexer
