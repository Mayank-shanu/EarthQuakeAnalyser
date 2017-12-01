package com.example.mayankjain.earthquakeanalyser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by mayank jain on 6/25/2017.
 */

public class customViewAdapter extends ArrayAdapter {
    public customViewAdapter(@NonNull Context context, ArrayList<customClass> al) {
        super(context, 0,al);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        customClass obj=(customClass) getItem(position);
        View view=convertView;
        view =View.inflate(getContext(),R.layout.reference_listview_layout,null);
        TextView t1=(TextView) view.findViewById(R.id.reference_layout_textView_setDate);
        t1.setText(obj.getDate());

        t1=(TextView) view.findViewById(R.id.reference_layout_textView_magnitude);
        t1.setText(obj.getMagnitude());

        t1=(TextView) view.findViewById(R.id.reference_layout_textView_header);
        t1.setText(obj.getHeader());

        t1=(TextView) view.findViewById(R.id.reference_layout_textView_footer);
        t1.setText(obj.getFooter());

        t1=(TextView) view.findViewById(R.id.reference_layout_textView_magnitude);
        Double mag = Double.parseDouble(obj.getMagnitude());
        t1.setTextColor(Color.parseColor("#ffffff"));

        if(mag>=10) t1.setBackgroundColor(Color.parseColor("#C03823"));
        else if(mag>=9) t1.setBackgroundColor(Color.parseColor("#D93218"));
        else if(mag>=8) t1.setBackgroundColor(Color.parseColor("#E13A20"));
        else if(mag>=7) t1.setBackgroundColor(Color.parseColor("#E75F40"));
        else if(mag>=6) t1.setBackgroundColor(Color.parseColor("#FC6644"));
        else if(mag>=5) t1.setBackgroundColor(Color.parseColor("#FF7D50"));
        else if(mag>=4) t1.setBackgroundColor(Color.parseColor("#F5A623"));
        else if(mag>=3) t1.setBackgroundColor(Color.parseColor("#10CAC9"));
        else if(mag>=2) t1.setBackgroundColor(Color.parseColor("#04B4B3"));
        else if(mag>=1) t1.setBackgroundColor(Color.parseColor("#4A7BA7"));



        return view;
    }

}
