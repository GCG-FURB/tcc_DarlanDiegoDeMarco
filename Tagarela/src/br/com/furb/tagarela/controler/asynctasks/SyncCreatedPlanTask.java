package br.com.furb.tagarela.controler.asynctasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.Plan;
import br.com.furb.tagarela.model.PlanDao;
import br.com.furb.tagarela.model.Symbol;
import br.com.furb.tagarela.model.SymbolPlan;
import br.com.furb.tagarela.model.SymbolPlanDao;
import br.com.furb.tagarela.utils.HttpUtils;
import br.com.furb.tagarela.utils.NameValuePairBuilder;
import br.com.furb.tagarela.view.activities.MainActivity;

public class SyncCreatedPlanTask extends AsyncTask<String, Void, Void> {

	private static final String HOST = "http://murmuring-falls-7702.herokuapp.com";
	private static final String PLAN_PATIENT_ID = "plan[patient_id]";
	private static final String PLAN_USER_ID = "plan[user_id]";
	private static final String PLAN_LAYOUT = "plan[layout]";
	private static final String PLAN_NAME = "plan[name]";
	private SparseArray<Symbol> symbolPlan;
	private ProgressDialog progress;
	private Context mContext;
	private int layout;
	private String planName;

	public SyncCreatedPlanTask(SparseArray<Symbol> symbolPlan, String planName, Context mContext, int layout) {
		super();
		this.symbolPlan = symbolPlan;
		this.mContext = mContext;
		this.layout = layout;
		this.planName = planName;
	}

	protected void onPreExecute() {
		progress = new ProgressDialog(mContext);
		progress.setMessage("Aguarde...");
		progress.show();
	}

	@Override
	protected Void doInBackground(String... params) {
		int position = 0;
		int planID = sendPlan();
		int symbolPlanID = 0;
		Symbol symbol = null;

		savePlan(planID);
		for (int i = 0; i < symbolPlan.size(); i++) {
			position = symbolPlan.keyAt(i);
			symbol = symbolPlan.get(position);
			symbolPlanID = sendSymbolPlan(position, planID, symbol);
			saveSymbolPlan(symbolPlanID, planID, symbol.getServerID(), position);
		}

		return null;
	}

	private void saveSymbolPlan(int serverID, int planID, int symbolID, int position) {
		SymbolPlanDao symbolPlanDao = DaoProvider.getInstance(null).getSymbolPlanDao();
		SymbolPlan symbolPlan = new SymbolPlan();
		symbolPlan.setServerID(serverID);
		symbolPlan.setPlanID(planID);
		symbolPlan.setSymbolID(symbolID);
		symbolPlan.setPosition(position);
		symbolPlanDao.insert(symbolPlan);
	}

	private void savePlan(int planID) {
		PlanDao planDao = DaoProvider.getInstance(null).getPlanDao();
		Plan plan = new Plan();
		plan.setName(planName);
		plan.setPatientID(MainActivity.getUsuarioLogado().getServerID());
		plan.setUserID(MainActivity.getUsuarioLogado().getServerID());
		plan.setLayout(layout);
		plan.setServerID(planID);
		plan.setDescription("");
		plan.setPlanType(0);

		planDao.insert(plan);
	}

	private int sendSymbolPlan(int position, int planID, Symbol symbol) {
		try {
			HttpPost post = new HttpPost(HOST + "/symbol_plans");
			post.addHeader("Accept", "application/json");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			final NameValuePairBuilder parametros = NameValuePairBuilder.novaInstancia();
			parametros.addParam("symbol_plan[plans_id]", String.valueOf(planID));
			parametros.addParam("symbol_plan[private_symbols_id]", String.valueOf(symbol.getServerID()));
			parametros.addParam("symbol_plan[position]", String.valueOf(position));
			HttpUtils.prepareUrl(post, parametros.build());
			HttpResponse response = HttpUtils.doRequest(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				JSONObject returnPlan = new JSONObject(HttpUtils.getContent(response));
				return returnPlan.getInt("id");
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return 0;
	}

	private int sendPlan() {
		try {
			HttpPost post = new HttpPost(HOST + "/plans");
			post.addHeader("Accept", "application/json");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			final NameValuePairBuilder parametros = NameValuePairBuilder.novaInstancia();
			parametros.addParam(PLAN_NAME, planName);
			parametros.addParam(PLAN_LAYOUT, String.valueOf(layout));
			parametros.addParam(PLAN_USER_ID, String.valueOf(MainActivity.getUsuarioLogado().getServerID()));
			parametros.addParam(PLAN_PATIENT_ID, String.valueOf(MainActivity.getUsuarioLogado().getServerID()));

			HttpUtils.prepareUrl(post, parametros.build());
			HttpResponse response = HttpUtils.doRequest(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				JSONObject returnPlan = new JSONObject(HttpUtils.getContent(response));
				return returnPlan.getInt("id");
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Void unused) {
		progress.dismiss();
	}

}
