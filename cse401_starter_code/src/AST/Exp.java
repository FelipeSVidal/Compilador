package AST;
import AST.Visitor.Visitor;
import TypeCheck.CriarTabela;

public abstract class Exp extends ASTNode {
    public Exp(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v);
}
