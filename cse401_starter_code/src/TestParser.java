import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import Throwables.*;
import TypeCheck.TypeVisitor;
import java_cup.runtime.Symbol;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TestParser {
    public static void main(String [] args) {
    	Scanner si = new Scanner(System.in);
    	int escolha ;
    	String arq ="";
    	while(true){
	        try {
	        	System.out.println("Escolha qual o programa deseja!" +
	        			"\n (1) - BinarySearch \t (2) - BinaryTree \n (3) - BubbleSort \t (4) - Factorial \n (5) - LinearSearch \t (6) - Linked List \n (7) - QuickSort \t (8) - TreeVisitor \n (0) - Sair \n");
	        	escolha = si.nextInt();
	            // create a scanner on the input file
	        	switch(escolha){
	        	case 1:
	        		arq = "BinarySearch";
	        		break;
	        	case 2:
	        		arq = "BinaryTree";
	        		break;
	        	case 3:
	        		arq = "BubbleSort";
	        		break;
	        	case 4:
	        		arq = "Factorial";
	        		break;
	        	case 5:
	        		arq = "LinearSearch";
	        		break;
	        	case 6:
	        		arq = "LinkedList";
	        		break;
	        	case 7:
	        		arq = "QuickSort";
	        		break;
	        	case 8:
	        		arq = "TreeVisitor";
	        		break;
	        	case 9:
	        		arq = "Final";
	        	case 0:
	        		return;
	        	default:
	        		System.out.println("Opção Incorreta!");
	        		arq = "";
	        		break;
	        	}
	        	if(!arq.equals("")){
		        	FileInputStream f = new FileInputStream("SamplePrograms/SampleMiniJavaPrograms/"+arq+".java");
		        	InputStream is = f;
		            scanner s = new scanner(is);
		            System.out.println("Scanner Sucefull");
		            parser p = new parser(s);
		            Symbol root;
		            root = p.parse();
					Program program = (Program)root.value;
					/*
		            for (Statement statement: program) {
		                statement.accept(new PrettyPrintVisitor());
						System.out.print("\n");
		            }*/
//					program.accept(new PrettyPrintVisitor());
					TypeVisitor tv = new TypeVisitor();
					program.accept(tv);
					//tv.gettable().imprimir();
					System.out.println(tv);
					
		            System.out.println("\nParsing successfull");
	        	}
	        }  catch (Exception e) {
	            // yuck: some kind of error in the compiler implementation
	            // that we're not expecting (a bug!)
	            System.err.println("Unexpected internal compiler error: " + 
	                               e.toString());
	            // print out a stack dump
	            e.printStackTrace();
	        }
	    }
	}
}