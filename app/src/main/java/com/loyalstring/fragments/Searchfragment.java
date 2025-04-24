package com.loyalstring.fragments;

import static com.loyalstring.MainActivity.binarySearch;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loyalstring.Adapters.SearchAdapter;
import com.loyalstring.MainActivity;
import com.loyalstring.R;
import com.loyalstring.database.StorageClass;
import com.loyalstring.databinding.FragmentSearchfragmentBinding;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.readersupport.KeyDwonFragment;
import com.loyalstring.tools.StringUtils;
import com.rscja.deviceapi.entity.UHFTAGInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Searchfragment extends KeyDwonFragment {

    FragmentSearchfragmentBinding b;

    MyApplication myApplication;
    HashMap<String, Itemmodel> totalitems = new HashMap<>();
    HashMap<String, Itemmodel> searchitems = new HashMap<>();

    MainActivity mainActivity;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UHFTAGInfo info = (UHFTAGInfo) msg.obj;
            Log.d("checktidva", "t" + info.getTid() + " e" + info.getEPC() + " r" + info.getReserved() + " " + info.getUser() + "  " + info.toString());
            addDataToList(info.getEPC(), info.getTid(), info.getRssi());
        }
    };

    List<String> tempDatas = new ArrayList<String>();
    StorageClass storageClass;

    boolean ploopFlag = false;
    Globalcomponents globalcomponents;

    SearchAdapter searchAdapter;
    private Handler mHandler = new Handler();
    ArrayList<String> searchlist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentSearchfragmentBinding.inflate(inflater, container, false);

        mainActivity = (MainActivity) getActivity();

//        globalcomponents = new Globalcomponents();
//        storageClass = new StorageClass(getActivity());

        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            // Update ActionBar properties
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Search");
            // actionBar.setHomeAsUpIndicator(R.drawable.your_custom_icon); // Set a custom icon
        }
        globalcomponents = new Globalcomponents();
        storageClass = new StorageClass(getActivity());
        myApplication = (MyApplication) requireActivity().getApplicationContext();
