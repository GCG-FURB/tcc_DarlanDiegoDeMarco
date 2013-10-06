package br.com.furb.tagarela.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.interfaces.CategoryTypeListener;
import br.com.furb.tagarela.interfaces.UserTypeListener;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.view.dialogs.CategoryChooserDialog;
import br.com.furb.tagarela.view.dialogs.SymbolCreateDialog;
import br.com.furb.tagarela.view.dialogs.TypeChooserDialog;
import br.com.furb.tagarela.view.dialogs.UserCreateDialog;
import br.com.furb.tagarela.view.dialogs.UserLoginDialog;

public class Principal extends FragmentActivity implements UserTypeListener, CategoryTypeListener {

	private static User usuarioLogado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DaoProvider.getInstance(getApplicationContext());
		setContentView(R.layout.activity_login);
		initComponents();
		showUserDialog();
	}

	private void initComponents() {
		TextView createSimbol = (TextView) findViewById(R.id.createSymbol);
		createSimbol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CategoryChooserDialog categoryChooser = new CategoryChooserDialog();
				categoryChooser.show(getSupportFragmentManager(), "");
			}
		});
	}

	private void addGetListener() {
		// findViewById(R.id.btGetUsers).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// try {
		// String results = getJsonResponse();
		// results = validaJson(results);
		// JSONArray user = new JSONArray(results);
		// JSONObject usuario = new JSONObject();
		// String texto = "";
		// String imagem = "";
		// for (int i = 0; i < user.length(); i++) {
		// usuario = user.getJSONObject(i);
		// if (!"".equals(usuario
		// .getString("image_representation"))) {
		// texto = "email: " + usuario.getString("email")
		// + " \n";
		// texto += "id: " + usuario.getString("id") + " \n";
		// texto += "nome: " + usuario.getString("name")
		// + " \n";
		// imagem = usuario.getString("image_representation")
		// .replaceAll("@", "+");
		// }
		// }
		// ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		// imageView.setScaleType(ScaleType.FIT_CENTER);
		// imageView.setImageBitmap(Base64Utils.decodeBase64(imagem));
		// EditText ed = (EditText) findViewById(R.id.edEmailUser);
		// ed.setText(texto);
		// } catch (JSONException e) {
		// Log.e("Tagarela", "Erro no parsing do JSON", e);
		// }

	}

	private void showUserDialog() {
		AlertDialog loginTypeChooser = new AlertDialog.Builder(this).create();
		loginTypeChooser.setTitle("Bem vindo ao Tagarela");
		loginTypeChooser.setMessage("Você já possui um usuário no Tagarela?");
		loginTypeChooser.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showLoginDialog();
			}
		});

		loginTypeChooser.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showTypeSelectorDialog();
			}
		});

		loginTypeChooser.show();
	}

	protected void showLoginDialog() {
		UserLoginDialog userLoginDialog = new UserLoginDialog();
		userLoginDialog.show(getSupportFragmentManager(), "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onUserReturnValue(int userType) {
		UserCreateDialog userPost = new UserCreateDialog();
		Bundle args = new Bundle();
		args.putInt("userType", userType);
		userPost.setArguments(args);

		userPost.show(getSupportFragmentManager(), "");
	}

	private void showTypeSelectorDialog() {
		TypeChooserDialog typeSelector = new TypeChooserDialog();
		typeSelector.show(getSupportFragmentManager(), "");
	}

	@Override
	public void onCategoryReturnValue(Long categoryID) {
		SymbolCreateDialog symbolCreate = new SymbolCreateDialog();
		Bundle args = new Bundle();
		args.putLong("categoryID", categoryID);
		symbolCreate.setArguments(args);
		symbolCreate.show(getSupportFragmentManager(), "");

	}

	public static void setUsuarioLogado(User usuarioLogado) {
		Principal.usuarioLogado = usuarioLogado;
	}

	public static User getUsuarioLogado() {
		return usuarioLogado;
	}

}
