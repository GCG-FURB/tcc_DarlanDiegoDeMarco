package br.com.furb.tagarela.controler.asynctasks;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.Observation;
import br.com.furb.tagarela.model.ObservationDao.Properties;
import br.com.furb.tagarela.utils.HttpUtils;
import br.com.furb.tagarela.utils.NameValuePairBuilder;
import br.com.furb.tagarela.view.activities.MainActivity;

public class SyncUnsynchronizedObservations extends AsyncTask<Integer, Integer, Void> {

	private static final String OBSERVATION_DATE = "user_historic[date]";
	private static final String OBSERVATION_HISTORIC = "user_historic[historic]";
	private static final String OBSERVATION_USER = "user_historic[user_id]";
	private static final String OBSERVATION_TUTOR = "user_historic[tutor_id]";
	private static final String URL_OBSERVATION_POST = "http://murmuring-falls-7702.herokuapp.com/user_historics/";

	@SuppressLint("SimpleDateFormat")
	@Override
	protected Void doInBackground(Integer... params) {
		List<Observation> observations = DaoProvider.getInstance(null).getObservationDao().queryBuilder()
				.where(Properties.UserID.eq(MainActivity.getUser().getId()), Properties.IsSynchronized.eq(false)).list();

		for (Observation observation : observations) {
			if (observation != null) {
				try {
					HttpPost post = new HttpPost(URL_OBSERVATION_POST);
					post.addHeader("Accept", "application/json");
					post.addHeader("Content-Type", "application/x-www-form-urlencoded");
					final NameValuePairBuilder parametros = NameValuePairBuilder.novaInstancia();

					parametros.addParam(OBSERVATION_DATE, observation.getDate().toString())
							.addParam(OBSERVATION_HISTORIC, observation.getObservation())
							.addParam(OBSERVATION_USER, String.valueOf(observation.getUserID()))
							.addParam(OBSERVATION_TUTOR, String.valueOf(observation.getTutorID()));

					post.setEntity(new UrlEncodedFormEntity(parametros.build(), HTTP.UTF_8));
					HttpResponse response = HttpUtils.doRequest(post);
					if (response.getStatusLine().getStatusCode() == 201) {
						JSONObject returnObservation = new JSONObject(HttpUtils.getContent(response));
						observation.setServerID(returnObservation.getLong("id"));
						observation.setIsSynchronized(true);
						DaoProvider.getInstance(null).getObservationDao().update(observation);
					}
				} catch (Exception e) {
					e.getMessage();
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}