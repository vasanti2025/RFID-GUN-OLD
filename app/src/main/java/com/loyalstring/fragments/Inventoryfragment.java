package com.loyalstring.fragments;

import static com.loyalstring.MainActivity.Isearching;
import static com.loyalstring.MainActivity.binarySearch;
import static com.loyalstring.MainActivity.decimalFormat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.loyalstring.Adapters.InventoryBottomAdaptor;
import com.loyalstring.Adapters.InventoryTopAdaptor;
import com.loyalstring.Excels.InventoryExcelCreation;
import com.loyalstring.MainActivity;
import com.loyalstring.R;
import com.loyalstring.apiresponse.Rfidresponse;
import com.loyalstring.database.StorageClass;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.database.support.Valuesdb;
import com.loyalstring.databinding.FragmentInventoryfragmentBinding;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.SaveCallback;
import com.loyalstring.modelclasses.Issuemode;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.readersupport.KeyDwonFragment;
import com.loyalstring.tools.StringUtils;
import com.rscja.deviceapi.entity.UHFTAGInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inventoryfragment extends KeyDwonFragment implements InventoryTopAdaptor.Onclickitem {

    FragmentInventoryfragmentBinding b;
    MainActivity mainActivity;
    MyApplication myapp;

    HashMap<String, Itemmodel> totalitems = new HashMap<>();
    HashMap<String, Itemmodel> filtereditems = new HashMap<>();

    HashMap<String, Itemmodel> topmap = new HashMap<>();
    HashMap<String, Itemmodel> bottommap = new HashMap<>();

    /*ConcurrentHashMap<String, Itemmodel> topmap1 = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Itemmodel> bottommap1 = new ConcurrentHashMap<>();*/

    InventoryTopAdaptor inventoryTopAdaptor;
    InventoryBottomAdaptor inventoryBottomAdaptor;
    Globalcomponents globalcomponents;
    boolean ploopFlag = false;
    StorageClass storageClass;
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            UHFTAGInfo info = (UHFTAGInfo) msg.obj;
//            Log.d("checktidva", "t" + info.getTid() + " e" + info.getEPC() + " r" + info.getReserved() + " " + info.getUser() + "  " + info.toString());
//            addDataToList(info.getEPC(), info.getTid(), info.getRssi());
//        }
//    };

    ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            UHFTAGInfo info = (UHFTAGInfo) msg.obj;
            Log.d("checktidva", "t" + info.getTid() + " e" + info.getEPC() + " r" + info.getReserved() + " " + info.getUser() + "  " + info.toString());
            processTag(info);
            return true;
        }
    });


    List<String> tempDatas = new ArrayList<>();
    Set<String> tempDataSet = ConcurrentHashMap.newKeySet(); // For O(1) lookups
    HashMap<String, Itemmodel> topmatch = new HashMap<>();
    HashMap<String, Itemmodel> bottommatch = new HashMap<>();
    EntryDatabase entryDatabase;

    double tmqty = 0, tmgwt = 0, tmswt = 0, tmnwt = 0;
    private Handler mHandler = new Handler();

    List<Itemmodel> pauselist = new ArrayList<>();
    List<Issuemode> issueitem = new ArrayList<>();
    List<Rfidresponse.ItemModel> rfidList = new ArrayList<>();
    ProgressDialog maindialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentInventoryfragmentBinding.inflate(inflater, container, false);
        mainActivity = (MainActivity) getActivity();

        globalcomponents = new Globalcomponents();
        storageClass = new StorageClass(getActivity());

        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            // Update ActionBar properties
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Inventory");
            // actionBar.setHomeAsUpIndicator(R.drawable.your_custom_icon); // Set a custom icon
        }
        entryDatabase = new EntryDatabase(getActivity());

        Bundle args = getArguments();
        if (args != null) {
            pauselist = (List<Itemmodel>) args.getSerializable("searchlist");
        }

        mainActivity.currentFragment = Inventoryfragment.this;
        mainActivity.toolpower.setVisibility(View.VISIBLE);
        mainActivity.toolpower.setText(String.valueOf(mainActivity.mReader.getPower()));
        mainActivity.toolpower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalcomponents.changepowerg(getActivity(), "Inventory", storageClass, mainActivity.toolpower, mainActivity.mReader);
            }
        });
        topmap.clear();
        bottommap.clear();
        topmatch.clear();
        bottommatch.clear();
        totalitems.clear();
        filtereditems.clear();

        myapp = (MyApplication) requireActivity().getApplicationContext();
        b.irecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        b.catprorecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        inventoryTopAdaptor = new InventoryTopAdaptor(topmap, getActivity(), "all", b.irecycler, Inventoryfragment.this);
        b.irecycler.setAdapter(inventoryTopAdaptor);
        inventoryBottomAdaptor = new InventoryBottomAdaptor(bottommap, getActivity(), null, "all");
        b.catprorecycler.setAdapter(inventoryBottomAdaptor);

        maindialog = new ProgressDialog(getActivity());
        maindialog = new ProgressDialog(getActivity());
        maindialog.setMessage("loading...");


        if (!myapp.isCountMatch()) {
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
                    totalitems = myapp.getInventoryMap();


                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Check for count match
                    while (!myapp.isCountMatch()) {
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
            totalitems = myapp.getInventoryMap();

        }


        rfidList.addAll(entryDatabase.getrfid(getActivity(), myapp));


        b.singlescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topmap.isEmpty() || bottommap.isEmpty()) {
                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mainActivity.mReader.isInventorying()) {
                    ploopFlag = false;
                    boolean s = stopscanner();
                    if (s) {
                        b.gtext.setText("Scan");
                        b.gimage.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_scanblack));
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

        b.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> searchlist = new ArrayList<>();

                for (Map.Entry<String, Itemmodel> entry : bottommap.entrySet()) {
                    String key = entry.getKey();
                    Itemmodel item = entry.getValue();
                    if (item.getAvlQty() != item.getMatchQty()) {
                        searchlist.add(item.getTidValue());
                    }
                }
                if (searchlist.isEmpty()) {
                    Toast.makeText(mainActivity, "no items to search", Toast.LENGTH_SHORT).show();
                    return;
                }
                Isearching = true;
                Searchfragment h = new Searchfragment();

                Bundle args = new Bundle();
                args.putStringArrayList("searchlist", searchlist);
                h.setArguments(args);

                // Hide the InventoryFragment instead of replacing it
                requireActivity().getSupportFragmentManager().beginTransaction().hide(Inventoryfragment.this).commit();

                // Add the SearchFragment to the container
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.mainfragment, h)
                        .addToBackStack(null) // Add the transaction to the back stack
                        .commit();

            }
        });

        b.icatgorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlist("Category", b.icategorytext, getActivity());
            }
        });
       /* b.icounterlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlist("Counter", b.icategorytext, getActivity());
            }
        });*/

        b.iclearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauselist.clear();
                b.iclearlayout.setVisibility(View.GONE);
                resetstate();
            }
        });

        b.iproductlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlist("Product", b.iproducttext, getActivity());
            }
        });
        b.iboxlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getlist("Box", b.iboxtext, getActivity());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        b.nemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bottommap.isEmpty()) {
                    Toast.makeText(mainActivity, "no item to send email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (areStoragePermissionsGranted()) {
                    boolean folder = globalcomponents.checkfileexist("inventory");
                    if (folder) {
                        File topmatch = createfile("matcheditems");
                        File topunmatch = createfile("unmatcheditems");
                        File bottommatch = createfile("matcheditemdetails");
                        File botomunmatch = createfile("unmatcheditemdetails");
                        File topall = createfile("allitems");
                        File bottomall = createfile("allitemdetails");

                        if (topmatch != null && topunmatch != null && bottommatch != null && botomunmatch != null && topall != null && bottomall != null) {

                            ArrayList<Itemmodel> top = new ArrayList<>(topmap.values());
                            ArrayList<Itemmodel> bottom = new ArrayList<>(bottommap.values());
                            HashMap<String, ArrayList<Itemmodel>> excelmap = new HashMap<>();
                            excelmap.put(topmatch.getAbsolutePath(), top);
                            excelmap.put(topunmatch.getAbsolutePath(), top);
                            excelmap.put(bottommatch.getAbsolutePath(), bottom);
                            excelmap.put(botomunmatch.getAbsolutePath(), bottom);
                            excelmap.put(topall.getAbsolutePath(), top);
                            excelmap.put(bottomall.getAbsolutePath(), bottom);

                            InventoryExcelCreation excelTask = new InventoryExcelCreation(top, bottom, "allitem", "itemdetails", getActivity(), "inventory", "scan", excelmap, inventoryBottomAdaptor);
                            excelTask.execute();


                        } else {
                            Toast.makeText(getActivity(), "failed to create file", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ArrayList<String> folders = new ArrayList<>();
                        folders.add("inventory");
                        boolean f = globalcomponents.createFolders(folders);
                        if (!f) {
                            Toast.makeText(getActivity(), "failed to create file", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "created file please click again", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(mainActivity, "File read permission required please restart app", Toast.LENGTH_SHORT).show();
//                    requestStoragePermissions();
                }

            }
        });


        b.nlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottommap.isEmpty()) {
                    Toast.makeText(mainActivity, "no item to save", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (b.listbuttons.getVisibility() == View.VISIBLE) {
                    b.listbuttons.setVisibility(View.GONE);
                    topmatch.clear();
                    bottommatch.clear();
                    inventoryTopAdaptor = new InventoryTopAdaptor(topmap, getActivity(), "all", b.irecycler, Inventoryfragment.this);
                    b.irecycler.setAdapter(inventoryTopAdaptor);
                    inventoryBottomAdaptor = new InventoryBottomAdaptor(bottommap, getActivity(), null, "all");
                    b.catprorecycler.setAdapter(inventoryBottomAdaptor);
//                    inventorytopadapter = new inventorytopadapter(toplist, getActivity(), "all");
//                    inventorytopadapter.setViewType("all");
//                    toprecycler.setAdapter(inventorytopadapter);
                } else {

                    b.listbuttons.setVisibility(View.VISIBLE);

                    topmatch.clear();
                    bottommatch.clear();

                }
            }
        });


        b.matchedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topmatch.clear();
                bottommatch.clear();
                for (Map.Entry<String, Itemmodel> entry : topmap.entrySet()) {
                    String key = entry.getKey();
                    Itemmodel item = entry.getValue();
                    if (item.getAvlQty() == item.getMatchQty()) {
                        topmatch.put(key, item);
                    }
                }
                for (Map.Entry<String, Itemmodel> entry : bottommap.entrySet()) {
                    String key = entry.getKey();
                    Itemmodel item = entry.getValue();
                    if (item.getAvlQty() == item.getMatchQty()) {
                        bottommatch.put(key, item);
                    }
                }

                /*inventoryTopAdaptor.updateview("matched");
                inventoryBottomAdaptor.updateview("matched");*/
                inventoryTopAdaptor.updatedata(topmatch);
                inventoryBottomAdaptor.updatedata(bottommatch);
                inventoryBottomAdaptor.notifyDataSetChanged();
                inventoryTopAdaptor.notifyDataSetChanged();


                /*inventoryTopAdaptor = new InventoryTopAdaptor(topmatch, getActivity(), "all", b.irecycler);
                b.irecycler.setAdapter(inventoryTopAdaptor);
                inventoryBottomAdaptor = new InventoryBottomAdaptor(bottommatch, getActivity(), null, "all");
                b.catprorecycler.setAdapter(inventoryBottomAdaptor);*/
            }
        });
        b.unmatchedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topmatch.clear();
                bottommatch.clear();
                for (Map.Entry<String, Itemmodel> entry : topmap.entrySet()) {
                    String key = entry.getKey();
                    Itemmodel item = entry.getValue();
                    if (item.getAvlQty() != item.getMatchQty()) {
                        topmatch.put(key, item);
                    }
                }
                for (Map.Entry<String, Itemmodel> entry : bottommap.entrySet()) {
                    String key = entry.getKey();
                    Itemmodel item = entry.getValue();
                    if (item.getAvlQty() != item.getMatchQty()) {
                        bottommatch.put(key, item);
                    }
                }

                /*inventoryTopAdaptor.updateview("unmatched");
                inventoryBottomAdaptor.updateview("unmatched");*/
                inventoryTopAdaptor.updatedata(topmatch);
                inventoryBottomAdaptor.updatedata(bottommatch);
                inventoryBottomAdaptor.notifyDataSetChanged();
                inventoryTopAdaptor.notifyDataSetChanged();

