package br.com.furb.tagarela.view.dialogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.model.UserDao.Properties;
import br.com.furb.tagarela.utils.Base64Utils;
import br.com.furb.tagarela.utils.JsonUtils;
import br.com.furb.tagarela.view.activities.Principal;

public class UserLoginDialog extends DialogFragment {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.login_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("Login de usuário");
		builder.setPositiveButton("Confirmar", getLoginListener());
		builder.setNeutralButton("Não tenho usuário", getNewUserListener());
		return builder.create();
	}

	private OnClickListener getNewUserListener() {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TypeChooserDialog typeSelector = new TypeChooserDialog();
				typeSelector.show(getActivity().getSupportFragmentManager(), "");
			}
		};
	}

	private OnClickListener getLoginListener() {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.login_id);
				String id = edit.getText().toString();
				User user = null;
				if (DaoProvider.getInstance(getActivity().getApplicationContext()).getUserDao().queryBuilder().where(Properties.Id.eq(id)).list().size() > 0) {
					user = DaoProvider.getInstance(getActivity().getApplicationContext()).getUserDao().queryBuilder().where(Properties.Id.eq(id)).unique();
					Principal.setUsuarioLogado(user);
				} else {
					String results = JsonUtils.getUserJsonResponse(Integer.parseInt(id));
					try {
						JSONArray users = new JSONArray(JsonUtils.validaJson(results));
						JSONObject jsonUser = users.getJSONObject(0);
						user = new User();
						user.setEmail(jsonUser.getString("email"));
						user.setServerID(jsonUser.getInt("id"));
						user.setId(jsonUser.getLong("id"));
						user.setName(jsonUser.getString("name"));
						user.setPatientPicture(Base64Utils.decodeImageBase64ToByteArray(jsonUser.getString("image_representation").replaceAll("@", "+")));
						DaoProvider.getInstance(getActivity().getApplicationContext()).getUserDao().insert(user);
						Principal.setUsuarioLogado(user);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				ImageView userPhoto = (ImageView) getActivity().findViewById(R.id.userPhoto);
				userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(user.getPatientPicture(), 0, user.getPatientPicture().length));
				TextView welcomeMessage = (TextView) getActivity().findViewById(R.id.welcomeMessage);
				welcomeMessage.setText("Olá " + user.getName() + " bem vindo ao Tagarela!");
			}
		};
	}
}
