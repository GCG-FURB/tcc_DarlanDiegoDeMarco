package br.com.furb.tagarela.game.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.furb.tagarela.game.controler.Gerenciador;
import br.com.furb.tagarela.game.util.LeitorArquivo;
import br.com.furb.tagarela.game.util.Util;
import br.com.furb.tagarela.model.GroupPlan;


public class PlanoBanco {

	private List<PranchaBanco> pranchas = null;
	private GroupPlan planoBD = new GroupPlan();
	
	public PlanoBanco(GroupPlan planoBD) {
		super();
		this.planoBD = planoBD;
		this.pranchas = new ArrayList<PranchaBanco>();			
	}	
					
	public GroupPlan getPlanoBD() {
		return planoBD;
	}
	
	public void setPlanoBD(GroupPlan planoBD) {
		this.planoBD = planoBD;
	}

	public void addPrancha(PranchaBanco prancha){
		this.pranchas.add(prancha);		
	}
	
	public List<PranchaBanco> getPranchas() {
		return this.pranchas;
	}

	public PranchaBanco getPrancha(int pranchaIndex) {
		// TODO Auto-generated method stub
		return getPranchas().get(pranchaIndex);
	}
				
}
