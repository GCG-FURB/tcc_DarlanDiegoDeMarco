package br.com.furb.tagarela.controler.asynctasks;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.Plan;
import br.com.furb.tagarela.model.PlanDao;
import br.com.furb.tagarela.utils.JsonUtils;
import br.com.furb.tagarela.view.activities.MainActivity;

public class SyncPlansTask extends AsyncTask<Integer, Integer, Void> {

	private Activity activity;

	public SyncPlansTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		String results = JsonUtils.getResponse(JsonUtils.URL_PLANS);
		if (results.equals("[]")) {
			return null;
		}

		try {
			JSONArray planArray = new JSONArray(results);
			JSONObject planJson = null;
			Plan planObject = new Plan();

			PlanDao planDao = DaoProvider.getInstance(null).getPlanDao();
			for (int i = 0; i < planArray.length(); i++) {
				planJson = planArray.getJSONObject(i);
				if (planJson.getInt("user_id") == MainActivity
						.getUser().getServerID()
						&& planDao
								.queryBuilder()
								.where(PlanDao.Properties.ServerID.eq(planJson
										.getInt("id"))).list().size() <= 0) {
					planObject.setName(planJson.getString("name"));
					planObject.setLayout(planJson.getInt("layout"));
					planObject.setPatientID(planJson.getInt("user_id"));
					planObject.setUserID(planJson.getInt("user_id"));
					planObject.setServerID(planJson.getInt("id"));
					planObject.setPlanType(0);
					planObject.setDescription("");
					planDao.insert(planObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		SyncInformationControler.getInstance().syncSymbolPlans(activity);
	}

}
