/*
* 
* This is the Lexer component of the compiler
* 
*/


import java.util.*; 

public class Lexer {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static ArrayList <Token> tokenOutput = new ArrayList <Token>();

    static int position = 0;

    static int lineNumber = 0;

    int programCounter = 2;

    int lastPosition = 0;

    int currentPosition =0;

    static int commentFlag = 0;

    static int stringFlag = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int intFlag = 0;

    static int braceFlag = 0;

    static int parenFlag = 0;

    //This method pushes each letter of the array into the stack
	public static ArrayList<Token> Lex(String program){

        System.out.println(" ");
        
        Token Token = new Token();
        
        
		//goes through the arraylist
		for(int i = 0; i < program.length(); i++){
			
            //System.out.println(program.charAt(i));

            
            
            character = program.charAt(i);


            fullbreak:


                switch(character){

                    //To start we have all the special characters

                    case '{':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        Token.setKind("L_BRACE");
                        Token.setSymbol("{");
                        Token.setLineNumber(lineNumber);
                        Token.setPosition(position);

                        tokenOutput.add(Token);
                        
                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;
                    

                    case '}':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        Token.setKind("R_BRACE");
                        Token.setSymbol("}");
                        Token.setLineNumber(lineNumber);
                        Token.setPosition(position);

                        tokenOutput.add(Token);
                        
                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        break;

                    case '$':

                        if(commentFlag == 1){


                            System.out.println("ERROR - Unclosed comment found at " + "(" + lineNumber + ":" + position + ")");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            commentFlag = 0;

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){


                            System.out.println("ERROR - Unclosed string found at " + "(" + lineNumber + ":" + position + ")");

                            System.out.println("ERROR Lexer - Lex failed");

                            System.out.println(" ");

                            System.out.println(" ");


                            stringFlag = 0;

                            break fullbreak;

                        }//if

                        else{

                            Token.setKind("EOP");
                            Token.setSymbol("$");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");


                            if (ErrorFlag == 1){
                                System.out.println("ERROR Lexer - Lex failed with " + numberOfErrors + " error(s)");

                                ErrorFlag = 0;
                            }//if

                            else{

                                System.out.println("INFO  Lexer - Lex completed with 0 errors");

                            }//else

                            System.out.println(" ");

                            System.out.println(" ");

                            System.out.println(" ");

                            System.out.println(" ");

                        }//else
                        
                        break;

                    case '(':


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){

                            Token.setKind("CHAR ");
                            Token.setSymbol("(");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

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


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){

                            Token.setKind("CHAR ");
                            Token.setSymbol(")");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

                        else{

                            parenFlag = 1;

                            Token.setKind("CLOSE_PAREN");
                            Token.setSymbol(")");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");

                        }//else
                    
                        break;

                    case '\"':


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 0){

                            stringFlag = 1;

                            Token.setKind("OPEN_QUOTE");
                            Token.setSymbol("\"");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

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


                        if(Character.compare(program.charAt(i + 1), '*') == 0){

                            commentFlag = 1;

                        }//if

                        else if(Character.compare(program.charAt(i - 1), '*')  == 0){

                            commentFlag = 0;

                        }//if
                    
                        break;


                    case '+':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if (stringFlag == 1){


                            Token.setKind("CHAR");
                            Token.setSymbol("+");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        break;

                    case '!':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(Character.compare(program.charAt(i + 1), '=')  == 0){

                            Token.setKind("NON_EQUALITY");
                            Token.setSymbol("!=");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        break;

                        

                    case '=':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(Character.compare(program.charAt(i + 1), '=')  == 0){

                            Token.setKind("EQUALITY");
                            Token.setSymbol("==");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        }//else if


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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("1");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("2");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("3");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("4");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("5");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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


                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("6");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("7");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("8");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("9");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        else{
                            Token.setKind("DIGIT");
                            Token.setSymbol("9");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                            
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ]  found at " + "(" + lineNumber + ":" + position + ")");
                            
                        }//else

                        break;

                    case 'a':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("a");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

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
                                                                    

                                            }//if
                                        }//if
                                    }//if
                                }//if
                            }//if
                        }//else if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("b");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("c");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("d");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("e");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(Character.compare(program.charAt(i + 1), 'a')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'l')  == 0){

                                if(Character.compare(program.charAt(i + 3), 's')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'e')  == 0){

                                        Token.setKind("FALSE");
                                        Token.setSymbol("false");
                                        Token.setLineNumber(lineNumber);
                                        Token.setPosition(position);

                                        tokenOutput.add(Token);
                                    
                                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                    }//if
                                }//if
                            }//if
                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("f");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("g");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("h");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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


                        if(Character.compare(program.charAt(i + 1), 'n')  == 0){

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

                        else if(Character.compare(program.charAt(i + 1), 'f')  == 0){

                            Token.setKind("IF");
                            Token.setSymbol("if");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                        
                        }//if

                        else if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("i");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("j");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("k");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("l");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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
                    

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("m");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(intFlag == 1){

                            break fullbreak;

                        }//else if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("n");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                            break fullbreak;

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("o");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

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
                                        

                                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                        }//if
                                    }//if
                                }//if
                            }//if 
                        }//else if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("p");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("q");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("r");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(Character.compare(program.charAt(i + 1), 't')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'r')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'i')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'n')  == 0){
                                       
                                        if(Character.compare(program.charAt(i + 5), 'g')  == 0){
                                                Token.setKind("STRING");
                                                Token.setSymbol("string");
                                                Token.setLineNumber(lineNumber);
                                                Token.setPosition(position);

                                                tokenOutput.add(Token);
                                        

                                                System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        

                                        }//if
                                     }//if
                                }//if
                            }//if 
                        }//else if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("s");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if


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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(intFlag == 1){

                            intFlag = 0;
                            

                        }//else if

                        else if(Character.compare(program.charAt(i + 1), 'r')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'u')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'e')  == 0){

                                        Token.setKind("TRUE");
                                        Token.setSymbol("true");
                                        Token.setLineNumber(lineNumber);
                                        Token.setPosition(position);

                                        tokenOutput.add(Token);
                                    
                                        System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                                }//if
                            }//if
                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("t");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        else{

                            Token.setKind("ID");
                            Token.setSymbol("t");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;

                    case 'u':

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("u");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("v");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(Character.compare(program.charAt(i + 1), 'h')  == 0){

                            if(Character.compare(program.charAt(i + 2), 'i')  == 0){

                                if(Character.compare(program.charAt(i + 3), 'l')  == 0){

                                    if(Character.compare(program.charAt(i + 4), 'e')  == 0){

                                            Token.setKind("WHILE");
                                            Token.setSymbol("while");
                                            Token.setLineNumber(lineNumber);
                                            Token.setPosition(position);

                                            tokenOutput.add(Token);
                                        

                                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");
                                        
                                     }//if
                                }//if
                            }//if 
                        }//else if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("w");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("x");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("y");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

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

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        else if(stringFlag == 1){

                            Token.setKind("CHAR");
                            Token.setSymbol("z");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");

                        }//else if

                        else{

                            Token.setKind("ID");
                            Token.setSymbol("z");
                            Token.setLineNumber(lineNumber);
                            Token.setPosition(position);

                            tokenOutput.add(Token);
                        
                            System.out.println("DEBUG Lexer - " + Token.getKind() + " [ " + Token.getSymbol() + " ] found at " + "(" + lineNumber + ":" + position + ")");


                        }//else
                        
                        break;
                        
                    default:

                        if(commentFlag == 1){

                            break fullbreak;

                        }//if

                        ErrorFlag = 1;
                        numberOfErrors++;
                        System.out.println("ERROR Lexer - Error:4:40 Unrecognized Token: " + program.charAt(i));
                    
                
                }//switch

                

		}//for

  

        return tokenOutput;
			
	}//Lex


}//Lexer
