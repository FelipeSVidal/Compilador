package CodeGen;
import java.util.ArrayList;
import java.util.Stack;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.ExpandedIcon;

import Tipos.Tipo;
import TypeCheck.Classe;
import TypeCheck.metodo;
import TypeCheck.tabela;
import TypeCheck.variavel;
import AST.ASTNode;
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
public class Gerar implements Visitor {
	tabela tabela;
	int indexlabel = 0;
	Classe lastClasse;
	metodo lastMetodo;
	ASTNode last;
	
	public Gerar(tabela t){
		this.tabela = t;
	}
	public void visit(Return n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Program n) {
		// TODO Auto-generated method stub
		n.m.accept(this);
		for(int i = 0; i<n.cl.size();i++){
			n.cl.elementAt(i).accept(this);
		}
	}

	public void visit(MainClass n) {
		// TODO Auto-generated method stub
		System.out.println("\t.text");
		System.out.println("\t.global asm_main\n");
		System.out.println("asm_main:");
		n.s.accept(this);
		System.out.println("ret");
	}

	public void visit(ClassDeclSimple n) {
		// TODO Auto-generated method stub
		lastClasse = this.tabela.pegar_classe(n.i.s);
		Classe pai = lastClasse.getPai();
		vtable(pai);
		if(lastClasse.getGlobais().size() >0){
			System.out.println("\t.data");
			System.out.println("\tglob_"+lastClasse.getKey()+":");
				for(variavel v : lastClasse.getGlobais().values()){
					System.out.println("\tdd\t "+v.getKey());
				}
			System.out.println("\t.code");
		}for(int i =0;i< n.ml.size();i++){
			n.ml.elementAt(i).accept(this);
		}
		lastClasse = null;
	}

	public void visit(ClassDeclExtends n) {
		// TODO Auto-generated method stub
		lastClasse = this.tabela.pegar_classe(n.i.s);
		Classe pai = lastClasse.getPai();
		vtable(pai);
		System.out.println("\t.code");
		if(lastClasse.getGlobais().size() >0){
		System.out.println("\t.data");
		System.out.println("\tglob_"+lastClasse.getKey()+":");
			for(variavel v : lastClasse.getGlobais().values()){
				System.out.println("\tdd\t "+v.getKey());
			}
			System.out.println("\t.code");
		}
		for(int i =0;i< n.ml.size();i++){
			n.ml.elementAt(i).accept(this);
		}
		lastClasse = null;
	}

