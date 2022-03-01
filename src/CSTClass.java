/*
* 
* This is the CST component of the compiler
* Here we take in the token stream and go through each token to create the CST
* 
*/


import java.util.*; 

public class CSTClass {

    //Declare Variables
    static Token theToken = new Token();

    static ArrayList <String> AST = new ArrayList <String>();

    static char character;

    static int position = 0;

    static int programNumber = 1;

    static CSTNode root = new CSTNode();

    static CSTNode current = new CSTNode();

    // Initialize the result string.
    static String traversalResult = "";


    //This method pushes each letter of the array into the stack
    public static ArrayList<String> CST(ArrayList<String> parseInput){

        //print out space for formating
        System.out.println(" ");

        System.out.println("CST For Program: " + programNumber);

        programNumber++;

        return AST;



    }//CST

    //This method creates the CST tree
    public static void addNode(String kind, String label){

        CSTNode newNode = new CSTNode();

        newNode.setName(label);

        if(root == null){

            root  = newNode;

            newNode.setParent(null);

        }//if

        else{

            newNode.setParent(current);

            newNode.getParent().children.add(newNode);

        }//else

        if ( kind.compareToIgnoreCase("leaf") != 0){

            current = newNode;

        }//if

    }//add node


    //This method moves up the pointer
    public static void moveUp(){

        current = current.getParent();

    }//move up


    
    // Recursive function to handle the expansion of the nodes.
    public static String expand(CSTNode node, int depth){
            
        // Space out based on the current depth so
        // this looks at least a little tree-like.
        for (int i = 0; i < depth; i++){
            
            traversalResult += "-";

        }//for

        // If there are no children (i.e., leaf nodes)...
        if ((node.children.size()==0)){
                
            // ... note the leaf node.
            traversalResult += "[" + node.getName() + "]";
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
        
        // Make the initial call to expand from the root.
        expand(root, 0);

        // Return the result.
        return traversalResult;

    }//expand


}//CST