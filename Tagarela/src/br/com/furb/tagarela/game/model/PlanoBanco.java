package br.com.furb.tagarela.game.model;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import br.com.furb.tagarela.game.controler.Gerenciador;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.GroupPlan;
import br.com.furb.tagarela.model.GroupPlanDao;
import br.com.furb.tagarela.model.GroupPlanRelationship;
import br.com.furb.tagarela.model.GroupPlanRelationshipDao;
import br.com.furb.tagarela.model.Plan;
import br.com.furb.tagarela.model.PlanDao;
import br.com.furb.tagarela.model.Symbol;
import br.com.furb.tagarela.model.SymbolDao;
import br.com.furb.tagarela.model.SymbolPlan;
import br.com.furb.tagarela.model.SymbolPlanDao;


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

	public void gravarPlano() {
		pranchas.clear();
		
		GroupPlanRelationshipDao planoXPranchaDAO = DaoProvider.getInstance(null).getGroupPlanRelationshipDao();
		PlanDao pranchaDAO = DaoProvider.getInstance(null).getPlanDao();
		SymbolPlanDao pranchaXSimboloDAO = DaoProvider.getInstance(null).getSymbolPlanDao();
		SymbolDao simboloDAO = DaoProvider.getInstance(null).getSymbolDao();
		
		SQLiteDatabase db = planoXPranchaDAO.getDatabase();
		
		db.delete(planoXPranchaDAO.getTablename(), "Group_ID = ?", new String[] {getPlanoBD().getServerID().toString()});
				
		char[] c = planoBD.getCustomText().toCharArray();
		
		for (char d : c) {
			
			for (Symbol simboloBD : simboloDAO.queryRaw("where Asc_Representation = ?", String.valueOf(d))) {
				SimboloBanco simbolo = new SimboloBanco(simboloBD);
				
				for (SymbolPlan pranchaXSimboloBD : pranchaXSimboloDAO.queryRaw("where Symbol_ID = ?", simboloBD.getServerID().toString())) {
					
					for (Plan pranchaBD : pranchaDAO.queryRaw("where Server_ID = ?", pranchaXSimboloBD.getPlanID().toString())) {
						
						GroupPlanRelationship planoXPranchaBD = new GroupPlanRelationship();
						planoXPranchaBD.setServerID(Gerenciador.getInstance().getNextServerID(planoXPranchaDAO));
						planoXPranchaBD.setGroupID(planoBD.getServerID());
						planoXPranchaBD.setPlanID(pranchaBD.getServerID());
						planoXPranchaDAO.insert(planoXPranchaBD);
						
						PranchaBanco prancha = new PranchaBanco(pranchaBD, simbolo);				
						
						addPrancha(prancha);																		
					}										
				}								
			}						
		}						
	}

	public void excluirPlano() {
		GroupPlanDao planoDAO = DaoProvider.getInstance(null).getGroupPlanDao();
		GroupPlanRelationshipDao planoXPranchaDAO = DaoProvider.getInstance(null).getGroupPlanRelationshipDao();
		
		SQLiteDatabase db = planoXPranchaDAO.getDatabase();
		
		db.delete(planoXPranchaDAO.getTablename(), "Group_ID = ?", new String[] {getPlanoBD().getServerID().toString()});
		planoDAO.delete(planoBD);				
	}				
}
