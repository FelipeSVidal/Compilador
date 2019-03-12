package TypeCheck;

import AST.Type;
/*
 * Ultimo estagio
 */
public class variavel {
	private String key;
	private Type tipo;
	private int offset;
	
	public variavel(String key, Type type,int offset){
		this.key = key;
		this.tipo = type;
		this.setOffset(offset);
	}

	public String getKey() {
		return key;
	}

	public Type getType() {
		return tipo;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	
	
}
