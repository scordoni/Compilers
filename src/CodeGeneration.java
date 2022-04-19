
/*
* 
* This is the Code Generation component of the compiler
* Here we take our AST and turn it into our 6502 machine code
* 
*/

public class CodeGeneration {

    //Declare Variables
    static int programNumber = 1;

    static int lineNumber = 0;

    static int ErrorFlag;

    static int i = 0;

    static int j = 0;

    static int currentScope = -1;

    static int tempScope = -1;

    static ASTNode currentAstNode;

    static String[][] executableImage = new String[248][8];

    //This method is the main code generation method
    public static void codeGeneration(ASTNode root){


    }//code generation


    //gonna copy the traverse function from the semantic analysis class

    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static void traverse(ASTNode currentAstNode){

        //System.out.println(currentAstNode.getName());

        //look into length of each instruction and add to th array from there

        //if we have block stmt then we need a new table and to up the scope
        if(currentAstNode.getName().compareToIgnoreCase("Block") == 0){


        }//if


        //if we have a variable declaration then we create a new node for the symbol table
        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            
            

        }//if

        //if we have an assignment stmt then we must check for type matching
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            
        }//if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

        }//if

        //if the node is equal to an if statement
        if(currentAstNode.getName().compareToIgnoreCase("IfStatement") == 0){
            
            
            
        }//if


        //if the node is equal to a while statement
        if(currentAstNode.getName().compareToIgnoreCase("WhileStatement") == 0){

            
        }//if

        //else we traverse through the ast to the next node
        else{
                
            //traverse through the AST
            for (int i = 0; i < currentAstNode.children.size(); i++){

                traverse(currentAstNode.children.get(i));
                
            }//for

        }//else
       
    }//traverse
    
}//Code Generation
