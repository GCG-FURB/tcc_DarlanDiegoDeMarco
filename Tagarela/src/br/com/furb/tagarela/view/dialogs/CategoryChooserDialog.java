package br.com.furb.tagarela.view.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.model.Category;
import br.com.furb.tagarela.model.CategoryDao;
import br.com.furb.tagarela.model.CategoryDao.Properties;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.utils.JsonUtils;

public class CategoryChooserDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		syncCategories(); //busca categorias no servidor, se não tiver no banco então insere.
		CategoryDao categoryDao = DaoProvider.getInstance(getActivity().getApplicationContext()).getCategoryDao();
		List<Category> categories = categoryDao.queryBuilder().list(); //pega todas as categorias do banco
		List<String> categoriesList = new ArrayList<String>(); //array de nomes
		for (Category category : categories) {
			categoriesList.add(category.getName());
		}

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_category_chooser, null);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categoriesList);
		ListView listView = (ListView) view.findViewById(R.id.category_list);
		listView.setOnItemClickListener(getCategoryListener());
		listView.setAdapter(adapter);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("Selecione uma categoria");
		return builder.create();
	}

	private OnItemClickListener getCategoryListener() {
		// TODO Auto-generated method stub
		return null;
	}

	private void syncCategories() {
		String results = JsonUtils.getCategoriesResponse();
		results = JsonUtils.validaJson(results);
		JSONArray categories;
		try {
			categories = new JSONArray(results);
			JSONObject category = new JSONObject();
			CategoryDao categoryDao = DaoProvider.getInstance(getActivity().getApplicationContext()).getCategoryDao();
			for (int i = 0; i < categories.length(); i++) {
				category = categories.getJSONObject(i);
				Category newCategory = new Category();
				newCategory.setBlue(category.getInt("blue"));
				newCategory.setGreen(category.getInt("green"));
				newCategory.setRed(category.getInt("red"));
				newCategory.setName(category.getString("name"));
				newCategory.setServerID(category.getInt("id"));
				if (categoryDao.queryBuilder().where(Properties.ServerID.eq(category.getInt("id"))).list().size() <= 0) {
					categoryDao.insert(newCategory);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
