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
    public Hashtable< String, SemanticAnalysisNode > mySTable = new Hashtable <>();
    
    /**
    * The default Constructor for SemanticAnalysisNode
    */
    public SymbolTableNode()
        {

        mySTable = new Hashtable();

        }//SymbolTableNode
    
    /**
    * The full constructor for SemanticAnalysisNode
    * @param newName the incoming data of the item
    */
    public SymbolTableNode (Hashtable newSTable)
    {
            mySTable = newSTable;
    }//SemanticAnalysisNode

}//symbol table node
