package com.loyalstring.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.loyalstring.interfaces.interfaces;
import com.loyalstring.modelclasses.Itemmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DownloadImagesTask extends AsyncTask<HashMap<String, Itemmodel>, Integer, Void> {
    private Context context;
    private SharedPreferences sharedPreferences;
    private interfaces.Imagedownload callback;

    int scount = 0;
    int fcount = 0;

    public DownloadImagesTask(Context context, interfaces.Imagedownload callback) {
        this.context = context;
        this.callback = callback;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected Void doInBackground(HashMap<String, Itemmodel>... totalitems) {
        int totalCount = totalitems[0].size();
        int currentCount = 0;

        for (Map.Entry<String, Itemmodel> entry : totalitems[0].entrySet()) {
            Itemmodel item = entry.getValue();
            String imageUrl = item.getPartyCode();

            if (imageUrl == null || imageUrl.isEmpty()) {
                continue;
            }

            try {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf('-') + 1, imageUrl.lastIndexOf('.')) + ".jpg";
                File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Loyalstring files/images/" + fileName);

                if (destinationFile.exists()) {
                    callback.onSaveSuccess(scount); // Image already exists, skip download
                    scount++;
                    continue;
                }

                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                        System.out.println("Access forbidden for URL: " + imageUrl);
                    }
                    callback.onSaveFailure(fcount); // Handle failure due to forbidden access or other error
                    fcount++;
                    continue;
                }

                InputStream input = connection.getInputStream();
                FileOutputStream output = new FileOutputStream(destinationFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                output.close();
                input.close();

                callback.onSaveSuccess(scount); // Image successfully downloaded
                scount++;
                currentCount++;
                int progress = (int) ((currentCount / (float) totalCount) * 100);
                publishProgress(progress);

            } catch (Exception e) {
                e.printStackTrace();
                callback.onSaveFailure(fcount); // Handle failure due to exception
                fcount++;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        sharedPreferences.edit().putInt("download_progress", values[0]).apply();
    }
}