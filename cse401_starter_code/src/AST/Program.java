package AST;
import TypeCheck.CriarTabela;
import AST.Visitor.Visitor;

public class Program extends ASTNode {
  public MainClass m;
  public ClassDeclList cl;

  public Program(MainClass am, ClassDeclList acl, int ln) {
    super(ln);
    m=am; cl=acl; 
  }

  public void accept(Visitor v) {
	  v.visit(this);
  }

public void accept(CriarTabela ct) {
	// TODO Auto-generated method stub
	ct.visit(this);
}
}
