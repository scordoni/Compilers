/*
* 
* This is the Lexer component of the compiler
* Here we take in the program as a string and go through letter by letter to lex the program
* 
*/


import java.util.*; 

public class Lexer {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static ArrayList <Token> tokenOutput = new ArrayList <Token>();

    static int position = 0;

    static int programNumber = 1;

    static int lineNumber = 0;

    static int commentFlag = 0;

    static int stringFlag = 0;

    static int stringWordFlag = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int intFlag = 0;

    static int braceFlag = 0;

    static int parenFlag = 0;

    static int whileFlag = 0;

    static int printFlag = 0;

    static int booleanFlag = 0;

    static int ifFlag = 0;

    static int trueFlag = 0;

    static int falseFlag = 0;

    //Just to keep this accessable at the top
	public static Integer ErrorFlag(Integer ErrorFlagInput){
        int ErrorFlag = 0;

        ErrorFlag = ErrorFlagInput;

        return ErrorFlag;
    }//error flag

    //This method pushes each letter of the array into the stack
	public static ArrayList<Token> Lex(String program, int lineNumber){

        //print out space for formating
        System.out.println(" ");

        System.out.println("Lexing Program: " + programNumber);

        programNumber++;

		//goes through the prgram letter by letter to create tokens
		for(int i = 0; i < program.length(); i++){
			
            //Print to check
            //System.out.println(program.charAt(i));

            //create a new instance of the token class
            Token Token = new Token();
            
            //assign the character to a variable for the switch statement.
            character = program.charAt(i);

            position = i;

            //create a labeled break to break completely out of the switch statement
            fullbreak:


                //here we switch through each symbol in our language grammer so that we know
                //what to do and what token to create 
                switch(character){

                    //To start we have all the special characters

                    case '{':

                        //if the symbol is in a comment we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //we then create the token for the character
                        Token.setKind("L_BRACE");
                        Token.setSymbol("{");
                        Token.setLineNumber(lineNumber);
                        Token.setPosition(position);

                        tokenOutput.add(Token);

                        //set brace flag
                        braceFlag = 1;
                        
                        //print out to the command line
                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;
                    

                    case '}':

                        //if the symbol is in a comment we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //we then create the token for the character
                        Token.setKind("R_BRACE");
                        Token.setSymbol("}");
                        Token.setLineNumber(lineNumber);
                        Token.setPosition(position);

                        tokenOutput.add(Token);

                        //reset brace flag
                        braceFlag = 0;
                        
                        //print out to the command line
                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;

                    case '$':

                        //if the symbol is in a comment then we have an unclosed comment and an error
                        if(commentFlag == 1){


                            System.out.println("ERROR - Unclosed comment found at " + "(" + lineNumber + ":" + position + ") - fix by adding \"/*\" at the end of your comment");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            commentFlag = 0;

                            

                        }//if

                        //if the symbol is in a string then we have an unclosed string and an error
                        else if(braceFlag == 1){


                            System.out.println("ERROR - Unclosed brace found at " + "(" + lineNumber + ":" + position + ") - fix by adding \"}\" at the end of your statement");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            braceFlag = 0;

                            

                        }//else if

                        //if the symbol is in a string then we have an unclosed string and an error
                        else if(parenFlag == 1){


                            System.out.println("ERROR - Unclosed parenthesie found at " + "(" + lineNumber + ":" + position + ") - fix by adding \")\" at the end of your string");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            parenFlag = 0;

                            

                        }//else if

                        //if the symbol is in a string then we have an unclosed string and an error
                        else if(stringFlag == 1){


                            System.out.println("ERROR - Unclosed string found at " + "(" + lineNumber + ":" + position + ") - fix by adding \"\"\" at the end of your string");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            stringFlag = 0;

                            

                        }//else if

                        //else we create the token 
                        else{

                            Token.setKind("EOP");
                            Token.setSymbol("$");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");


                            //if the error flag is one then we print how many errors we have and reset the flag
                            if (ErrorFlag == 1){
                                System.out.println("ERROR Lexer - Lex failed with " + numberOfErrors + " error(s)");

                                ErrorFlag = 0;
                            }//if

                            //else we had a successful program and we print that
                            else{

                                System.out.println("INFO  Lexer - Lex completed with 0 errors");

                            }//else

                            //space for fomatting to see the difference in programs
                            System.out.println(" ");

                            System.out.println(" ");

                            System.out.println(" ");

                            System.out.println(" ");

                            
                        }//else

                        numberOfErrors = 0;
                        commentFlag = 0;
                        braceFlag = 0;
                        parenFlag = 0;
                        stringFlag = 0;
                        ErrorFlag = 0;
                        
                        break;

                    case '(':

                        //if the symbol is in a comment we skip     
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if the symbol is in a string we create a char token from it
                        else if (stringFlag == 1){

                            Token.setKind("CHAR ");
                            Token.setSymbol("(");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        //else we set the paren flag and create the token
                        else{

                            parenFlag = 1;

                            Token.setKind("OPEN_PAREN");
                            Token.setSymbol("(");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                    
                        break;

                    case ')':

                        //if the symbol is in a comment we skip  
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if the symbol is in a string we create a char token from it
                        else if (stringFlag == 1){

                            Token.setKind("CHAR ");
                            Token.setSymbol(")");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        //else we set the paren flag back to 0 and create the token
                        else{

                            parenFlag = 0;

                            Token.setKind("CLOSE_PAREN");
                            Token.setSymbol(")");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                    
                        break;

                    case '\"':

                        //if the symbol is in a comment we skip 
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if the flag isnt set we set it to 1 and create the open token
                        else if (stringFlag == 0){

                            stringFlag = 1;

                            Token.setKind("OPEN_QUOTE");
                            Token.setSymbol("\"");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        //if the flag is set then we reset it and create the closed token
                        else if (stringFlag == 1){

                            stringFlag = 0;

                            Token.setKind("CLOSE_QUOTE");
                            Token.setSymbol("\"");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//if
                    
                        break;

                    case '/':

                        //if the character after is * then we have a comment and set the flag
                        if(Character.compare(program.charAt(i + 1), '*') == 0){

                            commentFlag = 1;

                        }//if

                        //if the character before is * then we have an end comment and reset the flag
                        else if(Character.compare(program.charAt(i - 1), '*')  == 0){

                            commentFlag = 0;

                        }//if
                    
                        break;


                    case '+':

                        //if the symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if the symbol is in a string then we create a char token
                        else if (stringFlag == 1){


                            Token.setKind("CHAR");
                            Token.setSymbol("+");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        //else we create the addition token
                        else{

                            Token.setKind("ADDITION");
                            Token.setSymbol("+");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//if

                        break;

                    case ' ':

                        //if the symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if the symbol is in a string then we create a char charcter
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol(" ");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                            break fullbreak;

                        }//if

                        break;

                    case '*':

                        //if the symbol is in a comment then we ignore
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        break;

                    case '!':

                        //else we check to see if we have a != sign and if so we create the token
                        if(Character.compare(program.charAt(i + 1), '=')  == 0){

                            Token.setKind("NON_EQUALITY");
                            Token.setSymbol("!=");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//if

                        else{

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));
                        }


                        break;

                        

                    case '=':

                        //if the symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else we check to see if we have a double = and if so we create the token
                        else if(Character.compare(program.charAt(i + 1), '=')  == 0){

                            Token.setKind("EQUALITY");
                            Token.setSymbol("==");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        //else we check to see if we have a double = and if so we skip
                        else if(Character.compare(program.charAt(i - 1), '=')  == 0){

                            break fullbreak;

                        }//else if

                        //else we check to see if we have a ! = and if so we skip
                        else if(Character.compare(program.charAt(i - 1), '!')  == 0){

                            break fullbreak;

                        }//else if

                        //else we create the normal assignment token
                        else{
                            Token.setKind("ASSIGNMENT");
                            Token.setSymbol("=");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;
                    

                    //Everthing below here is numbers 1 - 9 and the alphabet

                    case '0':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we creat the digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("0");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '1':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create the digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("1");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '2':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create the digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("2");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '3':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if the symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create the digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("3");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '4':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we creat a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("4");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '5':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("5");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '6':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("6");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '7':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                           //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("7");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '8':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if the symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("8");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case '9':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            //set the error flag if the symbol is unrecognized
                            ErrorFlag = 1;

                            //increment number of errors for output later
                            numberOfErrors++;

                            System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));

                        }//else if

                        //else we create a digit token
                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("9");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    //the aplhabet

                    case 'a':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in the spelling of "boolean" then we skip
                        else if(booleanFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is involved in the spelling of "false" then we skip
                        else if(falseFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is in a string then we create a char chracter
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("a");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an ID token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("a");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else
                        
                        break;
                        
                    case 'b':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it spells boolean we create a boolean token and set the flag
                        else if(Character.compare(program.charAt(i + 1), 'o')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'o')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'l')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'e')  == 0){
                                        
                                        if(Character.compare(program.charAt(i + 5), 'a')  == 0){

                                            if(Character.compare(program.charAt(i + 6), 'n')  == 0){
                
                                                Token.setKind("BOOLEAN");
                                                Token.setSymbol("boolean");
                                                Token.setLineNumber(lineNumber);
                                                Token.setPosition(position);

                                                tokenOutput.add(Token);
                                            
                                                System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                                                    

                                                booleanFlag = 1;
                                            }//if
                                        }//if
                                    }//if
                                }//if
                            }//if
                        }//else if

                        //else if it is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("b");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an ID token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("b");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else
                        
                        break;                          
                          
                    case 'c':
                    
                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("c");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an ID Token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("c");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'd':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("d");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("d");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'e':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in the spelling of "false" then we skip
                        else if(falseFlag == 1){

                            falseFlag = 0;
                            break fullbreak;

                        }//else if

                        //if our symbol is involved in the spelling of "true" then we skip
                        else if(trueFlag == 1){

                            trueFlag = 0;
                            break fullbreak;

                        }//else if

                        //else if our symbol is involved in spelling "while" then we skip and reset the flag
                        else if(whileFlag == 1){

                            whileFlag = 0;

                        }//if

                        //else if our symbol is involved in spelling "boolean" then we skip
                        else if(booleanFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("e");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("e");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;


                    case 'f':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involed in spelling "if" then we skip and reset the if flag
                        if(ifFlag == 1){

                            ifFlag = 0;
                            break fullbreak;

                        }//if

                        //else if out symbol is involved in spelling "false"  we create a flase token
                        else if(Character.compare(program.charAt(i + 1), 'a')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'l')  == 0){

                                if(Character.compare(program.charAt(i + 3), 's')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'e')  == 0){

                                        Token.setKind("FALSE");
                                        Token.setSymbol("false");
                                        Token.setLineNumber(lineNumber);
                                        Token.setPosition(position);

                                        tokenOutput.add(Token);

                                        falseFlag = 1;
                                    
                                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                    }//if
                                }//if
                            }//if
                        }//if

                        //else if our symbol is in a string we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("f");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("f");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;


                    case 'g':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in spelling "string" then we skip and reset the flag
                        else if(stringWordFlag == 1){

                            stringWordFlag = 0;

                        }//else if

                        //if the symbol is in the string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("g");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("g");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'h':
                        
                        //if our symbol is a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is invovled in spelling " while" then we skip
                        else if(whileFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("h");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an Id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("h");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'i':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is invovled in spelling "print" then we skip
                        else if(printFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol si involved in spelling "while" then we skip
                        else if(whileFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in spelling "string" then we skip
                        else if(stringWordFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if our symbol starts the work "int" then we create the int token
                        else if((stringFlag == 0)&&(Character.compare(program.charAt(i + 1), 'n')  == 0)){

                            if(Character.compare(program.charAt(i + 2), 't')  == 0){

                            
                                Token.setKind("INT");
                                Token.setSymbol("int");
                                Token.setLineNumber(lineNumber);
                                Token.setPosition(position);

                                tokenOutput.add(Token);
                        
                                System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        
                                intFlag = 1;

                                break;

                            }//if
                            
                        }//if

                        //else if our symbol is involved in spelling "if" then we create the if token
                        else if(Character.compare(program.charAt(i + 1), 'f')  == 0){

                            Token.setKind("IF");
                            Token.setSymbol("if");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);

                            ifFlag = 1;
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        
                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("i");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("i");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else

                        break;

                    case 'j':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("j");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we createa an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("j");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'k':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("k");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("k");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                           
                        }//else

                        break;

                    case 'l':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it is involved in spelling "boolean" then we skip
                        else if(booleanFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is involved in the spelling of "false" then we skip
                        else if(falseFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if it is involved in spelling "while" then we skip
                        else if(whileFlag == 1){

                            break fullbreak;

                        }//if

                        //else if the symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("l");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("l");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'm':
                    
                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("m");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("m");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                           
                        }//else

                        break;

                    case 'n':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it is involved in spelling "int" then we skip
                        else if(intFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if it is involved in spelling "print" then we skip
                        else if(printFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if it involved in spelling "boolean" then we skip
                        else if(booleanFlag == 1){

                            booleanFlag = 0;
                            break fullbreak;

                        }//else if

                        //else if it is involved in spelling "string" then we skip
                        else if(stringWordFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if the symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("n");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                            break fullbreak;

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("n");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else

                      
                        break;
                    
                    case 'o':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is involved in spelling "boolean" then we skip
                        else if(booleanFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("o");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("o");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                          
                        }//else

                        break;

                        //print
                    case 'p':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it spells the word "print" then we create a print token
                        else if(Character.compare(program.charAt(i + 1), 'r')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'i')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'n')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 't')  == 0){

                                        if(Character.compare(program.charAt(i + 5), '(')  == 0){
                                       
                                            Token.setKind("PRINT");
                                            Token.setSymbol("print");
                                            Token.setLineNumber(lineNumber);
                                            Token.setPosition(position);

                                            tokenOutput.add(Token);
                                        
                                            printFlag = 1;

                                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                        }//if
                                    }//if
                                }//if
                            }//if 
                        }//else if

                        //else if the symbol is in a string then we make a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("p");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("p");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'q':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("q");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create a id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("q");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'r':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is involved in spelling "print" then we skip
                        else if(printFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if our symbol is involved in spelling "string" then we skip
                        else if(stringWordFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is involved in the spelling of "true" then we skip
                        else if(trueFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("r");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{
                            Token.setKind("ID");
                            Token.setSymbol("r");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 's':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in the spelling of "false" then we skip
                        else if(falseFlag == 1){

                            break fullbreak;

                        }//else if

                        //if our symbol starts the spelling of "string" then we create a string token
                        else if((stringFlag == 0)&&(Character.compare(program.charAt(i + 1), 't')  == 0)){

                            if(Character.compare(program.charAt(i + 2), 'r')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'i')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'n')  == 0){
                                       
                                        if(Character.compare(program.charAt(i + 5), 'g')  == 0){
                                               
                                            Token.setKind("STRING");
                                            Token.setSymbol("string");
                                            Token.setLineNumber(lineNumber);
                                            Token.setPosition(position);

                                            tokenOutput.add(Token);
                                        
                                            stringWordFlag = 1;

                                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                        }//if
                                     }//if
                                }//if
                            }//if 
                        }//else if

                        //else if our symbol is involved in string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("s");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("s");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else

                        break;

                    case 't':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in spelling the word "int" then we skip and reset the flag
                        if(intFlag == 1){

                            intFlag = 0;
                            break fullbreak;
                            
                        }//else if

                        //if our symbol is involved in spelling the word "print" then we skip and reset the flag
                        if(printFlag == 1){

                            printFlag = 0;
                            break fullbreak;
                            

                        }//else if

                        //if our symbol is involved in spelling the word "string" then we skip 
                        if(stringWordFlag == 1){
                            
                            break fullbreak;
                            
                        }//else if

                        //else if our symbol creates the word "true" then we create the true token
                        if(Character.compare(program.charAt(i + 1), 'r')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'u')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'e')  == 0){

                                        Token.setKind("TRUE");
                                        Token.setSymbol("true");
                                        Token.setLineNumber(lineNumber);
                                        Token.setPosition(position);

                                        tokenOutput.add(Token);

                                        trueFlag = 1;
                                    
                                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        break fullbreak;
                                }//if
                            }//if
                        }//else if

                        //else if our symbol is in a string then we create a char token
                        if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("t");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            break fullbreak;
                        }//else if
                        
                        //else we create an id token
                        if((commentFlag==0)&&(intFlag==0)&&(printFlag==0)&&(stringWordFlag==0)&&(stringFlag==0)){

                            Token.setKind("ID");
                            Token.setSymbol("t");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                            break fullbreak;

                        }//else
                        
                        break;

                    case 'u':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is involved in the spelling of "true" then we skip
                        else if(trueFlag == 1){

                            break fullbreak;

                        }//else if

                        //else if our symbol is in a string then we create a char character
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("u");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("u");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case 'v':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("v");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("v");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;

                    case 'w':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if it creates the word "while" then we create the while token
                        else if(Character.compare(program.charAt(i + 1), 'h')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'i')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'l')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'e')  == 0){

                                            Token.setKind("WHILE");
                                            Token.setSymbol("while");
                                            Token.setLineNumber(lineNumber);
                                            Token.setPosition(position);

                                            tokenOutput.add(Token);
                                        
                                            whileFlag = 1;

                                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                     }//if
                                }//if
                            }//if 
                        }//else if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("w");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("w");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case 'x':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //if our symbol si in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("x");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("x");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                        
                        break;

                    case 'y':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("y");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("y");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else

                        break;

                    case 'z':

                        //if our symbol is in a comment then we skip
                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        //else if our symbol is in a string then we create a char token
                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("z");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        //else we create an id token
                        else{

                            Token.setKind("ID");
                            Token.setSymbol("z");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;
                        
                    //by default we have an error
                    default:

                        //if we have a comment then we ignore the symbol
                        if(commentFlag == 1){
                            
                            break fullbreak;

                        }//if

                        //set the error flag if the symbol is unrecognized
                        ErrorFlag = 1;

                        //increment number of errors for output later
                        numberOfErrors++;

                        System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));
                    
                }//switch

                
		}//for

        //set error flag
        ErrorFlag(ErrorFlag);

        //return the arraylist of tokens
        return tokenOutput;
	
	}//Lex

}//Lexer
