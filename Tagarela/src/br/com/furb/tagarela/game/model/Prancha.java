package br.com.furb.tagarela.game.model;

import br.com.furb.tagarela.model.Plan;

public class Prancha {

	private Simbolo simbolo = null;
	
	public Prancha(Simbolo simbolo) {
		super();
		
		this.simbolo = simbolo;		
	}
		
	public Simbolo getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(Simbolo simbolo) {
		this.simbolo = simbolo;
	}
									
}
