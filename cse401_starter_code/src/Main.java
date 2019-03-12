import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import CodeGen.Gerar;
import Throwables.*;
import TypeCheck.CriarTabela;
import TypeCheck.TypeVisitor;
import TypeCheck.tabela;
import java_cup.runtime.Symbol;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    public static void main(String [] args) {
    	Scanner si = new Scanner(System.in);
    	int escolha ;
    	String arq ="";
    	while(true){
	        try {
	        	System.out.println("Escolha qual o programa deseja!" +
	        			"\n (1) - BinarySearch \t (2) - BinaryTree \n (3) - BubbleSort \t (4) - Factorial \n (5) - LinearSearch \t (6) - Linked List \n (7) - QuickSort \t (8) - TreeVisitor \n (9) - Final \t\t (0) - Sair \n");
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
	        		break;
	        	case 0:
	        		return;
	        	default:
	        		System.out.println("Opção Incorreta!");
	        		arq = "";
	        		break;
	        	}
	        	if(!arq.equals("")){
	        		// Leitura do arquivo:
		        	FileInputStream f = new FileInputStream("SamplePrograms/SampleMiniJavaPrograms/"+arq+".java");
		        	InputStream is = f;
		        	
		        	// Scanner
		            scanner s = new scanner(is);
		            System.out.println("Scanner Sucefull");
		            
		            // Parser
		            parser p = new parser(s);
		            Symbol root;
		            root = p.parse();
		            System.out.println("Parsing successfull");
					Program program = (Program)root.value;
					
					// Imprimi o Programa .java
					program.accept(new PrettyPrintVisitor());
					
					// Cria Tabela 1ª Passada do analisado sintatico
					tabela t = new tabela();
					CriarTabela ct = new CriarTabela(t);
					program.accept(ct);
					
					// 2ª Passada do analisador sintático, verificação dos tipos
					TypeVisitor tv = new TypeVisitor(t);
					program.accept(tv);
					
					//Imprimi a tabela
					System.out.println(tv);
					
					// Gera o código Assembly
					Gerar g = new Gerar(t);
					program.accept(g);
				
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