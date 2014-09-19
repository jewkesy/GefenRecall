package com.daryljewkes.gefenrecall;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class MyActivity extends Activity implements View.OnClickListener {

    private Button btnRecall1, btnRecall2;
    private TextView txtConnStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        txtConnStatus = (TextView) findViewById(R.id.txtConnStatus);
        btnRecall1 = (Button) findViewById(R.id.btnRecall1);
        btnRecall2 = (Button) findViewById(R.id.btnRecall2);
        btnRecall1.setOnClickListener(this);
        btnRecall2.setOnClickListener(this);
        CheckWifi();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CheckWifi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CheckWifi() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            txtConnStatus.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "wifi connected", Toast.LENGTH_SHORT).show();
        } else {
            txtConnStatus.setVisibility(View.VISIBLE);
            Toast.makeText(this, "no wifi", Toast.LENGTH_SHORT).show();

        }

    }

    private void performRecall(int recallId) {
        Log.d("main", "id is :" + recallId);

        String geffonUrl = "http://" + getResources().getString(R.string.connectedTo)  + "/actionHandler.shtml?a=recallPreset&r=index.shtml&preset=" + recallId;

        HttpTask task = new HttpTask();

        task.execute(geffonUrl);

        Toast.makeText(this, "Recall " + recallId + " sent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.btnRecall1:
                performRecall(1);
                break;
            case R.id.btnRecall2:
                performRecall(2);
                break;
            default:
                break;
        }
    }

    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg) {
            String returnText = "", line = "";
            String url = arg[0];

            Log.d("url", url);

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);

            try {
                HttpResponse response = client.execute(get);

                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                if (statusCode == 200) { // Ok
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    while ((line = rd.readLine()) != null) {
                        returnText += line;
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return returnText; // This value will be returned to your onPostExecute(result) method
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("onPostExecute", result);
        }
    }
}
