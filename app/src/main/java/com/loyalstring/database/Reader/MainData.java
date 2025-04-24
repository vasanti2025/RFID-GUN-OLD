package com.loyalstring.database.Reader;

import android.content.Context;
import android.os.AsyncTask;

import com.loyalstring.apiresponse.SkuResponse;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.interfaces.interfaces;

import java.util.ArrayList;
import java.util.List;

public class MainData {


    public static void fetchAllsku(Context context, EntryDatabase entryDatabase, interfaces.OnSkusFetchedListener listener) {
        new AsyncTask<Void, Void, List<SkuResponse>>() {
            @Override
            protected List<SkuResponse> doInBackground(Void... voids) {
                List<SkuResponse> allSkus = new ArrayList<>();
                int offset = 0;
                int limit = 1000; // Number of records per batch
                List<SkuResponse> batch;

                do {
                    batch = entryDatabase.readSkusInBatches(context, offset, limit);
                    allSkus.addAll(batch);
                    offset += limit;
                } while (batch.size() == limit);

                return allSkus;
            }

            @Override
            protected void onPostExecute(List<SkuResponse> allSkus) {
                super.onPostExecute(allSkus);
                // Notify the listener with the result
                if (listener != null) {
                    listener.onSkusFetched(allSkus);
                }
            }
        }.execute();
    }

   /* public static void fetchAllLabelitems(Context context, EntryDatabase entryDatabase, interfaces.OnSkusFetchedListener listener) {
        new AsyncTask<Void, Void, List<SkuResponse>>() {
            @Override
            protected List<SkuResponse> doInBackground(Void... voids) {
                List<SkuResponse> allSkus = new ArrayList<>();
                int offset = 0;
                int limit = 1000; // Number of records per batch
                List<SkuResponse> batch;

                do {
                    batch = entryDatabase.readLabelitemsInBatches(context, offset, limit);
                    allSkus.addAll(batch);
                    offset += limit;
                } while (batch.size() == limit);

                return allSkus;
            }

            @Override
            protected void onPostExecute(List<SkuResponse> allSkus) {
                super.onPostExecute(allSkus);
                // Notify the listener with the result
                if (listener != null) {
                    listener.onSkusFetched(allSkus);
                }
            }
        }.execute();
    }*/




}
