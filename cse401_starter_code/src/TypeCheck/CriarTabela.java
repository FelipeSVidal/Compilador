package TypeCheck;

import Tipos.Tipo;
import AST.ClassDeclExtends;
import AST.ClassDeclSimple;
import AST.False;
import AST.Formal;
import AST.Identifier;
import AST.IntegerLiteral;
import AST.MainClass;
import AST.MethodDecl;
import AST.Program;
import AST.True;
import AST.VarDecl;

public class CriarTabela implements InterfaceTableVisitor{
	private Classe lastClass;
	private metodo lastMetodo;
	private tabela table;
	private int offset = 4;
	
	
	public CriarTabela(tabela t){
		this.table = t;
	}
	public tabela getTabela(){
		return table;
	}
	public void visit(Program n) {
		n.m.accept(this);
		for(int i = 0; i<n.cl.size();i++){
			n.cl.elementAt(i).accept(this);
		}
		
	}

	public void visit(MainClass n) {
		// TODO Auto-generated method stub
	    n.i1.accept(this);
	    n.i2.accept(this);
	   // n.s.accept(this);
	}

	public void visit(ClassDeclSimple n) {
		//System.out.println("Antes da busca:"+n.i.s);
		if(!(this.table.adicionar_classe(n.i.s, null))){
			throw new IllegalArgumentException("A Classe: "+n.i.s + "Já existe.");
		}
		lastClass = this.table.pegar_classe(n.i.s);
		n.i.accept(this);
		for(int i =0; i< n.vl.size();i++){
			lastClass.adicionar_globais(n.vl.elementAt(i).i.s, new variavel(n.vl.elementAt(i).i.s,n.vl.elementAt(i).t,offset*(i+1)));
			n.vl.elementAt(i).accept(this);
		}
		for(int j=0;j< n.ml.size();j++){
		//	System.out.print("1:"+n.ml.elementAt(j).i.s+"\n2:"+n.ml.elementAt(j).t+"\n3:"+lastClass.toString());
			lastClass.adicionar_metodos(n.ml.elementAt(j).i.s, new metodo(n.ml.elementAt(j).i.s,n.ml.elementAt(j).t));
			n.ml.elementAt(j).accept(this);
		}
	//	this.table.atualizar_classe(n.i.s, lastClass);
		lastClass = null;
		// TODO Auto-generated method stub
		
	}

	// n.j -> Pai
	public void visit(ClassDeclExtends n) {
		// TODO Auto-generated method stub
		n.i.accept(this);
		n.j.accept(this);
		if(!(this.table.adicionar_classe(n.i.s, this.table.pegar_classe(n.j.s)))){
			throw new IllegalArgumentException("A Classe: "+n.i.s + "Já existe.");
		}
		lastClass = this.table.pegar_classe(n.i.s);
		for(int i =0; i< n.vl.size();i++){
			lastClass.adicionar_globais(n.vl.elementAt(i).i.s, new variavel(n.vl.elementAt(i).i.s,n.vl.elementAt(i).t,offset*(i+1)));
			n.vl.elementAt(i).accept(this);
		}
		for(int j=0;j< n.ml.size();j++){
		//	System.out.print("1:"+n.ml.elementAt(j).i.s+"\n2:"+n.ml.elementAt(j).t+"\n3:"+lastClass.toString());
			lastClass.adicionar_metodos(n.ml.elementAt(j).i.s, new metodo(n.ml.elementAt(j).i.s,n.ml.elementAt(j).t));
			offset = (j+1)*4;
			n.ml.elementAt(j).accept(this);
		}
		offset = 4;
	//	this.table.atualizar_classe(n.i.s, lastClass);
		lastClass = null;
		// TODO Auto-generated method stub
		

	}

	public void visit(VarDecl n) {
		n.i.accept(this);
		if(lastMetodo ==null) this.lastClass.adicionar_globais(n.i.s, new variavel(n.i.s,n.t,0));
		else this.lastMetodo.adicionar_locais(n.i.s, new variavel(n.i.s,n.t,offset));
	}

	public void visit(MethodDecl n) {
		this.lastClass.adicionar_metodos(n.i.s, new metodo(n.i.s,n.t));
		this.lastMetodo = this.lastClass.pegar_metodo(n.i.s);
		
		n.i.accept(this);
		for(int i= 0;i<n.fl.size();i++){
			this.lastMetodo.adicionar_parametros(new variavel(n.fl.elementAt(i).i.s,n.fl.elementAt(i).t,offset*(i+1)+4));
			n.fl.elementAt(i).accept(this);
		}
		for(int k = 0; k<n.vl.size();k++){
			VarDecl v = n.vl.elementAt(k);
			this.lastMetodo.adicionar_locais(v.i.s, new variavel(v.i.s,v.t,offset*(k+1)));
			v.accept(this);
		}
		for(int j=0;j<n.sl.size();j++){
			//n.sl.elementAt(j).accept(this);
		}
	//	n.e.accept(this);
//		lastClass.atualizar_metodo(n.i.s, lastMetodo);
	}
	
	public void visit(Formal n){
		
	}
	public void visit(Identifier n) {
		// TODO Auto-generated method stub
	}
	


}
