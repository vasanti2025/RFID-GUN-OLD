package com.loyalstring.tools;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class SUtils {

    public static InputStream getServiceAccountInputStream(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            return assetManager.open("credentials.json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}