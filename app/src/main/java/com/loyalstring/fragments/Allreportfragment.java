package com.loyalstring.fragments;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loyalstring.Activities.Skureport;
import com.loyalstring.Apis.ApiManager;
import com.loyalstring.MainActivity;
import com.loyalstring.apiresponse.AlllabelResponse;
import com.loyalstring.apiresponse.SkuResponse;
import com.loyalstring.database.Reader.MainData;

import com.loyalstring.R;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.databinding.FragmentAllreportfragmentBinding;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.interfaces.interfaces;
import com.loyalstring.modelclasses.Itemmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Allreportfragment extends Fragment {

    FragmentAllreportfragmentBinding b;

    List<SkuResponse> allsku = new ArrayList<>();
    EntryDatabase entryDatabase;
    Map<String, Itemmodel> alllsit = new HashMap<>();
    ApiManager apiManager;

    TreeMap<String, List<Itemmodel>> skureport = new TreeMap<>();
    HashMap<String, List<Itemmodel>> salereport = new HashMap<>();

    int tgwt = 0;
    int tswt = 0;
    int tnwt = 0;
    int tsamount = 0;
    MyApplication myApplication;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        b = FragmentAllreportfragmentBinding.inflate(getLayoutInflater(), container, false);


        entryDatabase = new EntryDatabase(getActivity());
        myApplication = new MyApplication();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.loyalstring.co.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Initialize ApiManager
        apiManager = new ApiManager(apiService);
        alllsit = entryDatabase.getBilledItems(getActivity());


        apiManager.fetchAllLabeledStock("LS000026", new interfaces.ApiCallback<List<AlllabelResponse.LabelItem>>() {
            @Override
            public void onSuccess(List<AlllabelResponse.LabelItem> result) {
                getActivity().runOnUiThread(() -> {
                    // Handle the Labeled Stock data, update UI
                    Toast.makeText(getActivity(), "loaded1", Toast.LENGTH_SHORT).show();

                    for (Itemmodel m1 : alllsit.values()) {
                        for (AlllabelResponse.LabelItem m : result) {
                            if (m.getItemCode().equals(m1.getItemCode())) {
                                m1.setSKUId(m.getsKUId());
                            }
                        }
                    }
                    for (Itemmodel m1 : alllsit.values()) {
                        String itemCode = m1.getItemCode();
                        // Check if this itemCode already exists in the TreeMap
                        if (!salereport.containsKey(itemCode)) {
                            // If the key doesn't exist, create a new list
                            salereport.put(itemCode, new ArrayList<>());
                        }
                        // Add the item to the list for this itemCode
                        salereport.get(itemCode).add(m1);
                    }


                    fetchallsku();
                });
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Failed to fetch Labeled Stock data", Toast.LENGTH_SHORT).show();
                });
            }
        });


        b.skubox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myApplication.setSkureport(skureport);

                Intent go = new Intent(getActivity(), Skureport.class);
                go.putExtra("type", "skureport");
                startActivity(go);
            }
        });

        b.salebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the TreeMap is not empty before proceeding
                if (salereport.isEmpty()) {
                    Toast.makeText(getActivity(), "Sale report is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // Set the salereport in MyApplication
//                    myApplication.setSalereport(salereport);
                    Log.e("checking send", " "+salereport.size());


                    // Proceed to the next activity
                    Intent go = new Intent(getActivity(), Skureport.class);
                    go.putExtra("type", "salereport");
                    go.putExtra("salereport", salereport);
                    startActivity(go);
                }

            }
        });



        return b.getRoot();
//        return inflater.inflate(R.layout.fragment_allreportfragment, container, false);
    }

    private void fetchallsku() {
        MainData.fetchAllsku(getActivity(), entryDatabase, new interfaces.OnSkusFetchedListener() {

            @Override
            public void onSkusFetched(List<SkuResponse> skus) {
                Log.e("checking skus", "check1  " + skus.size());
                allsku = skus;
                if (!allsku.isEmpty()) {
                    for (SkuResponse sku : allsku) {
                        // Iterate through the alllsit values and compare itemCodes
                        for (Itemmodel item : alllsit.values()) {
//                                        Log.e("emptysku ", "  "+sku.id1+"  "+item.getSKUId());
                            /*if (sku.id1 == item.getSKUId()) {
                                item.setStockKeepingUnit((sku.stockKeepingUnit));
                            }*/
                        }
                    }

                    for (Itemmodel i : alllsit.values()) {

                        if (i.getStockKeepingUnit() != null) {
                            skureport.putIfAbsent(i.getStockKeepingUnit(), new ArrayList<>());
                            skureport.get(i.getStockKeepingUnit()).add(i);
                        } else {

                        }
                    }
                    for (Map.Entry<String, List<Itemmodel>> entry : skureport.entrySet()) {
                        String key = entry.getKey();
                        List<Itemmodel> item = entry.getValue();
//                        Log.e("skulist  ", "sku name " + key + "  " + "No of items " + item.size());

                        for(Itemmodel i : item){
                            tgwt += i.getGrossWt();
                            tswt += i.getStoneWt();
                            tnwt += i.getNetWt();
                            tsamount += i.getStoneAmount();
                        }
                    }

                    b.totalg.setText(String.valueOf(tgwt));
                    b.totals.setText(String.valueOf(tswt));
                    b.totaln.setText(String.valueOf(tnwt));
                    b.totalsa.setText(String.valueOf(tsamount));

                }
            }
        });

    }
}