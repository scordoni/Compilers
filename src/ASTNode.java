import java.util.ArrayList;

/*
 * 
 * This creates the AST nodes for the AST
 * 
 */

public class ASTNode
{
   /**
    * Instance Variable for word data and node 
    */
   public String myName;
   public String mySymbol;
   public ASTNode myParent;
   public  ArrayList <ASTNode> children = new ArrayList <ASTNode>();
   
   /**
    * The default Constructor for ASTNode
    */
   public ASTNode()
    {
       myName = new String();
       mySymbol = new String();
       myParent = null;
    }//CSTNode
   
    /**
    * The full constructor for NodeCordoni
    * @param newName the incoming data of the item
    */
   public ASTNode(String newName, String newSymbol)
   {
        myName = newName;
        mySymbol = newSymbol;
        myParent = null;
        
   }//NodeCordoni

   
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
    * @param newSymbol the incoming data of the item
    */
   public void setSymbol(String newSymbol)
   {mySymbol = newSymbol;} //set data

    /**
    * The getter for the item data
    * @return the incoming data of the item
    */
    public String getSymbol()
    {return mySymbol;}//get data
    
   /**
    * The setter for the node
    * @param NewNext the incoming node data
    */
   public void setParent(ASTNode newParent)
       {myParent = newParent;}//set Node
   
   /**
    * the getter for the node
    * @return the incoming node data
    */
   public ASTNode getParent()
       { return myParent;}//get node




}//ASTNode
