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
    static int programNumber = 1;

    static int lineNumber = 0;

    public static int ErrorFlag;

    static int WarningFlag;

    static int j = 0;

    static int currentScope = -1;

    static int tempScope = -1;

    static SemanticAnalysisNode currentSemanticAnalysisNode;

    public static ArrayList <SymbolTableNode> symbolTableList;

    static ASTNode currentAstNode;

    static SymbolTableNode currentSymbolTableNode;

    static SymbolTableNode tempNode;

    static SymbolTableNode currentSymbolTableNodeParent;

    static int rootScope = 0;

    static Hashtable < String, SemanticAnalysisNode > currentTable;

    static int i = 0;

    static int variableSize;
    
    //This method is the main Semantic Analysis method
    public static void SAnalysis(ASTNode root){

        System.out.println(" ");
        System.out.println(" ");

        //reset variables
        ErrorFlag = 0;
        lineNumber = 0;
        
        ArrayList <SymbolTableNode> symbolTableList= new ArrayList <SymbolTableNode>();

        SymbolTableNode initSymbolTableNode = new SymbolTableNode();

        symbolTableList.add(initSymbolTableNode);

        currentScope = -1;

        currentAstNode = root;

        variableSize = 0;


        //move pointer from program to block
        //this is the only way to make this work so ill take the points off for having to keep "Program" in the AST
        currentAstNode = currentAstNode.children.get(i);

        currentSymbolTableNode = initSymbolTableNode;

        System.out.println("INFO: SEMANTIC ANALYSIS START");

        //traverse through the AST for scope checking
        traverse(currentAstNode, currentSymbolTableNode, symbolTableList);

        while(currentScope != 0 ){

            System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);

            currentScope--;

            System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope);

        }//while

        if(currentScope == 0){

            System.out.println("SEMANTIC ANALYSIS: At scope " + currentScope);

        }//if


        //if we have warnings then print the warning table else skip
        if(WarningFlag == 1){

            //print any possible warnings
            printWarnings(symbolTableList);
        
        }//if

        else{

            

        }//else

       
        //if we have error then output error message and no table or output symbol table

        if(ErrorFlag == 1){

            System.out.println(" ");
            System.out.println("Due to Semantic Analysis Error the Symbol Table was not produced.");
            System.out.println("Program Compilation Halted");
        
        }//if

        else{

            System.out.println(" ");
            System.out.println("SEMANTIC ANALYSIS SUCCESSFUL");

            //print the symbol table
            printSymbolTable(symbolTableList);

        }//else

        //increment program number
        programNumber++;

    }//Semantic Analysis


    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static void traverse(ASTNode currentAstNode, SymbolTableNode currentSymbolTableNode, ArrayList<SymbolTableNode>  symbolTableList){

        //create and set node for hashtable
        SemanticAnalysisNode SANode = new SemanticAnalysisNode();

        //System.out.println(currentAstNode.getName());

        //System.out.println(currentScope);

        //if we have block stmt then we need a new table and to up the scope
        if(currentAstNode.getName().compareToIgnoreCase("Block") == 0){

            System.out.println("SEMANTIC ANALYSIS: Block");

            if(currentScope == -1){
                
                currentScope++;

                System.out.println("SEMANTIC ANALYSIS: Entering scope 0");

            }//if

            //else we create a new hashtable and up the scope for the new block of code
            else{
                
                System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);

                currentScope++;

                System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope);
            
                SymbolTableNode nextTable = new SymbolTableNode();

                currentSymbolTableNode.setNext(nextTable);

                nextTable.setParent(currentSymbolTableNode);

                currentSymbolTableNode = nextTable;

                symbolTableList.add(nextTable);


            }//else

        }//if


        //if we have a variable declaration then we create a new node for the symbol table
        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            System.out.println("SEMANTIC ANALYSIS: Variable Declaration");

            //if collison in hashtable then we have a re-definition
            if( currentSymbolTableNode.mySymbolTable.containsKey(currentAstNode.children.get(1).getSymbol())){

                System.out.println(" ");
                System.out.println("Error: Variable Declaration Error: variable  \"" + currentAstNode.children.get(1).getSymbol() + "\" has been re-defined ");
                System.out.println(" ");

                ErrorFlag = 1;

            }//if
            
            //else we create a new semantic analysis node to add to the symbol table
            else{

                SANode.setType(currentAstNode.children.get(0).getSymbol());

                SANode.setName(currentAstNode.children.get(1).getSymbol());

                SANode.setScope(currentScope);

                SANode.setIsInitilaized(false);

                SANode.setIsUsed(false);

                SANode.setLine(lineNumber);

                lineNumber++;

                variableSize++;


                //System.out.println(SANode.getName());
                //System.out.println(SANode.getType());

                System.out.println("SEMANTIC ANALYSIS: Variable Declaration of type " + SANode.getType() + " with name \"" + SANode.getName() + "\" of scope " + SANode.getScope());
               
                //add to hashtable
                currentSymbolTableNode.mySymbolTable.put(currentAstNode.children.get(1).getSymbol(), SANode );

            }//else
            

        }//if

        //if we have an assignment stmt then we must check for type matching
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            System.out.println("SEMANTIC ANALYSIS: Assignment Statement");

            //if it is in the hashtable then it exsists 
            if( currentSymbolTableNode.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                //if the symbol is an integer then we have to make sure we assign an integer
                if(currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("int") == 0){

                    //if we assign an int to the variable then it is initialized
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){

                            currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                            System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and initialized");

                    }//if

                    //otherwise we have a type mismatch
                    else{

                        System.out.println(" ");
                        System.out.println("Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\" assignment ");
                        System.out.println(" ");

                        ErrorFlag = 1;

                    }//else

                };

                //if the symbol is an string then we have to make sure we assign a string
                if(currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("string") == 0){

                    //to save room if we assign it an integer or a boolean it is a type mismatch
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                        || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                        System.out.println(" ");
                        System.out.println("Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\" assignment ");
                        System.out.println(" ");

                        ErrorFlag = 1;

                    }//if

                    //otherwise the string is initalized
                    else{

                        currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                        System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and initialized");

                    }//else

                };

                //if the symbol is an string then we have to make sure we assign a string
                if(currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("boolean") == 0){

                    //if we assign a boolean value to the variable then it is initialized.
                    if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                        currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);

                        System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been initialized");
                    }//if

                    //otherwise it is a type mismatch error
                    else{

                        System.out.println(" ");
                        System.out.println("Error: Assignment Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\"  assignment ");
                        System.out.println(" ");

                        ErrorFlag = 1;

                    }//else

                };
                
                
            }//if

            //we loop through the previous hashtables or scope to see if the variable was defined before we
            //entered the current scope, if the variable is not defined in any scope then the variable is 
            //not defined at all and an error thrown.
            else{

                tempNode = currentSymbolTableNode;

                tempScope = currentScope;

                currentSymbolTableNodeParent = currentSymbolTableNode;


                while(currentSymbolTableNodeParent.getParent() != null){

                    System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);
    
                    System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope--);
                    currentSymbolTableNodeParent = currentSymbolTableNodeParent.getParent();

                    //if it is in the hashtable then it exsists 
                    if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                        //if the symbol is an integer then we have to make sure we assign an integer
                        if(currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("int") == 0){

                            //if we assign an int to the variable then it is initialized
                            if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){

                                    currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);
                                    
                                    System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and initialized");

                                    break;
                            }//if

                            //otherwise we have a type mismatch
                            else{

                                System.out.println(" ");
                                System.out.println("Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\" assignment ");
                                System.out.println(" ");

                                ErrorFlag = 1;

                            }//else

                        };

                        //if the symbol is an string then we have to make sure we assign a string
                        if(currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("string") == 0){

                            //to save room if we assign it an integer or a boolean it is a type mismatch
                            if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
                                || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                                System.out.println(" ");
                                System.out.println("Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\" assignment ");
                                System.out.println(" ");

                                ErrorFlag = 1;

                            }//if

                            //otherwise the string is initalized
                            else{

                                
                                currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);
                                
                                System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and initialized");

                                break;

                            }//else

                        };

                        //if the symbol is an string then we have to make sure we assign a string
                        if(currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).getType().compareToIgnoreCase("boolean") == 0){

                            //if we assign a boolean value to the variable then it is initialized.
                            if ((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false")==0)){

                                currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsInitilaized(true);
                                
                                System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and initialized");

                                break;
                            }//if

                            //otherwise it is a type mismatch error
                            else{

                                System.out.println(" ");
                                System.out.println("Error: Assignment Error: Type mismatch error for variable \"" + currentAstNode.children.get(0).getSymbol() + "\" assignment ");
                                System.out.println(" ");

                                ErrorFlag = 1;

                            }//else

                        };
                        
                    }//if

                }//while

                //if the variable is not found at all then it does not exsist
                if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == false){

                    System.out.println(" ");
                    System.out.println("Error: Assignment Error: \"" + currentAstNode.children.get(0).getSymbol() + "\" does not exsist ");
                    System.out.println(" ");

                    ErrorFlag = 1;

                }//if


                //reset variables to where they were before backtrack

                currentSymbolTableNode = tempNode;

                currentScope = tempScope;

                System.out.println("SEMANTIC ANALYSIS: Back to scope " + currentScope);

            }//else
            
        }//if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

            System.out.println("SEMANTIC ANALYSIS: Print Statement");

            //if it is in the hashtable then it exsists 
            if( currentSymbolTableNode.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsUsed(true);
                
                System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

            }//if
            
            //we loop through the previous hashtables or scope to see if the variable was defined before we
            //entered the current scope, if the variable is not defined in any scope then the variable is 
            //not defined at all and an error thrown.
            else{

                tempNode = currentSymbolTableNode;

                tempScope = currentScope;

                currentSymbolTableNodeParent = currentSymbolTableNode;

                while(currentSymbolTableNodeParent.getParent() != null){

                    System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);
    
                    System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope--);

                    currentSymbolTableNodeParent = currentSymbolTableNodeParent.getParent();

                    //if it is in the hashtable then it exsists 
                    if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                        currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsUsed(true);
                        
                        System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

                    }//if

                }//while

                //if the variable is not found at all then it does not exsist
                if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == false){

                    System.out.println(" ");
                    System.out.println("Error: Print Statement Error: \"" + currentAstNode.children.get(0).getSymbol() + "\" does not exsist ");
                    System.out.println(" ");

                    ErrorFlag = 1;

                }//if

                //reset variables to what they were before backtrack

                currentSymbolTableNode = tempNode;
                
                currentScope = tempScope;

                System.out.println("SEMANTIC ANALYSIS: Back to scope " + currentScope);
                

            }//else

        }//if

        //if the node is equal to an if statement
        if(currentAstNode.getName().compareToIgnoreCase("IfStatement") == 0){
            
            System.out.println("SEMANTIC ANALYSIS: If Statement");

            //if it is in the hashtable then it exsists 
            if( currentSymbolTableNode.mySymbolTable.containsKey(currentAstNode.children.get(1).getSymbol()) == true){

                currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(1).getSymbol()).setIsUsed(true);
                
                System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

            }//if

            //we loop through the previous hashtables or scope to see if the variable was defined before we
            //entered the current scope, if the variable is not defined in any scope then the variable is 
            //not defined at all and an error thrown.
            else{

                tempNode = currentSymbolTableNode;

                currentSymbolTableNodeParent = currentSymbolTableNode;

                while(currentSymbolTableNodeParent.getParent() != null){

                    System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);

                    System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope--);

                    currentSymbolTableNodeParent = currentSymbolTableNodeParent.getParent();

                    //if it is in the hashtable then it exsists 
                    if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == true){

                        currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(0).getSymbol()).setIsUsed(true);
                        
                        System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

                    }//if

                }//while

                if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(0).getSymbol()) == false){

                    System.out.println(" ");
                    System.out.println("Error: If Statement Error: \"" + currentAstNode.children.get(1).getSymbol() + "\" does not exsist ");
                    System.out.println(" ");

                    ErrorFlag = 1;

                }//if

                //reset variables to where they were before backtrack

                currentSymbolTableNode = tempNode;

                currentScope = tempScope;

                System.out.println("SEMANTIC ANALYSIS: Back to scope " + currentScope);

            }//else
            
        }//if

        //if the node is equal to a while statement
        if(currentAstNode.getName().compareToIgnoreCase("WhileStatement") == 0){

            System.out.println("SEMANTIC ANALYSIS: While Statement");
            
            //if it is in the hashtable then it exsists 
            if( currentSymbolTableNode.mySymbolTable.containsKey(currentAstNode.children.get(1).getSymbol()) == true){

                currentSymbolTableNode.mySymbolTable.get(currentAstNode.children.get(1).getSymbol()).setIsUsed(true);
                
                System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

            }//if

            //we loop through the previous hashtables or scope to see if the variable was defined before we
            //entered the current scope, if the variable is not defined in any scope then the variable is 
            //not defined at all and an error thrown.
            else{

                tempNode = currentSymbolTableNode;

                currentSymbolTableNodeParent = currentSymbolTableNode;

                while(currentSymbolTableNodeParent.getParent() != null){

                    System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);
    
                    System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope--);

                    currentSymbolTableNodeParent = currentSymbolTableNodeParent.getParent();

                    //if it is in the hashtable then it exsists 
                    if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(1).getSymbol()) == true){

                        currentSymbolTableNodeParent.mySymbolTable.get(currentAstNode.children.get(1).getSymbol()).setIsUsed(true);
                        
                        System.out.println("SEMANTIC ANALYSIS: Variable \"" + currentAstNode.children.get(0).getSymbol() + "\" has been retrieved and used");

                    }//if

                }//while

                //if the variable is not found at all then it does not exsist
                if( currentSymbolTableNodeParent.mySymbolTable.containsKey(currentAstNode.children.get(1).getSymbol()) == false){

                    System.out.println(" ");
                    System.out.println("Error: While Statement Error: \"" + currentAstNode.children.get(1).getSymbol() + "\" does not exsist " );
                    System.out.println(" ");

                    ErrorFlag = 1;

                }//if

                //reset variables to where they were before backtrack

                currentSymbolTableNode = tempNode;

                currentScope = tempScope;

                System.out.println("SEMANTIC ANALYSIS: Back to scope " + currentScope);
                
            }//else
            
        }//if

        //else we traverse through the ast to the next node
        else{
                
            //traverse through the AST
            for (int i = 0; i < currentAstNode.children.size(); i++){

                traverse(currentAstNode.children.get(i), currentSymbolTableNode, symbolTableList);
                
                if(currentAstNode.children.get(i).getName().compareToIgnoreCase("Block")==0){

                    System.out.println("SEMANTIC ANALYSIS: Leaving scope " + currentScope);
    
                    currentScope--;

                    System.out.println("SEMANTIC ANALYSIS: Entering scope " + currentScope);

                }//if


            }//for

        }//else
       
    }//traverse


    //print any warning from the program
    public static void printWarnings(ArrayList<SymbolTableNode>  symbolTableList){
            

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Warnings for program: " + programNumber);
        
        //for each hashtable node in the symbol table we loop through to find un-used to un-initialized variables
        for(int i = 0; i < symbolTableList.size(); i++){

            //print code found online at https://www.javacodeexamples.com/print-hashtable-in-java-example/3154
            Iterator<SemanticAnalysisNode> itr = symbolTableList.get(i).mySymbolTable.values().iterator();
    
            while(itr.hasNext()){

                SemanticAnalysisNode temp = itr.next();

                //if the variable was initialized but not used then we throw a warning
                if ( temp.myIsUsed == false){

                    System.out.println("\"" + temp.myName + "\" has not been used on line " + temp.myLine);
    
                }//if
    
                //if the variable was not initialized then we throw a warning.
                if ( temp.myIsInitilaized == false){
    
                    System.out.println("\"" + temp.myName + "\" has not been initialized on line " + temp.myLine);
    
                }//if

            }//while


        }//for

    }//print warnings

    //print symbol table
    public static void printSymbolTable(ArrayList<SymbolTableNode>  symbolTableList){
            
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Symbol Table: ");
        System.out.println("------------------------------------------------------------");
        System.out.println("    Name    Type    Scope   IsUsed  IsInitialized   Line    ");
        System.out.println("------------------------------------------------------------");

        //for each hashtable node in the symbol table we loop through to print out its contents
        for(int i = 0; i < symbolTableList.size(); i++){

            //print code found online at https://www.javacodeexamples.com/print-hashtable-in-java-example/3154
            Iterator<SemanticAnalysisNode> itr = symbolTableList.get(i).mySymbolTable.values().iterator();
    
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

        }//for
        
    }//print symbol table

}//Semantic Analysis Class
