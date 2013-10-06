package br.com.furb.tagarela.game.view;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.game.controler.Gerenciador;
import br.com.furb.tagarela.game.model.Plano;

public class GerenciarLista extends Activity {

	private TextView tvTextoSuperior = null;
	private TextView tvNomeLista = null;
	private EditText edNomePlano = null;
	private TextView tvPalavras = null;
	private EditText edPalavras = null;
	private Button btnGravar = null;
	private Button btnRemover = null;
	private Button btnCancelar = null;
		
	private Gerenciador gerenciador = null;
	private Plano plano = null;
	private int planoIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.gerenciar_lista);
		
		gerenciador = Gerenciador.getInstance();
		inicializarCampos();
								
		Bundle extras = getIntent().getExtras();

		planoIndex = extras.getInt("planoindex");
		if (planoIndex >= 0) {
			plano = gerenciador.getPlano(planoIndex);
			edNomePlano.setText(plano.getNome());	
			edPalavras.setText(plano.getTextoPlano());
			
			tvTextoSuperior.setText("Alterar Lista");
			btnRemover.setEnabled(true);
		}
		else
		{
			tvTextoSuperior.setText("Incluir Lista");
			btnRemover.setEnabled(false);			
		}

		btnGravar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (plano == null) {
					plano = new Plano(edNomePlano.getText().toString());
					
				}
				
				plano.setNome(edNomePlano.getText().toString());
				plano.setTextoPlano(edPalavras.getText().toString());
				plano.gravarPlano();
				plano.carregarPranchas();
				
				if (planoIndex < 0) {
					gerenciador.addPlano(plano);
				}
				
				finish();
				
			}
		});
		
		btnRemover.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (plano == null) {
					finish();
					
				}
				else
				{
					plano.excluirPlano();
					gerenciador.getPlanos().remove(plano);
					finish();
				}
								
			}
		});
		
		btnCancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});		
	}

	private void inicializarCampos(){
		Typeface fontFace = gerenciador.getFontJogo();
		
		tvTextoSuperior = (TextView)findViewById(R.id.tvTextoSuperior);		       
		tvTextoSuperior.setTypeface(fontFace);
		tvTextoSuperior.setTextSize(60.f);
		       		
		tvNomeLista = (TextView)findViewById(R.id.tvNomeLista);		       
		tvNomeLista.setTypeface(fontFace);
		tvNomeLista.setTextSize(40.f);

		edNomePlano = (EditText)findViewById(R.id.edNomePlano);		       
		edNomePlano.setTypeface(fontFace);
		edNomePlano.setTextSize(40.f);
		
		tvPalavras = (TextView)findViewById(R.id.tvPalavras);		       
		tvPalavras.setTypeface(fontFace);
		tvPalavras.setTextSize(40.f);

		edPalavras = (EditText)findViewById(R.id.edPalavras);		       
		edPalavras.setTypeface(fontFace);
		edPalavras.setTextSize(40.f);

		btnGravar = (Button)findViewById(R.id.btnGravar);		       
		btnGravar.setTypeface(fontFace);
		btnGravar.setTextSize(40.f);

		btnRemover = (Button)findViewById(R.id.btnRemover);		       
		btnRemover.setTypeface(fontFace);
		btnRemover.setTextSize(40.f);

		btnCancelar = (Button)findViewById(R.id.btnCancelar);		       
		btnCancelar.setTypeface(fontFace);
		btnCancelar.setTextSize(40.f);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gerenciar_lista, menu);
		return true;
	}

}
