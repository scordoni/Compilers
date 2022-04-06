/*
 * 
 * This creates the nodes for the Symbol table
 * 
 */

import java.util.Hashtable;

public class SymbolTableNode {
    
    /**
    * Instance Variable for word data and node 
    */
    public Hashtable< String, SemanticAnalysisNode > mySTable = new Hashtable <String, SemanticAnalysisNode>();

    public SymbolTableNode myNext;
    
    /**
    * The default Constructor for SemanticAnalysisNode
    */
    public SymbolTableNode()
        {

        mySTable = new Hashtable <String, SemanticAnalysisNode> ();
        myNext = null;

        }//SymbolTableNode
    
    /**
    * The full constructor for SemanticAnalysisNode
    * @param newName the incoming data of the item
    */
    public SymbolTableNode (Hashtable<String, SemanticAnalysisNode> newSTable)
    {
            mySTable = newSTable;
            myNext = null;
    }//SemanticAnalysisNode


    /**
    * The setter for the node
    * @param NewNext the incoming node data
    */
    public void setNext(SymbolTableNode  nextTable)
        {myNext = nextTable;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public SymbolTableNode  getNext()
        {return myNext;}//get node
        

}//symbol table node
