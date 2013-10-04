package br.com.furb.tagarela.game.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.furb.tagarela.game.banco.model.dao.PlanoDAO;
import br.com.furb.tagarela.game.banco.model.dao.PranchaDAO;
import br.com.furb.tagarela.game.banco.model.dao.SimboloDAO;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "jogodb";
	private static final int VERSION    = 1;
	private static DBHelper instance    = null;
	
	public static DBHelper getInstance(Context context){
		if (instance == null) 
			instance = new DBHelper(context);
		
		return instance;
	}
	
	private DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SimboloDAO.CREATE);
		db.execSQL(PlanoDAO.CREATE);
		db.execSQL(PranchaDAO.CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + SimboloDAO.TABLE);
		db.execSQL(SimboloDAO.CREATE);

		db.execSQL("drop table if exists " + PlanoDAO.TABLE);
		db.execSQL(PlanoDAO.CREATE);

		db.execSQL("drop table if exists " + PranchaDAO.TABLE);
		db.execSQL(PranchaDAO.CREATE);		
	}

	public SQLiteDatabase getDB(){
		return getWritableDatabase();
	}
	
}
