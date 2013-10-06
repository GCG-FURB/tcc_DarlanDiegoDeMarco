package br.com.furb.tagarela.utils;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import br.com.furb.tagarela.model.Symbol;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	List<Symbol> symbols;
	private final android.view.ViewGroup.LayoutParams params;

	// Método
	public ImageAdapter(Context context, List<Symbol> symbols, android.view.ViewGroup.LayoutParams params) {
		this.context = context;
		this.symbols = symbols;
		this.params = params;
	}

	public int getCount() {
		return symbols.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Symbol symbol = symbols.get(0);
		ImageView imagem = new ImageView(context);
		imagem.setImageBitmap(BitmapFactory.decodeByteArray(symbol.getPicture(), 0, symbol.getPicture().length));
		imagem.setAdjustViewBounds(true);
		imagem.setLayoutParams(params);
		return imagem;
	}

}
