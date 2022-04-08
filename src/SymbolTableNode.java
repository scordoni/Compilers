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
    public Hashtable< String, SemanticAnalysisNode > mySymbolTable = new Hashtable <String, SemanticAnalysisNode>();

    public SymbolTableNode myNext;

    public SymbolTableNode myRoot;

    public SymbolTableNode myParent;
    
    /**
    * The default Constructor for SemanticAnalysisNode
    */
    public SymbolTableNode()
        {

        mySymbolTable = new Hashtable <String, SemanticAnalysisNode> ();
        myNext = null;

        }//SymbolTableNode
    
    /**
    * The full constructor for SemanticAnalysisNode
    * @param newName the incoming data of the item
    */
    public SymbolTableNode (Hashtable<String, SemanticAnalysisNode> newSTable)
    {
            mySymbolTable = newSTable;
            myNext = null;
    }//SemanticAnalysisNode


    /**
    * The setter for the node
    * @param NewNext the incoming node data
    */
    public void setNext(SymbolTableNode  newTable)
        {myNext = newTable;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public SymbolTableNode  getNext()
        {return myNext;}//get node
        
    /**
    * The setter for the node
    * @param NewRoot the incoming node data
    */
    public void setRoot(SymbolTableNode  newRoot)
        {myRoot = newRoot;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public SymbolTableNode  getRoot()
        {return myRoot;}//get node


     /**
    * The setter for the node
    * @param NewParent the incoming node data
    */
    public void setParent(SymbolTableNode  newParent)
        {myParent = newParent;}//set Node

    /**
    * the getter for the node
    * @return the incoming node data
    */
    public SymbolTableNode  getParent()
        {return myParent;}//get node

}//symbol table node
