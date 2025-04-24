package com.loyalstring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.loyalstring.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiConfigActivity extends AppCompatActivity {

    private EditText etApiUrl, etApiKey, etUsername, etPassword, etOAuthClientId, etOAuthClientSecret;
    private Spinner spinnerHttpMethod, spinnerAuthMethod;
    private LinearLayout layoutApiKey, layoutBasicAuth, layoutOAuth;
    private Button btnSave;

    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_config);

        etApiUrl = findViewById(R.id.et_api_url);
        etApiKey = findViewById(R.id.et_api_key);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etOAuthClientId = findViewById(R.id.et_oauth_client_id);
        etOAuthClientSecret = findViewById(R.id.et_oauth_client_secret);
        spinnerHttpMethod = findViewById(R.id.spinner_http_method);
        spinnerAuthMethod = findViewById(R.id.spinner_auth_method);
        layoutApiKey = findViewById(R.id.layout_api_key);
        layoutBasicAuth = findViewById(R.id.layout_basic_auth);
        layoutOAuth = findViewById(R.id.layout_oauth);
        btnSave = findViewById(R.id.btn_save);
        etApiUrl.setText("https://httpbin.org/basic-auth/user/pass");

        // Set up spinner for HTTP methods
        ArrayAdapter<CharSequence> httpMethodAdapter = ArrayAdapter.createFromResource(this,
                R.array.http_methods, android.R.layout.simple_spinner_item);
        httpMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHttpMethod.setAdapter(httpMethodAdapter);

        // Set up spinner for authentication methods
        ArrayAdapter<CharSequence> authMethodAdapter = ArrayAdapter.createFromResource(this,
                R.array.auth_methods, android.R.layout.simple_spinner_item);
        authMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthMethod.setAdapter(authMethodAdapter);

        spinnerAuthMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleAuthMethodSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

//        btnSave.setOnClickListener(v -> saveApiConfig());

        btnSave.setOnClickListener(v -> sendApiRequest());
    }

    private void handleAuthMethodSelection(int position) {
        // 0 - No Auth, 1 - API Key, 2 - Basic Auth, 3 - OAuth
        switch (position) {
            case 0: // No Auth
                layoutApiKey.setVisibility(View.GONE);
                layoutBasicAuth.setVisibility(View.GONE);
                layoutOAuth.setVisibility(View.GONE);
                break;
            case 1: // API Key
                layoutApiKey.setVisibility(View.VISIBLE);
                layoutBasicAuth.setVisibility(View.GONE);
                layoutOAuth.setVisibility(View.GONE);
                break;
            case 2: // Basic Auth
                layoutApiKey.setVisibility(View.GONE);
                layoutBasicAuth.setVisibility(View.VISIBLE);
                layoutOAuth.setVisibility(View.GONE);
                break;
            case 3: // OAuth
                layoutApiKey.setVisibility(View.GONE);
                layoutBasicAuth.setVisibility(View.GONE);
                layoutOAuth.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void saveApiConfig() {
        // Implement saving logic here
        String apiUrl = etApiUrl.getText().toString();
        String httpMethod = spinnerHttpMethod.getSelectedItem().toString();
        String apiKey = etApiKey.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String oauthClientId = etOAuthClientId.getText().toString();
        String oauthClientSecret = etOAuthClientSecret.getText().toString();

        // Validate and save the configuration
    }

    private void sendApiRequest() {
        String url = etApiUrl.getText().toString();
        String httpMethod = spinnerHttpMethod.getSelectedItem().toString();
        String authMethod = spinnerAuthMethod.getSelectedItem().toString();

        Request.Builder requestBuilder = new Request.Builder().url(url);

        switch (authMethod) {
            case "API Key":
                String apiKey = etApiKey.getText().toString();
                requestBuilder.addHeader("Authorization", "Bearer " + apiKey);
                break;
            case "Basic Auth":
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String credentials = okhttp3.Credentials.basic(username, password);
                requestBuilder.addHeader("Authorization", credentials);
                break;
            case "OAuth 2.0":
                String oauthToken = etOAuthClientId.getText().toString(); // Use clientId or token as required
                requestBuilder.addHeader("Authorization", "Bearer " + oauthToken);
                break;
        }

        Request request;
        switch (httpMethod) {
            case "POST":
                request = requestBuilder.post(okhttp3.RequestBody.create("", okhttp3.MediaType.get("application/json"))).build();
                break;
            case "PUT":
                request = requestBuilder.put(okhttp3.RequestBody.create("", okhttp3.MediaType.get("application/json"))).build();
                break;
            case "DELETE":
                request = requestBuilder.delete().build();
                break;
            default:
                request = requestBuilder.get().build();
                break;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ApiConfigActivity.this, "Request Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(() -> {
                        Toast.makeText(ApiConfigActivity.this, "Response Successful", Toast.LENGTH_SHORT).show();
                        // Process the response data as needed
                        Log.d("API Response", responseData);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ApiConfigActivity.this, "Request Failed with Status Code: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }



}