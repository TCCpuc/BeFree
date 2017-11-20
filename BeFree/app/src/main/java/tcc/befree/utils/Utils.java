package tcc.befree.utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

/**
 * Created by gabro on 20/08/2017.
 */

public class Utils {

    public static final int MAX_WIDTH = 1080;
    public static final int MAX_HEIGHT = 1920;

    //Criptografa Base64
//    public static String criptografarBase64(String stringNaoCriptografada){
//        return stringNaoCriptografada.replace("+","-").replace("/","_").replace("=","*");
//    }
//
//    //Descriptografa Base64
//    public static String descriptografarBase64(String stringCriptografada){
//        return stringCriptografada.replace("-","+").replace("_","/").replace("*","=");
//    }

    // Converte String base64 para Bitmap
    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // Converte String Bitmap base64
    public static String convert(Bitmap bitmap)
    {
        if (bitmap.getHeight()> MAX_HEIGHT || bitmap.getWidth()>MAX_WIDTH){
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, MAX_WIDTH, MAX_HEIGHT), Matrix.ScaleToFit.CENTER);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static String workaroundReplace(String string) {
        return string
                .replace('ç','c')
                .replace('Ç','C')
                .replace('ã','a')
                .replace('Ã','A')
                .replace('é','e')
                .replace('É','E')
                .replace('ê','e')
                .replace('Ê','E')
                .replace('õ','o')
                .replace('Õ','O')
                .replace('ó','o')
                .replace('Ó','O')
                .replace('í','i')
                .replace('Í','I')
                .replace('Â','A')
                .replace('â','â')
                .replace('à','a')
                .replace('À','A');
    }
}