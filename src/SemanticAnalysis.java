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

    static int ErrorFlag;

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

        System.out.println(" ");
        System.out.println(" ");

        //reset variables
        ErrorFlag = 0;

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

        //print any possible warnings
        printWarnings();

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

        //if we have a variable declaration then we create a new node for the symbol table
        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            SANode.setType(currentAstNode.children.get(0).getSymbol());

            SANode.setName(currentAstNode.children.get(1).getSymbol());

            SANode.setScope(currentScope);

            SANode.setIsInitilaized(false);

            SANode.setIsUsed(false);

            //if collison in hashtable then we have a re-definition
            if( currentTable.containsKey(currentAstNode.children.get(1).getSymbol())){

                System.out.println("Error: " + currentAstNode.children.get(1).getSymbol() + " has been re-defined ");

                ErrorFlag = 1;

            }//if

            //add to hashtable
            currentTable.put(currentAstNode.children.get(1).getSymbol(), SANode );

        }//if

        //if we have an assignment stmt then we must check for type matching
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            //if it is in the hashtable then it exsists 
            if( currentTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                //if the symbol is an integer then we have to make sure we assign an integer
                if(currentTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("int") == 0){

                    //if we assign an int to the variable then it is initialized
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){

                        currentTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                    }//if

                    //otherwise we have a type mismatch
                    else{

                        System.out.println("Error: Type mismatch error for variable assignment ");

                        ErrorFlag = 1;

                    }//else

                };

                //if the symbol is an string then we have to make sure we assign a string
                if(currentTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("string") == 0){

                    //to save room if we assign it an integer or a boolean it is a type mismatch
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                        System.out.println("Error: Type mismatch error for variable assignment ");

                        ErrorFlag = 1;

                    }//if

                    //otherwise the string is initalized
                    else{

                        currentTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                    }//else

                };

                //if the symbol is an string then we have to make sure we assign a string
                if(currentTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("boolean") == 0){

                    //if we assign a boolean value to the variable then it is initialized.
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                        currentTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                    }//if

                    //otherwise it is a type mismatch error
                    else{

                        System.out.println("Error: Type mismatch error for variable assignment ");

                        ErrorFlag = 1;

                    }//else

                };
                
                
            }//if

            //if it is not then it doesn't exist.
            else{

                System.out.println("Error: " + currentAstNode.children.get(0).getSymbol() + " does not exsist ");

                ErrorFlag = 1;

            }//else
            

        }//if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

            //if it is in the hashtable then it exsists 
            if( currentTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                currentTable.get(currentAstNode.children.get(0).getSymbol()).setIsUsed(true);
                
            }//if
            
            //if it is not then it doesn't exist.
            else{

                System.out.println("Error: " + currentAstNode.children.get(0).getSymbol() + " does not exsist ");

                ErrorFlag = 1;

            }//else

        }//if

        if(currentAstNode.getName().compareToIgnoreCase("IfStatement") == 0){

            //if it is in the hashtable then it exsists 
            if( currentTable.containsKey(currentAstNode.children.get(1).getSymbol()) == true){

                currentTable.get(currentAstNode.children.get(1).getSymbol()).setIsUsed(true);
                
            }//if

            //if it is not then it doesn't exist.
            else{

                System.out.println("Error: " + currentAstNode.children.get(0).getSymbol() + " does not exsist ");

                ErrorFlag = 1;

            }//else
            

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
    public static void printWarnings(){
            

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Warnings for program: " + programNumber);
        
        //print code found online at https://www.javacodeexamples.com/print-hashtable-in-java-example/3154
        Iterator<SemanticAnalysisNode> itr = currentTable.values().iterator();
 
        while(itr.hasNext()){

            SemanticAnalysisNode temp = itr.next();

            if ( temp.myIsUsed == false){

                System.out.println(temp.myName + " has not been used");

            }//if

            if ( temp.myIsInitilaized == false){

                System.out.println(temp.myName + " has not been initialized");

            }//if
        }//while
        
        


    }//print warnings

    //print symbol table
    public static void printSymbolTable(){
            
        System.out.println(" ");
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