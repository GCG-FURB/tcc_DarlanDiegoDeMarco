package br.com.furb.tcc;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ModelCreator {

	private static Entity user;
	private static Entity category;
	private static Entity symbol;

	public static void main(String[] args) {
		Schema schema = new Schema(6, "br.com.furb.tagarela.model");
		addUsers(schema);
		addCategories(schema);
		addSymbols(schema);
		DaoGenerator daoGenerator;
		try {
			daoGenerator = new DaoGenerator();
			daoGenerator.generateAll(schema, "../Tagarela/src");
			// daoGenerator.generateAll(schema,
			// "../tcc_darlanmarco/Tagarela/src");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addSymbols(Schema schema) {
		symbol = schema.addEntity("Symbol");
		symbol.addIntProperty("serverID");
		symbol.addIntProperty("userID");
		symbol.addBooleanProperty("isGeneral");
		symbol.addStringProperty("name");
		symbol.addStringProperty("videoLink");
		symbol.addByteArrayProperty("picture");
		symbol.addByteArrayProperty("sound");
		Property categoryID = symbol.addLongProperty("categoryID").notNull().getProperty();
		symbol.addToOne(category, categoryID);
		ToMany categoryToSymbols = category.addToMany(symbol, categoryID);
		categoryToSymbols.setName("symbols");
	}

	private static void addCategories(Schema schema) {
		category = schema.addEntity("Category");
		category.addIntProperty("red");
		category.addIntProperty("green");
		category.addIntProperty("blue");
		category.addStringProperty("name");
		category.addIntProperty("serverID");
		category.addIdProperty();
	}

	private static Entity addUsers(Schema schema) {
		user = schema.addEntity("User");
		user.addStringProperty("email");
		user.addStringProperty("name");
		user.addByteArrayProperty("patientPicture");
		user.addIntProperty("type");
		user.addIntProperty("serverID");
		user.addIdProperty();
		return user;
	}

}
