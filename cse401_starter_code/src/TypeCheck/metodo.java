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
	
	public metodo(String s, Type t){
		this.key = s;
		tipo = t;
		locais = new HashMap<String,variavel>();
		parametros = new ArrayList<variavel>();
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
}
