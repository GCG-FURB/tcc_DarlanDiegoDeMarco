package br.com.furb.tagarela.game.banco.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.furb.tagarela.game.banco.DBHelper;
import br.com.furb.tagarela.game.banco.model.Simbolo;

public class SimboloDAO {

	public static final String TABLE         = "simbolo";
	public static final String ID            = "_id";
	public static final String NOME          = "nome";
	public static final String IMAGEM        = "imagem";
	public static final String AUDIO         = "audio";
	public static final String CATEGORIA     = "categoria_id";
	public static final String USUARIO       = "usuario_id";
	
	private Context context = null;
	
	public static final String CREATE    = "create table if not exists " + TABLE +" ( " + ID         + " integer primary key, "
			  																			+ NOME       + " text, "
			  																			+ IMAGEM     + " text, "
			  																			+ AUDIO      + " text, "
			  																			+ CATEGORIA  + " integer, "
			  																			+ USUARIO    + " integer);";			
	
	
	public SimboloDAO(Context context) {
		this.context = context;
	}
	
	public boolean registroExiste(Simbolo simbolo){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();		
		
		boolean existe = (db.rawQuery("select 1 from " + TABLE + " where " + ID + " = " + simbolo.getId(), null).getCount() > 0);
		db.close();
		
		return existe;
	}	
	
	public void inserir(Simbolo simbolo){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(ID, simbolo.getId());
		cv.put(NOME, simbolo.getNome());
		cv.put(IMAGEM, simbolo.getImagem());
		cv.put(AUDIO, simbolo.getAudio());
		cv.put(CATEGORIA, simbolo.getCategoria());
		cv.put(USUARIO, simbolo.getUsuario());
				
		db.insert(TABLE, null, cv);
		db.close();
	}
	
	public Cursor getSimbolosCursor(){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		return db.rawQuery("select * from " + TABLE, null);		
		
	}

	public Simbolo getSimbolos(int id){
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		Cursor c = db.rawQuery("select * from " + TABLE + " WHERE " + ID + " = " + id, null);	
		
		c.moveToFirst();
		if (c.getCount() > 0) {
			Simbolo simbolo = new Simbolo();
			simbolo.setId(c.getInt(c.getColumnIndex(ID)));
			simbolo.setNome(c.getString(c.getColumnIndex(NOME)));
			simbolo.setImagem(c.getString(c.getColumnIndex(IMAGEM)));
			simbolo.setAudio(c.getString(c.getColumnIndex(AUDIO)));
			simbolo.setCategoria(c.getInt(c.getColumnIndex(CATEGORIA)));
			simbolo.setUsuario(c.getInt(c.getColumnIndex(USUARIO)));
			return simbolo;
			
		}
		
		return null;
	}
		
	public void alterar(Simbolo simbolo) {
		SQLiteDatabase db = DBHelper.getInstance(context).getDB();
		
		ContentValues cv = new ContentValues();
		cv.put(NOME, simbolo.getNome());
		cv.put(IMAGEM, simbolo.getImagem());
		cv.put(AUDIO, simbolo.getAudio());
		cv.put(CATEGORIA, simbolo.getCategoria());
		cv.put(USUARIO, simbolo.getUsuario());
		
		db.update(TABLE, cv, ID + "= ?", new String[]{simbolo.getId().toString()});
		db.close();		
	}
		
}
