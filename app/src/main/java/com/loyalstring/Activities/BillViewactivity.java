package com.loyalstring.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.loyalstring.Adapters.BillViewAdapter;
import com.loyalstring.R;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.tools.Pdfreportgenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BillViewactivity extends AppCompatActivity {

    RecyclerView trecycler;
    BillViewAdapter billViewAdapter;
    HashMap<String, Itemmodel> searchitems = new HashMap<>();
    TextView totalitem, totalgwt, totalnwt;
    int ti = 0;
    double tgwt = 0;
    double tnwt = 0;
    public static DecimalFormat decimalFormat = new DecimalFormat("#.###");
    Switch duplicate;
    Button print ;

    HashMap<String, List<Itemmodel>> dup = new HashMap<>();
    boolean du = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_viewactivity);


        Intent intent = getIntent();
        ArrayList<Itemmodel> itemList = (ArrayList<Itemmodel>) intent.getSerializableExtra("billList");

        Log.e("check billlist", " "+itemList.size());

        if (!itemList.isEmpty()) {
            for (Itemmodel i : itemList) {
                String key = i.getInvoiceNumber()+i.getGrossWt()+i.getStoneWt()+i.getNetWt();
                searchitems.put(key, i);

                if(i.getInvoiceNumber().equals("O3")){
                    dup.putIfAbsent(key, new ArrayList<>());
                    dup.get(key).add(i);
                }


            }
        }

        duplicate = findViewById(R.id.dupilcates);
        print = findViewById(R.id.print);



        totalgwt = findViewById(R.id.tdtotalgwt);
        totalitem = findViewById(R.id.tdtotalitems);
        totalnwt = findViewById(R.id.tdtotalamount);


        trecycler = findViewById(R.id.trecycler);


        trecycler.setLayoutManager(new LinearLayoutManager(this));
        billViewAdapter = new BillViewAdapter(BillViewactivity.this, searchitems);
        trecycler.setAdapter(billViewAdapter);

        duplicate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    du = false;

                    int a =0;
                    searchitems.clear();
                    if (!itemList.isEmpty()) {
                        for (Itemmodel i : itemList) {
                            String key = String.valueOf(a);//i.getInvoiceNumber()+i.getGrossWt()+i.getStoneWt()+i.getNetWt();
                            searchitems.put(key, i);
                            a++;
                        }
                    }
                }else{
                    searchitems.clear();
                    if (!itemList.isEmpty()) {
                        for (Itemmodel i : itemList) {
                            String key = i.getInvoiceNumber()+i.getGrossWt()+i.getStoneWt()+i.getNetWt();
                            searchitems.put(key, i);
                        }
                    }

                    du = true;
                }
                billViewAdapter = new BillViewAdapter(BillViewactivity.this, searchitems);
                trecycler.setAdapter(billViewAdapter);
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(du) {

                    try {
                        Pdfreportgenerator pdfreportgenerator = new Pdfreportgenerator(BillViewactivity.this);
                        TreeMap<String, List<Itemmodel>> nmap = new TreeMap<>();
                        for(Map.Entry<String, Itemmodel> entry : searchitems.entrySet()){
//                            String key = entry.getValue();
                            Itemmodel value = entry.getValue();
                            String k = value.getItemCode()+value.getGrossWt()+value.getStoneWt()+value.getNetWt();

                            nmap.putIfAbsent(k, new ArrayList<>());
                            nmap.get(k).add(value);
                        }

                        pdfreportgenerator.generatereportpdf(BillViewactivity.this, nmap, "salereport", 1);


                    } catch (Exception e) {
                        Log.e("check error", "" + e.getMessage());
                    }

//                }
//                else{
//                        Toast.makeText(BillViewactivity.this, "Please click after 5 seconds", Toast.LENGTH_SHORT).show();
//                    }
            }
        });



        if (itemList.size() > 0) {
            ti = itemList.size();

            for (Itemmodel m : itemList) {
                tgwt = tgwt + m.getGrossWt();
                tnwt = tnwt + m.getNetWt();
            }
            totalgwt.setText(decimalFormat.format(tgwt));
            totalnwt.setText(decimalFormat.format(tnwt));
            totalitem.setText(String.valueOf(ti));
        }

    }


}