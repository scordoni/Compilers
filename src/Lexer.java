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

    static int lineNumber = 0;

    int programCounter = 2;

    int lastPosition = 0;

    int currentPosition =0;

    static int commentFlag = 0;

    static int stringFlag = 0;

    static int ErrorFlag = 0;

    static int intFlag = 0;

    static int braceFlag = 0;

    static int parenFlag = 0;

    //This method pushes each letter of the array into the stack
	public static void Lex(ArrayList program){

        System.out.println(" ");
        

        
        
		//goes through the arraylist
		for(int i = 0; i < program.size(); i++){
			
            //System.out.println(program.get(i));


            character = program.get(i).toString();


            fullbreak:


                switch(character){

                    //To start we have all the special characters

                    case "{":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        theToken.setKind("L_BRACE");
                        theToken.setSymbol("{");
                        theToken.setLineNumber(lineNumber);
                        theToken.setPosition(position);
                        
                        System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;
                    

                    case "}":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        theToken.setKind("R_BRACE");
                        theToken.setSymbol("}");
                        theToken.setLineNumber(lineNumber);
                        theToken.setPosition(position);
                        
                        System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;

                    case "$":

                        if(commentFlag == 1){


                            System.out.println("ERROR - Unclosed comment found at " + "(" + lineNumber + ":" + position + ")");

                            System.out.println(" ");

                            System.out.println(" ");

                            break fullbreak;

                        }//if

                        if(stringFlag == 1){


                            System.out.println("ERROR - Unclosed string found at " + "(" + lineNumber + ":" + position + ")");

                            System.out.println(" ");

                            System.out.println(" ");

                            break fullbreak;

                        }//if

                        

                        theToken.setKind("EOP");
                        theToken.setSymbol("$");
                        theToken.setLineNumber(lineNumber);
                        theToken.setPosition(position);
                        
                        System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        System.out.println(" ");

                        
                        break;

                    case "(":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){

                            theToken.setKind("CHAR ");
                            theToken.setSymbol("(");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        else{

                            parenFlag = 1;

                            theToken.setKind("OPEN_PAREN");
                            theToken.setSymbol("(");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                    
                        break;

                    case ")":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){

                            theToken.setKind("CHAR ");
                            theToken.setSymbol(")");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        else{

                            parenFlag = 1;

                            theToken.setKind("CLOSE_PAREN");
                            theToken.setSymbol(")");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                    
                        break;

                    case "\"":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 0){

                            stringFlag = 1;

                            theToken.setKind("OPEN_QUOTE");
                            theToken.setSymbol("\"");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        else if (stringFlag == 1){

                            stringFlag = 0;

                            theToken.setKind("CLOSE_QUOTE");
                            theToken.setSymbol("\"");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
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


                    case "+":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){


                            theToken.setKind("CHAR");
                            theToken.setSymbol("+");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        else{

                            theToken.setKind("ADDITION");
                            theToken.setSymbol("+");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
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

                        else if(program.get(i + 1).toString().compareToIgnoreCase("=") == 0){

                            theToken.setKind("EQUALITY");
                            theToken.setSymbol("==");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if


                        else{
                            theToken.setKind("ASSIGNMENT");
                            theToken.setSymbol("=");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;
                    


                    //Everthing below here is numbers 1 - 9 and the alphabet




                    case "0":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("0");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "1":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("1");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "2":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("2");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "3":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("3");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "4":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("4");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "5":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("5");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "6":


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("6");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "7":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("7");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "8":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("8");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "9":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("DIGIT");
                            theToken.setSymbol("9");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "a":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{


                            theToken.setKind("ID");
                            theToken.setSymbol("a");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else

                        
                        
                        break;
                        
                    case "b":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{


                            theToken.setKind("ID");
                            theToken.setSymbol("b");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else
                        
                        break;                          
                          
                    case "c":
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("c");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "d":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("d");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "e":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("e");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;


                    case "f":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("f");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;


                    case "g":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("g");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "h":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("h");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "i":


                        if(program.get(i + 1).toString().compareToIgnoreCase("n") == 0){

                            if(program.get(i + 2).toString().compareToIgnoreCase("t") == 0){

                            
                                theToken.setKind("INT");
                                theToken.setSymbol("int");
                                theToken.setLineNumber(lineNumber);
                                theToken.setPosition(position);
                        

                                System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        
                                intFlag = 1;

                                break;

                            }//if
                            
                        }//if

                        else if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("i");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else

                        break;

                    case "j":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("j");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "k":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("k");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                           
                        }//else

                        break;

                    case "l":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("l");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "m":
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("m");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                           
                        }//else

                        break;

                    case "n":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(intFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            theToken.setKind("CHAR");
                            theToken.setSymbol("n");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("n");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else

                      
                        
                        break;
                    
                    case "o":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("o");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                          
                        }//else

                        break;

                        //print
                    case "p":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("p");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "q":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("q");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "r":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            break fullbreak;

                        }//if

                        else{
                            theToken.setKind("ID");
                            theToken.setSymbol("r");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                            
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case "s":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        if(program.get(i + 1).toString().compareToIgnoreCase("t") == 0){

                            if(program.get(i + 2).toString().compareToIgnoreCase("r") == 0){

                                if(program.get(i + 3).toString().compareToIgnoreCase("i") == 0){

                                    if(program.get(i + 4).toString().compareToIgnoreCase("n") == 0){
                                       
                                        if(program.get(i + 5).toString().compareToIgnoreCase("g") == 0){
                                                theToken.setKind("STRING");
                                                theToken.setSymbol("string");
                                                theToken.setLineNumber(lineNumber);
                                                theToken.setPosition(position);
                                        

                                                System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                                stringFlag = 1;

                                                break;
                                        }//if
                                     }//if
                                }//if
                            }//if 
                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("s");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

          
                        
                        break;

                    case "t":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        if(intFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("t");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case "u":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("u");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case "v":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("v");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;

                    case "w":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("w");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case "x":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("x");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case "y":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("y");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;

                    case "z":

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else{

                            theToken.setKind("ID");
                            theToken.setSymbol("z");
                            theToken.setLineNumber(lineNumber);
                            theToken.setPosition(position);
                        
                            System.out.println("DEBUG Lexer - " + theToken.getKind() + " [ " + theToken.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;
                        
                    default:

                        ErrorFlag = 1;
                        System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.get(i).toString());
                    
                
                }//switch


		}//for

  
			
	}//Lex


}//Lexer
