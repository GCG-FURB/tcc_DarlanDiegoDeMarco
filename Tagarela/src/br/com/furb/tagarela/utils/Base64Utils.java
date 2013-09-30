package br.com.furb.tagarela.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Base64Utils {
	public static String encodeTobase64(Bitmap image)
	{
	    Bitmap immagex=image;
	    ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();  
	    immagex.compress(Bitmap.CompressFormat.PNG, 50, byteOutPutStream);
	    byte[] b = byteOutPutStream.toByteArray();
	    String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

	    Log.e("LOOK", imageEncoded);
	    return imageEncoded;
	}
	public static Bitmap decodeBase64(String input) 
	{
	    byte[] decodedByte = Base64.decode(input, 0);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}
	
	public static byte[] decodeBase64ToByteArray(String input){
		Bitmap bitmap = decodeBase64(input);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}
}
