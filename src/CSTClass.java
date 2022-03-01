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





}//CST