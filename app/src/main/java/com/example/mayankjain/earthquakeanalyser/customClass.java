package com.example.mayankjain.earthquakeanalyser;

import android.util.Log;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mayank jain on 6/25/2017.
 */

public class customClass {
    private String header,footer,date,magnitude;

    customClass(String m, String h, String f, String d){
        header=h;
        footer=f;
        date=d;
        magnitude=m;
    }

    public String getFooter() {
        int c= footer.indexOf(',');
        if(c!=-1){
        return footer.substring(c+1,footer.length());
        }
        return footer;
    }

    public String getHeader() {
        if(footer.indexOf(',')!=-1){
            return footer.substring(0,footer.indexOf(','));
        }
        return footer;
    }

    public String getDate() {
        Date date_obj=new Date(Long.parseLong(date));

        return (new SimpleDateFormat("MM dd, yyyy \nHH:mm:ss a")).format(date_obj);


    }

    public String getMagnitude() {
       return (new DecimalFormat("0.00")).format(Double.parseDouble(magnitude));
    }
}