//                inventoryTopAdaptor = new InventoryTopAdaptor(topmatch, getActivity(), "all", b.irecycler);
//                b.irecycler.setAdapter(inventoryTopAdaptor);
//                inventoryBottomAdaptor = new InventoryBottomAdaptor(bottommatch, getActivity(), null, "all");
//                b.catprorecycler.setAdapter(inventoryBottomAdaptor);

            }
        });

        b.unlabelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                topmatch.clear();
                bottommatch.clear();
//                for (Map.Entry<String, Itemmodel> entry : topmap.entrySet()) {
//                    String key = entry.getKey();
//                    Itemmodel item = entry.getValue();
//                    if (item.getAvlQty() == item.getMatchQty()) {
//                        topmatch.put(key, item);
//                    }
//                }
                List<String> missingTidValues = new ArrayList<>();
                for (String tidValue : tempDatas) {
                    if (!bottommap.containsKey(tidValue)) {
                        missingTidValues.add(tidValue);
                    }
                }

                Log.e("checking unlabelled ", "items" + missingTidValues.size());

                // Step 2: Find corresponding barcode from rfidList
                List<Itemmodel> newItems = new ArrayList<>();
                for (String tidValue : missingTidValues) {
                    for (Rfidresponse.ItemModel rfidItem : rfidList) {
                        if (tidValue.equalsIgnoreCase(rfidItem.getTid().trim())) {
                            Itemmodel item = new Itemmodel();
                            item.setTidValue(tidValue);
                            item.setBarCode(rfidItem.getBarcodeNumber().trim());
                            newItems.add(item);
                            bottommatch.put(tidValue, item);
                            break;
                        }
                    }
                }


