package br.com.furb.tagarela.game.banco.model;

public class Prancha {

	private Integer id;
	private Integer plano;
	private Integer simbolo;
	private Integer posicao;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;	
	}
	
	public Integer getPlano() {
		return plano;
	}
	
	public void setPlano(Integer plano) {
		this.plano = plano;
	}
	
	public Integer getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(Integer simbolo) {
		this.simbolo = simbolo;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
		
}
