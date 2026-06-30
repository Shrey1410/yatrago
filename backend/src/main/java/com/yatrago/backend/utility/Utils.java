package com.yatrago.backend.utility;

public class Utils {
    public static boolean isNullOrEmpty(String data){
        if(data == null || data.isEmpty()){
            return true;
        }
        return false;
    }
}
