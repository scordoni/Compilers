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

    static Hashtable < String, SemanticAnalysisNode > currentTable;

    // Initialize the result string.
    static String traversalResult = "";

    static int i = 0;
    
    //This method is the main Semantic Analysis method
    public static void SAnalysis(ASTNode root){



        currentScope = 0;

        currentAstNode = root;

        //create hastable for scope 0
        Hashtable< String, SemanticAnalysisNode > ScopeTable = new Hashtable <>();

        currentTable = ScopeTable;

        //move pointer from program to block
        //this is the only way to make this work so ill take the points off for having to keep "Program" in the AST
        currentAstNode = currentAstNode.children.get(i);

        //traverse through the AST for scope checking
        traverse(currentAstNode);

        //print the symbol table
        printSymbolTable();

        //increment program number
        programNumber++;

    }//Semantic Analysis


    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static void traverse(ASTNode currentAstNode){

        

        //create and set node for hashtable
        SemanticAnalysisNode SANode = new SemanticAnalysisNode();
            
        //System.out.println(currentAstNode.getName());

        

        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            SANode.setType(currentAstNode.children.get(0).getSymbol());

            SANode.setName(currentAstNode.children.get(1).getSymbol());

            SANode.setScope(currentScope);

            SANode.setIsInitilaized(false);

            SANode.setIsUsed(false);

            //if collison in hashtable then we have a re-definition
            if( currentTable.containsKey(currentAstNode.children.get(1).getSymbol())){

                System.out.println( currentAstNode.children.get(1).getSymbol() + " has been re-defined ");

            }//if

            //add to hashtable
            currentTable.put(currentAstNode.children.get(1).getSymbol(), SANode );

        }//if

        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            //if it is in the hashtable then it exsists 
            if( currentTable.containsKey(currentAstNode.children.get(0).getSymbol())){

                System.out.println(SANode.getName());
                SANode.setIsInitilaized(true);

            }//if
            

        }//if




        else{
                
            //traverse through the AST
            for (int i = 0; i < currentAstNode.children.size(); i++){

                if(currentAstNode.children.get(i).getName().compareToIgnoreCase("Block") == 0){

                    currentScope++;

                    

                }//if

                traverse(currentAstNode.children.get(i));
                
            }//for

        }//else
       
    }//traverse


    //print symbol table
    public static void printSymbolTable(){
            

        System.out.println(" ");
        System.out.println("Symbol Table for Program: " + programNumber);
        System.out.println("------------------------------------------------------------");
        System.out.println("    Name    Type    Scope   IsUsed  IsInitialized   Line    ");
        System.out.println("------------------------------------------------------------");

        
        //print code found online at https://www.javacodeexamples.com/print-hashtable-in-java-example/3154
        Iterator<SemanticAnalysisNode> itr = currentTable.values().iterator();
 
        while(itr.hasNext()){

            SemanticAnalysisNode temp = itr.next();

            System.out.print("    " + temp.myName);
            System.out.print("       " + temp.myType);
            System.out.print("      " + temp.myScope);
            System.out.print("       " + temp.myIsUsed);
            System.out.print("       " + temp.myIsInitilaized);
            System.out.print("         " +temp.myLine);

            System.out.println("    ");
        }//while
        
        


    }//print symbol table


}//Semantic Analysis Class