//                inventoryTopAdaptor.updatedata(topmatch);
                inventoryBottomAdaptor.updatedata(bottommatch);
                inventoryBottomAdaptor.notifyDataSetChanged();
//                inventoryTopAdaptor.notifyDataSetChanged();


            }
        });

        b.transferbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Create a new AlertDialog Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Branch");

                // Inflate a custom layout that contains the spinner
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_spinner, null);
                builder.setView(dialogView);

                // Get reference to the spinner in the custom layout
                Spinner branchSpinner = dialogView.findViewById(R.id.branchSpinner);

                // Define the branches for the spinner
                String[] branches = {"Home", "Tray", "F1", "F2", "Exhibition"};

                // Create an ArrayAdapter for the spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, branches);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                branchSpinner.setAdapter(adapter);

                // Set up OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the selected branch
                        String selectedBranch = branchSpinner.getSelectedItem().toString();

                        // Show a progress dialog
                        ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Updating branch...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // Collect TidValues
                        ArrayList<String> tidvalues = new ArrayList<>();
                        for (Map.Entry<String, Itemmodel> entry : bottommap.entrySet()) {
                            Itemmodel item = entry.getValue();
                            if (item.getAvlQty() == item.getMatchQty()) {
                                tidvalues.add(item.getTidValue());
                            }
                        }

                        // Update database in a background thread
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Get writable database
                                EntryDatabase entryDatabase = new EntryDatabase(getActivity());
                                SQLiteDatabase db = entryDatabase.getWritableDatabase();
//                                SQLiteDatabase db = yourDatabaseHelper.getWritableDatabase();

                                // Update each TidValue
                                for (String tidValue : tidvalues) {
                                    ContentValues values = new ContentValues();
                                    values.put("Branch", selectedBranch);

                                    // Execute update
                                    int rowsAffected = db.update("alltable", values, "TidValue = ?", new String[]{tidValue});

                                    // Optional: log rows affected
                                    Log.d("Update", "Rows affected for TidValue " + tidValue + ": " + rowsAffected);
                                }

                                // Close the database
                                db.close();

                                // Dismiss the progress dialog on UI thread
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Branch updated successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        b.savelay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Itemmodel> ibottomlist = new ArrayList<>();
                ibottomlist.clear();
                ibottomlist.addAll(bottommap.values());
                if (mainActivity.mReader.isInventorying()) {
                    Toast.makeText(mainActivity, "stop scanning before save", Toast.LENGTH_SHORT).show();
                    return;
                }
                entryDatabase.makeentry(getActivity(), ibottomlist, "inventory", "inventory", myapp, issueitem, new SaveCallback() {
                    @Override
                    public void onSaveSuccess() {
                        Toast.makeText(mainActivity, "Inventory saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSaveFailure(List<Itemmodel> failedItems) {

                    }

                });

            }
        });


        b.nreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetstate();
            }
        });
        // Inflate the layout for this fragment
        return b.getRoot();
    }

//    private void checkpause(HashMap<String, Itemmodel> totalitems, List<Itemmodel> pauselist) {
//
//        for(Itemmodel i : pauselist){
//            Log.e("checking item", "  "+i.toString());
//        }
//
//
//
//    }

    private void resetstate() {
        b.icategorytext.setText("Category");
        b.iproducttext.setText("Product");
        b.iboxtext.setText("Box");

        tempDatas.clear();
        tempDataSet.clear();
        filtereditems.clear();
        topmap.clear();
        bottommap.clear();
        inventoryTopAdaptor.notifyDataSetChanged();
        inventoryBottomAdaptor.notifyDataSetChanged();

        b.ttqty.setText("TQty");
        b.tmqty.setText("MQty");
        b.ttgwt.setText("TGwt");
        b.tmgwt.setText("MGwt");
        b.ttnetwt.setText("TNwt");
        b.tmnetwt.setText("MNwt");
        tmqty = 0.0;
        tmgwt = 0.0;
        tmswt = 0.0;
        tmnwt = 0.0;


    }


    private boolean areStoragePermissionsGranted() {
        int readPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
    }

    private File createfile(String fname) {
        File file = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 and above
            try {
                file = File.createTempFile(fname, ".xlsx", getActivity().getFilesDir());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in internal storage: " + e.getMessage());
                return null;
            }
        } else { // Android versions below 10
            try {
                file = File.createTempFile(fname, ".xlsx", Environment.getExternalStorageDirectory());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in external storage: " + e.getMessage());
                return null;
            }
        }
    }

    private final Runnable updateUIRunnable1 = new Runnable() {
        @Override
        public void run() {
            // Update your UI here
            updateChangedItems();
            // Schedule the next update

            b.tmqty.setText(String.valueOf(tmqty));
            b.tmgwt.setText(String.valueOf(decimalFormat.format(tmgwt)));
            b.tmswt.setText(String.valueOf(decimalFormat.format(tmswt)));
            b.tmnetwt.setText(String.valueOf(decimalFormat.format(tmnwt)));
            String ttqtyText = b.ttqty.getText().toString();
            String tmqtyText = b.tmqty.getText().toString();


            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");

// Create a Matcher for the ttqtyText
            Matcher ttqtyMatcher = pattern.matcher(ttqtyText);
            boolean ttqtyIsNumeric = ttqtyMatcher.matches();

// Create a Matcher for the tmqtyText
            Matcher tmqtyMatcher = pattern.matcher(tmqtyText);
            boolean tmqtyIsNumeric = tmqtyMatcher.matches();


            Log.d("check values", " " + ttqtyIsNumeric + "  " + tmqtyIsNumeric + " " + ttqtyText + "  " + tmqtyText);
            if (ttqtyIsNumeric && tmqtyIsNumeric) {


                if (Double.parseDouble(ttqtyText) == Double.parseDouble(tmqtyText)) {
                    if (mainActivity.mReader.isInventorying()) {
                        Log.d("updateUIRunnable", "Stopping UI updates");
                        ploopFlag = false;
                        stopUpdatingUI();
                        b.singlescan.performClick();
                    }
                }
            }

            if (ploopFlag) {
                mHandler.postDelayed(this, 500); // 50 milliseconds interval for 20 updates per second
            }
//            Log.d("updateUIRunnable", "UI update callbacks removed: " + !mHandler.hasCallbacks(this));
        }
    };

    private void startUpdatingUI() {
        mHandler.post(updateUIRunnable1);
    }

    // Method to stop updating UI
    private void stopUpdatingUI() {
        mHandler.removeCallbacks(updateUIRunnable1);
    }

