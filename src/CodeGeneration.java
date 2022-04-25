
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

    static int i;

    static int t;

    static int j = 0;

    static int currentScope = -1;

    static int tempScope = -1;

    static ASTNode currentAstNode;

    static String[] executableImage = new String[256];

    static String[][] staticData = new String[10][10];

    static String[][] jumps = new String[10][10];

    //This method is the main code generation method
    public static void codeGeneration(ASTNode root){

        System.out.println(" ");
        System.out.println(" ");

        //reset variables
        ErrorFlag = 0;
        i = 0;
        t = 0;


        //move pointer from program to block
        //this is the only way to make this work so ill take the points off for having to keep "Program" in the AST
        currentAstNode = currentAstNode.children.get(i);

        System.out.println("INFO: CODE GENERATION START");

        //traverse through the AST for code generation
        traverse(currentAstNode, i);

        //if we have error then output error message and no table or output symbol table

        if(ErrorFlag == 1){

            System.out.println(" ");
            System.out.println("Error: Code Generation failed - too much code for executable image to compile");
            System.out.println("Program Compilation Halted");
        
        }//if

        else{

            System.out.println(" ");
            System.out.println("CODE GENERATION SUCCESSFUL");

            //print the symbol table
            System.out.println(executableImage);

        }//else

    }//code generation


    //gonna copy the traverse function from the semantic analysis class

    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static void traverse(ASTNode currentAstNode, int i){

        //System.out.println(currentAstNode.getName());

        //look into length of each instruction and add to th array from there

        //if we have block stmt then we need a new table and to up the scope
        if(currentAstNode.getName().compareToIgnoreCase("Block") == 0){


        }//if


        //if we have a variable declaration then we create a new node for the symbol table
        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            if(currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("int") == 0 ){
                
                //A9 00 8D T# XX
                executableImage[i] = "A9";
                i++;
                executableImage[i] = "00";
                i++;
                executableImage[i] = "8D";
                i++;
                executableImage[i] = "T"+t;
                i++;
                executableImage[i] = "XX";
            
            }//if
            
            else if (currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("string") == 0){


            }//else if

            else if (currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("boolean") == 0){


            }//else if
            

        }//if

        //if we have an assignment stmt then we must check for type matching
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            //Assignment of an Integer
            if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") == 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){
                
                //A9 # 8D T# XX
                executableImage[i] = "A9";
                i++;
                executableImage[i] = "0" + currentAstNode.children.get(1).toString();
                i++;
                executableImage[i] = "8D";
                i++;
                executableImage[i] = "T"+t;
                i++;
                t++;
                executableImage[i] = "XX";
            
            }//if

            //ASsignment of a boolean
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") == 0 ) ||
                    (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") == 0)){
                
                //A9 # 8D T# XX
                executableImage[i] = "A9";
                i++;
                executableImage[i] = "0" + currentAstNode.children.get(1).toString();
                i++;
                executableImage[i] = "8D";
                i++;
                executableImage[i] = "T"+t;
                i++;
                t++;
                executableImage[i] = "XX";
            
            }//else if

            //Assignment of a String
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") != 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") != 0 )||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") != 0)){
                
                
                
            
            }//else if
            
        }//Assignment if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

            //AC T# XX A2 # FF
            executableImage[i] = "AC";
            i++;
            executableImage[i] = "T"+t;
            i++;
            executableImage[i] = "XX";
            i++;
            executableImage[i] = "A2";
            i++;
            t++;
            executableImage[i] = "01";

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
            for (int m = 0; m < currentAstNode.children.size(); m++){

                traverse(currentAstNode.children.get(m), i);
                
            }//for

        }//else
       
    }//traverse
    
}//Code Generation
