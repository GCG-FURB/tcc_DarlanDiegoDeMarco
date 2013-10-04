package br.com.furb.tagarela.game.banco.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.furb.tagarela.game.banco.DBHelper;
import br.com.furb.tagarela.game.banco.model.Plano;
import br.com.furb.tagarela.game.banco.model.Prancha;
import br.com.furb.tagarela.game.banco.model.Simbolo;

public class PranchaDAO {

	public static final String TABLE         = "prancha";
	public static final String ID            = "_id";
	public static final String PLANO         = "plano_id";
	public static final String SIMBOLO       = "simbolo_id";
	public static final String POSICAO       = "posicao";
	
	private Context context = null;
	
	public static final String CREATE    = "create table if not exists " + TABLE +" ( " + ID         + " integer primary key, "
			  																			+ PLANO      + " integer, "
			  																			+ SIMBOLO    + " integer, "
			  																			+ POSICAO    + " integer);";			
	
	
	public PranchaDAO(Context context) {
		this.context = context;
	}
	
	public boolean registroExiste(Prancha prancha){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();		
		
		boolean existe = (db.rawQuery("select 1 from " + TABLE + " where " + ID + " = " + prancha.getId(), null).getCount() > 0);
		db.close();
		
		return existe;
	}	
		  
	public void inserir(Prancha prancha){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(ID, prancha.getId());
		cv.put(PLANO, prancha.getPlano());
		cv.put(SIMBOLO, prancha.getSimbolo());
		cv.put(POSICAO, prancha.getPosicao());
				
		db.insert(TABLE, null, cv);
		db.close();
	}
	
	public Cursor getSimbolosCursor(){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		return db.rawQuery("select * from prancha", null);		
		
	}
	
	public void alterar(Prancha prancha) {
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(PLANO, prancha.getPlano());
		cv.put(SIMBOLO, prancha.getSimbolo());
		cv.put(POSICAO, prancha.getPosicao());
		
		db.update(TABLE, cv, ID + "= ?", new String[]{prancha.getId().toString()});
		db.close();		
	}
		
}