	public void visit(VarDecl n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(MethodDecl n) {
		// TODO Auto-generated method stub
		this.lastMetodo = this.lastClasse.pegar_metodo(n.i.s);
		prologo();
		System.out.println("sub esp,"+(lastMetodo.getLocais().size()*4));
		/*
		for(variavel v: lastMetodo.getParametros()){
			System.out.println("push [ebp +"+v.getOffset()+"],eax");
		}
		*/
		/*
		for(variavel v: lastMetodo.getLocais().values()){
			System.out.println("mov [ebp - "+v.getOffset()+"], eax");
		}
		*/
		for(int j=0;j<n.sl.size();j++){
			n.sl.elementAt(j).accept(this);
		}
		epilogo();
		this.lastMetodo = null;
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
		for ( int i = 0; i < n.sl.size(); i++ ) {
	        n.sl.elementAt(i).accept(this);
	    }
	}

	public void visit(If n) {
		// TODO Auto-generated method stub
		/*
		 * 			exp
		 * 			jfalse elsei
		 * 			statement1 -true
		 * 			jmp done
		 *	elsei:	statement2 -false
		 *	donei:
		 */
		indexlabel++;
		last = n;
		n.e.accept(this);
		
		n.s1.accept(this);
		System.out.println("jmp done"+indexlabel);
		System.out.print("else"+indexlabel+":\t");
		n.s2.accept(this);
		System.out.println("done"+indexlabel+":");
		last = null;
				
	}
	
	public void visit(While n) {
		// TODO Auto-generated method stub
		/*
		 * testi:	exp
		 * 			jfalse	donei
		 * 			statement
		 * 			jtrue	testi
		 * donei:
		 */
		indexlabel++;
		last = n;
		System.out.print("test"+indexlabel+":\t"); 
		n.e.accept(this);
		n.s.accept(this);
		System.out.println("jmp test"+indexlabel);
		System.out.println("done"+indexlabel+":");
		last = null;
	}

	public void visit(Print n) {
		// TODO Auto-generated method stub
		if(n.e instanceof IntegerLiteral){
			System.out.print("mov eax,");
		}
		if(n.e instanceof IdentifierExp){
			System.out.print("mov eax,");
		}
		n.e.accept(this);
		System.out.println("push eax");
		System.out.println("call put");
		System.out.println("add esp,4");
		System.out.println("mov ecx,[esp]");
	}

	public void visit(Assign n) {
		// TODO Auto-generated method stub
		last = n;
		if(n.e instanceof IntegerLiteral){
			System.out.print("mov eax,");
		}
		n.e.accept(this);
		variavel v = null;
		for(variavel va:lastClasse.getGlobais().values()){
			if(va.getKey().equals(n.i.s)){
				v = va;
				System.out.println("mov eax,[ecx+"+v.getOffset()+"]");
				break;
			}
		}
		if(v == null){
			for(variavel va:lastMetodo.getLocais().values()){
				if(va.getKey().equals(n.i.s)){
					v = va;
					System.out.println("mov eax,[ebp-"+v.getOffset()+"]");
					break;
				}
			}
		}
		if(v==null){
			for(variavel va:lastMetodo.getParametros()){
				if(va.getKey().equals(n.i.s)){
					v = va;
					System.out.println("mov eax,[ebp+"+v.getOffset()+"]");
					break;
				}	
			}
		}
		
		last = null;
		
	}

	public void visit(ArrayAssign n) {
		// TODO Auto-generated method stub
		if(n.e2 instanceof IntegerLiteral){
			System.out.print("mov eax,");
		}
		n.e2.accept(this);
		System.out.println("push eax");
		n.e1.accept(this);
		System.out.println("push eax");
		System.out.println("pop edx");
		System.out.println("pop eax");
		variavel v = null;
		for(variavel va:lastClasse.getGlobais().values()){
			if(va.getKey().equals(n.i.s)){
				v = va;
				System.out.println("mov ecx,[ecx+"+v.getOffset()+"]");
				break;
			}
		}
		if(v == null){
			for(variavel va:lastMetodo.getLocais().values()){
				if(va.getKey().equals(n.i.s)){
					v = va;
					System.out.println("mov ecx,[ebp-"+v.getOffset()+"]");
					break;
				}
			}
		}
		if(v==null){
			for(variavel va:lastMetodo.getParametros()){
				if(va.getKey().equals(n.i.s)){
					v = va;
					System.out.println("mov ecx,[ebp+"+v.getOffset()+"]");
					break;
				}	
			}
		}
		System.out.println("shl edx,2");
		System.out.println("add edx,ecx");
		System.out.println("mov [edx],eax");
		System.out.println("mov ecx,[esp]");
		
	}

	public void visit(And n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		System.out.println("cmp eax, 0");
		if(last instanceof If){
			System.out.println("je else"+indexlabel);
		}else if(last instanceof While){
			System.out.println("je done"+indexlabel);
		}
		n.e2.accept(this);
		if(last instanceof If){
			System.out.println("je else"+indexlabel);
		}else if(last instanceof While){
			System.out.println("je done"+indexlabel);
		}

	}


	public void visit(LessThan n) {
		// TODO Auto-generated method stub
		System.out.print("mov eax,");
		n.e1.accept(this);
		System.out.print("mov edx,");
		n.e2.accept(this);
		if(last instanceof Not){
			System.out.println("cmp edx,eax");
		}else{
			System.out.println("cmp eax,edx");
		}
		if(last instanceof If){
			System.out.println("jge else"+indexlabel);
		}else if(last instanceof While){
			System.out.println("jge done"+indexlabel);
		}
	}

	public void visit(Plus n) {
		// TODO Auto-generated method stub
		System.out.print("mov eax,");
		n.e1.accept(this);
		System.out.print("mov edx,");
		n.e2.accept(this);
		System.out.println("add eax,edx");
	}

	public void visit(Minus n) {
		// TODO Auto-generated method stub
		System.out.print("mov eax,");
		n.e1.accept(this);
		System.out.print("mov edx,");
		n.e2.accept(this);
		System.out.println("sub eax,edx");
	}

	public void visit(Times n) {
		// TODO Auto-generated method stub
		last = n;
			System.out.print("mov eax,");
		n.e1.accept(this);
		if(n.e2 instanceof IntegerLiteral){
			System.out.print("mov edx,");
		}
		n.e2.accept(this);
		System.out.println("mul eax,edx");
		last = null;
	}

	public void visit(ArrayLookup n) {
		// TODO Auto-generated method stub
		n.e1.accept(this);
		System.out.println("push eax");
		n.e2.accept(this);
		System.out.println("pop edx");
		System.out.println("shl eax, 2");
		System.out.println("add eax,edx");
		System.out.println("mov eax, [eax]");
	}

	public void visit(ArrayLength n) {
		// TODO Auto-generated method stub
		n.e.accept(this);
		System.out.println("mov eax,[eax-4]");
	}

	public void visit(Call n) {
		// TODO Auto-generated method stub
	    n.e.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	    	System.out.print("mov eax,");
	    	n.el.elementAt(n.el.size()-(i+1)).accept(this);
	    	System.out.println("push eax");
	    }
	    if(!(n.e instanceof This)){
	    	System.out.print("mov ecx,[ebp+");
	    }
	    last = n;
	    n.e.accept(this);
	    System.out.println("mov eax,[ecx]");
	    metodo m = null;
	    for(Classe c : tabela.getClasses().values()){
	    	if(m == null){
	    		m = c.pegar_metodo(n.i.s);
	    	}
	    }
	    System.out.println("call dword ptr [eax+"+m.getOffset()+"]");
	    last = null;
	}

