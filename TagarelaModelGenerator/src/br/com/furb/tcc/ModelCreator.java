package br.com.furb.tcc;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ModelCreator {
	public static void main(String[] args) {
	    Schema schema = new Schema(4, "br.com.furb.tagarela.model");
	    addUsers(schema);
	    addCategories(schema);
	    DaoGenerator daoGenerator;
		try {
			daoGenerator = new DaoGenerator();
			daoGenerator.generateAll(schema, "../tcc_darlanmarco/Tagarela/src");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addCategories(Schema schema) {
		Entity category = schema.addEntity("Category");
		
		category.addIntProperty("red");
		category.addIntProperty("green");
		category.addIntProperty("blue");
		category.addStringProperty("name");
		category.addIntProperty("serverID");
		category.addIdProperty();
	}

	private static Entity addUsers(Schema schema) {
		Entity user = schema.addEntity("User");
		user.addStringProperty("email");
		user.addStringProperty("name");
		user.addByteArrayProperty("patientPicture");
		user.addIntProperty("type");
		user.addIntProperty("serverID");
		user.addIdProperty(); 
		return user;
	}

}