//    private Set<String> changedItemKeys = new HashSet<>();
//    private Set<String> changedItemKeys1 = new HashSet<>();

    private Set<String> changedItemKeys = ConcurrentHashMap.newKeySet();
    private Set<String> changedItemKeys1 = ConcurrentHashMap.newKeySet();

    private void updateChangedItems() {
        // Notify only the changed items
        for (String key : changedItemKeys) {
            if (bottommatch.isEmpty()) {
                int position = getPositionInList(bottommap, key);
                if (position != -1) {
                    inventoryBottomAdaptor.notifyItemChanged(position);
                }
            } else {
                int position = getPositionInList(bottommatch, key);
                if (position != -1) {
                    inventoryBottomAdaptor.notifyItemChanged(position);
                }
            }

        }
        changedItemKeys.clear(); // Clear the set after notifying changes
        for (String key : changedItemKeys1) {
            if (topmatch.isEmpty()) {
                int tposition = getPositionInList1(topmap, key);
                if (tposition != -1) {
                    // Notify the adapter about the position change
                    inventoryTopAdaptor.notifyItemChanged(tposition);
                }
            } else {
                int tposition = getPositionInList1(topmatch, key);
                if (tposition != -1) {
                    // Notify the adapter about the position change
                    inventoryTopAdaptor.notifyItemChanged(tposition);
                }

            }
        }
        changedItemKeys1.clear();


    }

    double mqty = 0;


    private void processTag(UHFTAGInfo info) {
        executorService.submit(() -> {
            String fepc = info.getEPC();
            String tidv = info.getEPC();
            String rssi = info.getRssi();


            if (StringUtils.isNotEmpty(fepc)) {
                String trimmedTidValue = trimTid(fepc);
                if (StringUtils.isNotEmpty(trimmedTidValue) && checkIsExist1(trimmedTidValue) == -1 && bottommap.containsKey(trimmedTidValue)) {
//                    String simulatedKey = getRandomKeyFromMap(bottommap);
//                    updateMaps(simulatedKey);
                    updateMaps(trimmedTidValue);
                    tempDataSet.add(trimmedTidValue); // Add to set for O(1) lookup
                }
            }
        });
    }

    private int checkIsExist1(String epc) {
        return tempDataSet.contains(epc) ? 1 : -1; // Using HashSet for O(1) lookups
    }

    private String getRandomKeyFromMap(Map<String, Itemmodel> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        return keys.isEmpty() ? null : keys.get(new Random().nextInt(keys.size()));
    }

    private String trimTid(String epc) {
        String trimmedTidValue = epc;
        if (epc.startsWith("0000")) {
            trimmedTidValue = epc.substring(2); // Remove "00"
        }
        if (trimmedTidValue.endsWith("0000")) {
            trimmedTidValue = trimmedTidValue.substring(0, trimmedTidValue.length() - 2);
        }
        return trimmedTidValue;
    }

    private void updateMaps(String tidValue) {
        // Logic to update your topMap and bottomMap
        if (bottommap.containsKey(tidValue)) {
            bottommap.get(tidValue).setMatchQty(1);
            String key = bottommap.get(tidValue).getCategory() + "|" + bottommap.get(tidValue).getProduct();
            Log.d("check fastid ", "  " + key);
            if (topmap.containsKey(key)) {
                topmap.get(key).setMatchQty(topmap.get(key).getMatchQty() + 1);
                topmap.get(key).setMatchGwt(topmap.get(key).getMatchGwt() + bottommap.get(tidValue).getGrossWt());
                topmap.get(key).setMatchNwt(topmap.get(key).getMatchNwt() + bottommap.get(tidValue).getNetWt());
                topmap.get(key).setMatchStonewt(topmap.get(key).getMatchStonewt() + bottommap.get(tidValue).getStoneWt());
                topmap.get(key).setTotMPcs(Integer.parseInt(topmap.get(key).getTotMPcs() + bottommap.get(tidValue).getPcs()));

                changedItemKeys.add(tidValue);
                changedItemKeys1.add(key);
                tmqty = tmqty + 1;
                tmgwt = tmgwt + bottommap.get(tidValue).getGrossWt();
                tmswt = tmswt + bottommap.get(tidValue).getStoneWt();
                tmnwt = tmnwt + bottommap.get(tidValue).getNetWt();
            }
        }
    }


    private void addDataToList(String fepc, String tidv, String rssi) {
        Log.d("check fastid ", "  " + fepc + " " + tidv);

//        if (StringUtils.isNotEmpty(fepc) && fepc.length() == 24) {
        if (StringUtils.isNotEmpty(fepc)) {

//            Random random = new Random();
//            int randomNumber = random.nextInt(14369);
            /*String epcValue = fepc.substring(0, 24);
            // Extract TID value (last 24 digits)
            String tidValue = fepc.substring(fepc.length() - 24);*/

            String trimmedTidValue = fepc;
            if (fepc.startsWith("00")) {
                // Compare ignoring the first two characters
                trimmedTidValue = fepc.substring(2); // Remove "00"
            }
            if (trimmedTidValue.endsWith("00")) {
                // Remove the last two characters ("00")
                trimmedTidValue = trimmedTidValue.substring(0, trimmedTidValue.length() - 2);
            }

            String epcValue = trimmedTidValue;
            // Extract TID value (last 24 digits)
            String tidValue = trimmedTidValue;
            if (StringUtils.isNotEmpty(tidValue)) {
                int index = checkIsExist(tidValue);
                if (index == -1) {


                    /*if (bottommap1.containsKey(tidValue)) {
                        Itemmodel bottomItem = bottommap1.get(tidValue);
                        bottomItem.setMatchQty(1);
                        String key = bottomItem.getCategory() + "|" + bottomItem.getProduct();

                        topmap1.compute(key, (k, topItem) -> {
                            if (topItem == null) {
                                topItem = new Itemmodel(); // Assuming a default constructor
                            }
                            topItem.setMatchQty(topItem.getMatchQty() + 1);
                            topItem.setMatchGwt(topItem.getMatchGwt() + bottomItem.getGrossWt());
                            return topItem;
                        });

                        changedItemKeys.add(tidValue);
                        changedItemKeys1.add(key);
                        tmqty++;
                        tmgwt += bottomItem.getGrossWt();
                        tmswt += bottomItem.getStoneWt();
                        tmnwt += bottomItem.getNetWt();
                    }*/

                    if (bottommap.containsKey(tidValue)) {
                        bottommap.get(tidValue).setMatchQty(1);
                        String key = bottommap.get(tidValue).getCategory() + "|" + bottommap.get(tidValue).getProduct();
                        if (topmap.containsKey(key)) {
                            topmap.get(key).setMatchQty(topmap.get(key).getMatchQty() + 1);
                            topmap.get(key).setMatchGwt(topmap.get(key).getMatchGwt() + bottommap.get(tidValue).getGrossWt());
                            topmap.get(key).setMatchNwt(topmap.get(key).getMatchNwt() + bottommap.get(tidValue).getNetWt());
                            topmap.get(key).setMatchStonewt(topmap.get(key).getMatchStonewt() + bottommap.get(tidValue).getStoneWt());
                            topmap.get(key).setTotMPcs(Integer.parseInt(topmap.get(key).getTotMPcs() + bottommap.get(tidValue).getPcs()));

                        }

                        changedItemKeys.add(tidValue);
                        changedItemKeys1.add(key);
                        tmqty = tmqty + 1;
                        tmgwt = tmgwt + bottommap.get(tidValue).getGrossWt();
                        tmswt = tmswt + bottommap.get(tidValue).getStoneWt();
                        tmnwt = tmnwt + bottommap.get(tidValue).getNetWt();

                    }
                }
                tempDatas.add(tidValue);

            }
        }
    }

    private int getPositionInList(Map<String, Itemmodel> list, String tidv) {
        int position = -1;
        int index = 0;
        for (Map.Entry<String, Itemmodel> entry : list.entrySet()) {
            if (entry.getKey().equals(tidv)) {
                position = index;
                break;
            }
            index++;
        }
        return position;
    }

    private int getPositionInList1(Map<String, Itemmodel> list, String tidv) {
        int position = -1;
        int index = 0;
        for (Map.Entry<String, Itemmodel> entry : list.entrySet()) {
            if (entry.getKey().equals(tidv)) {
                position = index;
                break;
            }
            index++;
        }
        return position;
    }

    public int checkIsExist(String epc) {
        if (StringUtils.isEmpty(epc)) {
            return -1;
        }
        return binarySearch(tempDatas, epc);
    }

    private void performsinglescan() {
        /*if(!mainActivity.mReader.setFastID(true)){
            Toast.makeText(mainActivity, "failed to set mode", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (!mainActivity.mReader.setEPCMode()) {
            Toast.makeText(mainActivity, "failed to set mode", Toast.LENGTH_SHORT).show();
            return;
        }
        readTag();
    }

    private void readTag() {
        if (mainActivity.mReader.startInventoryTag()) {
            ploopFlag = true;
            globalcomponents.keepScreenOn(true, mainActivity);
            startUpdatingUI();
            b.gimage.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_cancelblack));
            b.gtext.setText("Stop");

            /*topmap1.putAll(topmap);
            bottommap1.putAll(bottommap);*/


            new TagThread().start();
        } else {
            if (stopscanner()) {

                b.gimage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_scanblack));
                b.gtext.setText("Gscan");

            }
        }
    }

    @Override
    public void onclickitem(String category, String product, Itemmodel item1) {

        bottommatch.clear();

        for (Map.Entry<String, Itemmodel> entry : bottommap.entrySet()) {
            String key = entry.getKey();
            Itemmodel item = entry.getValue();
            if (item.getProduct().matches(item1.getProduct())) {
                bottommatch.put(key, item);
            }
        }


        inventoryBottomAdaptor.updatedata(bottommatch);
        inventoryBottomAdaptor.notifyDataSetChanged();

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
                    mainActivity.playSound(1);
                }
            }

        }
    }

    private int getpvalue(String power) {
        if (power == null || power.isEmpty() || power.matches("0")) {
            return 5;
        } else {
            return Integer.parseInt(power);
        }
    }

    private boolean stopscanner() {
        Log.d("removed", "handle");
        ploopFlag = false;
        stopUpdatingUI();
        try {
            globalcomponents.keepScreenOn(false, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mainActivity.mReader.isInventorying()) {

            return mainActivity.mReader.stopInventory();
        } else {
            return true;
        }
    }


    public void getlist(String title, TextView t, FragmentActivity activity) {
        List<String> bottomlist = new ArrayList<>();
        Valuesdb db = new Valuesdb(activity);

        if (title.equalsIgnoreCase("")) {
            for (Itemmodel m : topmap.values()) {
                bottomlist.add(m.getBox());
            }
        }
        if (title.equalsIgnoreCase("category")) {
            bottomlist = db.getcatpro();
        }
        if (title.equalsIgnoreCase("product")) {
            for (Itemmodel m : topmap.values()) {
                bottomlist.add(m.getProduct());
            }
        }
        try {
            if (title.equalsIgnoreCase("box")) {
                for (Itemmodel m : topmap.values()) {
                    bottomlist.add(m.getBox());
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if (bottomlist.isEmpty()) {
            Toast.makeText(activity, "no data found", Toast.LENGTH_SHORT).show();
//            globaltoast(activity, "no data found", "", "");
            return;
        }

        showbottom(activity, title, t, bottomlist);

    }


    public void showbottom(FragmentActivity activity, String title, TextView t, List<String> bottomlist) {
     try {
         BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
         bottomSheetDialog.setCancelable(false);
         View contentView = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_layout1, null);
         bottomSheetDialog.setContentView(contentView);

         ImageButton close = contentView.findViewById(R.id.closeButton);
         TextView ttitle = contentView.findViewById(R.id.maintitle);
         Button applyButton = contentView.findViewById(R.id.apply_button);
         ListView spinnerlist = contentView.findViewById(R.id.spinnerlist);
         EditText searchBar = contentView.findViewById(R.id.search_bar); // Search bar

         ttitle.setText(title);

         // Store the original list
         List<String> originalList = new ArrayList<>();

         for (Object obj : bottomlist) {
             if (obj != null) {
                 originalList.add(obj.toString()); // safe to convert now
             }
         }
      //   final List<String> originalList = new ArrayList<>(bottomlist);
         Log.d("BottomSheet", "Original List: " + originalList);

         final ArrayAdapter<String>[] adapter = new ArrayAdapter[]{new ArrayAdapter<>(activity, android.R.layout.simple_list_item_multiple_choice, originalList)};
         spinnerlist.setAdapter(adapter[0]);
         spinnerlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

         SparseBooleanArray checkedItems = new SparseBooleanArray(originalList.size());

// Listen for item checks and update checkedItems accordingly
         spinnerlist.setOnItemClickListener((parent, view, position, id) -> {
             String selectedItem = adapter[0].getItem(position);
             int originalPosition = originalList.indexOf(selectedItem);

             if (originalPosition != -1) {
                 boolean currentState = checkedItems.get(originalPosition, false);
                 checkedItems.put(originalPosition, !currentState);
                 spinnerlist.setItemChecked(position, !currentState);
                 Log.d("BottomSheet", "Checked state updated for " + selectedItem + ": " + !currentState);
             }
         });

         searchBar.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 Log.d("BottomSheet", "Search input: " + charSequence);

                 if (charSequence.length() == 0) {
                     // Reset adapter with the original list
                     ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, originalList);
                     spinnerlist.setAdapter(newAdapter);
                     adapter[0] = newAdapter;
                     adapter[0].notifyDataSetChanged();
                     Log.d("BottomSheet", "Adapter reset to original list. Item count: " + adapter[0].getCount());

                     // Restore checked states
                     for (int j = 0; j < originalList.size(); j++) {
                         spinnerlist.setItemChecked(j, checkedItems.get(j));
                         Log.d("BottomSheet", "Item " + j + " (" + originalList.get(j) + ") checked state: " + checkedItems.get(j));
                     }
                 } else {
                     // Filter adapter and apply checked states for filtered items
                     adapter[0].getFilter().filter(charSequence);
                     adapter[0].notifyDataSetChanged();
                     Log.d("BottomSheet", "Filtered adapter count after search: " + adapter[0].getCount());

                     // Apply checked states based on visible items in the filtered list
                     for (int j = 0; j < adapter[0].getCount(); j++) {
                         String item = adapter[0].getItem(j);
                         int originalPosition = originalList.indexOf(item);
                         if (originalPosition != -1) {
                             spinnerlist.setItemChecked(j, checkedItems.get(originalPosition));
                         }
                     }
                 }
             }

             @Override
             public void afterTextChanged(Editable editable) {
             }
         });

         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 bottomSheetDialog.dismiss();
             }
         });

         applyButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // Collect selected items
                 List<String> selectedItems = new ArrayList<>();
                 for (int i = 0; i < originalList.size(); i++) {
                     if (checkedItems.get(i)) {
                         selectedItems.add(originalList.get(i));
                     }
                 }

                 Log.d("BottomSheet", "Selected items: " + selectedItems);

                 if (selectedItems.isEmpty()) {
                     Toast.makeText(activity, "No items selected", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 // Update the TextView with selected items
                 t.setText(TextUtils.join(", ", selectedItems));

                 // Apply filtering based on the selected items
                 applyFilter(activity, title, selectedItems);

                 bottomSheetDialog.dismiss();
             }
         });

         bottomSheetDialog.show();
     }catch (Exception e)
     {
         e.printStackTrace();
     }
    }


    public void showbottom2(FragmentActivity activity, String title, TextView t, List<String> bottomlist) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setCancelable(false);
        View contentView = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_layout1, null);
        bottomSheetDialog.setContentView(contentView);

        ImageButton close = contentView.findViewById(R.id.closeButton);
        TextView ttitle = contentView.findViewById(R.id.maintitle);
        Button applyButton = contentView.findViewById(R.id.apply_button);
        ListView spinnerlist = contentView.findViewById(R.id.spinnerlist);
        EditText searchBar = contentView.findViewById(R.id.search_bar); // Search bar

        ttitle.setText(title);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_multiple_choice, bottomlist);
