package br.com.furb.tagarela.game.view;

import br.com.furb.tagarela.R;
import br.com.furb.tagarela.R.layout;
import br.com.furb.tagarela.R.menu;
import br.com.furb.tagarela.game.controler.Gerenciador;
import br.com.furb.tagarela.game.model.Plano;
import br.com.furb.tagarela.game.model.PlanoBanco;
import br.com.furb.tagarela.game.model.Simbolo;
import br.com.furb.tagarela.game.model.SimboloBanco;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class TesteJogo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teste_jogo);
		
		PlanoBanco plano = Gerenciador.getInstance().getPlanosBD().get(0);
		SimboloBanco simbolo = plano.getPrancha(0).getSimbolo();
		
		ImageView im = (ImageView) this.findViewById(R.id.imageTeste);
		im.setImageBitmap(simbolo.getSimboloBmp(1000));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teste_jogo, menu);
		return true;
	}

}
