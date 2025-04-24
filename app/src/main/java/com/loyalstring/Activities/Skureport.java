package com.loyalstring.Activities;

import static com.loyalstring.fsupporters.Pemissionscheck.STORAGE_PERMISSION_READWRITE_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loyalstring.Adapters.Skureportadpter;
import com.loyalstring.Apis.ApiManager;
import com.loyalstring.apiresponse.AlllabelResponse;
import com.loyalstring.apiresponse.SkuResponse;
import com.loyalstring.database.Reader.MainData;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.databinding.ActivitySkureportBinding;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.interfaces.interfaces;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.tools.Pdfreportgenerator;
import com.loyalstring.tools.ReportExcelsgenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Skureport extends AppCompatActivity {

    ActivitySkureportBinding b;
    Skureportadpter skureportadpter;
    List<SkuResponse> allsku = new ArrayList<>();
    EntryDatabase entryDatabase;
    Map<String, Itemmodel> alllsit = new HashMap<>();
    ApiManager apiManager;

    TreeMap<String, List<Itemmodel>> skureport = new TreeMap<>();

    int tgwt = 0;
    int tswt = 0;
    int tnwt = 0;
    int tsamount = 0;
    TreeMap<String, List<Itemmodel>> skureport1 = new TreeMap<>();

    Globalcomponents globalcomponents;

    String reporttype = "";
    MyApplication myApplication;

//    ReportExcelsgenerator reportExcelsgenerator = new ReportExcelsgenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivitySkureportBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

//        mainActivity = (MainActivity) getActivity();
//        ActionBar actionBar = mainActivity.getSupportActionBar();
//        if (actionBar != null) {
//            // Update ActionBar properties
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle("Stock Report");
//            // actionBar.setHomeAsUpIndicator(R.drawable.your_custom_icon); // Set a custom icon
//        }
//
//        globalcomponents = new Globalcomponents();

        myApplication = new MyApplication();
        DateTimeFormatter formatter;
        String timestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            timestamp = now.format(formatter);
        }

        reporttype = getIntent().getStringExtra("type");

        b.fromtext.setText(timestamp);
        b.totext.setText(timestamp);

        globalcomponents = new Globalcomponents();


        entryDatabase = new EntryDatabase(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.loyalstring.co.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Initialize ApiManager
        apiManager = new ApiManager(apiService);


        if(reporttype.equalsIgnoreCase("skureport")){
            apiManager.fetchAllLabeledStock("LS000026", new interfaces.ApiCallback<List<AlllabelResponse.LabelItem>>() {
                @Override
                public void onSuccess(List<AlllabelResponse.LabelItem> result) {
                    Skureport.this.runOnUiThread(() -> {
                        // Handle the Labeled Stock data, update UI
//                    Log.e("check list", "report " + result);
                        if (!result.isEmpty()) {

                            alllsit = entryDatabase.getBilledItems(Skureport.this);
                            if (alllsit != null) {
                                // Create a map for faster lookup based on item code
                                Map<String, AlllabelResponse.LabelItem> labelItemMap = new HashMap<>();
                                for (AlllabelResponse.LabelItem item : result) {
                                    labelItemMap.put(item.itemCode, item);
                                }

                                // Update SKUId for matching items
                                for (Itemmodel m1 : alllsit.values()) {
                                    AlllabelResponse.LabelItem labelItem = labelItemMap.get(m1.getItemCode());
                                    if (labelItem != null) {
                                        m1.setSKUId(labelItem.sKUId);
                                    }
                                }

                                // Optional: Notify UI or refresh data if necessary
                                // e.g., adapter.notifyDataSetChanged();
                                fetchallsku();
                            } else {
                                Log.e("Skureport", "Billed items map is null");
                            }

/*                        if (!alllsit.isEmpty()) {

                            Iterator<Map.Entry<String, Itemmodel>> iterator = alllsit.entrySet().iterator();
                            Map<String, List<Itemmodel>> removedlist = new HashMap<>();

                            while (iterator.hasNext()) {
                                Map.Entry<String, Itemmodel> entry = iterator.next();
                                Itemmodel currentItem = entry.getValue();

                                for (Itemmodel otherItem : alllsit.values()) {
                                    // Skip comparison with itself
                                    if (currentItem == otherItem) continue;

                                    // Check for duplicate based on CustomerName and GrossWt
                                    if (currentItem.getCustomerName().equals(otherItem.getCustomerName()) &&
                                            currentItem.getGrossWt() == otherItem.getGrossWt() && currentItem.getStoneWt() == otherItem.getStoneWt()
                                            && currentItem.getNetWt() == otherItem.getNetWt() && currentItem.getItemCode().equals(otherItem.getItemCode())) {
                                        // Remove the duplicate item

                                        String key = currentItem.getCustomerName() + "_" + currentItem.getInvoiceNumber();
                                        Log.e("items removed", "" + key + "  " + currentItem.getGrossWt() + "  " + currentItem.getProduct());
                                        removedlist.putIfAbsent(key, new ArrayList<>());
                                        removedlist.get(key).add(currentItem);

                                        iterator.remove();
                                        break; // Exit the loop once a duplicate is found and removed
                                    }
                                }

                            }
                            Log.e("items removed1", "" + removedlist.size());
                            fetchallsku();
                        }*/
                        }


                    });
                }

                @Override
                public void onError(Exception e) {
                    Skureport.this.runOnUiThread(() -> {
                        Log.e("checkallist2", "chexk  " + alllsit.size());

                        Toast.makeText(Skureport.this, "Failed to fetch Labeled Stock data", Toast.LENGTH_SHORT).show();
                    });
                }
            });

        }else if(reporttype.equalsIgnoreCase("salereport")){
//            skureport = myApplication.getSalereport();
            Intent intent = getIntent();
            if (intent != null) {
                HashMap<String, List<Itemmodel>> salereport = (HashMap<String, List<Itemmodel>>) intent.getSerializableExtra("salereport");
                if (salereport != null) {
                    Log.e("Skureport", "Received salereport with size: " + salereport.size());
                    skureport.putAll(salereport);
                }
            }
            Log.e("checking receive", " "+myApplication.getSalereport().size());

        }



        b.allexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (skureport1.isEmpty()) {
                    Toast.makeText(Skureport.this, "Please click after 5 seconds", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkreadandwrite(Skureport.this)) {
//                    ArrayList<Itemmodel> l = new ArrayList<>();
//                    l.addAll(item);
                    checkfolder("sku report");


                } else {
                    requestreadwrite(Skureport.this);
                }


            }
        });


        b.allpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!skureport.isEmpty()) {

                    try {
                        Pdfreportgenerator pdfreportgenerator = new Pdfreportgenerator(Skureport.this);
                        if(reporttype.equalsIgnoreCase("skureport")){

                            pdfreportgenerator.generatereportpdf(Skureport.this, skureport, "skureport", 1);
                        } else if (reporttype.equalsIgnoreCase("salereport")) {
                            pdfreportgenerator.generatereportpdf(Skureport.this, skureport, "salereport", 1);
                        }



                    } catch (Exception e) {
                        Log.e("check error", "" + e.getMessage());
                    }
                } else {
                    Toast.makeText(Skureport.this, "Please click after 5 seconds", Toast.LENGTH_SHORT).show();
                }

            }
        });
        b.reportlayout.setLayoutManager(new LinearLayoutManager(this));


    }

    public boolean checkreadandwrite(Context context) {

        int readPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;

    }

    public void requestreadwrite(FragmentActivity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_READWRITE_CODE);
    }

    private void checkfolder(String invno) {
        boolean folder = globalcomponents.checkfileexist("sku reports");
        if (folder) {
            File cfile = createfile(invno);
            if (cfile != null) {
                String filePath = cfile.getAbsolutePath();//externalDir.getAbsolutePath() + "/unfilleddata.xlsx";
//                    excelConverter.unfilledexcel(resultList,filePath, getActivity(), "unfilled" );
//                    ExcelCreationTask excelTask = new ExcelCreationTask(resultList, filePath, getActivity());
//                    excelTask.execute();

                Log.d("tag", "check unfilled path " + skureport1.size());


                HashMap<String, TreeMap<String, List<Itemmodel>>> excelmap = new HashMap<>();
                excelmap.put(filePath, skureport1);

//                InventoryExcelCreation excelTask = new InventoryExcelCreation(null, null, filePath, "", Skureport.this, "billlist", "excel", excelmap, null);
//                excelTask.execute();

                ReportExcelsgenerator.Skureport reportExcelsgenerator = new ReportExcelsgenerator.Skureport(Skureport.this, skureport1, excelmap);
                reportExcelsgenerator.execute();

//                            readitems(getActivity(), importfragment.this, "", inventorylist, "export", cfile);

            } else {
                Toast.makeText(Skureport.this, "failed to create file", Toast.LENGTH_SHORT).show();
            }
        } else {
            ArrayList<String> folders = new ArrayList<>();
            folders.add("sku reports");
            boolean f = globalcomponents.createFolders(folders);
            if (!f) {
                Toast.makeText(Skureport.this, "failed to create file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Skureport.this, "created file please click again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createfile(String invno) {
        File file = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 and above
            try {
                file = File.createTempFile("sku report", ".xlsx", Skureport.this.getFilesDir());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in internal storage: " + e.getMessage());
                return null;
            }
        } else { // Android versions below 10
            try {
                file = File.createTempFile("sku report", ".xlsx", Environment.getExternalStorageDirectory());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in external storage: " + e.getMessage());
                return null;
            }
        }
    }

    private void fetchallsku() {
        MainData.fetchAllsku(Skureport.this, entryDatabase, new interfaces.OnSkusFetchedListener() {

            @Override
            public void onSkusFetched(List<SkuResponse> skus) {

                allsku = skus;
                skureport1.clear();
                if (!allsku.isEmpty()) {
                    for (SkuResponse sku : allsku) {
                        // Iterate through the alllsit values and compare itemCodes
                        for (Itemmodel item : alllsit.values()) {
                            if (sku.id1 == item.getSKUId()) {
                                item.setStockKeepingUnit((sku.stockKeepingUnit));
                            }
                        }
                    }

//                    Map<Integer, Itemmodel> itemModelMap = new HashMap<>();
//                    for (Itemmodel item : alllsit.values()) {
//                        itemModelMap.put(item.getSKUId(), item);
//                    }
//
//                    // Iterate through the SKU responses and update Itemmodel
//                    for (SkuResponse sku : allsku) {
//                        Itemmodel item = itemModelMap.get(sku.id1);
//                        if (item != null) {
//                            item.setStockKeepingUnit(sku.stockKeepingUnit);
//                        }
//                    }

                    Log.e("checkallist", "chexk  " + alllsit.size());


                    for (Itemmodel i : alllsit.values()) {
                        if (i.getStockKeepingUnit() != null) {

                            skureport.putIfAbsent(i.getStockKeepingUnit(), new ArrayList<>());
                            skureport.get(i.getStockKeepingUnit()).add(i);


                        }
                    }

                    int totalx = 0;
                    for (Map.Entry<String, List<Itemmodel>> entry : skureport.entrySet()) {
                        totalx = totalx + entry.getValue().size();

                        if(entry.getValue().get(0).getStockKeepingUnit().equalsIgnoreCase("32pl")){
                            for(int i=0; i<entry.getValue().size() ; i++){
                                Log.e("skunotfound", "check "+entry.getValue().get(i).getStockKeepingUnit()+"  "
                                        + entry.getValue().get(i).getItemCode()
                                        +"  "+entry.getValue().get(i).getGrossWt());
                            }


                        }

                    }
//                    Log.e("skunotfound", "check 1" + totalx);


// Populate the TreeMap
//                    for (Map.Entry<String, List<Itemmodel>> entry : skureport.entrySet()) {
//                        List<Itemmodel> list = entry.getValue();
//                        for (Itemmodel l : list) {
//                            // Ensure the key is formatted correctly for sorting
//                            String key = l.getStockKeepingUnit() +"    G "+ l.getGrossWt() +" S "+ l.getStoneWt() +" N "+ l.getNetWt();
//                            // Add to TreeMap
//                            skureport1.putIfAbsent(key, new ArrayList<>());
//                            skureport1.get(key).add(l);
//                        }
//                    }

// Convert to a list if you need a specific sorted list
//                    List<Map.Entry<String, List<Itemmodel>>> sortedEntries = new ArrayList<>(skureport.entrySet());
//                    Collections.sort(sortedEntries, Map.Entry.comparingByKey());

//                    int total1 = 0;
//                    for (Map.Entry<String, List<Itemmodel>> entry : skureport.entrySet()) {
//                        total1 += entry.getValue().size();
//
//                    }


                    skureportadpter = new Skureportadpter(Skureport.this, skureport);
                    b.reportlayout.setAdapter(skureportadpter);
                    skureportadpter.notifyDataSetChanged();
                }
            }
        });

    }
}