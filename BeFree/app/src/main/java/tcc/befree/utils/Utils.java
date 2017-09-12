package tcc.befree.utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

/**
 * Created by gabro on 20/08/2017.
 */

public class Utils {

    //Criptografa Base64
    public static String criptografarBase64(String stringNaoCriptografada){
        return stringNaoCriptografada.replace("+","-").replace("/","_").replace("=","*");
    }

    //Descriptografa Base64
    public static String descriptografarBase64(String stringCriptografada){
        return stringCriptografada.replace("-","+").replace("_","/").replace("*","=");
    }

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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

}