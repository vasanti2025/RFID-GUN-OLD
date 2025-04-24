package com.loyalstring.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.loyalstring.R;
import com.loyalstring.database.StorageClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


public class googlesheet extends AppCompatActivity {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/raw/credentials.json";
    private static final String RANGE = "Class Data!A2:E";
    private static final String SPREADSHEET_ID = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    EditText sheet;
    Button setbtn;
    StorageClass storageClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlesheet);


        sheet = findViewById(R.id.sheetid);
        setbtn = findViewById(R.id.setbtn);
        storageClass = new StorageClass(googlesheet.this);


//        sheet.setText("1yCiz-ip1o8Wgm0xyZomD41vWxO5tWu1Fwp4WjwMGN_c");
        String u = storageClass.getSheeturl();
         sheet.setText(u);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sheet.getText().toString().isEmpty()){
                    Toast.makeText(googlesheet.this, "Please enter sheet id", Toast.LENGTH_SHORT).show();
                    return;
                }
                storageClass.setsheeturl(sheet.getText().toString().trim());
                Toast.makeText(googlesheet.this, "sheet set successful", Toast.LENGTH_SHORT).show();

            }
        });
        
//        readdata();

//        try {
//            getdata();
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void readdata() {


        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://docs.google.com/spreadsheets/d/1yCiz-ip1o8Wgm0xyZomD41vWxO5tWu1Fwp4WjwMGN_c/gviz/tq?tqx=out:json";
String url = "https://docs.google.com/spreadsheets/d/1iPGrzFGItlfYj4dlbmU_iVQDHxiNufR_iUBH-ybIkt4/gviz/tq?tqx=out:json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Here you parse the response
                        try {
                            // Extracting the JSON string from the JavaScript function call
                            String jsonString = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                            JSONObject jsonObject = new JSONObject(jsonString);
                            JSONObject table = jsonObject.getJSONObject("table");
                            JSONArray rows = table.getJSONArray("rows");
                            for (int i = 1; i < rows.length(); i++) {
                                JSONObject entryObj = rows.getJSONObject(i);
                                JSONArray rowData = entryObj.getJSONArray("c");
                                String tid = rowData.getJSONObject(0).getString("v");
                                String category = rowData.getJSONObject(1).getString("v");
                                String itemtype = rowData.getJSONObject(2).getString("v");
                                String purity = rowData.getJSONObject(3).getString("v");
                                String barcode = rowData.getJSONObject(4).getString("v");
                                String itemcode = rowData.getJSONObject(5).getString("v");
                                String grosswt = rowData.getJSONObject(6).getString("v");
                                String stonewt = rowData.getJSONObject(7).getString("v");
                                String netwt = rowData.getJSONObject(8).getString("v");

                                Log.e("check response", "First Name: " + tid + ", Last Name: " + category);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMessage = new String(error.networkResponse.data);
                            Log.e("check error", errorMessage);
                        } else {
                            Log.e("check error", "Unknown error occurred.");
                        }
                    }
                });

        queue.add(stringRequest);


        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://docs.google.com/spreadsheets/d/1yCiz-ip1o8Wgm0xyZomD41vWxO5tWu1Fwp4WjwMGN_c/gviz/tq?tqx=out:json";

        // Request a string response from the provided URL.
        *//*StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Here you parse the response
                        // For example, if it's a JSON object:
                        String json = response.substring(47, response.length() - 2);


                        try {
                            JSONObject jsonObject = new JSONObject(json);

                            JSONObject table = jsonObject.getJSONObject("table");
                            JSONArray rows = table.getJSONArray("rows");
                            for(int i=0; i<rows.length(); i++) {
                                JSONObject entryObj = rows.getJSONObject(i);
                                String firstName = entryObj.getJSONObject("gsx$name").getString("$t");
                                String lastName = entryObj.getJSONObject("gsx$last name").getString("$t");
                                Log.e("check response", "" + "  " + firstName+"  "+lastName);
                            }

                            *//**//*for (int i = 0; i < rows.length(); i++) {
                                JSONObject row = rows.getJSONObject(i);
                                JSONArray c = row.getJSONArray("c");

                                for (int j = 0; j < c.length(); j++) {
                                    JSONObject cell = c.getJSONObject(j);
                                    String value = cell.getString("v");
                                    Log.e("check cell value", value);
                                }
                            }*//**//*

//
                            // Do something with the JSON object
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });*//*

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Here you parse the response
                        try {
                            JSONObject table = response.getJSONObject("table");
                            JSONArray rows = table.getJSONArray("rows");
                            for(int i = 0; i < rows.length(); i++) {
                                JSONObject entryObj = rows.getJSONObject(i);
                                String firstName = entryObj.getJSONObject("gsx$name").getString("$t");
                                String lastName = entryObj.getJSONObject("gsx$last name").getString("$t");
                                Log.e("check response", "" + "  " + firstName + "  " + lastName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("check error", error.toString());
                    }
                });

//        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);*/

        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
    }

    private void getdata() throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        final String range = "Class Data!A2:E";
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(0), row.get(4));
            }
        }


    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = getResources().openRawResource(R.raw.credentials);//googlesheet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

   /* private class FetchDataFromSheetTask extends AsyncTask<Void, Void, List<List<Object>>> {

        @Override
        protected List<List<Object>> doInBackground(Void... voids) {
            try {
                return fetchDataFromSheet();
            } catch (IOException | GeneralSecurityException e) {
                Log.e("FetchDataFromSheetTask", "Error fetching data from Google Sheets", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<List<Object>> values) {
            super.onPostExecute(values);
            if (values != null) {
                // Update UI with the retrieved values
                // For example, you can display the data in a TextView
                // textView.setText(values.toString());
                Log.d("FetchDataFromSheetTask", "Values retrieved: " + values);
            } else {
                // Handle error
                Log.e("FetchDataFromSheetTask", "Failed to fetch data from Google Sheets");
            }
        }
    }

    private List<List<Object>> fetchDataFromSheet() throws IOException, GeneralSecurityException {
        InputStream in = getResources().getAssets().open("credentials.json");
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        Sheets sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();
        return response.getValues();
    }*/

    /*private void getDataFromAPI() {
        String spreadsheetId = "1yCiz-ip1o8Wgm0xyZomD41vWxO5tWu1Fwp4WjwMGN_c";
//        String url = "https://docs.google.com/spreadsheets/d/" + spreadsheetId + "/gviz/tq?tqx=out:json";
        String url = "https://spreadsheets.google.com/feeds/list/" + spreadsheetId + "/od6/public/values?alt=json";
        RequestQueue queue = Volley.newRequestQueue(googlesheet.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                loadingPB.setVisibility(View.GONE);
                Log.d("TAG", "onsheetResponse: "+ response);
                *//*try {
                    JSONArray tableRows = response.getJSONObject("table").getJSONArray("rows");

                    for (int i = 0; i < tableRows.length(); i++) {
                        JSONObject row = tableRows.getJSONObject(i);
                        JSONArray columns = row.getJSONArray("c");
                        String firstName = columns.getJSONObject(0).getString("v");
                        String lastName = columns.getJSONObject(1).getString("v");
                        String email = columns.getJSONObject(2).getString("v");
                        String avatar = columns.getJSONObject(3).getString("v");
//                        userModalArrayList.add(new UserModal(firstName, lastName, email, avatar));
                    }
//                    userRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*//*
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("check response ", " "+error.toString());
                Toast.makeText(googlesheet.this, "Failed to get data "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }*/

}