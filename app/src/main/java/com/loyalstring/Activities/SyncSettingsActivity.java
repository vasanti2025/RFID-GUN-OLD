package com.loyalstring.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loyalstring.LatestSettings.SyncSettings;
import com.loyalstring.LatestStorage.SharedPreferencesManager;
import com.loyalstring.R;

public class SyncSettingsActivity extends AppCompatActivity {

    private CheckBox checkboxProduct, checkboxInventory, checkboxBills, checkboxReports;
    private SharedPreferencesManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_settings);

        // Initialize SharedPreferenceManager
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        sharedPreferenceManager = new SharedPreferencesManager(SyncSettingsActivity.this);

        // Initialize checkboxes
        checkboxProduct = findViewById(R.id.checkbox_product);
        checkboxInventory = findViewById(R.id.checkbox_inventory);
        checkboxBills = findViewById(R.id.checkbox_bills);
        checkboxReports = findViewById(R.id.checkbox_reports);

        // Load and set initial states from SharedPreferences
        loadSyncSettings();

        // Set up the save button
        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> saveSyncSettings());
    }

    private void loadSyncSettings() {
        SyncSettings syncSettings = sharedPreferenceManager.loadSyncSettings();
        checkboxProduct.setChecked(syncSettings.isProduct());
        checkboxInventory.setChecked(syncSettings.isInventory());
        checkboxBills.setChecked(syncSettings.isBills());
        checkboxReports.setChecked(syncSettings.isReports());
    }

    private void saveSyncSettings() {
        SyncSettings syncSettings = new SyncSettings();
        syncSettings.setProduct(checkboxProduct.isChecked());
        syncSettings.setInventory(checkboxInventory.isChecked());
        syncSettings.setBills(checkboxBills.isChecked());
        syncSettings.setReports(checkboxReports.isChecked());

        sharedPreferenceManager.saveSyncSettings(syncSettings);
        Toast.makeText(this, "Sync settings saved!", Toast.LENGTH_SHORT).show();
    }
}