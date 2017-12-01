package com.example.mayankjain.earthquakeanalyser;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.view.View.GONE;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private String url;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()) {
            bar = (ProgressBar) findViewById(R.id.loading_spinner);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = dateFormat.format(c.getTime());
            dateFormat = new SimpleDateFormat("yyyy");
            String startDate = dateFormat.format(c.getTime()) + "-01-01";



            SharedPreferences preferences = getSharedPreferences("USER DATA", Context.MODE_PRIVATE);
            if(preferences.getString("Start Date",startDate).length()==10)
            startDate = preferences.getString("Start Date",startDate);

            if(preferences.getString("Start Date",startDate).length()==10)
                endDate = preferences.getString("End Date",endDate);


            url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + startDate +
                    "&endtime=" + endDate + "&minmagnitude=5.7";

            Log.e("trekking url" , url);

            LoaderManager.LoaderCallbacks<ArrayList<customClass>> loaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<customClass>>() {
                @Override
                public Loader<ArrayList<customClass>> onCreateLoader(int id, Bundle args) {
                    Log.e("trekking loader", "arrived onCreateL");
                    return new Background(getApplicationContext(), url);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<customClass>> loader, ArrayList<customClass> data) {
                    Log.e("trekking loader", "arrived onLoadFinish " + data.size());
                    updateUI(data);
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<customClass>> loader) {

                }
            };
            getSupportLoaderManager().initLoader(0, null, loaderCallbacks);
        }

        else {
            ((TextView) findViewById(R.id.onEmptyView)).setBackgroundResource(R.drawable.no_internet);
        }

    }

    void updateUI(ArrayList<customClass> arrayList)
    {
        bar.setVisibility(GONE);

        ListView listView = (ListView) findViewById(R.id.main_activity_listView);
        customViewAdapter adapter = new customViewAdapter(getApplicationContext(),arrayList);
        listView.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("USER DATA", Context.MODE_PRIVATE);


        if(arrayList.size()==0&&!preferences.contains("Start Date")&&!preferences.contains("End Date"))
        {
            ((RelativeLayout) findViewById(R.id.top_details_RelativeView)).setVisibility(GONE);

            ((TextView) findViewById(R.id.onEmptyView)).setText("Something Not Right: Possible Causes " +
                    "\n*data connection is off" +
                    "\n*very slow net connection" +
                    "\n*USGS server not responding" +
                    "\n\n Possible Solution" +
                    "=> Restart app with data connection on");
        }

        else if(arrayList.size()==0&&(preferences.contains("Start Date")||preferences.contains("End Date")))
        {
            ((RelativeLayout) findViewById(R.id.top_details_RelativeView)).setVisibility(GONE);

            ((TextView) findViewById(R.id.onEmptyView)).setText("Something Not Right: Possible Causes " +
                    "\n*No data available for currently selected dates" +
                    "\n\n Possible Solution" +
                    "=> Change the dates ");
        }


        else if(arrayList.size()!=0)
        {
            TextView emptyCase = (TextView) findViewById(R.id.onEmptyView);
            emptyCase.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.menu_about)
        {
            Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);

        }

        else if(item.getItemId()==R.id.menu_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        else if(item.getItemId()==R.id.refresh_data){

            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static class fragOne extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_main,container);

            return view;
        }
    }

}
