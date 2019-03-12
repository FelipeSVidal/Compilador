package TypeCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AST.Type;
/*
 * Terceiro Estagio
 */
public class metodo {
	private String key;
	private Type tipo;
	private Map<String,variavel> locais;
	private List<variavel> parametros;
	private int offset;
	static int os;
	
	public metodo(String s, Type t){
		this.key = s;
		tipo = t;
		locais = new HashMap<String,variavel>();
		parametros = new ArrayList<variavel>();
		this.offset = Classe.os+4;
		Classe.os += 4;
	}
	
	public boolean adicionar_parametros(variavel v){
		if(parametros.contains(v)){
			return false;
		}
		parametros.add(v);
		return true;
	}
	
	public boolean adicionar_locais(String s, variavel v){
		if(locais.containsKey(s)){
			return false;
		}
		locais.put(s, v);	
		return true;
	}
	
	public variavel pegar_parametro(String s){
		for(variavel v: parametros){
			if(v.getKey().equals(s)){
				return v;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public Type getTipo() {
		return tipo;
	}

	public Map<String, variavel> getLocais() {
		return locais;
	}

	public List<variavel> getParametros() {
		return parametros;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

	public void setLocais(Map<String, variavel> locais) {
		this.locais = locais;
	}

	public void setParametros(List<variavel> parametros) {
		this.parametros = parametros;
	}
	
	public String toString(){
		String result = "\t\t"+this.key+"\t -> \t"+this.tipo+"\n" +
				"\t\t\tParametros: \n";
		for(variavel v : parametros){
			result +="\t\t\t\t";
			result += v.getKey()+"\t -> \t"+v.getType()+"\t -> \t"+v.getOffset()+"\n";
		}
		result += "\t\t\tLocais: \n";
		for(String s : locais.keySet()){
			variavel v = locais.get(s);
			result +="\t\t\t\t";
			result += v.getKey()+"\t -> \t"+v.getType()+"\t -> \t"+v.getOffset()+"\n";
		}
		
		return result;
	}

	public int getOffset() {
		return offset;
	}
}
