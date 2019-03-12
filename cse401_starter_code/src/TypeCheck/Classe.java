package TypeCheck;

import java.util.HashMap;
import java.util.Map;

import AST.Identifier;
import AST.IdentifierType;
import AST.Type;
/*
 * Segundo Estágio
 */
public class Classe {
	private String key;
//	private Type tipo;
	private Classe pai;
	private Map<String,metodo> metodos;
	private Map<String,variavel> globais;
	private int offset;
	static int os;
	public Classe(String key, Classe pai) {
		this.key = key;
		this.pai = pai;
//		this.tipo = (IdentifierType) t;
		this.metodos = new HashMap<String, metodo>();
		this.globais = new HashMap<String, variavel>();
		this.offset = Classe.os+4;
		Classe.os += 4;
	}
	
	public boolean adicionar_metodos(String s, metodo m){
		if(metodos.containsKey(s)){
			return false;
		}
		metodos.put(s, m);
		return true;
	}
	public boolean adicionar_globais(String s, variavel v){
		if(globais.containsKey(s)){
			return false;
		}
		globais.put(s, v);
		return true;
	}
	
	public metodo pegar_metodo(String s){
		if(metodos.containsKey(s)){
			return metodos.get(s);
		}
		return null;
	}
	
	public variavel pegar_global(String s){
		if(globais.containsKey(s)){
			return globais.get(s);
		}
		return null;
	}
	
	public void atualizar_metodo(String s, metodo m){
		metodos.replace(s, m);
	}
	public void atualizar_global(String s, variavel v){
		globais.replace(s, v);
	}

	public String getKey() {
		return key;
	}
/*
	public Type getTipo() {
		return tipo;
	}
*/
	public Classe getPai() {
		return pai;
	}

	public Map<String, metodo> getMetodos() {
		return metodos;
	}

	public Map<String, variavel> getGlobais() {
		return globais;
	}

	public void setKey(String key) {
		this.key = key;
	}
/*
	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}
*/
	public void setPai(Classe pai) {
		this.pai = pai;
	}

	public void setMetodos(Map<String, metodo> metodos) {
		this.metodos = metodos;
	}

	public void setGlobais(Map<String, variavel> globais) {
		this.globais = globais;
	}
	
	public String toString(){
		String result = "\tVariaveis Globais: \n";
		for(String s: globais.keySet()){
			variavel v = globais.get(s);
			result +="\t\t";
			result += v.getKey()+"\t -> \t"+v.getType()+"\t -> \t"+v.getOffset()+"\n";
		}
		result += "\tMetodos: \n";
		for(String s:metodos.keySet()){
			metodo m = metodos.get(s);
			result += m.toString();
		}
		
		return result;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
