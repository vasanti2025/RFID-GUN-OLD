package com.loyalstring.Excels;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.fragment.app.FragmentActivity;

import com.loyalstring.Excels.reader.ReadableWorkbook;
import com.loyalstring.Excels.reader.Sheet;
import com.loyalstring.apiresponse.Rfidresponse;
import com.loyalstring.fsupporters.MyApplication;
import com.opencsv.CSVReader;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AllExcel {


    /*public void processsheet(Uri uri, FragmentActivity activity, ProgressDialog progressDialog, MyApplication app, List<Rfidresponse.ItemModel> rfidList) {

        AsyncTask.execute(() -> {
            try {
                activity.runOnUiThread(progressDialog::show);
                InputStream is = activity.getContentResolver().openInputStream(uri);
                String extension = getFileExtension(activity, uri);
                List<String> headings = new ArrayList<>();

                if ("xls".equalsIgnoreCase(extension)) {
                    // Handle .xls file
                    HSSFSheet sheet =
                    processXlsFile(is, headings);
                    if(!headings.isEmpty()) {
                        activity.runOnUiThread(() -> showSelection(headings, sheet, activity, progressDialog));
                    }
                } else if ("xlsx".equalsIgnoreCase(extension)) {
                    // Handle .xlsx file
                    Sheet sheet =
                    processXlsxFile(is, headings);
                    if(!headings.isEmpty()) {
                        activity.runOnUiThread(() -> showSelection(headings, sheet, activity, progressDialog));
                    }
                } else if ("csv".equalsIgnoreCase(extension)) {
                    // Handle .csv file
                    processCsvFile(is, headings);
                    if(!headings.isEmpty()) {
                        activity.runOnUiThread(() -> showSelection(headings, sheet1, activity, progressDialog));
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported file type");
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("processExcelInBackground", e.getMessage());
            } finally {
                activity.runOnUiThread(progressDialog::dismiss);
            }
        });


    }

    private HSSFSheet processXlsFile(InputStream is, List<String> headings) throws IOException {
        // Use Apache POI to handle .xls file
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);
        processSheet(sheet, null, headings);
        return sheet;
    }

    private Sheet processXlsxFile(InputStream is, List<String> headings) throws IOException {
        // Use Apache POI to handle .xlsx file
        ReadableWorkbook wb = new ReadableWorkbook(is);
        Sheet sheet1 = wb.getFirstSheet();
        processSheet(null, sheet1, headings);
        return sheet1;
    }

    private void processSheet(HSSFSheet lssheet, Sheet sxsheet, List<String> headings) {

        if (lssheet != null) {
            // Process the first row for .xls file
            HSSFRow row = lssheet.getRow(0);
            if (row != null) {
                for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                    HSSFCell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    headings.add(cell.toString());
                }
            }
        } else if (sxsheet != null) {
            // Process the first row for .xlsx file
            try (Stream<com.loyalstring.Excels.reader.Row> rows = sxsheet.openStream()) {
                rows.findFirst().ifPresent(row -> {
                    row.forEach(cell -> headings.add(cell.getText()));
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void processCsvFile(InputStream is, List<String> headings) throws IOException {
        // Use OpenCSV to handle .csv file
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        CSVReader csvReader = new CSVReader(reader);
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
//            processCsvRow(Arrays.asList(nextLine));
        }
    }

















    public static String getFileExtension(Context context, Uri uri) {
        String extension = null;

        // Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            // If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            // If scheme is a File
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        return extension;
    }*/

}
