package model.entities;

public class Cliente extends Pessoa {

	private int id;
	private Divida divida;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Divida getDivida() {
		return divida;
	}
	public void setDivida(Divida divida) {
		this.divida = divida;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome:"+ getNome() + ", divida=" + divida + "]";
	}
	
	
	
  
	

}
