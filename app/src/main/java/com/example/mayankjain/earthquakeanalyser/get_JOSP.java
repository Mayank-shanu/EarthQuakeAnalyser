package com.example.mayankjain.earthquakeanalyser;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mayank jain on 6/26/2017.
 */

class get_JOSP  {

  protected   ArrayList<customClass> inBackground(String u)
  {
      ArrayList<customClass> arrayList=new ArrayList<>();

      try {
            URL url = new URL(u);
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          connection.setRequestMethod("GET");
          connection.setConnectTimeout(20000);
          connection.connect();

          InputStream inputStream = connection.getInputStream();
          InputStreamReader Ireader = new InputStreamReader(inputStream, Charset.defaultCharset());
          BufferedReader bufferedReader = new BufferedReader(Ireader);
          StringBuilder line = new StringBuilder();

          for (String temp=bufferedReader.readLine();temp!=null;temp=bufferedReader.readLine())
         {
             line.append(temp);

         }

          String jsonReference=line.toString();
          Log.e("trekking string Builder",""+ jsonReference.length());
          JSONObject jsonObject = new JSONObject(jsonReference);
          JSONArray jsonArray=jsonObject.getJSONArray("features");

          for(int i=0;i<jsonArray.length();++i)
          {
              JSONObject jobj=jsonArray.getJSONObject(i);
              JSONObject jobj2=jobj.getJSONObject("properties");
              arrayList.add(new customClass(
                      "" +jobj2.getDouble("mag") ,
                      "" + jobj2.getString("place"),
                      "" + jobj2.getString("place"),
                      "" + jobj2.getLong("time")));

          }
          Log.e("trekking arraylist size","" + arrayList.size());
          return arrayList;

      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (JSONException e) {
          e.printStackTrace();
      }

      return arrayList;
  }

}

class Background extends AsyncTaskLoader<ArrayList<customClass>>{
    private String url;
    public Background(Context context,String u) {
        super(context);
        url=u;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<customClass> loadInBackground() {
        return (new get_JOSP().inBackground(url));
    }

    @Override
    public void deliverResult(ArrayList<customClass> data) {
        super.deliverResult(data);
    }
}