//         myApplication = new MyApplication();

        mainActivity.currentFragment = Searchfragment.this;
        if (!myApplication.isCountMatch()) {
            // Show progress dialog
            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Loading data ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Runnable onCountMatched = new Runnable() {
                @Override
                public void run() {
                    // Dismiss the progress dialog
                    progressDialog.dismiss();
                    totalitems = myApplication.getInventoryMap();

                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Check for count match
                    while (!myApplication.isCountMatch()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    requireActivity().runOnUiThread(onCountMatched);
                }
            }).start();
        } else {
            totalitems = myApplication.getInventoryMap();

        }

        b.singleimage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_scanblack));
        b.singletext.setText("Scan");

        b.searchrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new SearchAdapter(getActivity(), searchitems);
        b.searchrecycler.setAdapter(searchAdapter);


        if (getArguments() != null) {
            searchlist = getArguments().getStringArrayList("searchlist");
            for(String bar : searchlist){
                Itemmodel item = totalitems.get(bar);

                Itemmodel item1 = new Itemmodel(item);
                item1.setOperation("0");
                searchitems.put(item1.getTidValue(), item1);
                searchAdapter.notifyDataSetChanged();
            }
        }
        b.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().isEmpty()) {
                    if (!totalitems.isEmpty()) {
                        String sname = charSequence.toString();
                        for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
                            Itemmodel item = entry.getValue();
                            if (sname.equalsIgnoreCase(getvalue(item.getBarCode())) || sname.equalsIgnoreCase(getvalue(item.getItemCode()))) {
                                Log.d("check search ", "item " + item);
                                Itemmodel item1 = new Itemmodel(item);
                                item1.setOperation("0");
                                searchitems.clear();
                                searchitems.put(item.getTidValue(), item1);
                                searchAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    Toast.makeText(mainActivity, "no item found", Toast.LENGTH_SHORT).show();
                    searchAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        b.singlescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchitems.isEmpty()) {
                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mainActivity.mReader.isInventorying()) {
                    ploopFlag = false;
                    boolean s = stopscanner();
                    if (s) {
                        b.singletext.setText("Scan");
                        b.singleimage.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_scanblack));
//                        stopTemperatureCheck(getActivity());
                    } else {
                        Toast.makeText(mainActivity, "failed to stop scanning", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    stopUpdatingUI();
                    boolean checkpower = globalcomponents.checkpower(getActivity(), mainActivity.mReader, getpvalue(storageClass.getipower()), mainActivity.toolpower);
                    if (checkpower) {

                        performsinglescan();
                    } else {
                        Toast.makeText(mainActivity, "failed to set power", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b.singlereset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.searchbar.setText("");
                b.searchbar.clearFocus();
                searchitems.clear();
                tempDatas.clear();
                searchAdapter.notifyDataSetChanged();
            }
        });

        // Inflate the layout for this fragment
        return b.getRoot();
    }

    private int getpvalue(String power) {
        if (power == null || power.isEmpty() || power.matches("0")) {
            return 5;
        } else {
            return Integer.parseInt(power);
        }
    }

    private void performsinglescan() {
//        if(!mainActivity.mReader.setFastID(true)){
//            Toast.makeText(mainActivity, "failed to set mode", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(!mainActivity.mReader.setEPCMode()){
            Toast.makeText(myApplication, "failed to set mode", Toast.LENGTH_SHORT).show();
            return;
        }
        readTag();
    }


    private final Runnable updateUIRunnable1 = new Runnable() {
        @Override
        public void run() {

            searchAdapter.notifyDataSetChanged();
            if (ploopFlag) {
                mHandler.postDelayed(this, 500); // 50 milliseconds interval for 20 updates per second
            }

        }
    };

    private void startUpdatingUI() {
        mHandler.post(updateUIRunnable1);
    }

    // Method to stop updating UI
    private void stopUpdatingUI() {
        mHandler.removeCallbacks(updateUIRunnable1);
    }


    private void readTag() {
        if (mainActivity.mReader.startInventoryTag()) {
            ploopFlag = true;
            globalcomponents.keepScreenOn(true, mainActivity);
            startUpdatingUI();
            b.singleimage.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_cancelblack));
            b.singletext.setText("Stop");
            new TagThread().start();

        } else {
            if (stopscanner()) {

                b.singleimage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_scanblack));
                b.singletext.setText("Scan");

            }
        }
    }

    class TagThread extends Thread {
        public void run() {
            UHFTAGInfo uhftagInfo;
            Message msg;
            Log.d("product1", "check 1" + ploopFlag);
            while (ploopFlag) {
                uhftagInfo = mainActivity.mReader.readTagFromBuffer();
                if (uhftagInfo != null) {
                    msg = handler.obtainMessage();
                    msg.obj = uhftagInfo;
                    handler.sendMessage(msg);
                }
            }

        }
    }

    private void addDataToList(String fepc, String tidv, String rssi) {
        Log.d("check fastid ", "  " + fepc + " " + tidv + "  " + rssi);

        if (StringUtils.isNotEmpty(fepc) && fepc.length() == 24) {
            String epcValue =fepc;// fepc.substring(0, 24);
            // Extract TID value (last 24 digits)
            String tidValue =  fepc;//fepc.substring(fepc.length() - 24);
            if (StringUtils.isNotEmpty(tidValue)) {
                int index = -1;//checkIsExist(tidValue);
                int id = 0;
                if (index == -1) {
                    if (searchitems.containsKey(tidValue)) {
                        double rssi1 = Double.parseDouble(rssi);
                        double pr = Math.abs(rssi1);
                        if (pr < 50 && pr > 0) {
                            mainActivity.playSound(4);
                            id = 4;
                        } else if (pr > 50 && pr < 60) {
                            mainActivity.playSound(2);
                            id = 2;
                        } else if (pr > 60 && pr < 70) {
                            mainActivity.playSound(5);
                            id = 5;
                        } else if (pr > 70) {
                            mainActivity.playSound(1);
                            id = 1;
                        } else if (pr == 0) {

                        }
                        searchitems.get(tidValue).setOperation(String.valueOf(pr));
                        Log.d("check progress", "  " + pr);
                    }

                }
            }

        }
    }

    public int checkIsExist(String epc) {
        if (StringUtils.isEmpty(epc)) {
            return -1;
        }
        return binarySearch(tempDatas, epc);
    }

    private String getvalue(String barCode) {
        if (barCode == null || barCode.isEmpty()) {
            return "";
        }
        return barCode.trim();
    }

    private boolean stopscanner() {
        Log.d("removed", "handle");
        ploopFlag = false;
        stopUpdatingUI();
        globalcomponents.keepScreenOn(false, getActivity());
        if (mainActivity.mReader.isInventorying()) {

            return mainActivity.mReader.stopInventory();
        } else {
            return true;
        }
    }

    @Override
    public void myOnKeyDwon(String barcode) {
        b.singlescan.performClick();
//        super.myOnKeyDwon();
    }


}