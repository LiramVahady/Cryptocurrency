package liram.dev.cryptocurrency.utility;

import android.annotation.SuppressLint;

/*
    This class contain static method
    could help us to for permanent performance
     entire the project
 */
public class Util {


    //TODO: Return %.f format
    @SuppressLint("DefaultLocale")
    public static String priceFormat(double value){
        return String.format("%,.2f", value) + " $";
    }

    public static boolean isNumeric(String str){
       char[] arr = str.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (Character.isDigit(arr[i])){
                return true;
            }
        }

       return false;
    }

}
