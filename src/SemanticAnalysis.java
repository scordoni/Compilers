/*
* 
* This is the Semantic Analysis component of the compiler
* Here we look at scope and type checking
* 
*/


import java.util.*; 
import java.util.Hashtable;

public class SemanticAnalysis {

    //Declare Variables
    static Token theToken = new Token();

    static char character;

    static ArrayList <Token> globalTokens= new ArrayList <Token>();

    static Hashtable< String, SemanticAnalysisNode > sTable = new Hashtable <>();

    static int position = 0;

    static int programNumber = 1;

    static int lineNumber = 0;

    static int ErrorFlag = 0;

    static int numberOfErrors = 0;

    static int j = 0;

    static int currentScope = 0;

    static int rootScope = 0;

    // Initialize the result string.
    static String traversalResult = "";

    
    //This method is the main Semantic Analysis method
    public static void SAnalysis(ASTNode root){

        //create a hashtable to keep track of scope

        currentScope = 0;

        SemanticAnalysisNode SANode = new SemanticAnalysisNode();



        //grab symbol
        



        System.out.println(" ");
        System.out.println("Symbol Table for Program: " + programNumber);
        System.out.println("------------------------------------");
        System.out.println("    Name    Type    Scope   Line    ");
        System.out.println("------------------------------------");


        programNumber++;

    }//Semantic Analysis


    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static String traverse(ASTNode node, int depth){
            
        // Space out based on the current depth so
        // this looks at least a little tree-like.
        for (int i = 0; i < depth; i++){
            
            traversalResult += "-";

        }//for

        // If there are no children (i.e., leaf nodes)...
        if ((node.children.size() == 0) || (node.children == null)){
                
            

           
            // ... note the leaf node.
            traversalResult += "[" + node.getSymbol() + "]";
            traversalResult += "\n";
            

        }//if

        else{
                
            // There are children, so note these interior/branch nodes and ...
            traversalResult += "<" + node.getName() + "> \n";

            // .. recursively expand them.


            for (int i = 0; i < node.children.size(); i++){

                
                traverse(node.children.get(i), depth + 1);
                
            }//for

        }//else
        

        // Return the result.
        return traversalResult;

    }//expand




}//Semantic Analysis Class