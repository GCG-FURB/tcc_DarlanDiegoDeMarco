package br.com.furb.tagarela.game.model;

import br.com.furb.tagarela.model.Plan;

public class PranchaBanco {

	private SimboloBanco simbolo = null;
	private Plan pranchaBD = new Plan();
	
	public PranchaBanco(Plan pranchaBD, SimboloBanco simbolo) {
		super();
		
		this.simbolo = simbolo;		
		this.pranchaBD = pranchaBD;
	}
		
	public SimboloBanco getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(SimboloBanco simbolo) {
		this.simbolo = simbolo;
	}
									
}
