/*
* 
* This is the token component of the compiler
* Here we create the token objects to store the tokens of our programs
* 
*/

public class Token {



    /**
    * Instance Variable for tokens 
    */
    private String myKind;
    private String mySymbol;
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

    /**
    * the setter for the item data
    * @param newKind the incoming data of the item
    */
    public void setKind(String newKind)
        {myKind = newKind;} //set kind

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public String getKind()
        {return myKind;}//get kind


    /**
    * the setter for the item data
    * @param newSymbol the incoming data of the item
    */
    public void setSymbol(String newSymbol)
        {mySymbol = newSymbol;} //set kind

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public String getSymbol()
        {return mySymbol;}//get kind


    /**
    * the setter for the item data
    * @param newLineNumber the incoming data of the item
    */
    public void setLineNumber(int newLineNumber)
        {myLineNumber = newLineNumber;} //set kind

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public int getLineNumber()
        {return myLineNumber;}//get kind

    /**
    * the setter for the item data
    * @param newPosition  the incoming data of the item
    */
    public void setPosition (int newPosition )
        {myPosition  = newPosition ;} //set kind

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public int getPosition ()
        {return myPosition ;}//get kind

    
    
}//Token Class
