package br.com.furb.tagarela.view.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.interfaces.CategoryTypeListener;
import br.com.furb.tagarela.interfaces.UserLoginListener;
import br.com.furb.tagarela.interfaces.UserTypeListener;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.view.dialogs.CategoryChooserDialog;
import br.com.furb.tagarela.view.dialogs.SymbolCreateDialog;
import br.com.furb.tagarela.view.dialogs.TypeChooserDialog;
import br.com.furb.tagarela.view.dialogs.UserCreateDialog;
import br.com.furb.tagarela.view.dialogs.UserLoginDialog;
import br.com.furb.tagarela.view.dialogs.WelcomeDialog;

public class Principal extends FragmentActivity implements UserTypeListener, CategoryTypeListener, UserLoginListener {

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

	private void showUserDialog() {
		WelcomeDialog welcomeDialog = new WelcomeDialog();
		welcomeDialog.show(getSupportFragmentManager(), "");
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

	@Override
	public void onLoginReturnValue(boolean hasUser) {
		if (hasUser) {
			showLoginDialog();
		} else {
			showTypeSelectorDialog();
		}
	}

	public static void setUsuarioLogado(User usuarioLogado) {
		Principal.usuarioLogado = usuarioLogado;
	}

	public static User getUsuarioLogado() {
		return usuarioLogado;
	}

}
