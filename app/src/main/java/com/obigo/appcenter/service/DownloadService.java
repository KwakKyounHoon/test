package com.obigo.appcenter.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by obigo on 10/31/16.
 */
public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String PROGRESS = "progress";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.obigo.appcenter.service.receiver";

    public DownloadService() {
        super("DownloadService");
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra(URL);
        String fileName = intent.getStringExtra(FILENAME);
        File output = new File(Environment.getExternalStorageDirectory(),
                fileName);
        if (output.exists()) {
            output.delete();
        }

        InputStream stream = null;
        FileOutputStream fos = null;
        try {

            URL url = new URL(urlPath);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int file_size = urlConnection.getContentLength();
            stream = urlConnection.getInputStream();
//            InputStreamReader reader = new InputStreamReader(stream);
            InputStream input = new BufferedInputStream(urlConnection.getURL().openStream(), 8192);

            Log.i("test", file_size+"");
            fos = new FileOutputStream(output.getPath());
            byte data[] = new byte[700];
            int next;
            long total = 0;
            while ((next = input.read(data)) != -1) {
                total += next;
                int progress = (int)((total*100)/file_size);
                Log.i("test",(int)((total*100)/file_size)+"");
                publishResults(output.getAbsolutePath(), 2, progress);
                fos.write(data, 0, next);
            }
            fos.flush();
            // successfully finished
            result = Activity.RESULT_OK;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publishResults(output.getAbsolutePath(), result,0);
    }

    private void publishResults(String outputPath, int result, int progress) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(PROGRESS, progress);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}