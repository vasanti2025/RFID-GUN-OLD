package com.loyalstring.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.loyalstring.Adapters.Bill1adapter;
import com.loyalstring.Adapters.BillAdapter;
import com.loyalstring.Billmodels.Customersmodel;
import com.loyalstring.Billmodels.CustomersmodelItem;
import com.loyalstring.Billmodels.Onboardmodel;
import com.loyalstring.MainActivity;
import com.loyalstring.R;
import com.loyalstring.database.StorageClass;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.databinding.FragmentBill1fragmentBinding;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.modelclasses.Issuemode;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.readersupport.KeyDwonFragment;
import com.loyalstring.transactionhelper.TransactionIDGenerator;
import com.rscja.deviceapi.entity.UHFTAGInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Bill1fragment extends KeyDwonFragment {

    FragmentBill1fragmentBinding b;
    List<String> bottomlist = new ArrayList<>();

    String transactionno = "";
    MyApplication myApplication;
    MainActivity mainActivity;
    List<String> tempDatas = new ArrayList<String>();
    Globalcomponents globalcomponents;
    StorageClass storageClass;
    HashMap<String, Itemmodel> totalitems = new HashMap<>();
    HashMap<String, Itemmodel> searchitems = new HashMap<>();
    boolean ploopFlag = false;
    BillAdapter billAdapter;
    double totalgwt = 0;
    double totalnwt = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UHFTAGInfo info = (UHFTAGInfo) msg.obj;
            Log.d("checktidva", "t" + info.getTid() + " e" + info.getEPC() + " r" + info.getReserved() + " " + info.getUser() + "  " + info.toString());
//            addDataToList(info.getEPC(), info.getTid(), info.getRssi());
        }
    };

    private Handler mHandler = new Handler();

    EntryDatabase entryDatabase;
    int invoicenumber = 0;
    List<Issuemode> issueitem = new ArrayList<>();
    private ApiService apiService;
    List<CustomersmodelItem> customers = new ArrayList<>();
    List<String> cnames = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentBill1fragmentBinding.inflate(inflater, container, false);











        b.tname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String baseUrl = "https://goldstringwebapp.loyalstring.co.in/";

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Log request and response body

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService = retrofit.create(ApiService.class);

                Onboardmodel request = new Onboardmodel("LS000001");

                apiService.getAllCustomers(request).enqueue(new Callback<List<CustomersmodelItem>>() {
                    @Override
                    public void onResponse(Call<List<CustomersmodelItem>> call, Response<List<CustomersmodelItem>> response) {
                        if (response.isSuccessful()) {
                            customers = response.body();
                            // Handle the response data here
                            cnames.clear();
                            for (CustomersmodelItem customer : customers) {
                                Log.d("Customer", "Name: " + customer.getFirstName() + " " + customer.getLastName());

                                cnames.add(customer.getFirstName() + " " + customer.getLastName());
                            }
                        } else {
                            // Handle the error response
                            Log.e("Error", "Response Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CustomersmodelItem>> call, Throwable t) {
                        // Handle the failure response
                        Log.e("Error at api", t.getMessage());
                    }
                });
                openBottomSheetDialog("Customer Name");


            }
        });



        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_bill1fragment, container, false);
        return b.getRoot();
    }

    private void openBottomSheetDialog(String s) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());

        bottomSheetDialog.setCancelable(false);

        bottomSheetDialog.setCancelable(false);

        View contentView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        bottomSheetDialog.setContentView(contentView);

        ImageButton close = contentView.findViewById(R.id.closeButton);
        TextView title = contentView.findViewById(R.id.maintitle);
        Button addbtn = contentView.findViewById(R.id.additem);
        EditText itemname = contentView.findViewById(R.id.itemname);
        ListView spinnerlist = contentView.findViewById(R.id.spinnerlist);
        LinearLayout bholder = contentView.findViewById(R.id.beholder);

        bholder.setVisibility(View.GONE);

        itemname.setVisibility(View.GONE);
        addbtn.setVisibility(View.GONE);

        if (s.matches("tt")) {
            bottomlist.clear();
            bottomlist.add("Estimation");
            bottomlist.add("Reserved");
            bottomlist.add("Bill");
            bottomlist.add("Sample in");
            bottomlist.add("Sample out");
            title.setText("Transaction Type");
        }else if(s.matches("Customer Name")){
            bottomlist.clear();
            bottomlist.addAll(cnames);
            bottomlist.add("New customer");
        }
//        else if (s.matches("si")) {
//            title.setText("Sample In");
//            bottomlist.clear();
//            bottomlist.add("Repair");
//            bottomlist.add("Order");
//        }
//        else {
//            title.setText(b.tcategorytext.getText().toString());
//            bottomlist.clear();
//            bottomlist.add("Repair");
//            bottomlist.add("Order");
//        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });


//        final List<String>[] allItemNames = new List[]{};//new List[]{categoryDb.getAllItemNames(s.toLowerCase(Locale.ROOT))};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, bottomlist);
        spinnerlist.setAdapter(adapter);


        spinnerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selecteditem = (String) adapterView.getItemAtPosition(position);
                Toast.makeText(requireContext(), "Selected " + s + " : " + selecteditem, Toast.LENGTH_SHORT).show();
//                transactionno.setText("");

                if (s.matches("tt")) {
                    if (!selecteditem.equalsIgnoreCase("Estimation") && !selecteditem.equalsIgnoreCase("reserved")) {
                        Toast.makeText(getActivity(), "not enabled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    b.tcategorytext.setText(selecteditem);
                    transactionno = TransactionIDGenerator.generateBillRepairTransactionID("E");
//                    if (selecteditem.toLowerCase(Locale.ROOT).matches("sample in")
//                            || selecteditem.toLowerCase(Locale.ROOT).matches("sample out")) {
//
//                        tsamplelay.setVisibility(View.VISIBLE);
//
//                    } else if (selecteditem.toLowerCase(Locale.ROOT).matches("bill")) {
//                    tsamplelay.setVisibility(View.GONE);
//                    transactionno.setText(TransactionIDGenerator.generateBillRepairTransactionID("E"));
//                    }
                } else {
//                    tsampletext.setText(selecteditem);
//                    if (selecteditem.toLowerCase(Locale.ROOT).matches("repair")) {
//                        transactionno.setText(TransactionIDGenerator.generateRepairTransactionID());
//                    } else {
//                        transactionno.setText(TransactionIDGenerator.generateOrderTransactionID());
//                    }

                }
                bottomSheetDialog.dismiss();

            }
        });
        bottomSheetDialog.show();
    }
}