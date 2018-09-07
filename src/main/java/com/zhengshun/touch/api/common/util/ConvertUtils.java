package com.zhengshun.touch.api.common.util;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ConvertUtils {

    public static String convertMapToString( Map resourceMap ) {
        Map.Entry entry;
        JSONObject result = new JSONObject();
        for ( Iterator iterator = resourceMap.entrySet().iterator(); iterator.hasNext(); ) {
            entry = ( Map.Entry ) iterator.next();
            result.put( entry.getKey().toString(), entry.getValue() );
        }
        return result.toString();
    }

    public static Map<String, Object> convertObjToMap( Object obj ) {
        if ( obj == null ) {
            return null;
        }
        Map<String, Object> res = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for ( Field field : declaredFields ) {
            field.setAccessible( true );
            try {
                res.put( field.getName(), field.get( obj ) );
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static String dateStr(Date date, String f) {
        if(date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(f);
            String str = format.format(date);
            return str;
        }
    }

    public static String convertFieldName( String oriFieldName ) {
        String resultFieldName = oriFieldName.toLowerCase();
        if ( resultFieldName.contains( "_" ) ) {
            String[] fieldNameArr = resultFieldName.split( "_" );
            resultFieldName = fieldNameArr[0];
            for ( int i = 1; i < fieldNameArr.length; i++ ) {
                String temp = fieldNameArr[i];
                resultFieldName = resultFieldName + temp.substring( 0, 1 ).toUpperCase() + temp.substring( 1, temp.length() );
            }
        }
        return resultFieldName;
    }

    public static void main( String[] args ) {
//        System.out.println( convertFieldName( "aaaa" ) );
//        System.out.println( convertFieldName( "ab_bc_cd" ) );
        BigDecimal dd = new BigDecimal( "123.999" );
        System.out.println( dd.intValue() );
    }

}
