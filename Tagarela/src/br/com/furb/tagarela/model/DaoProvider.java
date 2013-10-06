package br.com.furb.tagarela.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.furb.tagarela.model.DaoMaster.DevOpenHelper;

public class DaoProvider {
	private SQLiteDatabase tagarelaDB;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private UserDao userDao;
	private CategoryDao categoryDao;
	private SymbolDao symbolDao;
	private Context context;

	private static DaoProvider daoProvider;

	private DaoProvider(Context context) {
		this.context = context;
		DevOpenHelper daoHelper = new DaoMaster.DevOpenHelper(context, "tagarela-db", null);
		tagarelaDB = daoHelper.getWritableDatabase();
		DaoMaster.createAllTables(tagarelaDB, true);
		daoMaster = new DaoMaster(tagarelaDB);
		daoSession = daoMaster.newSession();
		userDao = daoSession.getUserDao();
		categoryDao = daoSession.getCategoryDao();
		symbolDao = daoSession.getSymbolDao();
	}

	public static DaoProvider getInstance(Context context) {
		if (daoProvider == null) {
			daoProvider = new DaoProvider(context);
		}
		return daoProvider;
	}

	public DaoMaster getDaoMaster() {
		return daoMaster;
	}

	public DaoSession getDaoSession() {
		return daoSession;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	public SymbolDao getSymbolDao() {
		return symbolDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public Context getContext() {
		return context;
	}
}
