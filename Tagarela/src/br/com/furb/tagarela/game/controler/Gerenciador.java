package br.com.furb.tagarela.game.controler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import br.com.furb.tagarela.game.model.Plano;
import br.com.furb.tagarela.game.model.Prancha;
import br.com.furb.tagarela.game.model.Simbolo;
import br.com.furb.tagarela.game.util.LeitorArquivo;
import br.com.furb.tagarela.game.util.Util;
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
import br.com.furb.tagarela.utils.Base64Utils;

public class Gerenciador extends Observable {

	public final static String dirPlanos      = "planos";
	public final static String dirSimbolos    = "simbolos";
	public final static String dirCoordenadas = "coordenadas";
	public final static String dirAudios      = "audios";
	public final static String dirCheckPoints = "checkpoints";
	
	public final static String HTTP_SIMBOLOS  = "http://murmuring-falls-7702.herokuapp.com/private_symbols";
	public final static String HTTP_PLANOS    = "http://murmuring-falls-7702.herokuapp.com/plans";
	public final static String HTTP_PRANCHAS  = "http://murmuring-falls-7702.herokuapp.com/symbol_plans";	
	
	private static Gerenciador instance = null;
	private List<Plano> planos = null;
	private List<Simbolo> checkPoints = null;
	private LeitorArquivo iniCheckPoints = null;			
	private Context context = null;
	private Typeface fontJogo = null;
	private int sizeFont = 60;
	//private DBHelper helper = null;

	private Gerenciador() {		
		this.planos = new ArrayList<Plano>();				
		this.checkPoints = new ArrayList<Simbolo>();				
	}

	public static Gerenciador getInstance(){ 
		 if (instance == null) {
			  instance = new Gerenciador();
		 }

		 return instance; 		
	}
		
	public void init(){
		planos.clear();
		checkPoints.clear();
	}
		
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
		
		if (this.fontJogo == null)
			this.fontJogo = Typeface.createFromAsset(this.context.getAssets(), "fonts/Prescriptbold.ttf");
				
	//	this.helper = DBHelper.getInstance(context);
	}
	
	public Typeface getFontJogo() {
		return fontJogo;
	}

	public void setFontJogo(Typeface fontJogo) {
		this.fontJogo = fontJogo;
	}

	public int getsizeFont() {
		return sizeFont;
	}

	public void setsizeFont(int sizeFont) {
		this.sizeFont = sizeFont;
	}
	
	public String getDirPlanos(){
		return context.getExternalFilesDir(null).getAbsolutePath() + "/" + dirPlanos;
	}
	
	public String getDirCheckPoints(){
		return context.getExternalFilesDir(null) + "/" + dirCheckPoints;
	}
			
	public void prepararArquivos(){
		new Thread() {		
			public void run() {
				//downloadArquivos();		
				
				CarregarPlanos();
				
				CarregarCheckPoints();
				
				InicializarBanco();
				
				//CarregarPlanosBD(); 
				
				setChanged();
				notifyObservers();				
			}

		}.start();			
	}
	
	
	/*
	    Sincronizar categorias -> http://murmuring-falls-7702.herokuapp.com/categories
		Sincronizar símbolos -> http://murmuring-falls-7702.herokuapp.com/private_symbols
		Sincronizar planos -> http://murmuring-falls-7702.herokuapp.com/plans
		Sincronizar símbolos de um plano -> http://murmuring-falls-7702.herokuapp.com/symbol_plans

		A symbol_plans faz o relacionamento entre um plano e um símbolo, para exemplificar, na tabela estaria assim:

		ID: 10
		SYMBOL_ID : 25
		PLAN_ID : 2
		POSITION: 3

		Ou seja, o símbolo de ID 25 está no plano de ID 2 na posição 3.

		Então a ordem de sincronização para mostrar um plano na tela é private_symbols -> plans -> symbol_plans

		E para pegar um desses pelo id é só acrescentar o /{codigo} ao final igual na url de users !
*/	
	
	private void downloadArquivos() {
		if (Util.checkNetworkState(context)) {	
			//downloadSimbolos();
			//downloadPlanos();
			//downloadPranchas();					
		}								
	}	
	
	private JSONArray getJsonHttp(String url){
		HttpGet httpGet = new HttpGet(url);

		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");

		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = null;

			response = client.execute(httpGet);

			String content = null;
			content = EntityUtils.toString(response.getEntity());

			return new JSONArray(content);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}
	
