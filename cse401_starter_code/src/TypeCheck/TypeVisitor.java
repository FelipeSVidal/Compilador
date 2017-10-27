package TypeCheck;

import java.util.HashMap;
import java.util.Map;

import Tipos.AbsTipo;
import Tipos.ClassTipo;
import Tipos.IntArray;
import Tipos.Tipo;

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
	    n.i1.accept(this);
	    n.i2.accept(this);
	    n.s.accept(this);
	}

	public void visit(ClassDeclSimple n) {
		//System.out.println("Antes da busca:"+n.i.s);
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
		//	System.out.print("1:"+n.ml.elementAt(j).i.s+"\n2:"+n.ml.elementAt(j).t+"\n3:"+lastClass.toString());
			lastClass.adicionar_metodos(n.ml.elementAt(j).i.s, new metodo(n.ml.elementAt(j).i.s,n.ml.elementAt(j).t));
			n.ml.elementAt(j).accept(this);
		}
		
		lastClass = null;
		// TODO Auto-generated method stub
		
	}

	// n.j -> Pai
	public void visit(ClassDeclExtends n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(VarDecl n) {
		n.t.accept(this);
		n.i.accept(this);
		if(lastMetodo ==null) this.lastClass.adicionar_globais(n.i.s, new variavel(n.i.s,n.t));
		else this.lastMetodo.adicionar_locais(n.i.s, new variavel(n.i.s,n.t));
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
	
// Inicio da definição de tipos
	
	//Tipos base
	public void visit(IntegerLiteral n) {
		// TODO Auto-generated method stub
		n.tipo = Tipo.INTEGER;
	}

	public void visit(True n) {
		// TODO Auto-generated method stub
		n.tipo = Tipo.BOOLEAN;
	}

	public void visit(False n) {
		// TODO Auto-generated method stub
		n.tipo = Tipo.BOOLEAN;
	}
	//Fim dos tipos base
	
	//New 
	public void visit(NewArray n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		if(!n.e.tipo.equals(Tipo.INTEGER)){
			throw new IllegalArgumentException("A expressão deve ser Inteira na linha"+n.e.line_number);
		}
		n.tipo = Tipo.INTARRAY;
		
	}

	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		n.i.accept(this);
		Classe c;
		c = table.pegar_classe(n.i.s);
		if(c == null){
			throw new IllegalArgumentException("Objeto inexistente na linha: "+n.line_number);
		}
//		System.out.println("KEY: "+c.getKey()+ " Pai: "+ c.getPai()+" LINHA: "+n.line_number);
		n.tipo = new ClassTipo(c.getPai(),c.getKey());
	}
// Fim da definição de tipo
	
// Inicio da verificação dos tipos
	// tipos boolean

	public void visit(If n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		if(!(n.e.tipo.equals(Tipo.BOOLEAN))){
			throw new IllegalArgumentException("A expressão deve ser Boolean na linha: "+n.e.line_number);
		}
		n.s1.accept(this);
		n.s2.accept(this);
		n.tipo = n.e.tipo;
	}

	public void visit(While n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		if(!(n.e.tipo.equals(Tipo.BOOLEAN))){
			throw new IllegalArgumentException("A expressão deve ser Boolean na linha: "+n.e.line_number);
		}
		n.s.accept(this);
		n.tipo = n.e.tipo;
	}
	
	public void visit(And n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		n.e2.accept(this);
		if(!n.e1.tipo.equals(Tipo.BOOLEAN)){
			if(!n.e2.tipo.equals(Tipo.BOOLEAN)){
				throw new IllegalArgumentException("A expressão deve ser Boolean na linha: "+n.line_number);
			}
		}
		n.tipo = n.e1.tipo;
	}
	
	public void visit(Not n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		if(!n.e.tipo.equals(Tipo.BOOLEAN)){
			throw new IllegalArgumentException("A expressão deve ser Boolean na linha: "+n.line_number);
		}
		n.tipo = n.e.tipo;
	}
	// Fim dos tipos booleanos
	
	// Inicio dos tipos inteiros

	public void visit(LessThan n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		n.e2.accept(this);
		if(!(n.e1.tipo.equals(Tipo.INTEGER))){
			if(!(n.e2.tipo.equals(Tipo.INTEGER))){
				System.out.println(n.e2.tipo);
				throw new IllegalArgumentException("A expressão deve ser int na linha: "+n.line_number);
			}
		}
		n.tipo = Tipo.BOOLEAN;
	}

	public void visit(Plus n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		n.e2.accept(this);
		if(!n.e1.tipo.equals(Tipo.INTEGER)){
			if(!n.e2.tipo.equals(Tipo.INTEGER)){
				throw new IllegalArgumentException("A expressão deve ser int na linha: "+n.line_number);
			}
		}
		n.tipo = n.e1.tipo;
	}

	public void visit(Minus n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		n.e2.accept(this);
		if(!n.e1.tipo.equals(Tipo.INTEGER)){
			if(!n.e2.tipo.equals(Tipo.INTEGER)){
				throw new IllegalArgumentException("A expressão deve ser int na linha: "+n.line_number);
			}
		}
		n.tipo = n.e1.tipo;
	}

	public void visit(Times n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		n.e2.accept(this);
		if(!n.e1.tipo.equals(Tipo.INTEGER)){
			if(!n.e2.tipo.equals(Tipo.INTEGER)){
				throw new IllegalArgumentException("A expressão deve ser int na linha: "+n.line_number);
			}
		}
		n.tipo = n.e1.tipo;
	}
	// Fim dos tipos inteiros
	
	// Inicio dos Arrays
	public void visit(ArrayLookup n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		if( !(n.e1.tipo.equals(Tipo.INTARRAY))){
			throw new IllegalArgumentException("erro na linha"+n.e1.line_number+"esperado uma array");
		}
		n.e2.accept(this);
		if(!(n.e2.tipo.equals(Tipo.INTEGER))){
			throw new IllegalArgumentException("A expressão da array deve ser inteira na linha"+n.e2.line_number);
		}
		n.tipo = Tipo.INTARRAY;
	}

	public void visit(ArrayLength n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		if(!n.e.tipo.equals(Tipo.INTARRAY)){
			throw new IllegalArgumentException("erro na linha"+n.e.line_number+"esperado uma array");
		}
		n.tipo = Tipo.INTEGER;
	}

	// Fim dos Arrays

	//Atribuição
	public void visit(Assign n) {
		// TODO Auto-generated method stub
		n.i.accept(this);
		n.e.accept(this);		
		variavel v;
		if(lastMetodo != null){
			v = lastMetodo.pegar_parametro(n.i.s);
			if(v==null) v=lastMetodo.getLocais().get(n.i.s);
		}else{
			v=lastClass.getGlobais().get(n.i.s);
		}
//		System.out.println("KEY: "+v.getKey()+ " TIPO: "+v.getType());
		v.getType().accept(this); // Necessário nos caso de ser um Objeto
//		System.out.println("KEY: "+n.e+" TIPO: "+n.e.tipo);
		if(v.getType() instanceof IntegerType){
			n.i.tipo = Tipo.INTEGER;
		}else if(v.getType() instanceof BooleanType){
			n.i.tipo = Tipo.BOOLEAN;
		}else{
			n.i.tipo = v.getType().tipo;
		}
		if((n.e.tipo instanceof ClassTipo)){
			if(!((n.e.tipo.igual(n.i.tipo))||(n.e.tipo.insta(n.i.tipo)))){
				throw new IllegalArgumentException("O objeto não pode ser instanciado na linha: "+n.line_number);
			}
		}else{
			if(!n.i.tipo.equals(n.e.tipo)){
				throw new IllegalArgumentException("Atribuição incorreta de tipos na linha: "+n.line_number);
			}
		}
//		System.out.println("N.i: "+n.i.tipo+" n.e.tipo: " +n.e.tipo);
		
		n.tipo = n.i.tipo;
	}
	//Atribuição de Array
	public void visit(ArrayAssign n) {
		// TODO Auto-generated method stub
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
		variavel v;
		if(lastMetodo != null){
			v = lastMetodo.pegar_parametro(n.i.s);
			if(v==null) v=lastMetodo.getLocais().get(n.i.s);
		}else{
			v=lastClass.getGlobais().get(n.i.s);
		}
		if(!n.e1.tipo.equals(Tipo.INTEGER)){
			throw new IllegalArgumentException("Esperado valor inteiro no Colcheite: "+n.line_number);
		}
		if(!n.e2.tipo.equals(Tipo.INTEGER)){
			throw new IllegalArgumentException("Esperado valor inteiro na atribuição da linha: "+n.line_number);
		}
		if(!(v.getType() instanceof IntArrayType)){
			throw new IllegalArgumentException("Esperado uma array na linha: "+n.line_number);
		}
		n.i.tipo = Tipo.INTARRAY;
		n.tipo = Tipo.INTARRAY;
	}
	
	// Outros

	public void visit(Print n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		System.out.println(n.e.tipo);
	}
	
	public void visit(Block n) {
		// TODO Auto-generated method stub
		for ( int i = 0; i < n.sl.size(); i++ ) {
	        n.sl.elementAt(i).accept(this);
	    }
	}	
	
	public void visit(Call n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		System.out.println("AQUI: "+n.e.tipo);
	}


	public void visit(IdentifierExp n) {
		// TODO Auto-generated method stub
		variavel v;
		if(lastMetodo != null){
			v = lastMetodo.pegar_parametro(n.s);
			if(v==null) v=lastMetodo.getLocais().get(n.s);
		}else{
			v=lastClass.getGlobais().get(n.s);
		}
		if(v.getType() instanceof IntegerType){
			n.tipo = Tipo.INTEGER;
		}else{
			n.tipo = Tipo.BOOLEAN;
		}
		//System.out.println("LINHA: "+n.s);
		
	}

	public void visit(This n) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	// Não Necessários

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
		
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
		n.tipo = new Tipo(n.s);
	}

	// fim não necessários

	
	
	
	@Override
	public String toString(){
		String result = "";
		result += "Classes:\n";
		for(String s : table.classes.keySet()){
			result += s+"\n";
			Classe c = table.classes.get(s);
			if(c.getPai() != null){
				result += "\tExtends\t"+c.getPai()+"\n";
			}
			result += c.toString();
		}
		return result;
	}

}