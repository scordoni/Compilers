/*
* 
* This is the AST component of the compiler
* Here we take in the token stream and go through each token to create the AST
* 
*/


import java.util.*; 

public class ASTClass {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static int position = 0;

    static int programNumber = 1;

    static ASTNode root = null;

    static ASTNode current = null;

    // Initialize the result string.
    static String traversalResult = "";


    //This method pushes each letter of the array into the stack
    public static void AST(ArrayList<String> parseInput){

        //print out space for formating
        //System.out.println(" ");

        //System.out.println("AST For Program: " + programNumber);

        System.out.println(expand(root, 0));

    }//CST

    //This method creates the CST tree
    //taken from class power point
    public static void addNode(String kind, String label, String symbol){

        ASTNode newNode = new ASTNode();

        newNode.setName(label);

        newNode.setSymbol(symbol);

        if(root == null){

            root  = newNode;

            newNode.setParent(null);

            current = root;

        }//if

        else{

            newNode.setParent(current);

            newNode.getParent().children.add(newNode);

        }//else

        if ( kind.compareToIgnoreCase("leaf") != 0){

            current = newNode;

        }//if

        //System.out.println(" ");
        //System.out.println(newNode.getName());
        //System.out.println(newNode.getParent().getName());

        for(int i = 0; i < newNode.children.size(); i++){

            //System.out.println(newNode.children.get(i));

        }//for

        
    }//add node


    //This method moves up the pointer
    public static void moveUp(){

        current = current.getParent();

    }//move up


    
    // Recursive function to handle the expansion of the nodes.
    //Code given during class presentation
    public static String expand(ASTNode node, int depth){
            
        // Space out based on the current depth so
        // this looks at least a little tree-like.
        for (int i = 0; i < depth; i++){
            
            traversalResult += "-";

        }//for

        // If there are no children (i.e., leaf nodes)...
        if ((node.children.size() == 0) || (node.children == null)){
                
            // ... note the leaf node.
            traversalResult += "[" + node.getName() +  ", " + node.getSymbol() + "]";
            traversalResult += "\n";
        }//if

        else{
                
            // There are children, so note these interior/branch nodes and ...
            traversalResult += "<" + node.getName() + "> \n";

            // .. recursively expand them.


            for (int i = 0; i < node.children.size(); i++){

                
                expand(node.children.get(i), depth + 1);
                
            }//for

        }//else
        

        // Return the result.
        return traversalResult;

    }//expand


}//AST