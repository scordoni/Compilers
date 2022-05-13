import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.Iterator;

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

    static int ifFlag;

    static int tFlag;

    static int tempLength;

    static int offset;

    static int jump;
    
    static int jumpNum;

    static int tempStringLength;

    static int currentScope ;

    static int tempScope = -1;

    static int newExecuableImageLength;

    static ASTNode currentAstNode;

    static String asciiString;

    static String[] printTemp = new String[4];

    static String[] executableImage = new String[256];

    static String[][] staticData = new String[10][5];

    static String[][] jumpTable = new String[10][2];

    //This method is the main code generation method
    public static void codeGeneration(ASTNode root){

        System.out.println(" ");
        System.out.println(" ");

        //reset variables
        ErrorFlag = 0;
        i = 0;
        t = 0;
        j = -1;
        k = 0;
        l = 0;
        m = 0;

        ifFlag = 0;
        tFlag = 0;

        jump = 0;
        jumpNum = 0;

        currentScope = -1;

        tempStringLength = 0;

        newExecuableImageLength = -1;

        for(int i = 0; i < executableImage.length; i++){

            executableImage[i] = null;

        }//for


        //set the current AST node as the root
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

        //System.out.println(temp);

        System.out.println("CODE GENERATION: Replacing all null values with 00 ");

        //for each null value we replace it with 00
        for(int i = 0; i < executableImage.length; i++){

            if(executableImage[i] == null){
                executableImage[i] = "00";
            }//if

        }//for

        //print the arrays to test
        //System.out.println("  ");

        //System.out.println(Arrays.deepToString(staticData));

        //System.out.println("  ");

        //System.out.println(Arrays.deepToString(jumpTable));

        //System.out.println("  ");



        //System.out.println("jump " + executableImage.length);

        //for the 'If' stmt create and set the jump value
        fullbreak:
        for(int c = 0; c < executableImage.length; c++){

            //if we hit 'J0' set the jump flag
            if(executableImage[c].compareToIgnoreCase("J0") == 0){

                jump = 1;

            }//if

            //keep going until we hit a break stmt
            else if((jump == 1)&&(executableImage[c].compareToIgnoreCase("00") != 0)){

                jumpNum++;

            }//else if

            //reset the jump flag
            else if((jump == 1)&&(executableImage[c].compareToIgnoreCase("00") == 0)){

                jump = 0;
                break fullbreak;

            }//else if
            
        }//for

        //fix jump table

        //System.out.println(jumpNum);

        //backpatching the static data table of the T values

        for(int x = 0; x <= t; x++){

            //System.out.println("T " + x);

            for(int r = 0; r < executableImage.length; r++){

                //if we hit a T value we replace it with its intended value
                if((executableImage[r].compareToIgnoreCase("T" + x) == 0) && (executableImage[r+1].compareToIgnoreCase("XX") == 0)){
    
                    tFlag = 1;

                    tempString = Integer.toString(temp, 16).toUpperCase().toString();
    
                    //if it has a length of one then we pad it with a 0
                    if(tempString.length() == 1){
    
                        tempString = "0" + Integer.toString(temp, 16).toUpperCase().toString();
    
                    }//if
    
                    executableImage[r] = tempString;
    
                    executableImage[r+1] = "00";

                    
    

                }//if
    
            }//for

            temp = temp + 1;

            if((executableImage[x] != null)&&(tFlag == 1)){
                System.out.println("CODE GENERATION: Backpatching " + "T" + x + " with " + tempString);
            }//if

            tFlag = 0;

        }//for
        
        //backpatching the J values for the if stmts
        for(int y = 0; y <= j; y++){

            //System.out.println("J" + y);

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

            System.out.println("CODE GENERATION: Backpatching " + "J" + y + " with " + tempString);

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

        //if we have a block then we increment the scope
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

            //if we declare an integer
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
                l++;
                
                //value or pointer
                staticData[k][l] = "value";
                

                //increment k and offset, and reset l
                offset++;
                k++;
                l = 0;
                t++;
                

            }//if
            
            //if we declare a string
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
                l++;

                //value or pointer
                staticData[k][l] = "pointer";

                //increment k and offset, and reset l
                offset++;
                k++;
                l = 0;
                t++;
                
            }//else if

            //if we declare a boolean
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
                l++;
                
                //value or pointer
                staticData[k][l] = "value";

                //increment k and offset, and reset l
                offset++;
                k++;
                l = 0;
                t++;

            }//else if
            
        }//if

        //if we have an assignment stmt 
        if(currentAstNode.getName().compareToIgnoreCase("AssignmentStatement") == 0){

            System.out.println("CODE GENERATION: Assignment Statement");

            //Assignment of an Integer
            if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") == 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")==0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")==0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")==0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")==0)){

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
                
                //loop through the static data to find the correct variable to print
                int r = 0;

                fullbreak:
                
                for(int e = 0; e < staticData.length; e++){

                    //for(int r = 0; r < staticData[e].length; r++){

                        /*
                        System.out.println(staticData[e][r]);
                        System.out.println("letter " + staticData[e][r + 1]);
                        System.out.println("letter " + currentAstNode.children.get(0).getSymbol());
                        System.out.println("scope " + staticData[e][r + 2]);
                        System.out.println("scope " + currentScope);

                        System.out.println("thing " + staticData[e][r + 4]);
                        */

                        if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 2]) == currentScope)){

                            //System.out.println("hello1");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//if

                        else if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 2]) != currentScope)){

                            //System.out.println(staticData[e][r + 1]);
                            //System.out.println(currentScope);
                            //System.out.println("hello");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//else

                    //}//for
                }//for

                executableImage[i] = "XX";
                i++;

                
            
            }//if

            //Assignment of a boolean
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") == 0 ) ||
                    (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") == 0)){
                
                //A9 # 8D T# XX
                executableImage[i] = "A9";
                i++;

                //if we have a true
                if(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") == 0 ){

                    executableImage[i] = "01";
                    i++;

                }//if

                //else if we have a false
                else if(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") == 0 ){

                    executableImage[i] = "00";
                    i++;

                }//else if
                
                executableImage[i] = "8D";
                i++;
                

                //loop through the static data to find the correct variable to print
                int r = 0;

                fullbreak:
                
                for(int e = 0; e < staticData.length; e++){

                    //for(int r = 0; r < staticData[e].length; r++){

                        /*
                        System.out.println(staticData[e][r]);
                        System.out.println("letter " + staticData[e][r + 1]);
                        System.out.println("letter " + currentAstNode.children.get(0).getSymbol());
                        System.out.println("scope " + staticData[e][r + 2]);
                        System.out.println("scope " + currentScope);

                        System.out.println("thing " + staticData[e][r + 4]);
                        */

                        if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 2]) == currentScope)){

                            //System.out.println("hello1");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//if

                        else if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 2]) != currentScope)){

                            //System.out.println(staticData[e][r + 1]);
                            //System.out.println(currentScope);
                            //System.out.println("hello");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//else

                    //}//for
                }//for


                executableImage[i] = "XX";
                i++;

                
            
            }//else if

            //Assignment of a String we add to the heap
            else if((currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("0") != 0 ) ||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("1")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("2")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("3")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("4")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("5")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("6")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("7")!=0)||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("8")!=0)
            || (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("9")!=0)|| (currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("true") != 0 )||(currentAstNode.children.get(1).getSymbol().compareToIgnoreCase("false") != 0)){

                //pass the string to the heap
                for(int f = 1; f < currentAstNode.children.size(); f++){

                    tempStringLength++;

                }//for

                //increment the length by one to account for a break stmt
                tempStringLength = tempStringLength + 1;

                //newExecuableImageLength = executableImage.length - tempStringLength;

                if(newExecuableImageLength == -1 ){

                    newExecuableImageLength = executableImage.length - tempStringLength;

                    int d = 1;

                    for(int w = executableImage.length - tempStringLength; w < executableImage.length - 1 ; w++){

                        /*
                        System.out.println("letter " + currentAstNode.children.get(d).getSymbol());
                        System.out.println("letter as char " + currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                        System.out.println("char as ascii " + (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                        System.out.println("char as hex " + Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString() );
                        */

                        if(currentAstNode.children.size() == 2){

                            asciiString = Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString();

                            executableImage[w] = asciiString;

                            

                            if(w == executableImage.length - 1){

                                executableImage[w] = "00";

                            }//if

                        }//if

                        else if(currentAstNode.children.get(d) != null){

                            asciiString = Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString();

                            executableImage[w] = asciiString;

                            

                            if(w == executableImage.length - 1){

                                executableImage[w] = "00";

                            }//if

                            if(d != currentAstNode.children.size() - 1){

                                d++;

                            }//if
                            
                            
                        }//if

                    }//for

                    tempStringLength = 0;

                }//if

                else{

                    //System.out.println(newExecuableImageLength);
                    //System.out.println(tempStringLength);

                    tempLength = newExecuableImageLength;

                    newExecuableImageLength = newExecuableImageLength - tempStringLength;

                    //System.out.println(newExecuableImageLength);

                    int d = 1;

                    for(int w = newExecuableImageLength; w < tempLength ; w++){

                        /*
                        System.out.println("letter " + currentAstNode.children.get(d).getSymbol());
                        System.out.println("letter as char " + currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                        System.out.println("char as ascii " + (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0));
                        System.out.println("char as hex " + Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString() );
                        */

                        if(currentAstNode.children.size() == 2){

                            asciiString = Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString();

                            executableImage[w] = asciiString;

                            

                            if(w == tempLength - 1){

                                executableImage[w] = "00";

                            }//if

                        }//if

                        else if(currentAstNode.children.get(d) != null){

                            asciiString = Integer.toString( (int)currentAstNode.children.get(d).getSymbol().toString().charAt(0) , 16 ).toUpperCase().toString();

                            executableImage[w] = asciiString;

                            

                            if(w == tempLength - 1){

                                executableImage[w] = "00";

                            }//if

                            if(d != currentAstNode.children.size() - 1){

                                d++;

                            }//if
                            
                            
                        }//if

                    }//for

                    tempStringLength = 0;

                }//else




                //assign the string in the code
                //add to executable image
                //A9 ## 8D T# XX
                executableImage[i] = "A9";
                i++;
                executableImage[i] = Integer.toString( newExecuableImageLength , 16).toUpperCase().toString();
                i++;
                executableImage[i] = "8D";
                i++;
                

                //loop through the static data to find the correct variable to print
                int r = 0;

                fullbreak:
                
                for(int e = 0; e < staticData.length; e++){

                    //for(int r = 0; r < staticData[e].length; r++){

                        /*
                        System.out.println(staticData[e][r]);
                        System.out.println("letter " + staticData[e][r + 1]);
                        System.out.println("letter " + currentAstNode.children.get(0).getSymbol());
                        System.out.println("scope " + staticData[e][r + 2]);
                        System.out.println("scope " + currentScope);

                        System.out.println("thing " + staticData[e][r + 4]);
                        */

                        if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 2]) == currentScope)){

                            //System.out.println("hello1");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//if

                        else if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 2]) != currentScope)){

                            //System.out.println(staticData[e][r + 1]);
                            //System.out.println(currentScope);
                            //System.out.println("hello");

                            printTemp = staticData[e][r].split("");

                            executableImage[i] = printTemp[0] + printTemp[1];
                            i++;

                            Arrays.fill(printTemp, null);

                            break fullbreak;

                        }//else

                    //}//for
                }//for


                executableImage[i] = "XX";
                i++;

                

            }//else if
            
        }//Assignment if

        //if we have a print statment then we can check to see whether or not the symbol exsists
        //if it does exsist then we set the is used parameter to true
        if(currentAstNode.getName().compareToIgnoreCase("PrintStatement") == 0){

            //decrement T so that we are looking at the correct T value.

            if(ifFlag == 1){
                t--;
            }//if
            

            System.out.println("CODE GENERATION: Print Statement");

            //AC T# XX A2 # FF
            executableImage[i] = "AC";
            i++;

            //loop through the static data to find the correct variable to print
            int r = 0;

            fullbreak:
            
            for(int e = 0; e < staticData.length; e++){

                //for(int r = 0; r < staticData[e].length; r++){

                    /*
                    System.out.println(staticData[e][r]);
                    System.out.println("letter " + staticData[e][r + 1]);
                    System.out.println("letter " + currentAstNode.children.get(0).getSymbol());
                    System.out.println("scope " + staticData[e][r + 2]);
                    System.out.println("scope " + currentScope);

                    System.out.println("thing " + staticData[e][r + 4]);
                    */

                    if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (Integer.parseInt(staticData[e][r + 2]) == currentScope)){

                        //System.out.println("hello1");

                        printTemp = staticData[e][r].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//if

                    else if((staticData[e][r] != null )&&(staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0)&& (Integer.parseInt(staticData[e][r + 2]) != currentScope)){

                        //System.out.println(staticData[e][r + 1]);
                        //System.out.println(currentScope);
                        //System.out.println("hello");

                        printTemp = staticData[e][r].split("");

                        executableImage[i] = printTemp[0] + printTemp[1];
                        i++;

                        Arrays.fill(printTemp, null);

                        break fullbreak;

                    }//else

                //}//for
            }//for


            executableImage[i] = "XX";
            i++;
            executableImage[i] = "A2";
            i++;

            r = 0;

            //loop through the static data to detemine if we print a string or a int

            fullbreak:
            
            for(int e = 0; e < staticData.length; e++){

                //for(int r = 0; r < staticData[e].length; r++){

                    /*
                    System.out.println(staticData[e][r]);
                    System.out.println("letter " + staticData[e][r + 1]);
                    System.out.println("letter " + currentAstNode.children.get(0).getSymbol());
                    System.out.println("scope " + staticData[e][r + 2]);
                    System.out.println("scope " + currentScope);

                    System.out.println("thing " + staticData[e][r + 4]);
                    */

                    if((staticData[e][r] != null ) && (staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (staticData[e][r + 4].compareToIgnoreCase("value") == 0)){

                        //System.out.println("hello1");

                        executableImage[i] = "01";
                        i++;

                        break fullbreak;

                    }//if

                    
                    else if((staticData[e][r] != null ) && (staticData[e][r + 1].compareToIgnoreCase(currentAstNode.children.get(0).getSymbol()) == 0) && (staticData[e][r + 4].compareToIgnoreCase("pointer") == 0)){

                        //System.out.println("hello");

                        executableImage[i] = "02";
                        i++;


                        break fullbreak;

                    }//else

                //}//for
            }//for


            executableImage[i] = "FF";
            i++;

            t++;

        }//if


        //set global if flags

        //if the node is equal to an if statement
        if(currentAstNode.getName().compareToIgnoreCase("IfStatement") == 0){
            
            t--;

            ifFlag = 1;

            System.out.println("CODE GENERATION: If Statement");

            //A2 T# XX A2 # FF
            executableImage[i] = "A2";
            i++;

    
            if(currentAstNode.children.get(3).getSymbol().toString().length() == 1){
    
                executableImage[i]  = "0" + Integer.parseInt(currentAstNode.children.get(3).getSymbol().toString(), 16);
                i++;

            }//if

            else if(currentAstNode.children.get(3).getSymbol().toString().compareToIgnoreCase("true") == 0){

                executableImage[i] = "01";
                i++;

            }//else

            else if(currentAstNode.children.get(3).getSymbol().toString().compareToIgnoreCase("false") == 0){

                executableImage[i] = "00";
                i++;

            }//else

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
            executableImage[i] = "D0";
            i++;

            //increment j
            j++;

            executableImage[i] = "J"+j;
            i++;

            //add to jump table 

            //temp
            jumpTable[j][m] = "J" + j;
            m++;

            //var
            jumpTable[j][m] = "00";

            //increment t and reset m
            m = 0;
            t++;

        }//if


        //if the node is equal to a while statement
        if(currentAstNode.getName().compareToIgnoreCase("WhileStatement") == 0){

            t--;

            System.out.println("CODE GENERATION: While Statement");

            //AD T# XX A2 # FF
        
            //while()
            executableImage[i] = "AD";
            i++;

            //grab variable to compare
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

            //increment T to get back to next t vlaue
            t++;

            //copy value
            executableImage[i] = "8D";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;


            executableImage[i] = "A9";
            i++;
                
            if(currentAstNode.children.get(3).getSymbol().toString().length() == 1){
    
                executableImage[i]  = "0" + Integer.parseInt(currentAstNode.children.get(3).getSymbol().toString(), 16);
                i++;

            }//if

            else if(currentAstNode.children.get(3).getSymbol().toString().compareToIgnoreCase("true") == 0){

                executableImage[i] = "01";
                i++;

            }//else

            else if(currentAstNode.children.get(3).getSymbol().toString().compareToIgnoreCase("false") == 0){

                executableImage[i] = "00";
                i++;

            }//else

            else{

                executableImage[i] = currentAstNode.children.get(3).getSymbol().toString();
                i++;

            }//else

            //Copy the compare-to value to t1.
            t--;

            //copy value
            executableImage[i] = "8D";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;

            //increment T to get back to next t vlaue
            t++;

            //Compare t2 and t1, and assign Z flag.
            executableImage[i] = "AE";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;

            //
            t--;

            executableImage[i] = "EC";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;

            //set accumlator to 0
            executableImage[i] = "A9";
            i++;

            executableImage[i] = "00";
            i++;
                
            //if branch
            executableImage[i] = "D0";
            i++;

            //increment j
            j++;

            executableImage[i] = "J"+j;
            i++;

            //add to jump table 

            //temp
            jumpTable[j][m] = "J" + j;
            m++;

            //var
            jumpTable[j][m] = "00";

            //reset m
            m = 0;
            
            
            //(if t2 == t1) Acc = 1.
            executableImage[i] = "A9";
            i++;

            executableImage[i] = "01";
            i++;

            //X register = 0.
            executableImage[i] = "A2";
            i++;

            executableImage[i] = "00";
            i++;

            //Store Acc in t1 (0x53).
            executableImage[i] = "8D";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;

            //Compare t1 and X reg,
            executableImage[i] = "EC";
            i++;

            executableImage[i] = "T"+t;
            i++;
                
            executableImage[i] = "XX";
            i++;

            //branch if unequal

            executableImage[i] = "D0";
            i++;

            //increment j
            j++;

            executableImage[i] = "J"+j;
            i++;

            //add to jump table 

            //temp
            jumpTable[j][m] = "J" + j;
            m++;

            //var
            jumpTable[j][m] = "00";

            //reset m
            m = 0;

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

                if(currentAstNode.children.get(m).getName().compareToIgnoreCase("WhileStatement") == 0){

                    //unconditional jump

                    //Acc = 0
                    executableImage[i] = "A9";
                    i++;

                    executableImage[i] = "00";
                    i++;

                    //store
                    executableImage[i] = "8D";
                    i++;

                    executableImage[i] = "T"+t;
                    i++;
                        
                    executableImage[i] = "XX";
                    i++;

                    //X register = 1
                    executableImage[i] = "A2";
                    i++;

                    executableImage[i] = "01";
                    i++;

                    //Compare t1 and X reg,
                    executableImage[i] = "EC";
                    i++;

                    executableImage[i] = "T"+t;
                    i++;
                        
                    executableImage[i] = "XX";
                    i++;

                    //branch if unequal

                    executableImage[i] = "D0";
                    i++;

                    //increment j
                    j++;

                    executableImage[i] = "J"+j;
                    i++;

                    //add to jump table 

                    //temp
                    jumpTable[j][m] = "J" + j;
                    m++;

                    //var
                    jumpTable[j][m] = "00";

                    //reset m
                    m = 0;

                    executableImage[i] = "00";

                }//if

            }//for

        }//else

        return temp;
       
    }//traverse
    
}//Code Generation