//        spinnerlist.setAdapter(adapter);
//        spinnerlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final List<String> originalList = Collections.unmodifiableList(new ArrayList<>(bottomlist));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_multiple_choice, new ArrayList<>(originalList));
        spinnerlist.setAdapter(adapter);
        spinnerlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Track selected items
        SparseBooleanArray checkedItems = new SparseBooleanArray();

        spinnerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Toggle selection
                boolean checked = spinnerlist.isItemChecked(position);
                checkedItems.put(position, checked);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                adapter.getFilter().filter(charSequence);

                if (charSequence.length() == 0) {
                    // If the search bar is empty, show the full list again
                    adapter.clear();
                    adapter.addAll(originalList);
                } else {
                    // Filter the list based on user input
                    adapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect selected items
//                List<String> selectedItems = new ArrayList<>();
//                for (int i = 0; i < bottomlist.size(); i++) {
//                    if (checkedItems.get(i)) {
//                        selectedItems.add(bottomlist.get(i));
//                    }
//                }

                List<String> selectedItems = new ArrayList<>();
                for (int i = 0; i < originalList.size(); i++) {
                    if (checkedItems.get(i)) {
                        selectedItems.add(originalList.get(i));
                    }
                }

                if (selectedItems.isEmpty()) {
                    Toast.makeText(activity, "No items selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the TextView with selected items (you can change this logic as needed)
                t.setText(TextUtils.join(", ", selectedItems));

                // Apply filtering based on the selected items
                applyFilter(activity, title, selectedItems);

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }


    public void applyFilter(FragmentActivity activity, String title, List<String> selectedItems) {
        // Clear the filtered items before applying a new filter



        if (title.toLowerCase(Locale.ROOT).equalsIgnoreCase("category")) {
            filtereditems.clear();
            tempDatas.clear();
            tempDataSet.clear();
            b.iproducttext.setText("Product");

            String currentBranch = storageClass.getBranch(); // Get the current branch

            if (currentBranch.toLowerCase(Locale.ROOT).contains("home")) {
                // If branch is 'home', apply the filter without checking branch
                for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
                    Itemmodel item = entry.getValue();
                    Log.e("bottomsheet", "check 1"+item.getBranch());
                    // Apply case-insensitive check for the category
                    if (selectedItems.stream().anyMatch(selectedItem -> selectedItem.toLowerCase(Locale.ROOT).equalsIgnoreCase(item.getCategory().toLowerCase(Locale.ROOT)))) {
                        Itemmodel item1 = new Itemmodel(item);
                        filtereditems.put(item1.getTidValue(), item1);
                    }
                }
            } else {
                // If branch is not 'home', apply the filter and also check the branch
                for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
                    Itemmodel item = entry.getValue();
                    // Apply case-insensitive checks for both category and branch
                    if (selectedItems.stream().anyMatch(selectedItem -> selectedItem.toLowerCase(Locale.ROOT).equalsIgnoreCase(item.getCategory().toLowerCase(Locale.ROOT))) &&
                            item.getBranch().toLowerCase(Locale.ROOT).equalsIgnoreCase(currentBranch.toLowerCase(Locale.ROOT))) {
                        Itemmodel item1 = new Itemmodel(item);
                        filtereditems.put(item1.getTidValue(), item1);
                    }
                }
            }

            readitems(activity, "category", "all");
            maindialog.dismiss();
        }

        if (title.equalsIgnoreCase("Product")) {
            Iterator<Map.Entry<String, Itemmodel>> iterator = filtereditems.entrySet().iterator();
            Log.e("check selected ", " " + selectedItems.toString() + "  " + filtereditems.size() + "  ");
            while (iterator.hasNext()) {
                Map.Entry<String, Itemmodel> entry = iterator.next();
                Itemmodel item = entry.getValue();
                // Apply case-insensitive check for the product

                if (selectedItems.stream().noneMatch(selectedItem -> selectedItem.equalsIgnoreCase(item.getProduct()))) {
                    iterator.remove();
                }
            }
            readitems1(activity, "product", "all");
            maindialog.dismiss();
        }

        if (title.equalsIgnoreCase("box")) {
            Iterator<Map.Entry<String, Itemmodel>> iterator = filtereditems.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Itemmodel> entry = iterator.next();
                Itemmodel item = entry.getValue();
                // Apply case-insensitive check for the box
                if (selectedItems.stream().noneMatch(selectedItem -> selectedItem.equalsIgnoreCase(item.getBox()))) {
                    iterator.remove();
                }
            }
            readitems1(activity, "box", "all");
            maindialog.dismiss();
        }
    }


    public void showbottomold(FragmentActivity activity, String title, TextView t, List<String> bottomlist) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCancelable(false);
        View contentView = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(contentView);
        ImageButton close = contentView.findViewById(R.id.closeButton);
        TextView ttitle = contentView.findViewById(R.id.maintitle);
        Button addbtn = contentView.findViewById(R.id.additem);
        EditText itemname = contentView.findViewById(R.id.itemname);
        ListView spinnerlist = contentView.findViewById(R.id.spinnerlist);
        TextView purehint = contentView.findViewById(R.id.purehint);
        LinearLayout beholder = contentView.findViewById(R.id.beholder);
        addbtn.setVisibility(View.GONE);
        itemname.setVisibility(View.GONE);
        beholder.setVisibility(View.GONE);
        ttitle.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, bottomlist);
        spinnerlist.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        Log.d("idata", "title " + title);
        spinnerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selecteditem = (String) adapterView.getItemAtPosition(i);
                t.setText(selecteditem);

                bottomSheetDialog.dismiss();