	public void visit(IntegerLiteral n) {
		// TODO Auto-generated method stub
		if(last instanceof ArrayAssign){
			System.out.println("mov eax,"+n.i);
		}
		else{
			System.out.println(n.i);
		}
		
	}

	public void visit(True n) {
		// TODO Auto-generated method stub
		System.out.println("mov eax,1");
	}

	public void visit(False n) {
		// TODO Auto-generated method stub
		System.out.println("mov eax,0");
	}

	public void visit(IdentifierExp n) {
		// TODO Auto-generated method stub
			variavel v = null;
			for(variavel va : lastClasse.getGlobais().values()){
				if(va.getKey().equals(n.s)){
					v = va;
					System.out.println("["+v.getKey()+"]");
				}
			}
			if(v==null){
				for(variavel va:lastMetodo.getLocais().values()){
					if(va.getKey().equals(n.s)){
						v = va;
					}
				}
				if(v!=null){
					System.out.println("ebp-"+v.getOffset());
				}
			}
			if(v==null){
				for(variavel va:lastMetodo.getParametros()){
					if(va.getKey().equals(n.s)){
						v = va;
					}
				}
				System.out.println("ebp+"+v.getOffset());
			}
		
	}

	public void visit(This n) {
		// TODO Auto-generated method stub
		System.out.println("mov ecx,"+lastClasse.getKey()+"$");
	}

	public void visit(NewArray n) {
		// TODO Auto-generated method stub
		System.out.println("push eax");
		System.out.println("add eax,1");
		System.out.println("shl eax,2");
		System.out.println("push eax");
		System.out.println("call mallocEquiv");
		System.out.println("add esp,4");
		System.out.println("pop edx");
		System.out.println("mov [eax], edx");
		System.out.println("mov ecx,[esp]");
		System.out.println("add eax,4");
	}

	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		if(last instanceof Call){
			Classe c = tabela.pegar_classe(n.i.s);
			System.out.println(c.getOffset()+"]");
			return;
		}
		int tam = 4;
		Classe c = tabela.pegar_classe(n.i.s);
		tam += c.getMetodos().size() * 4;
		while(c.getPai() != null){
			c = c.getPai();
			tam += c.getMetodos().size() *4;
		}
		System.out.println("push "+tam);
		System.out.println("call mallocEquiv");
		System.out.println("add esp, 4");
		System.out.println("lea edx,"+n.i.s+"$$");
		System.out.println("mov [eax],edx");
		System.out.println("mov ecx, eax");
		System.out.println("push ecx");
		System.out.println("call "+n.i.s+"$"+n.i.s);
		System.out.println("pop eax");
		System.out.println("mov [ebp+"+c.getOffset()+"],eax");
	}

	public void visit(Not n) {
		// TODO Auto-generated method stub
		last = n;
		n.e.accept(this);
		last = null;
	}

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
	}
	public void vtable(Classe pai){
		String spai;
		ArrayList<String> mpl = new ArrayList();
		ArrayList<String> ml = new ArrayList();
		ArrayList<String> fim = new ArrayList();

		System.out.println("\t.data");
		if(pai == null){
			System.out.println(lastClasse.getKey()+"$$"+"\tdd\t"+"0");
			System.out.println("\tdd\t"+lastClasse.getKey()+"$"+lastClasse.getKey());
			for(String s: lastClasse.getMetodos().keySet()){
				System.out.println("\tdd\t"+lastClasse.getKey()+"$"+s);
			}
		}else{
			spai = pai.getKey();
			System.out.println(lastClasse.getKey()+"$$"+"\tdd\t"+spai);
			System.out.println("\tdd\t"+lastClasse.getKey()+"$"+lastClasse.getKey());
			for(String mp : pai.getMetodos().keySet()){
				mpl.add(mp);
			}
			for(String m : lastClasse.getMetodos().keySet()){
				ml.add(m);
			}
			for(int i=0;i<mpl.size();i++){
				if(ml.contains(mpl.get(i))){
					fim.add("\tdd\t"+lastClasse.getKey()+"$"+mpl.get(i).toString());
				}else{
					fim.add("\tdd\t"+pai.getKey()+"$"+mpl.get(i).toString());
				}
			}
			for(int j =0;j<ml.size();j++){
				if(!fim.contains("\tdd\t"+lastClasse.getKey()+"$"+ml.get(j))){
					fim.add("\tdde\t"+lastClasse.getKey()+"$"+ml.get(j).toString());
				}
			}
			for(String s : fim){
				System.out.println(s);
			}

		}
		/*
		 * Adicionar todos os metodos do pai, vêndo se o filho tem
		 */
	}
	public void prologo(){
		System.out.println("push ebp");
		System.out.println("mov ebp,esp");
	}
	public void epilogo(){
		System.out.println("pop ebp");
		System.out.println("ret");
	}

}
