package com.loyalstring.mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.loyalstring.Apis.LoginRequest;
import com.loyalstring.Apis.ActivationResponse;
import com.loyalstring.LatestStorage.SharedPreferencesManager;
import com.loyalstring.MainActivity;
import com.loyalstring.R;
import com.loyalstring.database.StorageClass;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.network.NetworkUtils;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class splashscreen extends AppCompatActivity {
    EntryDatabase entryDatabase;

    StorageClass storageClass;
    NetworkUtils networkUtils;
    private ApiService apiService;
    SharedPreferencesManager sharedPreferencesManager;

    ProgressDialog dialog;
    private static final int MY_REQUEST_CODE = 123;
    private AppUpdateManager appUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splasshscreen);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        entryDatabase = new EntryDatabase(this);
        sharedPreferencesManager = new SharedPreferencesManager(splashscreen.this);


//        FirebaseDatabase.getInstance().getReference().child("rfiddata")
//                .child("shudh").child("Sheet1")
//                .child("test").setValue("1");

        storageClass = new StorageClass(this);
        networkUtils = new NetworkUtils(this);
        dialog = new ProgressDialog(this);
        appUpdateManager = AppUpdateManagerFactory.create(this);
        entryDatabase.checkdatabase(splashscreen.this);
        // Check for updates
        checkForAppUpdate();





        /*String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/file-sample_150kB.pdf";
        File file = new File(filePath);

        if (!file.exists()) {
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If no app is available to view PDF files
            Toast.makeText(getApplicationContext(), "No application available to view PDF", Toast.LENGTH_SHORT).show();
        }*/
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                entryDatabase.checkdatabase(splashscreen.this);
                MyApplication app = (MyApplication) getApplicationContext();
                int count = entryDatabase.gettotalcount(splashscreen.this);
                Log.e("checktotalcount ", "" + count);
//                Toast.makeText(splashscreen.this, "check to"  +count, Toast.LENGTH_SHORT).show();
                if (count > 0) {
//                    app.setcount(count);
                    HashMap<String, Itemmodel> inventoryMap = entryDatabase.loadInventoryItems(splashscreen.this, app, count);
                }