//                finalInventoryList.clear();

                maindialog.show();

                if (title.equalsIgnoreCase("category")) {
                    filtereditems.clear();
                    tempDatas.clear();
                    tempDataSet.clear();
                    b.iproducttext.setText("Product");

                    if (storageClass.getBranch().equalsIgnoreCase("home")) {
                        for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
                            Itemmodel item = entry.getValue();
                            if (item.getCategory().equalsIgnoreCase(selecteditem)) {
                                Itemmodel item1 = new Itemmodel(item);
//                            if(item1.getBranch()!=null)
//                                Log.d("check reset", "  " + item1.toString());
                                filtereditems.put(item1.getTidValue(), item1);
                            }
                        }
                    } else {
                        for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
                            Itemmodel item = entry.getValue();
                            if (item.getCategory().equalsIgnoreCase(selecteditem) && item.getBranch().equalsIgnoreCase(storageClass.getBranch())) {
                                Itemmodel item1 = new Itemmodel(item);
//                            if(item1.getBranch()!=null)
                                filtereditems.put(item.getTidValue(), item1);
                            }
                        }
                    }

                    readitems(activity, selecteditem, "all");
                    maindialog.dismiss();
                }
                if (title.equalsIgnoreCase("Product")) {
//                    iboxtext.setText("Box");

                    Log.d("Idata", "postall  " + filtereditems.size());
                    Iterator<Map.Entry<String, Itemmodel>> iterator = filtereditems.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Itemmodel> entry = iterator.next();
                        Itemmodel item = entry.getValue();
                        Log.d("Idata", "pre  " + item.getProduct() + "  " + selecteditem + "    " + filtereditems.size());
                        if (!item.getProduct().equalsIgnoreCase(selecteditem)) {
                            iterator.remove();
                        }
                        Log.d("Idata", "post  " + item.getProduct() + "  " + selecteditem + "    " + filtereditems.size());
                    }
                    readitems1(activity, selecteditem, "all");
                    maindialog.dismiss();

                   /* bottommap.clear();
                    bottommap.putAll(filtereditems);
                    inventoryTopAdaptor.notifyDataSetChanged();
                    inventoryBottomAdaptor.notifyDataSetChanged();*/
                }

                if (title.equalsIgnoreCase("box")) {
                    Iterator<Map.Entry<String, Itemmodel>> iterator = filtereditems.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Itemmodel> entry = iterator.next();
                        Itemmodel item = entry.getValue();
                        if (!item.getBox().equalsIgnoreCase(selecteditem)) {
                            iterator.remove();
                        }
                    }
                    readitems1(activity, selecteditem, "all");

                    /*bottommap.clear();
                    bottommap.putAll(filtereditems);
                    inventoryTopAdaptor.notifyDataSetChanged();
                    inventoryBottomAdaptor.notifyDataSetChanged();*/
                }


