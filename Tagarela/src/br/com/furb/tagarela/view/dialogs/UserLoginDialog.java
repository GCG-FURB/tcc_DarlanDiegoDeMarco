package br.com.furb.tagarela.view.dialogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.interfaces.UserLoginListener;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.model.UserDao;
import br.com.furb.tagarela.model.UserDao.Properties;
import br.com.furb.tagarela.utils.JsonUtils;
import br.com.furb.tagarela.view.activities.MainActivity;

public class UserLoginDialog extends DialogFragment {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.login_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("Login de usuário");
		builder.setPositiveButton("Confirmar", doLoginListener());
		builder.setNeutralButton("Não tenho usuário", createUserListener());
		return builder.create();
	}

	private OnClickListener createUserListener() {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TypeChooserDialog typeSelector = new TypeChooserDialog();
				typeSelector.show(getActivity().getSupportFragmentManager(), "");
			}
		};
	}

	private OnClickListener doLoginListener() {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.login_id);
				String id = edit.getText().toString();
				UserDao userDao = DaoProvider.getInstance(null).getUserDao();
				User user = null;
				if (userDao.queryBuilder().where(Properties.Id.eq(id)).list().size() > 0) {
					user = userDao.queryBuilder().where(Properties.Id.eq(id)).unique();
					MainActivity.setUsuarioLogado(user);
					showWelcomeScreen();
					((UserLoginListener) getActivity()).syncInformations();
				} else {
					new SyncUserTask().execute(id);
				}
			}

			private void showWelcomeScreen() {
				User user = MainActivity.getUsuarioLogado();
				ImageView userPhoto = (ImageView) getActivity().findViewById(R.id.userPhoto);
				userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(user.getPatientPicture(), 0, user.getPatientPicture().length));
				TextView welcomeMessage = (TextView) getActivity().findViewById(R.id.welcomeMessage);
				welcomeMessage.setText("Olá " + user.getName() + " bem vindo ao Tagarela!");
			}
		};
	}

	private class SyncUserTask extends AsyncTask<String, Void, Void> {
		private ProgressDialog progress;

		@Override
		protected void onPreExecute() {
			// Cria novo um ProgressDialogo e exibe
			progress = new ProgressDialog(getActivity().getApplicationContext());
			progress.setMessage("Aguarde...");
			progress.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				String id = params[0];
				String results = JsonUtils.getUserJsonResponse(id);
				JSONArray users = new JSONArray(JsonUtils.validaJson(results));
				JSONObject jsonUser = users.getJSONObject(0);
				User user = JsonUtils.getUser(jsonUser);
				UserDao userDao = DaoProvider.getInstance(null).getUserDao();
				userDao.insert(user);
				MainActivity.setUsuarioLogado(user);
				ImageView userPhoto = (ImageView) getActivity().findViewById(R.id.userPhoto);
				userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(user.getPatientPicture(), 0, user.getPatientPicture().length));
				TextView welcomeMessage = (TextView) getActivity().findViewById(R.id.welcomeMessage);
				welcomeMessage.setText("Olá " + user.getName() + " bem vindo ao Tagarela!");
				((UserLoginListener) getActivity()).syncInformations();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			progress.dismiss();
		}
	}

}
