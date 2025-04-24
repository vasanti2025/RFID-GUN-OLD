package com.loyalstring.mainscreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.loyalstring.MainActivity;
import com.loyalstring.R;
import com.loyalstring.database.StorageClass;
import com.loyalstring.databinding.ActivityLoginpageBinding;

import java.util.ArrayList;
import java.util.List;

public class Loginpage extends AppCompatActivity {

    ActivityLoginpageBinding b;
    StorageClass storageClass;
    String branch;
    List<String> branchNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginpageBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_loginpage);
        setContentView(b.getRoot());

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        storageClass = new StorageClass(this);

        branchNames.clear();
        branchNames.add("Home");
        branchNames.add("Tray");
        branchNames.add("Box");
        branchNames.add("F1");
        branchNames.add("F2");
        branchNames.add("Exhibition");
        branchNames.add("HLJ-VJ-22N24");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.branchspinner.setAdapter(adapter);
        b.branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (branchadded) {
                branch = adapterView.getItemAtPosition(i).toString();
//                } else {
//                    showtoast("something went wrong please close and open the app");
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (branch == null || branch.isEmpty()) {
                    Toast.makeText(Loginpage.this, "please choose branch", Toast.LENGTH_SHORT).show();
                    return;
                }
                storageClass.setBranch(branch.trim());
                storageClass.setLoggedInStatus(true);
                storageClass.setppower("5");
                storageClass.setipower("30");
                storageClass.settpower("30");
                storageClass.setspower("5");
                storageClass.setstpower("5");
                storageClass.setshpower("5");
                Intent intent = new Intent(Loginpage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Intent intent = new Intent(Loginpage.this, MainActivity.class);
//        startActivity(intent);


    }
}