//                app.setInventoryMap(inventoryMap);
                // Update main activity UI with loaded data (using Handler or AsyncTask)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update list view in your home page activity with data from inventoryMap
                    }
                });
            }
        }).start();*/

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent go = new Intent(splashscreen.this, Loginpage.class);
//                startActivity(go);
//                finish();
//            }
//        }, 2000);



        /*if (storageClass.isActivated()) {
//            storageClass.setActivationStatus(false, "", "", "", "", "");
//            Intent go = new Intent(splashscreen.this, Activationpage.class);
//            startActivity(go);
//            finish();
            if(sharedPreferencesManager.readLoginData().getEmployee().getClientCode() == null || sharedPreferencesManager.readLoginData().getEmployee().getClientCode().isEmpty()){

                storageClass.setActivationStatus(false, "", "", "", "", "");
                Intent go = new Intent(splashscreen.this, Activationpage.class);
                startActivity(go);
                finish();
            }else{
                if (!storageClass.isLoggedIn()) {
                    Intent go = new Intent(splashscreen.this, Loginpage.class);
                    startActivity(go);
                    finish();
                } else {
                    Intent go = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(go);
                    finish();
                }
            }


        } else {
            if (networkUtils.isNetworkAvailable()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent go = new Intent(splashscreen.this, Activationpage.class);
                        startActivity(go);
                        finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(this, "no network", Toast.LENGTH_SHORT).show();
            }
        }*/

        /*if (storageClass.isActivated()) {
            Log.e("splashscreen", "check 1");
            if (storageClass.getPhone() == null || storageClass.getPhone().isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent = new Intent(splashscreen.this, Activationpage.class);
//
//                        startActivity(intent);
                        if (!storageClass.isLoggedIn()) {
                            Intent go = new Intent(splashscreen.this, Loginpage.class);
                            startActivity(go);
                            finish();
                        } else {
                            Intent go = new Intent(splashscreen.this, MainActivity.class);
                            startActivity(go);
                            finish();
                        }
                    }
                }, 2000);
            }
            else {

                *//*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!storageClass.isLoggedIn()) {
                            Intent go = new Intent(splashscreen.this, Loginpage.class);
                            startActivity(go);
                            finish();
                        } else {
                            Intent go = new Intent(splashscreen.this, MainActivity.class);
                            startActivity(go);
                            finish();
                        }
                    }
                }, 2000);*//*

                if (networkUtils.isNetworkAvailable()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!storageClass.isLoggedIn()) {
                                Intent go = new Intent(splashscreen.this, Loginpage.class);
                                startActivity(go);
                                finish();
                            } else {
                                Intent go = new Intent(splashscreen.this, MainActivity.class);
                                startActivity(go);
                                finish();
                            }
                        }
                    }, 2000);
                    *//*try {
                        // Make API call

                        String baseUrl = "https://raniwalajewellers.loyalstring.co.in/";
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Log request and response body

                        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(loggingInterceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .client(okHttpClient)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService apiService = retrofit.create(ApiService.class);
                        LoginRequest loginRequest = new LoginRequest(Build.DISPLAY, storageClass.getPhone());


                        String json = "{\"DeviceBuildNo\":\"" + Build.DISPLAY + "\",\"MobileNo\":\"" + storageClass.getPhone() + "\"}";
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

                        Call<ActivationResponse> call = apiService.login(requestBody);
                        call.enqueue(new Callback<ActivationResponse>() {
                            @Override
                            public void onResponse(Call<ActivationResponse> call, retrofit2.Response<ActivationResponse> response) {
                                if (response.isSuccessful()) {
                                    Log.e("splashscreen", "response  "+response);
                                    if (response.body() != null) {
                                        String devicestate = response.body().getDeviceStatus();
                                        if (devicestate != null && !devicestate.isEmpty()) {
                                            if (devicestate.equalsIgnoreCase("locked")) {
                                                storageClass.updateactivation(false);
                                                storageClass.setLoggedInStatus(false);
                                                Intent go = new Intent(splashscreen.this, Activationpage.class);
                                                startActivity(go);
                                                finish();
                                            } else {
                                                if (storageClass.isActivated()) {
                                                    if (!storageClass.isLoggedIn()) {
                                                        Intent go = new Intent(splashscreen.this, Loginpage.class);
                                                        startActivity(go);
                                                        finish();
                                                    } else {
                                                        Intent go = new Intent(splashscreen.this, MainActivity.class);
                                                        startActivity(go);
                                                        finish();
                                                    }
                                                } else {
                                                    Intent go = new Intent(splashscreen.this, Activationpage.class);
                                                    startActivity(go);
                                                    finish();
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(splashscreen.this, "no data found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(splashscreen.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ActivationResponse> call, Throwable t) {
                                Toast.makeText(splashscreen.this, "somethiing went wrong please restart app or contact us"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }*//*
                }
                else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!storageClass.isLoggedIn()) {
                                Intent go = new Intent(splashscreen.this, Loginpage.class);
                                startActivity(go);
                                finish();
                            } else {
                                Intent go = new Intent(splashscreen.this, MainActivity.class);
                                startActivity(go);
                                finish();
                            }
                        }
                    }, 2000);
                }
            }

        }
        else {
            Toast.makeText(this, "device not activated...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(splashscreen.this, Activationpage.class);

                    startActivity(intent);
                }
            }, 2000);
        }*/


    }

    private void checkForAppUpdate() {
        // Check if an update is available
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnCompleteListener(new OnCompleteListener<AppUpdateInfo>() {
            @Override
            public void onComplete(@NonNull Task<AppUpdateInfo> task) {
                Log.e("checking task ", "  "+task.isSuccessful());
                if (task.isSuccessful()) {
                    AppUpdateInfo appUpdateInfo = task.getResult();

                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Immediate update is available
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    AppUpdateType.IMMEDIATE,
                                    splashscreen.this,
                                    MY_REQUEST_CODE
                            );
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Continue with other initializations if no update is available
                        continueToNextScreen();
                    }
                }else{
                    continueToNextScreen();
                }
            }
        });
    }

    private void continueToNextScreen() {
        // Your existing logic for transitioning to the next screen
//        Toast.makeText(this, "came here", Toast.LENGTH_SHORT).show();
        if (storageClass.isActivated()) {
            if (sharedPreferencesManager.readLoginData().getEmployee().getClientCode() == null ||
                    sharedPreferencesManager.readLoginData().getEmployee().getClientCode().isEmpty()) {
                storageClass.setActivationStatus(false, "", "", "", "", "");
                Intent go = new Intent(splashscreen.this, Activationpage.class);
                startActivity(go);
                finish();
            } else {
                new Thread(new Runnable() {
            @Override
            public void run() {
                entryDatabase.checkdatabase(splashscreen.this);
                MyApplication app = (MyApplication) getApplicationContext();
                int count = entryDatabase.gettotalcount(splashscreen.this);
                Log.e("checktotalcount ", "" + count);
//                Toast.makeText(splashscreen.this, "check to"  +count, Toast.LENGTH_SHORT).show();
                if (count > 0) {
//                    app.setcount(count);
                    HashMap<String, Itemmodel> inventoryMap = entryDatabase.loadInventoryItems(splashscreen.this, app, count);
                }
//                app.setInventoryMap(inventoryMap);
                // Update main activity UI with loaded data (using Handler or AsyncTask)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update list view in your home page activity with data from inventoryMap
                    }
                });
            }
        }).start();
                if (!storageClass.isLoggedIn()) {
                    Intent go = new Intent(splashscreen.this, Loginpage.class);
                    startActivity(go);
                    finish();
                } else {
                    Intent go = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(go);
                    finish();
                }
            }
        } else {
            if (networkUtils.isNetworkAvailable()) {
                new Handler().postDelayed(() -> {
                    Intent go = new Intent(splashscreen.this, Activationpage.class);
                    startActivity(go);
                    finish();
                }, 2000);
            } else {
                Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                // If the update is canceled or fails, proceed to the next screen
                continueToNextScreen();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Checks if the update is downloaded but not installed, prompting the user to complete the update.
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQUEST_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}