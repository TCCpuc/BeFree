package tcc.befree.utils;

/**
 * Created by gabro on 20/08/2017.
 */

public class Utils {

    public static String criptografarBase64(String stringNaoCriptografada){
        return stringNaoCriptografada.replace("+","-").replace("/","_").replace("=","*");
    }


    public static String descriptografarBase64(String stringCriptografada){
        return stringCriptografada.replace("-","+").replace("_","/").replace("*","=");
    }
}
