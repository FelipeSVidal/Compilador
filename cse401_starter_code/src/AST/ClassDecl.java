package AST;
import TypeCheck.CriarTabela;
import AST.Visitor.Visitor;

public abstract class ClassDecl extends ASTNode{
  public ClassDecl(int ln) {
    super(ln);
  }
  public abstract void accept(Visitor v);
  public abstract void accept(CriarTabela v);
	// TODO Auto-generated method stub
}
