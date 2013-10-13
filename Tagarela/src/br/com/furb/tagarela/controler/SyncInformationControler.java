package br.com.furb.tagarela.controler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.furb.tagarela.model.Category;
import br.com.furb.tagarela.model.CategoryDao;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.CategoryDao.Properties;
import br.com.furb.tagarela.model.Symbol;
import br.com.furb.tagarela.model.SymbolDao;
import br.com.furb.tagarela.utils.Base64Utils;
import br.com.furb.tagarela.utils.JsonUtils;
import br.com.furb.tagarela.view.activities.MainActivity;
import android.app.Activity;
import android.os.AsyncTask;

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
	
	private class SyncSymbolsTask extends AsyncTask<Integer, Integer, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			String results = JsonUtils.getSymbolsResponse();
			results = JsonUtils.validaJson(results);
			JSONArray symbols;
			try {
				symbols = new JSONArray(results);
				SymbolDao symbolDao = DaoProvider.getInstance(null).getSymbolDao();
				for (int i = 0; i < symbols.length(); i++) {
					JSONObject symbol = symbols.getJSONObject(i);
					if (symbol.getInt("user_id") == MainActivity.getUsuarioLogado().getServerID()
							&& symbolDao.queryBuilder().where(SymbolDao.Properties.ServerID.eq(symbol.getInt("id"))).list().size() <= 0) {
						Symbol newSymbol = new Symbol();
						newSymbol.setCategoryID(symbol.getInt("category_id"));
						newSymbol.setIsGeneral(false);
						newSymbol.setName(symbol.getString("name"));
						newSymbol.setUserID(symbol.getInt("user_id"));
						newSymbol.setServerID(symbol.getInt("id"));
						newSymbol.setPicture(Base64Utils.decodeImageBase64ToByteArray(symbol.getString("image_representation").replaceAll("@", "+")));
						newSymbol.setSound(Base64Utils.decodeAudioFromBase64(symbol.getString("sound_representation").replaceAll("@", "+")));
						symbolDao.insert(newSymbol);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private class SyncCategoriesTask extends AsyncTask<Integer, Integer, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			String results = JsonUtils.getCategoriesResponse();
			results = JsonUtils.validaJson(results);
			try {
				JSONArray categories = new JSONArray(results);
				CategoryDao categoryDao = DaoProvider.getInstance(null).getCategoryDao();
				for (int i = 0; i < categories.length(); i++) {
					JSONObject category = categories.getJSONObject(i);
					if (categoryDao.queryBuilder().where(Properties.ServerID.eq(category.getInt("id"))).list().size() <= 0) {
						Category newCategory = new Category();
						newCategory.setBlue(category.getInt("blue"));
						newCategory.setGreen(category.getInt("green"));
						newCategory.setRed(category.getInt("red"));
						newCategory.setName(category.getString("name"));
						newCategory.setServerID(category.getInt("id"));
						categoryDao.insert(newCategory);
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	

}