//                inventorylist = readitems(activity, inventoryfragment1);
            }
        });
        bottomSheetDialog.show();

    }

    private void readitems(FragmentActivity activity, String selecteditem, String str) {
        topmap.clear();

        Map<String, Itemmodel> aggregatedItems = new HashMap<>();
        if (!pauselist.isEmpty()) {
            b.iclearlayout.setVisibility(View.VISIBLE);
        } else {
            b.iclearlayout.setVisibility(View.GONE);
        }

        double totalqty = 0, totalgwt = 0, totalswt = 0, totalnwt = 0, totalPc = 0;
        double totalmqty = 0, totalmgwt = 0, totalmswt = 0, totalmnwt = 0, totalMapcs = 0;
        boolean isbranch = false;

        for (Itemmodel it : pauselist) {
            Log.e("checking items", "check1  " + it.toString());
        }

        for (Map.Entry<String, Itemmodel> entry : filtereditems.entrySet()) {
            Itemmodel item = entry.getValue();
            // Create a key using category and product
//            if (storageClass.getBranch().equalsIgnoreCase("home")) {
            String key = item.getCategory() + "|" + item.getProduct();

            // Check if the item is already aggregated
            if (aggregatedItems.containsKey(key)) {
                // If item already exists, update its values
                Itemmodel aggregatedItem = aggregatedItems.get(key);
                // Update count
                aggregatedItem.setAvlQty(aggregatedItem.getAvlQty() + 1);
                // Update weights
                aggregatedItem.setTotalGwt(aggregatedItem.getTotalGwt() + item.getGrossWt());
                totalqty = totalqty + 1;
                totalgwt = totalgwt + item.getGrossWt();
                totalswt = totalswt + item.getStoneWt();
                totalnwt = totalnwt + item.getNetWt();
                totalPc = Double.parseDouble(totalPc + item.getPcs());


                for (Itemmodel it : pauselist) {
                    if (it.getTidValue().equals(item.getTidValue())) {

                        aggregatedItem.setMatchQty(aggregatedItem.getMatchQty() + it.getMatchQty());

                        item.setMatchQty(it.getMatchQty());
                        if (it.getMatchQty() > 0) {
                            tempDatas.add(it.getTidValue());
                            totalmqty = totalmqty + 1;
                            totalmgwt = totalmgwt + it.getGrossWt();
                            totalmswt = totalmswt + it.getStoneWt();
                            totalmnwt = totalmnwt + it.getNetWt();
                        }
                    }
                }

            } else {
                // If item doesn't exist, add it to the map
                // Make a copy of the item and set count to 1
                Itemmodel newItem = new Itemmodel();
                newItem.setProduct(item.getProduct());
                newItem.setBox(item.getBox());
                newItem.setAvlQty(1);
                newItem.setTotalGwt(item.getGrossWt());
                newItem.setTotalStonewt(item.getStoneWt());
                newItem.setTotalNwt(item.getNetWt());

                String pcsStr = item.getPcs();
                if (pcsStr != null && !pcsStr.trim().isEmpty()) {
                    try {
                        newItem.setTotPcs(Integer.parseInt(pcsStr.trim()));
                    } catch (NumberFormatException e) {
                        // handle error, or set default
                        newItem.setTotPcs(0);  // or log the error
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(item);
                Log.d("@@", "item.getPcs()" + json);
                // Add to aggregatedItems map
                totalqty = totalqty + 1;
                totalgwt = totalgwt + item.getGrossWt();
                totalswt = totalswt + item.getStoneWt();
                totalnwt = totalnwt + item.getNetWt();
                try {
                    if (item.getPcs() != null) {
                        totalPc = Double.parseDouble(totalPc + item.getPcs());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Itemmodel it : pauselist) {
                    if (it.getTidValue().equals(item.getTidValue())) {
                        newItem.setMatchQty(it.getMatchQty());
                        item.setMatchQty(it.getMatchQty());
                        if (it.getMatchQty() > 0) {
                            tempDatas.add(it.getTidValue());
                            totalmqty = totalmqty + 1;
                            totalmgwt = totalmgwt + it.getGrossWt();
                            totalmswt = totalmswt + it.getStoneWt();
                            totalmnwt = totalmnwt + it.getNetWt();
                            totalPc = Double.parseDouble(totalPc + item.getPcs());
                        }
                    }
                }
                aggregatedItems.put(key, newItem);
            }

        }


        topmap.putAll(aggregatedItems);
        bottommap.clear();
        bottommap.putAll(filtereditems);
        b.ttqty.setText(String.valueOf(totalqty));
        b.ttgwt.setText(String.valueOf(decimalFormat.format(totalgwt)));
        b.ttswt.setText(String.valueOf(decimalFormat.format(totalswt)));
        b.ttnetwt.setText(String.valueOf(decimalFormat.format(totalnwt)));
        b.tmqty.setText(String.valueOf(totalmqty));
        b.tmgwt.setText(decimalFormat.format(totalmgwt));
        b.tmswt.setText(decimalFormat.format(totalmswt));
        b.tmnetwt.setText(decimalFormat.format(totalmnwt));
        tmqty = totalmqty;
        tmgwt = totalmgwt;
        tmnwt = totalmnwt;
        tmswt = totalmswt;
        inventoryTopAdaptor.notifyDataSetChanged();
        inventoryBottomAdaptor.notifyDataSetChanged();

        Log.d("checktoplist", "  " + bottommap.size());


    }

    private void readitems1(FragmentActivity activity, String selecteditem, String all) {
        topmap.clear();

        Map<String, Itemmodel> aggregatedItems = new HashMap<>();

        double totalqty = 0, totalgwt = 0, totalswt = 0, totalnwt = 0, totalPc = 0;
        boolean isbranch = false;


        for (Map.Entry<String, Itemmodel> entry : filtereditems.entrySet()) {
            Itemmodel item = entry.getValue();
            // Create a key using category and product
//            if (storageClass.getBranch().equalsIgnoreCase("home")) {
            String key = item.getCategory() + "|" + item.getProduct();

            // Check if the item is already aggregated
            if (aggregatedItems.containsKey(key)) {
                // If item already exists, update its values
                Itemmodel aggregatedItem = aggregatedItems.get(key);
                // Update count
                aggregatedItem.setAvlQty(aggregatedItem.getAvlQty() + 1);
                aggregatedItem.setMatchQty(aggregatedItem.getMatchQty() + item.getMatchQty());
                // Update weights
                aggregatedItem.setTotalGwt(aggregatedItem.getTotalGwt() + item.getGrossWt());
                totalqty = totalqty + 1;
                totalgwt = totalgwt + item.getGrossWt();
                totalswt = totalswt + item.getStoneWt();
                totalnwt = totalnwt + item.getNetWt();
                totalPc = Double.parseDouble(totalPc + item.getPcs());
            } else {
                // If item doesn't exist, add it to the map
                // Make a copy of the item and set count to 1
                Itemmodel newItem = new Itemmodel();
                newItem.setProduct(item.getProduct());
                newItem.setBox(item.getBox());
                newItem.setAvlQty(1);
                newItem.setMatchQty(item.getMatchQty());
                newItem.setTotalGwt(item.getGrossWt());
                newItem.setTotalStonewt(item.getStoneWt());
                newItem.setTotalNwt(item.getNetWt());
                newItem.setTotPcs(item.getTotPcs());

                Gson gson = new Gson();
                String json = gson.toJson(item);
                Log.d("@@", "item.getPcs()" + json);

                // Add to aggregatedItems map
                totalqty = totalqty + 1;
                totalgwt = totalgwt + item.getGrossWt();
                totalswt = totalswt + item.getStoneWt();
                totalnwt = totalnwt + item.getNetWt();
                totalPc = Double.parseDouble(totalPc + item.getPcs());
                aggregatedItems.put(key, newItem);
            }

        }
        topmap.putAll(aggregatedItems);
        bottommap.clear();
        bottommap.putAll(filtereditems);
        b.ttqty.setText(String.valueOf(totalqty));
        b.ttgwt.setText(String.valueOf(totalgwt));
        b.ttswt.setText(String.valueOf(totalswt));
        b.ttnetwt.setText(String.valueOf(totalnwt));

        inventoryTopAdaptor.notifyDataSetChanged();
        inventoryBottomAdaptor.notifyDataSetChanged();

        Log.d("checktoplist", "  " + bottommap.size());
    }

    @Override
    public void onPause() {
        stopscanner();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        stopscanner();
        super.onDestroy();
    }

    private void additems() {
        topmap.clear();

        Map<String, Itemmodel> aggregatedItems = new HashMap<>();

        for (Map.Entry<String, Itemmodel> entry : totalitems.entrySet()) {
            Itemmodel item = entry.getValue();
            // Create a key using category and product
            String key = item.getCategory() + "|" + item.getProduct();

            // Check if the item is already aggregated
            if (aggregatedItems.containsKey(key)) {
                // If item already exists, update its values
                Itemmodel aggregatedItem = aggregatedItems.get(key);
                // Update count
                aggregatedItem.setAvlQty(aggregatedItem.getAvlQty() + 1);
                // Update weights
                aggregatedItem.setTotalGwt(aggregatedItem.getTotalGwt() + item.getGrossWt());
            } else {
                // If item doesn't exist, add it to the map
                // Make a copy of the item and set count to 1
                Itemmodel newItem = new Itemmodel();
                newItem.setAvlQty(1);
                newItem.setTotalGwt(item.getGrossWt());
                // Add to aggregatedItems map
                aggregatedItems.put(key, newItem);
            }
        }
        topmap.putAll(aggregatedItems);
        bottommap.clear();
        bottommap.putAll(totalitems);
        inventoryBottomAdaptor.notifyDataSetChanged();

        Log.d("checktoplist", "  " + bottommap.size());

    }

    @Override
    public void myOnKeyDwon(String barcode) {
        b.singlescan.performClick();
//        super.myOnKeyDwon();
    }
}