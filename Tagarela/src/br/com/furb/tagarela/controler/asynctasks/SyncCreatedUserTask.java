package br.com.furb.tagarela.controler.asynctasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.interfaces.UserLoginListener;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.model.UserDao;
import br.com.furb.tagarela.utils.HttpUtils;
import br.com.furb.tagarela.utils.JsonUtils;
import br.com.furb.tagarela.view.activities.MainActivity;
import br.com.furb.tagarela.view.dialogs.UserLoginDialog;

public class SyncCreatedUserTask extends AsyncTask<String, Void, Void> {
	private ProgressDialog progress;
	private Context mContext;
	private Activity activity;
	private User user;
	private String password;

	public SyncCreatedUserTask(Activity activity, User user, String password) {
		this.mContext = activity;
		this.activity = activity;
		this.user = user;
		this.password = password;
	}

	@Override
	protected void onPreExecute() {
		// Cria novo um ProgressDialogo e exibe
		progress = new ProgressDialog(mContext);
		progress.setMessage("Aguarde...");
		progress.show();
	}

	@Override
	protected Void doInBackground(String... params) {

		HttpUtils.userPost(user, password, activity);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ImageView userPhoto = (ImageView) activity.findViewById(R.id.userPhoto);
				userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(user.getPatientPicture(), 0, user.getPatientPicture().length));
				TextView welcomeMessage = (TextView) activity.findViewById(R.id.welcomeMessage);
				welcomeMessage.setText("Olá " + user.getName() + " bem vindo ao Tagarela!");
			}
		});
		((UserLoginListener) activity).syncInformations();
		return null;
	}

	@Override
	protected void onPostExecute(Void unused) {
		progress.dismiss();
	}
	
	
}