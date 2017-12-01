package com.example.mayankjain.earthquakeanalyser;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.button;
import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences preferences = getSharedPreferences("USER DATA", Context.MODE_PRIVATE);
        Button button = (Button) findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Start Date",
                                ((EditText)findViewById(R.id.startDateYear)).getText().toString()     +
                                "-" +
                                ((EditText)findViewById(R.id.startDateMonth)).getText().toString() +
                                "-" +
                                ((EditText)findViewById(R.id.startDate)).getText().toString());

                editor.putString("End Date",

                        ((EditText)findViewById(R.id.endDateYear)).getText().toString()     +
                                "-" +
                                ((EditText)findViewById(R.id.endDateMonth)).getText().toString() +
                                "-" +
                                ((EditText)findViewById(R.id.endDate)).getText().toString()

                        );

                editor.apply();

                Toast.makeText(getApplicationContext(),"settings saved",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.setting_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.settings_reset){
            SharedPreferences preferences = getSharedPreferences("USER DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear().apply();

            Toast.makeText(getApplicationContext(),"Reset Done",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
