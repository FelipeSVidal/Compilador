package TypeCheck;

import java.util.HashMap;
import java.util.Map;
/*
 * Primeiro Estágio
 */
public class tabela{
	Map<String,Classe> classes ;
	
	public tabela(){
		classes = new HashMap<String,Classe>(); 
	}
	
	public boolean adicionar_classe(String s, Classe c){
		if(classes.containsKey(s)){
			return false;
		}
		classes.put(s, new Classe(s,c));
//		System.out.println("Adicionou"+);
		return true;
	}
	
	public Classe pegar_classe(String s){
		//System.out.println("nome da busca"+s);
		if(classes.containsKey(s)){
			return classes.get(s);
		}
		return null;
	}
	
	public metodo pegar_metodos(String s,String c2){
		Classe c = pegar_classe(c2);
		if(c == null) throw new IllegalArgumentException("A classe: "+c2+"não definida");
		while(c != null){
			if(c.pegar_metodo(s) !=null){
				return c.pegar_metodo(s);
			}else{
				if(c.getPai()== null){
					c = null;
				}else{
					c = c.getPai();
				}
			}
		}
		throw new IllegalArgumentException("Metodo" +s+ "não definido na classe"+c2);
	}
	
	public variavel pegar_variavel(metodo m,Classe c, String s){
		if(m != null){
			if(m.getLocais().containsKey(s)){
				return m.getLocais().get(s);
			}
			if(m.getParametros().contains(s)){
				return m.pegar_parametro(s);
			}
		}
		
		while(c != null){
			if(c.pegar_global(s)!= null){
				return c.pegar_global(s);
			}else{
				if(c.getPai()== null){
					c = null;
				}else{
					c = c.getPai();
				}
			}
		}
		throw new IllegalArgumentException("Variavel "+s+"não definida");
		
	}
	
	
	
}
