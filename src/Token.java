/*
* 
* This is the token component of the compiler
* 
*/

public class Token {

    /**
    * Instance Variable for tokens 
    */
    private String myKind;
    private int myLineNumber;
    private int myPosition;
    
    /**
    * The default Constructor for token
    */
    public Token(){
        myKind = new String();
        myLineNumber = 0;
        myPosition = 0;
    }//Token
    
    /**
    * The full constructor for Token
    */
    public Token(String newKind, int newLineNumber, int newPosition){
        myKind = newKind;
        myLineNumber = newLineNumber;
        myPosition = newPosition;
    }//Token
    
    
}//Token Class
