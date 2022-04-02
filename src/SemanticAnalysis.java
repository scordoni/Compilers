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

    static SemanticAnalysisNode currentSemanticAnalysisNode;

    static ASTNode currentAstNode;

    static int rootScope = 0;

    // Initialize the result string.
    static String traversalResult = "";

    
    //This method is the main Semantic Analysis method
    public static void SAnalysis(ASTNode root){

        int i = 0;

        currentScope = 0;

        currentAstNode = root;

        //block
        System.out.println(currentAstNode.getName());
        System.out.println(currentAstNode.getSymbol());

        System.out.println(currentAstNode.children.size());


        for(int j = 0; j < currentAstNode.children.size(); j++){

            System.out.println(currentAstNode.children.get(j).getName());

        }


        
        traverse(currentAstNode);


        printSymbolTable();

        programNumber++;

    }//Semantic Analysis


    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static void traverse(ASTNode currentAstNode){
            
        // If there are no children (i.e., leaf nodes)...
        if ((currentAstNode.children.size() == 0) || (currentAstNode.children == null)){
                        
            //SemanticAnalysisNode SANode = new SemanticAnalysisNode();

            //add to hashtable

            //if collison in hashtable then we have a re-definition
            
            //if not initialized then error

        }//if

        else{
                
            //traverse through the AST
            for (int i = 0; i < currentAstNode.children.size(); i++){

                traverse(currentAstNode.children.get(i));
                
            }//for

        }//else
       
    }//traverse


    //print symbol table
    public static void printSymbolTable(){
            

        System.out.println(" ");
        System.out.println("Symbol Table for Program: " + programNumber);
        System.out.println("------------------------------------");
        System.out.println("    Name    Type    Scope   Line    ");
        System.out.println("------------------------------------");
        //System.out.println(SemanticAnalysisNode.getName());


    }//print symbol table


}//Semantic Analysis Class