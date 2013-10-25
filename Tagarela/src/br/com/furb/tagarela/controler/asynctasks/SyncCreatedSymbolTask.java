package br.com.furb.tagarela.controler.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.interfaces.UserLoginListener;
import br.com.furb.tagarela.model.Symbol;
import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.utils.HttpUtils;

public class SyncCreatedSymbolTask extends AsyncTask<String, Void, Void> {
	private ProgressDialog progress;
	private Context mContext;
	private Activity activity;
	private Symbol symbol;

	public SyncCreatedSymbolTask(Activity activity, Symbol symbol) {
		this.mContext = activity;
		this.activity = activity;
		this.symbol = symbol;
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
		if(symbol != null){
			HttpUtils.symbolPost(symbol, activity);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void unused) {
		progress.dismiss();
	}
	
	
}