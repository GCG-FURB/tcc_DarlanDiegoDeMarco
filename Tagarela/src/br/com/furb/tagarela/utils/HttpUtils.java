package br.com.furb.tagarela.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.BitmapFactory;
import br.com.furb.tagarela.model.DaoProvider;
import br.com.furb.tagarela.model.User;

public class HttpUtils {
	public static final int MOVIDO_TEMPORARIAMENTE = 302; // utilizado para
															// validação de
															// campos
															// obrigatórios e
															// tipos
	public static final int NAO_AUTENTICADO = 401; // utilizado para serviços
													// que requer autenticação
	public static final int NAO_PERMITIDO = 403; // utilizado para serviços
													// não disponíveis, URLs
													// não definidas
	public static final int ERRO_NO_SERVIDOR = 500; // utilizado para erros de
													// regra de negócio
													// Oracle(SqlException)
	public static final int SUCESSO = 200; // utilizado para sinalizar sucesso
											// na requisição

	@SuppressWarnings("deprecation")
	public static void preparaUrl(HttpEntityEnclosingRequestBase post,
			List<NameValuePair> params) {
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpResponse doRequest(HttpUriRequest request) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;

		try {
			response = client.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String getResponse(Header[] cabecalhoHttp, String content) {
		String value = null;
		for (Header header : cabecalhoHttp) {
			if (header.getName().equals(content)) {
				value = header.getValue();
			}
		}
		if (content.equals("Set-Cookie")) {
			value = value.split(";")[0];
		}
		return value;
	}

	public static String getContent(HttpResponse response) {
		String content = null;
		try {
			content = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static int userPost(User user, String password, Activity activity) {
		try {
			HttpPost post = new HttpPost(
					"http://murmuring-falls-7702.herokuapp.com/users/");
			post.addHeader("Accept", "application/json");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			final NameValuePairBuilder parametros = NameValuePairBuilder
					.novaInstancia();
			parametros.addParam("user[name]", user.getName());
			parametros.addParam("user[hashed_password]", password);
			parametros.addParam("user[image_representation]",
					imageEncoder(user.getPatientPicture()));
			parametros.addParam("user[user_type]",
					String.valueOf(user.getType()));
			parametros.addParam("user[email]", user.getEmail());
			HttpUtils.preparaUrl(post, parametros.build());
			HttpResponse response = HttpUtils.doRequest(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				JSONObject returnUser = new JSONObject(getContent(response));
				user.setServerID(returnUser.getInt("id"));
				DaoProvider provider = DaoProvider.getInstance(null);
				provider.getUserDao().insert(user);
				return response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {

		}
		return -1;
	}

	private static String imageEncoder(byte[] imagem) {
		return Base64Utils.encodeTobase64(
				BitmapFactory.decodeByteArray(imagem, 0, imagem.length))
				.replaceAll("\\+", "@");
	}
}
