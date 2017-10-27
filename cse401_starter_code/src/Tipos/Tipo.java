package Tipos;

public class Tipo extends AbsTipo{
	public static final Tipo INTEGER = new Tipo("int");
	public static final Tipo BOOLEAN = new Tipo("boolean");
	public static final Tipo INTARRAY = new Tipo("array");
	
	public String tipo;
	
	public Tipo(String s){
		this.tipo=s;
	}
	
	public String toString(){
		return tipo;
	}
	public String getTipo(){
		return tipo;
	}

	@Override
	public boolean igual(AbsTipo outro) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insta(AbsTipo outro) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
