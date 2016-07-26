package com.sactalreja.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView listApps;
    private String mFileContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = (Button)findViewById(R.id.btnParse);
        listApps = (ListView)findViewById(R.id.xmlListView);


        String urlForDownload = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=100/xml";
        // call the download data.
        DownloadData downloadData = new DownloadData();
        downloadData.execute(urlForDownload);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplications parseApplications = new ParseApplications(mFileContents);
                parseApplications.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                        MainActivity.this,R.layout.list_item,parseApplications.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });
    }


    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground (String...params){
            mFileContents = downloadXmlFiles(params[0]);
            if (mFileContents == null) {
                Log.d("DownloadData", "Error Downloading");
            }
            return mFileContents;
         }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result is:"+result);
        }

        private String downloadXmlFiles(String urlPath) {
        StringBuilder tempBuffer = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            Log.d("DownloadData", "The response code was" + response);

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            // read data few chars at a time.
            int charRead;
            // reading 500 characters at a time.
            char[] inputBuffer = new char[500];
            while (true) {
                charRead = isr.read(inputBuffer);
                if (charRead <= 0) {
                    break;
                }
                tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
            }
            return tempBuffer.toString();

        } catch (IOException e) {
            Log.d("DownloadData", "IO Exception reading data" + e.getMessage());
        }catch (SecurityException e){
            Log.d("DownloadData", "Security Exception " + e.getMessage());
        }

        return null;

    }

}

}
