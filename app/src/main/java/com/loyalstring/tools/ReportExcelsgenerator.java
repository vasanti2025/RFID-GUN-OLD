package com.loyalstring.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.loyalstring.R;
import com.loyalstring.fsupporters.Globalcomponents;
import com.loyalstring.modelclasses.Itemmodel;
import com.loyalstring.network.NetworkUtils;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReportExcelsgenerator {


    public static class Skureport extends AsyncTask<Void, Integer, String> {

        private ProgressDialog progressDialog;
        Context context;
        TreeMap<String, List<Itemmodel>> skureport1 = new TreeMap<>();
        String outfile = "";

        Map<String, String> fileMap = new HashMap<>();
        NetworkUtils networkUtils;
        private LayoutInflater inflater;
        HashMap<String, TreeMap<String, List<Itemmodel>>> excelmap = new HashMap<>();


        public Skureport(Context context, TreeMap<String, List<Itemmodel>> skureport1, HashMap<String, TreeMap<String, List<Itemmodel>>> excelmap) {

            this.context = context;
            this.skureport1 = skureport1;
            this.inflater = LayoutInflater.from(context);
            this.outfile = "";
            this.excelmap = excelmap;
            this.networkUtils = new NetworkUtils(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Creating Excel file...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(skureport1.values().size());
//            if (frag.equalsIgnoreCase("inventory")) {
//                progressDialog.setMax(bottomlist.size());
//            } else {
//                progressDialog.setMax(excelmap.size());
//            }
            progressDialog.setProgress(0);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Void... voids) {


            for (Map.Entry<String, TreeMap<String, List<Itemmodel>>> entry : excelmap.entrySet()) {
                outfile = entry.getKey();
                TreeMap<String, List<Itemmodel>> treeMap = entry.getValue();

            }
            try {

                OutputStream bottomOutputStream = new FileOutputStream(outfile);
                // Create a new FastExcel Workbook instance for top sheet
                Workbook bottomWorkbook = new Workbook(bottomOutputStream, "stock report", "1.0");
                // Create a worksheet named "Sheet 1" for bottom sheet
                Worksheet bottomsheet = bottomWorkbook.newWorksheet("Sheet 1");
                String[] headers;
                headers = new String[]{"Sku", "Order Number", "G Wt", "S Wt", "N Wt", "Qty",
                        "S Amount", "T GWt", "T SWt", "T NWt", "T SAmount"};
                for (int i = 0; i < headers.length; i++) {
                    bottomsheet.value(0, i, headers[i]);
                }


                int bottomMatchRowIndex = 1;
                int rowsProcessed = 0;
                int progressUpdateInterval = 100;
                for (Map.Entry<String, List<Itemmodel>> entry : skureport1.entrySet()) {
                    List<Itemmodel> items = entry.getValue();
                    String sku = entry.getKey();
                    double gwt = 0;
                    double swt = 0;
                    double nwt = 0;
                    int qty = 0;
                    for (Itemmodel m : items) {
                        gwt += m.getGrossWt();
                        swt += m.getStoneWt();
                        nwt += m.getNetWt();
                        qty++;
                    }


                    for (Itemmodel m : items) {
                        String[] values = {m.getStockKeepingUnit(), m.getInvoiceNumber(),
                                String.valueOf(m.getGrossWt()), String.valueOf(m.getStoneWt()),
                                String.valueOf(m.getNetWt()), String.valueOf(qty), "",
                                String.valueOf(gwt),
                                String.valueOf(swt), String.valueOf(nwt)};
                        createRow(bottomsheet, bottomMatchRowIndex, values);

                        bottomMatchRowIndex++;
                        rowsProcessed++;
                        if (rowsProcessed % progressUpdateInterval == 0) {
                            publishProgress(bottomMatchRowIndex);
                        }

                    }

//                    String[] values = {"", "", String.valueOf(gwt), String.valueOf(swt), String.valueOf(nwt), String.valueOf(qty)};
//                    createRow(bottomsheet, bottomMatchRowIndex+1, values);





                }

                bottomWorkbook.finish();
                bottomOutputStream.close();

                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Loyalstring files");
                File productDir = new File(dir, "sku reports");
                if (!productDir.exists()) {
                    if (!productDir.mkdirs()) {
                        Log.e("TAG", "Failed to create directory: " + productDir.getAbsolutePath());

                    }
                }

                File targetFile = new File(productDir, "stock report" + ".xlsx");

                try (FileInputStream in = new FileInputStream(outfile);
                     FileOutputStream out = new FileOutputStream(targetFile)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                } catch (IOException e) {
                    Log.e("TAG", "Error copying file: " + e.getMessage());
                }

                fileMap.put("stock report" + ".xlsx", outfile);

                return "Excel file created successfully";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error creating Excel file: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (result.equals("Excel file created successfully")) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Globalcomponents instance = new Globalcomponents();
                List<String> emails = instance.readallemails(context);
                showbottom(context, emails);
            } else {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                // Handle the case when Excel file creation fails
            }

        }


        private static void createRow(Worksheet sheet, int rowIndex, String[] values) {
            for (int i = 0; i < values.length; i++) {
                sheet.value(rowIndex, i, values[i]);
            }
        }

        private void showbottom(Context context, List<String> emails) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

            final String[][] emailsArray = {emails.toArray(new String[0])};
            bottomSheetDialog.setCancelable(false);
            bottomSheetDialog.setCancelable(false);
//        View contentView = inflater.getContext().getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
            View contentView = inflater.inflate(R.layout.bottom_sheet_layout, null);
            bottomSheetDialog.setContentView(contentView);
            ImageButton close = contentView.findViewById(R.id.closeButton);
            TextView title = contentView.findViewById(R.id.maintitle);
            Button addbtn = contentView.findViewById(R.id.additem);
            EditText itemname = contentView.findViewById(R.id.itemname);
            ListView spinnerlist = contentView.findViewById(R.id.spinnerlist);
            Button sendall = contentView.findViewById(R.id.allmails);
            sendall.setVisibility(View.VISIBLE);
            title.setText("Emails");
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, emails);
            spinnerlist.setAdapter(adapter);

            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemname.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (isValidGmailAddress(itemname.getText().toString())) {
                        Globalcomponents instance = new Globalcomponents();
                        boolean b = instance.insetemail(itemname.getText().toString().trim(), context);
                        if (b) {
                            Toast.makeText(context, "email address added", Toast.LENGTH_SHORT).show();
                            emailsArray[0] = instance.readallemails(context).toArray(new String[0]);
                            emails.clear();
                            emails.addAll(Arrays.asList(emailsArray[0]));
                            itemname.setText("");
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "failed to add email address", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "invalid email address", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            spinnerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (!networkUtils.isNetworkAvailable()) {
                        Toast.makeText(context, "Please check internet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String selecteditem = (String) adapterView.getItemAtPosition(position);
                    Toast.makeText(context, "Selected " + selecteditem + " : " + selecteditem, Toast.LENGTH_SHORT).show();

                    List<String> ems = new ArrayList<>();
                    ems.add(selecteditem);
//                Map<String, String> fileMap = new HashMap<>();
//                if (frag.equalsIgnoreCase("inventory")) {
////                    fileMap = new HashMap<>();
//                    fileMap.put("allitems.xlsx", topfilepath);
//                    fileMap.put("itemdetails.xlsx", bottomfilepath);
//                } else {
////                    Map<String, String> fileMap = new HashMap<>();
//                    fileMap.put("allitems.xlsx", topfilepath);
//                }
                    Globalcomponents.sendglobalemil sendEmailTask = new Globalcomponents.sendglobalemil(
                            "reports@loyalstring.com", "Loyal@321", ems, "loyal sting", "", fileMap, "inventory", context
                    );
                    sendEmailTask.execute();


                }
            });


            sendall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                multiple = true;
//                emaildialog.show();
//                sendEmails();
                }
            });
            bottomSheetDialog.show();

        }

    }
        public static boolean isValidGmailAddress(String email) {
            String regex = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b";
            return true;//Pattern.matches(regex, email);
        }





}
