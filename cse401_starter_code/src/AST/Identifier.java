package AST;
import TypeCheck.CriarTabela;
import AST.Visitor.Visitor;

public class Identifier extends ASTNode {
  public String s;

  public Identifier(String as, int ln) { 
    super(ln);
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public String toString(){
    return s;
  }

  public void accept(CriarTabela v) {
	  // TODO Auto-generated method stub
	  v.visit(this);
  }
}
