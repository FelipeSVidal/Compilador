package AST;
import TypeCheck.CriarTabela;
import AST.Visitor.Visitor;

public class ClassDeclExtends extends ClassDecl {
  public Identifier i;
  public Identifier j;
  public VarDeclList vl;  
  public MethodDeclList ml;
 
  public ClassDeclExtends(Identifier ai, Identifier aj, 
                  VarDeclList avl, MethodDeclList aml, int ln) {
    super(ln);
    i=ai; j=aj; vl=avl; ml=aml;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  @Override
  public void accept(CriarTabela criarTabela) {
		// TODO Auto-generated method stub
		criarTabela.visit(this);
	}
}
