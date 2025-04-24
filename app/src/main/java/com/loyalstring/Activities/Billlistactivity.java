package com.loyalstring.Activities;

import static com.loyalstring.fsupporters.Pemissionscheck.STORAGE_PERMISSION_READWRITE_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loyalstring.Adapters.BillListAdaptor;
import com.loyalstring.Excels.InventoryExcelCreation;
import com.loyalstring.LatestApis.LoginApiSupport.Clients;
import com.loyalstring.LatestCallBacks.ActivationCallback;
import com.loyalstring.LatestStorage.SharedPreferencesManager;
import com.loyalstring.R;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.databinding.ActivityBilllistactivityBinding;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.interfaces.SaveCallback;
import com.loyalstring.modelclasses.Issuemode;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.modelclasses.jjjresponse;
import com.loyalstring.tools.PdfGenerator;
import com.loyalstring.tools.Pdfreportgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Billlistactivity extends AppCompatActivity implements BillListAdaptor.Onsend, BillListAdaptor.MoreOptions {

    ActivityBilllistactivityBinding b;
    Map<String, Itemmodel> alllsit = new HashMap<>();
    EntryDatabase entryDatabase;
    BillListAdaptor billListAdaptor;

//    TreeMap<String, List<Itemmodel>> billlist = new TreeMap<>();

    LinkedHashMap<String, List<Itemmodel>> billlist = new LinkedHashMap<>();

    Globalcomponents globalcomponents;
    MyApplication myApplication;
    SharedPreferencesManager sharedPreferencesManager;

    Clients clients;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityBilllistactivityBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_billlistactivity);

        setContentView(b.getRoot());

        entryDatabase = new EntryDatabase(this);
        globalcomponents = new Globalcomponents();
        myApplication = (MyApplication) this.getApplicationContext();

        sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        b.billrecycler.setLayoutManager(new LinearLayoutManager(this));

        billListAdaptor = new BillListAdaptor(this, billlist, this, this);
        b.billrecycler.setAdapter(billListAdaptor);
        clients = sharedPreferencesManager.readLoginData().getEmployee().getClients();

        fetchbills();


        b.printall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean folder = globalcomponents.checkfileexist("Pdfs");
                if (folder) {
                    try {

                        if (billlist.isEmpty()) {
                            Toast.makeText(myApplication, "no items found", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        Pdfreportgenerator pdfreportgenerator = new Pdfreportgenerator(Billlistactivity.this);
                        pdfreportgenerator.generatereportpdf(Billlistactivity.this, billlist, "salereport", 3);

                    } catch (Exception e) {

                        Log.e("check allpdf error", " " + e.getMessage());

                    }
                }


            }
        });


    }

    private void fetchbills() {
        alllsit = entryDatabase.getBilledItems(this);
        billlist.clear();
        billListAdaptor.notifyDataSetChanged();

        if (!alllsit.isEmpty()) {

            Map<String, List<Itemmodel>> invoiceMap = new HashMap<>();

            Map<Long, List<Itemmodel>> timestampMap = new HashMap<>();
            for (Map.Entry<String, Itemmodel> entry : alllsit.entrySet()) {
                Itemmodel item = entry.getValue();
                long operationTime = item.getOperationTime();

                if (timestampMap.containsKey(operationTime)) {
                    // Add the item to the existing list
                    timestampMap.get(operationTime).add(item);
                } else {
                    // Create a new list for this timestamp
                    List<Itemmodel> itemList = new ArrayList<>();
                    itemList.add(item);
                    timestampMap.put(operationTime, itemList);
                }
            }

//            alllsit.get().sort((item1, item2) -> Long.compare(item1.getOperationTime(), item2.getOperationTime()));

            // Group items by invoice number
            for (Map.Entry<String, Itemmodel> entry : alllsit.entrySet()) {
                String invoiceNumber = entry.getValue().getInvoiceNumber();
                if (invoiceMap.containsKey(invoiceNumber)) {
                    // Update item in the list
                    List<Itemmodel> itemList = invoiceMap.get(invoiceNumber);
                    itemList.add(entry.getValue());
                } else {
                    List<Itemmodel> itemList = new ArrayList<>();
                    itemList.add(entry.getValue());
                    invoiceMap.put(invoiceNumber, itemList);
                }
            }




            for (Map.Entry<String, List<Itemmodel>> entry : invoiceMap.entrySet()) {
                List<Itemmodel> itemList = entry.getValue();
                Collections.sort(itemList, new Comparator<Itemmodel>() {
                    @Override
                    public int compare(Itemmodel o1, Itemmodel o2) {
                        return Long.compare(o2.getOperationTime(), o1.getOperationTime());
                    }
                });
//                Collections.reverse(itemList);
                // Get the earliest timestamp for the invoice group
                if (!itemList.isEmpty()) {
                    long earliestTimestamp = itemList.get(0).getOperationTime();
                    billlist.put(String.valueOf(earliestTimestamp), itemList);
                }
            }

            // Sort billlist itself by keys (timestamps) in descending order to ensure the list is displayed correctly.
            LinkedHashMap<String, List<Itemmodel>> sortedBillList = new LinkedHashMap<>();
            billlist.entrySet().stream()
                    .sorted((entry1, entry2) -> Long.compare(Long.parseLong(entry2.getKey()), Long.parseLong(entry1.getKey())))
                    .forEachOrdered(entry -> sortedBillList.put(entry.getKey(), entry.getValue()));

// Replace the original billlist with the sorted one
            billlist.clear();
            billlist.putAll(sortedBillList);


            billListAdaptor.notifyDataSetChanged();

            b.uploadbills.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Clients clients = sharedPreferencesManager.readLoginData().getEmployee().getClients();
                    String clientCode = clients.getClientCode();

                    if(clientCode == null || clientCode.isEmpty()){
                        Toast.makeText(myApplication, "no client  code found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ProgressDialog udialog = new ProgressDialog(Billlistactivity.this);
                    udialog.setMessage("uploading bills");
                    udialog.setCanceledOnTouchOutside(false);
                    udialog.show();
                    entryDatabase.updatebillstoweb(clientCode, new ActivationCallback(){

                        @Override
                        public void onSaveSuccess() {
                            Toast.makeText(myApplication, "uploaded successfully", Toast.LENGTH_SHORT).show();
                            udialog.dismiss();

                        }

                        @Override
                        public void onFailed(String error) {
                            Toast.makeText(myApplication, "uploading failed", Toast.LENGTH_SHORT).show();

                            udialog.dismiss();
                        }
                    });
                }
            });

            /*b.uploadbills.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ApiService apiService = new Retrofit.Builder()
                            .baseUrl("https://jjj.panel.jewelmarts.in/")//https://jjj.panel.jewelmarts.in/
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService.class);

                    for (Map.Entry<String, List<Itemmodel>> entry : billlist.entrySet()) {
                        // Assuming key is the voucher ID
                        List<Itemmodel> itemList = entry.getValue();
                        String voucherId = itemList.get(0).getInvoiceNumber();
                        StringBuilder rfidValueBuilder = new StringBuilder();
                        String userId = itemList.get(0).getDiamondSize(); // Assuming this is the user ID in your data structure
                        String username = "RFID"; // Static value for username
                        String password = "Rg^%6mkj676G%$)jhAZ"; // Static value for password


                        if(userId != null && !userId.isEmpty()){

                            // Construct the comma-separated RFID values
                            for (Itemmodel item : itemList) {
                                if (rfidValueBuilder.length() > 0) {
                                    rfidValueBuilder.append(",");
                                }
                                rfidValueBuilder.append(item.getBarCode()); // Assuming Itemmodel has getBarcode method
                            }

                            String rfidValue = rfidValueBuilder.toString();

                            // Create RequestBody parts for multipart
                            RequestBody voucherIdPart = RequestBody.create(MediaType.parse("text/plain"), voucherId);
                            RequestBody rfidValuePart = RequestBody.create(MediaType.parse("text/plain"), rfidValue);
                            RequestBody usernamePart = RequestBody.create(MediaType.parse("text/plain"), username);
                            RequestBody passwordPart = RequestBody.create(MediaType.parse("text/plain"), password);
                            RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), userId);


                            Log.e("check userid ", "  "+userId);
                            // Make the API call
                            Call<jjjresponse> call = apiService.uploadBill(voucherIdPart, rfidValuePart, usernamePart, passwordPart, userIdPart);
                            call.enqueue(new Callback<jjjresponse>() {
                                @Override
                                public void onResponse(Call<jjjresponse> call, Response<jjjresponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        jjjresponse apiResponse = response.body();
                                        Log.e("check userid ", "  "+apiResponse.getMsg());
                                        if (apiResponse.getAck() == 1) {
                                            // Process successful response
                                            Toast.makeText(getApplicationContext(), "Upload Successful for voucher: " + voucherId, Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Handle ack != 1 (failure case)
                                            Toast.makeText(getApplicationContext(), "Upload Failed for voucher: " + voucherId + ". Reason: " + apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Handle error response
                                        Toast.makeText(getApplicationContext(), "Upload Failed for voucher: " + voucherId + ". Error: " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<jjjresponse> call, Throwable t) {
                                    // Handle failure
                                    Log.e("check userid ", "  "+t.getMessage());
                                    Toast.makeText(getApplicationContext(), "Upload Failed for voucher: " + voucherId + ". Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });*/


        }
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

    @Override
    public void onsend(String invno, List<Itemmodel> list) {

        if (checkreadandwrite(Billlistactivity.this)) {
            ArrayList<Itemmodel> l = new ArrayList<>();
            l.addAll(list);
            checkfolder(invno, l);


        } else {
            requestreadwrite(Billlistactivity.this);
        }
    }

    private void checkfolder(String invno, ArrayList<Itemmodel> list) {
        boolean folder = globalcomponents.checkfileexist("Bill");
        if (folder) {
            File cfile = createfile(invno);
            if (cfile != null) {
                String filePath = cfile.getAbsolutePath();//externalDir.getAbsolutePath() + "/unfilleddata.xlsx";
//                    excelConverter.unfilledexcel(resultList,filePath, getActivity(), "unfilled" );
//                    ExcelCreationTask excelTask = new ExcelCreationTask(resultList, filePath, getActivity());
//                    excelTask.execute();

                Log.d("tag", "check unfilled path " + filePath);


                HashMap<String, ArrayList<Itemmodel>> excelmap = new HashMap<>();
                excelmap.put(filePath, list);

                InventoryExcelCreation excelTask = new InventoryExcelCreation(null, null, filePath, "", Billlistactivity.this, "billlist", "excel", excelmap, null);
                excelTask.execute();

//                            readitems(getActivity(), importfragment.this, "", inventorylist, "export", cfile);

            } else {
                Toast.makeText(Billlistactivity.this, "failed to create file", Toast.LENGTH_SHORT).show();
            }
        } else {
            ArrayList<String> folders = new ArrayList<>();
            folders.add("product");
            boolean f = globalcomponents.createFolders(folders);
            if (!f) {
                Toast.makeText(Billlistactivity.this, "failed to create file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Billlistactivity.this, "created file please click again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createfile(String invno) {
        File file = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 and above
            try {
                file = File.createTempFile(invno + "estimation", ".xlsx", Billlistactivity.this.getFilesDir());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in internal storage: " + e.getMessage());
                return null;
            }
        } else { // Android versions below 10
            try {
                file = File.createTempFile(invno + "estimation", ".xlsx", Environment.getExternalStorageDirectory());
                return file;
            } catch (IOException e) {
                Log.e("TAG", "Error creating temporary file in external storage: " + e.getMessage());
                return null;
            }
        }
    }


    @Override
    public void MoreOptions(List<Itemmodel> item, View view, Context context) {

        PopupMenu popupMenu = new PopupMenu(context, view);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked

//                return true;
                int itemId = menuItem.getItemId();

                if (itemId == R.id.billitemview) {

                    Intent go = new Intent(Billlistactivity.this, BillViewactivity.class);
                    go.putExtra("billList", new ArrayList<>(item));
                    startActivity(go);

                } else if (itemId == R.id.editbill) {

                    SharedPreferences sharedPreferences = getSharedPreferences("BillPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Convert the list to a JSON string
                    Gson gson = new Gson();
                    String json = gson.toJson(item);

                    editor.putString("updatedBillList", json);
                    editor.apply();

                    // Go back to BillFragment
                    onBackPressed();
                } else if (itemId == R.id.deletebill) {
                    String invoiceNumber = item.get(0).getInvoiceNumber(); // Get the invoice number

                    // Create an AlertDialog
                    new AlertDialog.Builder(Billlistactivity.this)
                            .setTitle("Delete Bill")
                            .setMessage("Are you sure you want to delete this bill?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Trigger delete operation if "Yes" is clicked
                                    deleteBillsAsync(invoiceNumber);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dismiss the dialog if "Cancel" is clicked
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (itemId == R.id.billitemsend) {
                    if (checkreadandwrite(Billlistactivity.this)) {
                        ArrayList<Itemmodel> l = new ArrayList<>();
                        l.addAll(item);
                        checkfolder(item.get(0).getInvoiceNumber(), l);


                    } else {
                        requestreadwrite(Billlistactivity.this);
                    }

                }else if(itemId == R.id.upload){

                    ApiService apiService = new Retrofit.Builder()
                            .baseUrl("https://jjj.panel.jewelmarts.in/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService.class);

                    String voucherId = item.get(0).getInvoiceNumber();
                    StringBuilder rfidValueBuilder = new StringBuilder();
                    String userId = item.get(0).getDiamondSize(); // Assuming you get it from your data
                    String username = "RFID"; // Static value
                    String password = "Rg^%6mkj676G%$)jhAZ"; // Static value

                    for (Itemmodel item : item) {
                        if (rfidValueBuilder.length() > 0) {
                            rfidValueBuilder.append(",");
                        }
                        rfidValueBuilder.append(item.getBarCode()); // Assuming Itemmodel has getBarCode method
                    }

                    String rfidValue = rfidValueBuilder.toString();

// Create RequestBody parts for multipart
                    RequestBody voucherIdPart = RequestBody.create(MediaType.parse("text/plain"), voucherId);
                    RequestBody rfidValuePart = RequestBody.create(MediaType.parse("text/plain"), rfidValue);
                    RequestBody usernamePart = RequestBody.create(MediaType.parse("text/plain"), username);
                    RequestBody passwordPart = RequestBody.create(MediaType.parse("text/plain"), password);
                    RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), userId);

// Make the API call
                    Call<jjjresponse> call = apiService.uploadBill(voucherIdPart, rfidValuePart, usernamePart, passwordPart, userIdPart);
                    call.enqueue(new Callback<jjjresponse>() {
                        @Override
                        public void onResponse(Call<jjjresponse> call, Response<jjjresponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                jjjresponse apiResponse = response.body();
                                Log.e("check response jjj", "  "+apiResponse.getMsg()) ;
                                Toast.makeText(Billlistactivity.this, "response "+apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                if (apiResponse.getAck() == 1) {
                                    // Process successful response
                                    Toast.makeText(getApplicationContext(), "Upload Successful!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle ack != 1 (failure case)
                                    Toast.makeText(getApplicationContext(), "Upload Failed: " + apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Handle error response
                                Toast.makeText(getApplicationContext(), "Upload Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<jjjresponse> call, Throwable t) {
                            // Handle failure
                            Toast.makeText(getApplicationContext(), "Upload Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                else if (itemId == R.id.printstock) {

                    if(clients.getLabelFormat() == null || clients.getLabelFormat().isEmpty()){
                        Toast.makeText(context, "pdf formate not found", Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    HashMap<String, List<Itemmodel>> billmap = new HashMap<>();
                    for (Itemmodel s : item) {

                        String itemCode = s.getItemCode();
                        if (billmap.containsKey(itemCode)) {
                            // If item exists, update the existing list
                            List<Itemmodel> existingList = billmap.get(itemCode);
                            existingList.add(s);
                            billmap.put(itemCode, existingList);
                        } else {
                            // If item doesn't exist, add a new entry to the map
                            List<Itemmodel> newList = new ArrayList<>();
                            newList.add(s);
                            billmap.put(itemCode, newList);
                        }
                    }

                    // Create an AlertDialog to show format options
                    /*String[] formats = {"Format 1", "Format 2", "Format 3", "Format 4", "Format 5"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Print Format")
                            .setItems(formats, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Based on the selected format, call the generatePdf method with the corresponding format number
                                    PdfGenerator pdfGenerator = new PdfGenerator(Billlistactivity.this);
                                    try {
                                        pdfGenerator.generatePdf(billmap, item, 14, which + 1);  // Format number starts from 1
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .show();*/

                    PdfGenerator pdfGenerator = new PdfGenerator(Billlistactivity.this);
                    try {
                        if(clients.getLabelFormat() == null || clients.getLabelFormat().isEmpty()){
                            Toast.makeText(context, "formate not found", Toast.LENGTH_SHORT).show();

                            return false;
                        }
                        pdfGenerator.generatePdf(billmap, item, Integer.parseInt(clients.getLabelFormat()));
//                        pdfGenerator.generatePdf(billmap, item, 14, 0);
//                                        Pdfreportgenerator pdfreportgenerator = new Pdfreportgenerator(Billlistactivity.this);
//                                        pdfreportgenerator.generatereportpdf1(Billlistactivity.this, billmap, 4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    new AlertDialog.Builder(Billlistactivity.this)
//                            .setTitle("Choose one")
//                            .setMessage("What you want to download with or without duplicates?")
//                            .setPositiveButton("Without", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Trigger delete operation if "Yes" is clicked
//
////                                    Map<String, Itemmodel> alllsit1 = new HashMap<String, Itemmodel>();
////                                    Iterator<Map.Entry<String, Itemmodel>> iterator = alllsit.entrySet().iterator();
////                                    Map<String, List<Itemmodel>> removedlist = new HashMap<>();
////
////                                    while (iterator.hasNext()) {
////                                        Map.Entry<String, Itemmodel> entry = iterator.next();
////                                        Itemmodel currentItem = entry.getValue();
////
////                                        for (Itemmodel otherItem : alllsit.values()) {
////                                            // Skip comparison with itself
////                                            if (currentItem == otherItem) continue;
////
////                                            // Check for duplicate based on CustomerName and GrossWt
////                                            if (currentItem.getCustomerName().equals(otherItem.getCustomerName()) &&
////                                                    currentItem.getGrossWt() == otherItem.getGrossWt() && currentItem.getStoneWt() == otherItem.getStoneWt()
////                                                    && currentItem.getNetWt() == otherItem.getNetWt()) {
////                                                // Remove the duplicate item
////
////                                                String key = currentItem.getCustomerName()+"_"+currentItem.getInvoiceNumber();
////                                                Log.e("items removed", ""+key+"  "+currentItem.getGrossWt()+"  "+currentItem.getProduct());
////                                                removedlist.putIfAbsent(key, new ArrayList<>());
////                                                removedlist.get(key).add(currentItem);
////
////                                                iterator.remove();
////                                                break; // Exit the loop once a duplicate is found and removed
////                                            }
////                                        }
////
////                                    }
////
////
////
////
////
////
////
////
////                                    HashMap<String, List<Itemmodel>> billmap = new HashMap<>();
////                                    for(Itemmodel s : item){
////                                        String key = s.getItemCode()+s.getGrossWt()+s.getStoneWt();
////                                        billmap.put(key, new ArrayList<>());
////                                        billmap.get(key).add(s);
////
////
//////                                        String itemCode = s.getItemCode();
//////                                        if (billmap.containsKey(itemCode)) {
//////                                            // If item exists, update the existing list
//////                                            List<Itemmodel> existingList = billmap.get(itemCode);
//////                                            existingList.add(s);
//////                                            billmap.put(itemCode, existingList);
//////                                        } else {
//////                                            // If item doesn't exist, add a new entry to the map
//////                                            List<Itemmodel> newList = new ArrayList<>();
//////                                            newList.add(s);
//////                                            billmap.put(itemCode, newList);
//////                                        }
////                                    }
////
////                                    PdfGenerator pdfGenerator = new PdfGenerator(Billlistactivity.this);
////                                    try {
////                                        pdfGenerator.generatePdf(billmap, item, 3);
////                                    } catch (IOException e) {
////                                        e.printStackTrace();
////                                    }
//
//
//                                }
//                            })
//                            .setNegativeButton("With", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    HashMap<String, List<Itemmodel>> billmap = new HashMap<>();
//                                    for (Itemmodel s : item) {
//
//                                        String itemCode = s.getItemCode();
//                                        if (billmap.containsKey(itemCode)) {
//                                            // If item exists, update the existing list
//                                            List<Itemmodel> existingList = billmap.get(itemCode);
//                                            existingList.add(s);
//                                            billmap.put(itemCode, existingList);
//                                        } else {
//                                            // If item doesn't exist, add a new entry to the map
//                                            List<Itemmodel> newList = new ArrayList<>();
//                                            newList.add(s);
//                                            billmap.put(itemCode, newList);
//                                        }
//                                    }
//
//
//                                }
//                            })
//                            .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Dismiss the dialog if "Cancel" is clicked
//                                    dialog.dismiss();
//                                }
//                            })
//                            .show();


                }
                else if(itemId == R.id.addtostock){
                    List<Itemmodel> relist = new ArrayList<>();
                    for(Itemmodel it : item){
                        it.setOperation("readd");
                        it.setOperationTime(System.currentTimeMillis());
                        relist.add(it);


                    }

                    if(!relist.isEmpty()){
                        entryDatabase.checkdatabase(Billlistactivity.this);
                        List<Issuemode> issueitem = new ArrayList<>();
                        entryDatabase.makeentry(Billlistactivity.this, relist, "adding", "reserved", myApplication, issueitem, new SaveCallback(){

                                    @Override
                                    public void onSaveSuccess() {

                                    }

                                    @Override
                                    public void onSaveFailure(List<Itemmodel> failedItems) {

                                    }
                                });
//                            @Override
//                            public void onSaveSuccess() {
//                                Toast.makeText(Billlistactivity.this, "Re-Added items successfully", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onSaveFailure(List<Itemmodel> failedItems) {
//
//                                Toast.makeText(Billlistactivity.this, "Failed to add items", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                    }else{
                        Toast.makeText(context, "nothing to readd", Toast.LENGTH_SHORT).show();
                    }
                }


                // Handle menu item clicks using a switch case
//                if (itemId == R.id.edit) {
//                    // Handle edit option
//                    // Example: Call a method to edit the item
////                    if (context instanceof Billlistactivity) {
//////                        ((Billlistactivity) context).onBilldatapass(item);
////                        if (!item.isEmpty()) {
////                            // Pass the item list to Billfragment
////                            Gson gson = new Gson();
////                            String jsonList = gson.toJson(item);
//////                            storageClass.setbilllist(jsonList);
////                            onBackPressed();
//////                            FragmentManager fragmentManager = getSupportFragmentManager();
//////                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//////                            Billfragment billFragment = new Billfragment();
//////
//////                            fragmentTransaction.replace(R.id.mainfragment, billFragment);
//////                            fragmentTransaction.commit();
////
////                        }
////
////                    }
//                    return true;
//                } else if (itemId == R.id.delete) {
//                    // Handle delete option
//                    // Example: Call a method to delete the item
//                    Toast.makeText(context, "Delete option clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
                return false;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    private void deleteBillsAsync(String invoiceNumber) {
        // Display progress dialog
        ProgressDialog dialog = new ProgressDialog(Billlistactivity.this);
        dialog.setMessage("Deleting bill for invoice: " + invoiceNumber);
        dialog.show();

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    entryDatabase.deleteBills(invoiceNumber);
                    return true; // Return true if deletion is successful
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // Return false if there is an error
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                dialog.dismiss();

                if (success) {
                    Toast.makeText(getApplicationContext(), "Bills deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchbills(); // Invoke fetchbills() after successful deletion
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete bills", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


}