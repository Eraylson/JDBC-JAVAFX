package model.entities;

import java.util.Date;

public class Divida {
	

	private Integer id;
	private double valor;
	private Date data; 
	private String descricao;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return " Divida [id=" + id + ", valor=" + valor + ", data=" + data + ", descricao=" + descricao + "]";
	}
	
	
	


}
