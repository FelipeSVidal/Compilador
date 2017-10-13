package TypeCheck;

import java.util.HashMap;
import java.util.Map;

import AST.And;
import AST.ArrayAssign;
import AST.ArrayLength;
import AST.ArrayLookup;
import AST.Assign;
import AST.Block;
import AST.BooleanType;
import AST.Call;
import AST.ClassDeclExtends;
import AST.ClassDeclSimple;
import AST.False;
import AST.Formal;
import AST.Identifier;
import AST.IdentifierExp;
import AST.IdentifierType;
import AST.If;
import AST.IntArrayType;
import AST.IntegerLiteral;
import AST.IntegerType;
import AST.LessThan;
import AST.MainClass;
import AST.MethodDecl;
import AST.Minus;
import AST.NewArray;
import AST.NewObject;
import AST.Not;
import AST.Plus;
import AST.Print;
import AST.Program;
import AST.Return;
import AST.This;
import AST.Times;
import AST.True;
import AST.VarDecl;
import AST.While;
import AST.Visitor.Visitor;

public class TypeVisitor implements Visitor{
	private Classe lastClass;
	private metodo lastMetodo;
	private tabela table;
	
	
	
	
	public TypeVisitor() {
		table = new tabela();
	}
	public tabela gettable(){
		return table;
	}

	public void visit(Return n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Program n) {
		n.m.accept(this);
		for(int i = 0; i<n.cl.size();i++){
			n.cl.elementAt(i).accept(this);
		}
	}

	public void visit(MainClass n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(ClassDeclSimple n) {
		System.out.println("Antes da busca:"+n.i.s);
		if(!(this.table.adicionar_classe(n.i.s, null))){
			throw new IllegalArgumentException("A Classe: "+n.i.s + "Já existe.");
		}
		lastClass = this.table.pegar_classe(n.i.s);
		n.i.accept(this);
		for(int i =0; i< n.vl.size();i++){
			lastClass.adicionar_globais(n.vl.elementAt(i).i.s, new variavel(n.vl.elementAt(i).i.s,n.vl.elementAt(i).t));
			n.vl.elementAt(i).accept(this);
		}
		for(int j=0;j< n.ml.size();j++){
			System.out.print("1:"+n.ml.elementAt(j).i.s+"\n2:"+n.ml.elementAt(j).t+"\n3:"+lastClass.toString());
			lastClass.adicionar_metodos(n.ml.elementAt(j).i.s, new metodo(n.ml.elementAt(j).i.s,n.ml.elementAt(j).t));
			n.ml.elementAt(j).accept(this);
		}
		
		lastClass = null;
		// TODO Auto-generated method stub
		
	}

	public void visit(ClassDeclExtends n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(VarDecl n) {
		if(lastMetodo ==null) this.lastClass.adicionar_globais(n.i.s, new variavel(n.i.s,n.t));
		else this.lastMetodo.adicionar_locais(n.i.s, new variavel(n.i.s,n.t));
		n.t.accept(this);
		n.i.accept(this);
		
	}

	public void visit(MethodDecl n) {
		this.lastClass.adicionar_metodos(n.i.s, new metodo(n.i.s,n.t));
		this.lastMetodo = this.lastClass.pegar_metodo(n.i.s);
		n.t.accept(this);
		n.i.accept(this);
		for(int i= 0;i<n.fl.size();i++){
			this.lastMetodo.adicionar_parametros(new variavel(n.fl.elementAt(i).i.s,n.fl.elementAt(i).t));
			n.fl.elementAt(i).accept(this);
		}
		for(int k = 0; k<n.vl.size();k++){
			VarDecl v = n.vl.elementAt(k);
			this.lastMetodo.adicionar_locais(v.i.s, new variavel(v.i.s,v.t));
			v.accept(this);
		}
		for(int j=0;j<n.sl.size();j++){
			n.sl.elementAt(j).accept(this);
		}
		n.e.accept(this);
	}

	public void visit(Formal n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IntArrayType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(BooleanType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IntegerType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IdentifierType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Block n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(If n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(While n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Print n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Assign n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(ArrayAssign n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(And n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(LessThan n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Plus n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Minus n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Times n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(ArrayLookup n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(ArrayLength n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Call n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IntegerLiteral n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(True n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(False n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IdentifierExp n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(This n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(NewArray n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Not n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
		
	}

}
