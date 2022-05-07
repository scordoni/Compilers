import java.lang.reflect.Executable;
import java.util.Arrays;

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

    static int n;

    static int t;

    static int temp;

    static String tempString;

    static int temp1;

    static int temp2;

    static int j;

    static int k;

    static int l;

    static int m;

    static int offset;

    static int jump;
    
    static int jumpNum;

    static int tempStringLength;

    static int currentScope ;

    static int tempScope = -1;

    static int newExecuableImageLength = -1;

    static ASTNode currentAstNode;

    static String asciiString;

    static String[] printTemp = new String[4];

    static String[] executableImage = new String[256];

    static String[][] staticData = new String[10][4];

    static String[][] jumpTable = new String[10][2];

    //This method is the main code generation method
    public static void codeGeneration(ASTNode root){

        System.out.println(" ");
        System.out.println(" ");

        //reset variables
        ErrorFlag = 0;
        i = 0;
        t = 0;
        j = 0;
        k = 0;
        l = 0;
        m = 0;

        jump = 0;
        jumpNum = 0;

        currentScope = -1;

        tempStringLength = 0;

        for(int i = 0; i < executableImage.length; i++){

            executableImage[i] = null;

        }//for



        currentAstNode = root;

        //move pointer from program to block
        //this is the only way to make this work so ill take the points off for having to keep "Program" in the AST
        currentAstNode = currentAstNode.children.get(i);

        System.out.println("INFO: CODE GENERATION START");

        //traverse through the AST for code generation
        traverse(currentAstNode, i);

        //back patching? i forget what we call it

        //System call to end
        if(executableImage[temp] == null){
            executableImage[temp] = "00";
        }//if

        else{
            //ErrorFlag = 1;
        }//else

        temp = temp + 1;

        //System.out.println("temp " + temp);

        //for each null value we replace it with 00
        for(int i = 0; i < executableImage.length; i++){

            if(executableImage[i] == null){
                executableImage[i] = "00";
            }//if

        }//for

        System.out.println("  ");

        System.out.println(Arrays.deepToString(staticData));

        System.out.println("  ");

        System.out.println(Arrays.deepToString(jumpTable));

        System.out.println("  ");

        

        //System.out.println("jump " + executableImage.length);

        fullbreak:
        for(int c = 0; c < executableImage.length; c++){


            if(executableImage[c].compareToIgnoreCase("J0") == 0){

                jump = 1;

            }//if

            else if((jump == 1)&&(executableImage[c].compareToIgnoreCase("00") != 0)){

                jumpNum++;

            }//else if

            else if((jump == 1)&&(executableImage[c].compareToIgnoreCase("00") == 0)){

                jump = 0;
                break fullbreak;

            }//else if
            
        }//for

        System.out.println(jumpNum);

        /*
        for(int x = 0; x <= t; x++){

            //System.out.println("T " + x);

            for(int r = 0; r < executableImage.length; r++){


                if((executableImage[r].compareToIgnoreCase("T" + x) == 0) && (executableImage[r+1].compareToIgnoreCase("XX") == 0)){
    
                    tempString = Integer.toString(temp, 16).toUpperCase().toString();
    
                    //System.out.println("tempstring " + tempString);
                    //System.out.println("tempstring length " + tempString.length());
    
                    if(tempString.length() == 1){
    
                        tempString = "0" + Integer.toString(temp, 16).toUpperCase().toString();
    
                    }//if
    
                    executableImage[r] = tempString;
    
                    executableImage[r+1] = "00";
    
                }//if
    
            }//for

            temp = temp + 1;

        }//for
        */

        for(int y = 0; y <= j; y++){

            System.out.println("J" + y);

            for(int z = 0; z < executableImage.length; z++){


                if((executableImage[z].compareToIgnoreCase("J" + y) == 0)){
    
                    tempString = Integer.toString(jumpNum, 16).toUpperCase().toString();
    
                    //System.out.println("tempstring " + tempString);
                    //System.out.println("tempstring length " + tempString.length());
    
                    if(tempString.length() == 1){
    
                        tempString = "0" + Integer.toString(jumpNum, 16).toUpperCase().toString();
    
                    }//if
    
                    executableImage[z] = tempString;

 
    
                }//if
    
            }//for

        }//for

        //if we have error then output error message or executable image
        if(ErrorFlag == 1){

            System.out.println(" ");
            System.out.println("Error: Code Generation failed - too much code for executable image to compile");
            System.out.println("Program Compilation Halted");
        
        }//if

        else{

            System.out.println(" ");
            System.out.println("CODE GENERATION SUCCESSFUL");
            System.out.println(" ");

            //print the symbol table
            for(int i = 0; i < executableImage.length; i++){

                System.out.print(executableImage[i]);
                System.out.print(" ");

            }//for



        }//else

    }//code generation


    //gonna copy the traverse function from the semantic analysis class

    //Recursive function to handle the traversal through the tree
    //similar to expand but not printing anything
    public static int traverse(ASTNode currentAstNode, int i){

        /*
        System.out.println(" ");
        System.out.println(currentAstNode.getName());
        System.out.println(" ");
        */
        
        //look into length of each instruction and add to the array from there

        //if we have then we skip
        if(currentAstNode.getName().compareToIgnoreCase("Block") == 0){

            System.out.println("CODE GENERATION: Block" );

            //if current scope is still -1
            if(currentScope == -1){
                
                currentScope++;

                System.out.println("CODE GENERATION: Entering scope 0");

            }//if

            else{
               
                System.out.println("CODE GENERATION: Leaving scope " + currentScope);

                currentScope++;

                System.out.println("CODE GENERATION: Entering scope " + currentScope);

            }//else

            
            //scpe and check vars

        }//if

        //if we have a variable declaration then we create a new node for the symbol table
        if(currentAstNode.getName().compareToIgnoreCase("VarDecl") == 0){

            System.out.println("CODE GENERATION: Variable Declaration" );

            if(currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("int") == 0 ){

                //add to executable image
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
                i++;

                //add to static image
                //T# XX var name and address

                //temp
                staticData[k][l] = "T" + t + "XX";
                //System.out.println("t " + t);
                l++;

                //var
                staticData[k][l] = currentAstNode.children.get(1).getSymbol();
                l++;

                //scope
                staticData[k][l] = Integer.toString(currentScope);
                l++;

                //offset
                staticData[k][l] = Integer.toString(offset);

                offset++;

                //increment j and reset k
                k++;
                l = 0;

                
            
            }//if
            
            else if (currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("string") == 0){

                //add pointer to static image

                //System.out.println("hello");

                //temp
                staticData[k][l] = "T" + t + "XX";
                //System.out.println("t " + t);
                l++;

                //var
                staticData[k][l] = currentAstNode.children.get(1).getSymbol();
                l++;

                //scope
                staticData[k][l] = Integer.toString(currentScope);
                l++;

                //offset
                staticData[k][l] = Integer.toString(offset);

                offset++;

                k++;
                l = 0;
                
                

            }//else if

            else if (currentAstNode.children.get(0).getSymbol().compareToIgnoreCase("boolean") == 0){

                //add to executable image
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
                i++;

                //add to static image
                //T# XX var name and address

                //temp
                staticData[k][l] = "T" + t + "XX";
                //System.out.println("t " + t);
                l++;

                //var
                staticData[k][l] = currentAstNode.children.get(1).getSymbol();
                l++;
                
                //scope
                staticData[k][l] = Integer.toString(currentScope);
                l++;

                //offset
                staticData[k][l] = Integer.toString(offset);

                offset++;

                k++;
                l = 0;

                

            }//else if
            

        }//if

        //if we have an assignment stmt then we must check for type matching
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            System.out.println("CODE GENERATION: Assignment Statement");

            //Assignment of an Integer
            if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") == 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){
                
                //System.out.println("int ");

                //A9 # 8D T# XX
                executableImage[i] = "A9";
                i++;

                //if we are assigning a single digit
                if(currentAstNode.children.get(1).getSymbol().toString().length() == 1){

                    executableImage[i] = "0" + currentAstNode.children.get(1).getSymbol().toString();
                    i++;

                }//if
                
                //if we are assigning a double digit
                else{

                    executableImage[i] = currentAstNode.children.get(1).getSymbol().toString();
                    i++;

                }//else

                executableImage[i] = "8D";
                i++;
                executableImage[i] = "T"+t;
                i++;
                //t++;
                executableImage[i] = "XX";
                i++;

                //System.out.println("t " + t);

                t++;

                //System.out.println("t " + t);
            
            }//if

            //ASsignment of a boolean
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") == 0 ) ||
                    (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") == 0)){
                
                //A9 # 8D T# XX
                executableImage[i] = "A9";
                i++;

                if(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") == 0 ){

                    executableImage[i] = "01";
                    i++;

                }//if

                else if(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") == 0 ){

                    executableImage[i] = "00";
                    i++;

                }//else if
                
                executableImage[i] = "8D";
                i++;
                executableImage[i] = "T"+t;
                i++;
                executableImage[i] = "XX";
                i++;

                //System.out.println("t " + t);

                t++;
            
                //System.out.println("t " + t);

            }//else if

            //Assignment of a String we add to the heap
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") != 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") != 0 )||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") != 0)){
                
                //System.out.println("string ");

                //pass the string to the heap
                for(int f = 1; f < currentAstNode.children.size(); f++){

                    tempStringLength++;

                }//for
            
                //System.out.println("tempStringLength  "  + tempStringLength);

                tempStringLength = tempStringLength + 1;

                //System.out.println("tempStringLength + 1 "  + tempStringLength);

                int d = 1;

                newExecuableImageLength = executableImage.length - tempStringLength;
                
                for(int w = executableImage.length - tempStringLength; w < executableImage.length - 1 ; w++){

                    /*
                    System.out.println("letter " + currentAstNode.children.get(d).getSymbol());
                    System.out.println("letter as char " + currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                    System.out.println("char as ascii " + (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                    System.out.println("char as hex " + Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString() );
                    */

                    asciiString = Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString();

                    executableImage[w] = asciiString;

                    d++;

                    //System.out.println("w  " + w );

                    //System.out.println("length " + newExecuableImageLength);

                    if(w == executableImage.length - 1){

                        executableImage[w] = "00";

                    }//if

                }//for


                //assign the string in the code
                //add to executable image
                //A9 ## 8D T# XX
                executableImage[i] = "A9";
                i++;
                executableImage[i] = Integer.toString( newExecuableImageLength , 16).toUpperCase().toString();
                i++;
                executableImage[i] = "8D";
                i++;

                //System.out.println("t " + t );
                
                executableImage[i] = "T"+t;
                i++;
                executableImage[i] = "XX";
                i++;
                
            
                //System.out.println("t " + t);

                t++;
                //System.out.println("t " + t);

            }//else if
            
        }//Assignment if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

            t--;

            System.out.println("CODE GENERATION: Print Statement");

            //AC T# XX A2 # FF
            executableImage[i] = "AC";
            i++;

            fullbreak:
            
            for(int e = 0; e < staticData.length; e++){

                for(int r = 0; r < staticData[e].length; r++){

                    //System.out.println(staticData[e][r]);

                    if((staticData[e][r] != null )&&(staticData[e][r].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 1]) == currentScope)){

                        //System.out.println("hello");

                        printTemp = staticData[e][r - 1].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//if

                    else if((staticData[e][r] != null )&&(staticData[e][r].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 1]) != currentScope)){

                        //System.out.println("hello");

                        printTemp = staticData[e][r - 1].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//else

                }//for
            }//for



            
            
            executableImage[i] = "XX";
            i++;
            executableImage[i] = "A2";
            i++;
            executableImage[i] = "01";
            i++;
            executableImage[i] = "FF";
            i++;

            t++;

        }//if

        //if the node is equal to an if statement
        if(currentAstNode.getName().compareToIgnoreCase("IfStatement") == 0){
            
            t--;

            System.out.println("CODE GENERATION: If Statement");

            //A2 T# XX A2 # FF
            executableImage[i] = "A2";
            i++;

    
            if(currentAstNode.children.get(3).getSymbol().toString().length() == 1){
    
                executableImage[i]  = "0" + Integer.parseInt(currentAstNode.children.get(3).getSymbol().toString(), 16);
                i++;

            }//if

            else{

                executableImage[i] = currentAstNode.children.get(3).getSymbol().toString();
                i++;

            }//else


            
            executableImage[i] = "EC";
            i++;
            
           fullbreak:

            for(int e = 0; e < staticData.length; e++){

                for(int r = 0; r < staticData[e].length; r++){

                    //System.out.println(staticData[e][r]);

                    if((staticData[e][r] != null )&&(staticData[e][r].compareToIgnoreCase(currentAstNode.children.get(1).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 1]) == currentScope)){

                        //System.out.println("hello");

                        printTemp = staticData[e][r - 1].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//if

                    else if((staticData[e][r] != null )&&(staticData[e][r].compareToIgnoreCase(currentAstNode.children.get(1).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 1]) != currentScope)){

                        //System.out.println("hello");

                        printTemp = staticData[e][r - 1].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//else

                }//for
            }//for
            
            executableImage[i] = "XX";
            i++;
            executableImage[i] = "DO";
            i++;
            executableImage[i] = "J"+j;
            i++;

            //add to jump table 

            //temp
            jumpTable[j][m] = "J" + j;
            m++;

            //var
            jumpTable[j][m] = "00";

            //increment j and reset m
            j++;
            m = 0;

            
            t++;
        }//if


        //if the node is equal to a while statement
        if(currentAstNode.getName().compareToIgnoreCase("WhileStatement") == 0){

            t--;

            System.out.println("CODE GENERATION: While Statement");

            
        }//if

        //else we traverse through the ast to the next node
        else{

            temp = i;

            temp1 = temp + i;
                
            //traverse through the AST
            for (int m = 0; m < currentAstNode.children.size(); m++){

                traverse(currentAstNode.children.get(m), temp);

                if(currentAstNode.children.get(m).getName().compareToIgnoreCase("Block")==0){

                    System.out.println("CODE GENERATION: Leaving scope " + currentScope);
    
                    currentScope--;

                    System.out.println("CODE GENERATION: Entering scope " + currentScope);


                }//if

            }//for

        }//else

        return temp;
       
    }//traverse
    
}//Code Generation