//	private void downloadSimbolos() {
//		JSONArray jsonArray = getJsonHttp(HTTP_SIMBOLOS);
//		
//		if (jsonArray != null) {
//		    SimboloDAO dao = new SimboloDAO(context);
//		    
//		    for (int i = 0; i < jsonArray.length(); i++) {
//		        try {
//		        	JSONObject j = jsonArray.getJSONObject(i);
//		        	
//		        	br.com.furb.tagarela.game.banco.model.Simbolo simbolo = new br.com.furb.tagarela.game.banco.model.Simbolo();
//		        	
//		        	simbolo.setId(j.getInt("id"));
//		        	simbolo.setNome(j.getString("name"));
//		        	simbolo.setImagem(j.getString("image_representation"));
//		        	simbolo.setAudio(j.getString("sound_representation"));
//		        	simbolo.setCategoria(j.getInt("category_id"));
//		        	simbolo.setUsuario(j.getInt("user_id"));
//		        	
//		        	if (dao.registroExiste(simbolo)) {
//		        		dao.alterar(simbolo);		        				        		
//		        	}
//		        	else {
//		        		dao.inserir(simbolo);		        		
//		        	}
//		        				        			        	
//		        } catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		        		        		        		    		        
//		    }
//		    			
//		}
//		
//	}
//
//	private void downloadPlanos() {
//		JSONArray jsonArray = getJsonHttp(HTTP_PLANOS);
//		
//		if (jsonArray != null) {
//		    PlanoDAO dao = new PlanoDAO(context);
//		    
//		    for (int i = 0; i < jsonArray.length(); i++) {
//		        try {
//		        	JSONObject j = jsonArray.getJSONObject(i);
//		        	
//		        	br.com.furb.tagarela.game.banco.model.Plano plano = new br.com.furb.tagarela.game.banco.model.Plano();
//		        			        	
//		        	plano.setId(j.getInt("id"));
//		        	plano.setNome(j.getString("name"));
//		        	plano.setPaciente(j.getInt("patient_id"));
//		        	plano.setUsuario(j.getInt("user_id"));
//		        	
//		        	if (dao.registroExiste(plano)) {
//		        		dao.alterar(plano);		        				        		
//		        	}
//		        	else {
//		        		dao.inserir(plano);		        		
//		        	}
//		        				        			        	
//		        } catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		        		        		        		    		        
//		    }
//		    			
//		}
//	}
//
//	private void downloadPranchas() {
//		JSONArray jsonArray = getJsonHttp(HTTP_PRANCHAS);
//		
//		if (jsonArray != null) {
//		    PranchaDAO dao = new PranchaDAO(context);
//		    
//		    for (int i = 0; i < jsonArray.length(); i++) {
//		        try {
//		        	JSONObject j = jsonArray.getJSONObject(i);
//		        	
//		        	br.com.furb.tagarela.game.banco.model.Prancha prancha = new br.com.furb.tagarela.game.banco.model.Prancha();
//		        			       		        	
//		        	prancha.setId(j.getInt("id"));
//		        	prancha.setPlano(j.getInt("plans_id"));
//		        	prancha.setSimbolo(j.getInt("private_symbols_id"));
//		        	prancha.setPosicao(j.getInt("position"));
//		        	
//		        	if (dao.registroExiste(prancha)) {
//		        		dao.alterar(prancha);		        				        		
//		        	}
//		        	else {
//		        		dao.inserir(prancha);		        		
//		        	}
//		        				        			        	
//		        } catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		        		        		        		    		        
//		    }
//		    			
//		}
//				
//	}


	public void gravarJSON(String name, String json){
		
		try {

			File f = new File(context.getExternalFilesDir(null) + "/" + name + ".txt");		
			
			if (!f.exists()) {
				f.createNewFile();
			}
						
			FileWriter fw;
			fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(json);					
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
		
//	private String getJsonResponse() {
//		//URL_USERS
//		
//		HttpGet httpGet = new HttpGet("http://murmuring-falls-7702.herokuapp.com/users");
//		httpGet.addHeader("Accept", "application/json");
//		httpGet.addHeader("Content-Type", "application/json");
//		String json = null;
//	
//		try {
//			HttpResponse response = HttpUtils.doRequest(httpGet);
//			json = HttpUtils.getContent(response);
//		} catch (Exception e) {
//			return e.getLocalizedMessage();
//		}
//		
//		return json;
//	}	
	
	private void InicializarBanco() {
		try {
			int idPlano = 300;
			int idPlanoXPrancha = 400;
			int idPrancha = 500;
			int idPranchaXSimbolo = 600;
			int idSimbolo = 700;
			int idSimboloCheckPoint = 800;
					
			GroupPlanDao planoDAO = DaoProvider.getInstance(null).getGroupPlanDao();
			GroupPlanRelationshipDao planoXPranchaDAO = DaoProvider.getInstance(null).getGroupPlanRelationshipDao();
			PlanDao pranchaDAO = DaoProvider.getInstance(null).getPlanDao();
			SymbolPlanDao pranchaXSimboloDAO = DaoProvider.getInstance(null).getSymbolPlanDao();
			SymbolDao simboloDAO = DaoProvider.getInstance(null).getSymbolDao();
			
			for (Plano plano: planos) {
				
				GroupPlan planoBD = new GroupPlan();
				planoBD.setServerID(idPlano);
				planoBD.setName(plano.getNome());
				planoBD.setHunterID(idSimboloCheckPoint);
				planoBD.setPreyID(idSimboloCheckPoint+10);			
				planoBD.setIsNative(true);
				
				planoDAO.insert(planoBD);
				idPlano++;
				
				int position = 0;
				for (Prancha prancha : plano.getPranchas()) {					
					
					Plan pranchaBD = new Plan();
					pranchaBD.setServerID(idPrancha);
					pranchaBD.setName("???");
					pranchaBD.setLayout(0);
					pranchaBD.setPatientID(0);
					pranchaBD.setPlanType(0);
					pranchaBD.setUserID(0);
					pranchaBD.setDescription("???");								
					
					pranchaDAO.insert(pranchaBD);
					idPrancha++; 				
									
					GroupPlanRelationship planoXPranchaBD = new GroupPlanRelationship();
					planoXPranchaBD.setServerID(idPlanoXPrancha);
					planoXPranchaBD.setGroupID(planoBD.getServerID());
					planoXPranchaBD.setPlanID(pranchaBD.getServerID());
					
					planoXPranchaDAO.insert(planoXPranchaBD);
					idPlanoXPrancha++;
					
					Simbolo simbolo = prancha.getSimbolo();
					
					Symbol simboloBD = new Symbol();
					simboloBD.setServerID(idSimbolo);
					simboloBD.setName(simbolo.getSimboloName());
					simboloBD.setCategoryID(0);
					simboloBD.setIsGeneral(true);
					simboloBD.setUserID(0);
					simboloBD.setAlphaID(0);					
					simboloBD.setAscRepresentation(prancha.getSimbolo().getSimboloName());
					
					Bitmap bmp = simbolo.getSimboloBmp(100); 
					if (bmp != null) {
						simboloBD.setPicture(Base64Utils.encodeImageTobase64(bmp).getBytes());
					}
					
					File file = new File(simbolo.getCaminhoAudio());
					if (file.exists()) {
						simboloBD.setSound(Base64Utils.encodeAudioToBase64(simbolo.getCaminhoAudio()).getBytes());
					}
									
					simboloDAO.insert(simboloBD);
					idSimbolo++;
						
					SymbolPlan pranchaXSimboloBD = new SymbolPlan();
					pranchaXSimboloBD.setServerID(idPranchaXSimbolo);
					pranchaXSimboloBD.setPlanID(pranchaBD.getServerID());
					pranchaXSimboloBD.setSymbolID(simboloBD.getServerID());
					pranchaXSimboloBD.setPosition(position);
					
					pranchaXSimboloDAO.insert(pranchaXSimboloBD);
					idPranchaXSimbolo++;
					position++;				
															
				}						
			}		
			
			int alphaID = 0;
			for (Simbolo simbolo : checkPoints) {
				alphaID++;
				
				Symbol simboloBD = new Symbol();
				simboloBD.setServerID(idSimboloCheckPoint);
				simboloBD.setName(simbolo.getSimboloName());
				simboloBD.setCategoryID(0);
				simboloBD.setIsGeneral(true);
				simboloBD.setUserID(0);
				simboloBD.setAlphaID(alphaID);
				
				Bitmap bmp = simbolo.getSimboloBmp(100); 
				if (bmp != null) {
					simboloBD.setPicture(Base64Utils.encodeImageTobase64(bmp).getBytes());
				}
				
				File file = new File(simbolo.getCaminhoAudio());
				if (file.exists()) {
					simboloBD.setSound(Base64Utils.encodeAudioToBase64(simbolo.getCaminhoAudio()).getBytes());
				}
								
				simboloDAO.insert(simboloBD);
				idSimboloCheckPoint++;								
			}
			
//			planoDAO;
//			planoXPranchaDAO;
//			pranchaDAO;
//			pranchaXSimboloDAO;
//			simboloDAO;

			Log.i("PLANOS", "-------------------------------");
			for (GroupPlan plano : planoDAO.loadAll()) {
				Log.i("PLANOS", plano.getServerID() + " " + plano.getName());				
			}
			Log.i("PLANOS", "-------------------------------");
			
			Log.i("PLANOS X PRANCHAS", "-------------------------------");
			for (GroupPlanRelationship planoXPrancha : planoXPranchaDAO.loadAll()) {
				Log.i("PLANOS X PRANCHAS", planoXPrancha.getServerID() + " " + planoXPrancha.getGroupID() + " " + planoXPrancha.getPlanID());				
			}
			Log.i("PLANOS X PRANCHAS", "-------------------------------");
			
			Log.i("PRANCHAS", "-------------------------------");
			for (Plan prancha : pranchaDAO.loadAll()) {
				Log.i("PRANCHAS", prancha.getServerID() + " " + prancha.getName());				
			}
			Log.i("PRANCHAS", "-------------------------------");

			Log.i("PRANCHAS X SIMBOLOS", "-------------------------------");
			for (SymbolPlan pranchaXSimbolo : pranchaXSimboloDAO.loadAll()) {
				Log.i("PRANCHAS X SIMBOLOS", pranchaXSimbolo.getServerID() + " " + pranchaXSimbolo.getPlanID() + " " + pranchaXSimbolo.getSymbolID());				
			}
			Log.i("PRANCHAS X SIMBOLOS", "-------------------------------");

			Log.i("SIMBOLOS", "-------------------------------");
			for (Symbol simbolo : simboloDAO.loadAll()) {
				Log.i("SIMBOLOS", simbolo.getServerID() + " " + simbolo.getName());				
			}
			Log.i("SIMBOLOS", "-------------------------------");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
	
	private void CarregarPlanosBD() {
		init();
		
//		planoDAO;
//		planoXPranchaDAO;
//		pranchaDAO;
//		pranchaXSimboloDAO;
//		simboloDAO;

		GroupPlanDao planoDAO = DaoProvider.getInstance(null).getGroupPlanDao();
		GroupPlanRelationshipDao planoXPranchaDAO = DaoProvider.getInstance(null).getGroupPlanRelationshipDao();
		PlanDao pranchaDAO = DaoProvider.getInstance(null).getPlanDao();
		SymbolPlanDao pranchaXSimboloDAO = DaoProvider.getInstance(null).getSymbolPlanDao();
		SymbolDao simboloDAO = DaoProvider.getInstance(null).getSymbolDao();
		
		for (GroupPlan planoBD : planoDAO.loadAll()) {
			Plano plano = new Plano(planoBD.getName());
			
			for (GroupPlanRelationship planoXPranchaBD : planoXPranchaDAO.queryRaw("where group_ID = ?", planoBD.getServerID().toString())) {
								
				for (Plan pranchaBD : pranchaDAO.queryRaw("where server_ID = ?", planoXPranchaBD.getPlanID().toString())) {
					
					for (SymbolPlan pranchaXSimboloBD : pranchaXSimboloDAO.queryRaw("where plan_ID = ?", pranchaBD.getServerID().toString())) {
												
						for (Symbol simboloBD : simboloDAO.queryRaw("where server_ID = ?", pranchaXSimboloBD.getSymbolID().toString())) {
							Simbolo simbolo = new Simbolo("", simboloBD.getName(), simboloBD.getServerID());
							simbolo.simboloBD = simboloBD;
							Prancha prancha = new Prancha(simbolo);
							plano.addPrancha(prancha);
						}
					}																								
				}				
			}
			
			planos.add(plano);
		}
				
	}
	
	private void CarregarPlanos() {						
		init();
		File file = new File(getDirPlanos());

		File[] planosArray;
		planosArray = file.listFiles();

		Arrays.sort(planosArray, new Comparator<File>() {
			@Override
			public int compare(final File f1, final File f2) {
				LeitorArquivo infoPlano1 = new LeitorArquivo(f1.getAbsolutePath() + "/ini.txt");
				Boolean nativePlano1 = Boolean.valueOf(infoPlano1.get("native",	"true"));

				LeitorArquivo infoPlano2 = new LeitorArquivo(f2.getAbsolutePath() + "/ini.txt");
				Boolean nativePlano2 = Boolean.valueOf(infoPlano2.get("native",	"true"));

				return nativePlano1 && nativePlano2 ? -1 : nativePlano1 == nativePlano2 ? 0 : 1;
			}
		});

		Plano plano = null;
		for (File planoF : planosArray) {

			plano = new Plano(planoF.getName(), planoF);
			plano.carregarPranchas();
			planos.add(plano);

		}
	}
	
	public int getIdCheckPoint(String simbolo){
		int id = 1;
		String nome = iniCheckPoints.get("" + id);
		
		while (nome != null) {
			if (nome.equals(simbolo)) {
				return id;
			}
			
			id++;
			nome = iniCheckPoints.get("" + id);
		}
		
		return 999;
	}
	
	private void CarregarCheckPoints() {
		iniCheckPoints = new LeitorArquivo(getDirCheckPoints() + "/ini.txt");
		File file = new File(getDirCheckPoints() + "/" + dirSimbolos);

		File[] cPArray;
		cPArray = file.listFiles();

		Arrays.sort(cPArray, new Comparator<File>() {
			@Override
			public int compare(final File f1, final File f2) {
				String name;				
				name = f1.getName().substring(0, f1.getName().indexOf(".png"));				
				int v1 = getIdCheckPoint(name);
				
				name = f2.getName().substring(0, f2.getName().indexOf(".png"));				
				int v2 = getIdCheckPoint(name);

				return v1 < v2 ? -1 : v1 == v2 ? 0 : 1;
			}
		});

		Simbolo simbolo = null;
		for (File simboloF : cPArray) {
			String name;				
			name = simboloF.getName().substring(0, simboloF.getName().indexOf(".png"));				
			int id = getIdCheckPoint(name);
			
			simbolo = new Simbolo(getDirCheckPoints(), name, id);
			checkPoints.add(simbolo);

		}				
	}
		
	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}

	public Plano getPlano(int plano) {
		return planos.get(plano);
	}

	public void addPlano(Plano plano) {
		planos.add(plano);		
	}
	
	public List<Simbolo> getCheckPoints() {
		return checkPoints;
	}

	public void setCheckPoints(List<Simbolo> checkPoints) {
		this.checkPoints = checkPoints;
	}

	public void addCheckPoint(Simbolo simbolo) {
		checkPoints.add(simbolo);		
	}	
	
	public Simbolo getCheckPoint(int id){
		for (Simbolo simbolo : checkPoints) {
			if (simbolo.getId() == id) {
				return simbolo;
			}
			
		}
		
		return null;
	}
	
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		super.notifyObservers();
				
	}

	public Simbolo getCheckPointRelacionado(String simboloName) {
		String nome = iniCheckPoints.get(simboloName);
		
		for (Simbolo simbolo : checkPoints) {
			if (nome.equals(simbolo.getSimboloName())) {
				return simbolo;
			}
			
		}
		
		// TODO Auto-generated method stub
		return null;
	}			
}
