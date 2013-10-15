package br.com.furb.tagarela.controler.asynctasks;

import android.app.Activity;
import android.view.View;


public class SyncInformationControler {

	private static SyncInformationControler syncInformationControler;
	
	private SyncInformationControler(){
	}
	
	public static SyncInformationControler getInstance(){
		if(syncInformationControler == null){
			syncInformationControler = new SyncInformationControler();
		}
		return syncInformationControler;
	}
	
	public void syncSymbols(){
		new SyncSymbolsTask().execute();
	}
	
	public void syncCategories(){
		new SyncCategoriesTask().execute();
	}
	
	public void syncUser(Activity activity, String id){
		new SyncUserTask(activity).execute(id);
	}

}
