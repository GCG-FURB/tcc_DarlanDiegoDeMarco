package br.com.furb.tagarela.game.banco.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.furb.tagarela.game.banco.DBHelper;
import br.com.furb.tagarela.game.banco.model.Plano;

public class PlanoDAO {

	public static final String TABLE         = "plano";
	public static final String ID            = "_id";
	public static final String NOME          = "nome";
	public static final String PACIENTE      = "paciente_id";
	public static final String USUARIO       = "usuario_id";
	
	private Context context = null;
	
	public static final String CREATE    = "create table if not exists " + TABLE +" ( " + ID         + " integer primary key, "
			  																			+ NOME       + " text, "
			  																			+ PACIENTE   + " integer, "
			  																			+ USUARIO    + " integer);";			
	
	
	public PlanoDAO(Context context) {
		this.context = context;
	}
	  
	public boolean registroExiste(br.com.furb.tagarela.game.banco.model.Plano plano) {
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();		
		
		boolean existe = (db.rawQuery("select 1 from " + TABLE + " where " + ID + " = " + plano.getId(), null).getCount() > 0);
		db.close();
		
		return existe;
	}
		
	public void inserir(Plano plano){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(ID, plano.getId());
		cv.put(NOME, plano.getNome());
		cv.put(PACIENTE, plano.getPaciente());
		cv.put(USUARIO, plano.getUsuario());
				
		db.insert(TABLE, null, cv);
		db.close();
	}
	
	public Cursor getSimbolosCursor(){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		return db.rawQuery("select * from plano", null);		
		
	}
	
	public void alterar(Plano plano) {
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(NOME, plano.getNome());
		cv.put(PACIENTE, plano.getPaciente());
		cv.put(USUARIO, plano.getUsuario());
		
		db.update(TABLE, cv, ID + "= ?", new String[]{plano.getId().toString()});
		db.close();		
	}
		
}
