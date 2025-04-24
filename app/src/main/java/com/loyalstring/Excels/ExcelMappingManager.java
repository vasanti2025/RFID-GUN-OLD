package com.loyalstring.Excels;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelMappingManager {
    public static void saveMappings(Context context, Map<String, String> selectedMappings, String mapname) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        // Serialize the map name and mappings together
        Gson gson = new Gson();
        String jsonMappings = gson.toJson(selectedMappings);
        Map<String, String> mapData = new HashMap<>();
        mapData.put("name", mapname);
        mapData.put("mappings", jsonMappings);
        String jsonMapData = gson.toJson(mapData);

        // Save the combined data
        editor.putString(mapname, jsonMapData);
        editor.apply();
        Toast.makeText(context, "saved mappings", Toast.LENGTH_SHORT).show();
    }

    public static Map<String, String> getSavedMappings(Context context, String mapname) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonMapData = prefs.getString(mapname, null);
        if (jsonMapData != null) {
            // Deserialize the combined data and extract mappings
            Gson gson = new Gson();
            Map<String, String> mapData = gson.fromJson(jsonMapData, new TypeToken<Map<String, String>>(){}.getType());
            String jsonMappings = mapData.get("mappings");
            return gson.fromJson(jsonMappings, new TypeToken<Map<String, String>>(){}.getType());
        }
        return new HashMap<>();
    }

    public static List<String> getSavedMappingNames(FragmentActivity activity) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Map<String, ?> allEntries = prefs.getAll();
        List<String> mappingNames = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            // Extract map name from the key and add it to the list
            String mapName = entry.getKey();
            mappingNames.add(mapName);
        }
        return mappingNames;
    }
}