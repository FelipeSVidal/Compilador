package TypeCheck;

import AST.Type;
/*
 * Ultimo estagio
 */
public class variavel {
	private String key;
	private Type tipo;
	
	public variavel(String key, Type type){
		this.key = key;
		this.tipo = type;
	}

	public String getKey() {
		return key;
	}

	public Type getType() {
		return tipo;
	}
	
	
	
	
}
