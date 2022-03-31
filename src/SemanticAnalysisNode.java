/*
 * 
 * This creates the Semantic Analysis  nodes for the Semantic Analysis
 * 
 */

public class SemanticAnalysisNode 
{
   /**
    * Instance Variable for word data and node 
    */
   public String myName;
   public String myType;
   public int myScope;
   public int myIsInitilaized;
   public int myIsUsed;
   
   /**
    * The default Constructor for SemanticAnalysisNode
    */
   public SemanticAnalysisNode ()
    {
       myName = new String();
       myType = new String();
       myScope = 0;
       myIsInitilaized = 0;
       myIsUsed = 0;
    }//SemanticAnalysisNode
   
    /**
    * The full constructor for SemanticAnalysisNode
    * @param newName the incoming data of the item
    */
   public SemanticAnalysisNode (String newName, String newType, int newScope, int newIsInitilaized, int newIsUsed)
   {
        myName = newName;
        myType = newType;
        myScope = newScope;
        myIsInitilaized = newIsInitilaized;
        myIsUsed = newIsUsed;
        
   }//SemanticAnalysisNode

   
   /**
    * the setter for the item data
    * @param newName the incoming data of the item
    */
   public void setName(String newName)
       {myName = newName;} //set data
   
   /**
    * The getter for the item data
    * @return the incoming data of the item
    */
   public String getName()
       {return myName;}//get data

    /**
    * the setter for the item data
    * @param newType the incoming data of the item
    */
   public void setType(String newType)
   {myType = newType;} //set data

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public String getType()
    {return myType;}//get data
    
   /**
    * The setter for the node
    * @param NewScope  the incoming node data
    */
   public void setScope (int newScope )
       {myScope  = newScope ;}//set Node
   
   /**
    * the getter for the node
    * @return the incoming node data
    */
   public int getScope ()
       { return myScope ;}//get node

    /**
    * The setter for the node
    * @param NewIsInitilaized the incoming node data
    */
   public void setIsInitilaized(int newIsInitilaized)
   {myIsInitilaized  = newIsInitilaized;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public int getIsInitilaized()
    { return myIsInitilaized;}//get node

    /**
    * The setter for the node
    * @param NewIsUsed the incoming node data
    */
   public void setIsUsed(int newIsUsed)
   {myIsUsed  = newIsUsed;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public int getIsUsed()
    { return myIsUsed ;}//get node

}//SemanticAnalysisNode 
