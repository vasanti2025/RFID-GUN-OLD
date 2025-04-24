package com.loyalstring.LatestDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.loyalstring.LatestApis.LoginApiSupport.Clients;
import com.loyalstring.LatestApis.LoginApiSupport.Employee;
import com.loyalstring.LatestApis.LoginResponse;
import com.loyalstring.LatestCallBacks.ActivationCallback;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.modelclasses.Itemmodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "loyalstring.db";
    private static final int DATABASE_VERSION = 1;

    private ExecutorService executorService;

    String logintable = "LoginTable";
    String clienttable = "ClientTable";


    public LoginDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Any other initialization if needed
        executorService = Executors.newFixedThreadPool(8);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void checkdatabase(Context mContext) {
        EntryDatabase entryDatabase = new EntryDatabase(mContext);
        SQLiteDatabase db = entryDatabase.getWritableDatabase();
        createalltable(db, Employee.class, logintable);
createalltable(db, Clients.class, clienttable);

    }




    private void createalltable(SQLiteDatabase db, Class<?> modelClass, String tableName) {
        // Check if the table already exists
        if (!isTableExists(db, tableName)) {
            // Get the model class fields
            Field[] fields = modelClass.getDeclaredFields();

            // StringBuilder to create the SQL CREATE TABLE statement
            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");
            createTableQuery.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");

            // Iterate over model class fields and add columns to the CREATE TABLE statement
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String columnName = field.getName();
//                if (!columnName.equals("id")) {
                Class<?> fieldType = field.getType();

                createTableQuery.append(columnName).append(" ").append(getSqliteType(fieldType));

                if (i < fields.length - 1) {
                    createTableQuery.append(", ");
                }
//                }
            }

            // Close the CREATE TABLE statement
            createTableQuery.append(");");

            // Execute the CREATE TABLE query
            db.execSQL(createTableQuery.toString());
        } else {
            addMissingColumns(db, modelClass, tableName);

        }
    }
    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{tableName});
        boolean tableExists = cursor.moveToFirst();
        cursor.close();
        return tableExists;
    }
    private void addMissingColumns(SQLiteDatabase db, Class<?> modelClass, String tableName) {
        // Get existing columns
        List<String> existingColumns = getExistingColumns(db, tableName);


        // Get the model class fields
        Field[] fields = modelClass.getDeclaredFields();

//        Log.e("checkcoloumname", existingColumns.toString()+ "  "+fields.toString());

        // Iterate over model class fields and check for missing columns
        for (Field field : fields) {
            String columnName = field.getName();
            if (!existingColumns.contains(columnName)) {
                // Column is missing, add it
                Class<?> fieldType = field.getType();
                String alterTableQuery = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + getSqliteType(fieldType) + ";";
                db.execSQL(alterTableQuery);
                Log.d("addColumn", "Added column " + columnName + " to table " + tableName);
            }
        }
    }

    private List<String> getExistingColumns(SQLiteDatabase db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex("name");
            if (columnIndex != -1) { // Check if the column exists
                String columnName = cursor.getString(columnIndex);
                columns.add(columnName);
            } else {
                Log.e("DatabaseError", "Column 'name' does not exist in the table info.");
            }
        }
        cursor.close();
        return columns;
    }

    private String getSqliteType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "TEXT";
        } else if (fieldType == long.class || fieldType == Date.class) {
            return "INTEGER";
        } else if (fieldType == double.class) {
            return "REAL";
        } else if (fieldType == boolean.class) {
            return "INTEGER"; // SQLite does not have a boolean type, using INTEGER (0 or 1)
        } else {
            // Add additional cases as needed for other data types
            return "TEXT"; // Default to TEXT if the type is not recognized
        }
    }


    public void ClientOnboarding(LoginResponse response, ActivationCallback activationCallback) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Itemmodel> failedItems = new ArrayList<>();
                Log.e("check processing items", "  ");
                String errorMessage = insertEmployee(response.getEmployee(), response.getEmployee().getClients());
                // Notify the callback on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (errorMessage == null) {
                        activationCallback.onSaveSuccess();
                    } else {
                        activationCallback.onFailed(errorMessage);
                    }
                });
            }
        });

    }



    private String insertEmployee(Employee employee, Clients clients) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();  // Start a database transaction

        try {
            // Prepare the SQL statement for Employee
            Field[] fields = Employee.class.getDeclaredFields();
            StringBuilder columnNames = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();
            List<Object> valuesList = new ArrayList<>(); // Use a list for dynamic sizing

            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase("id")) {
                    continue; // Skip the ID field
                }

                if (columnNames.length() > 0) {
                    columnNames.append(", ");
                    placeholders.append(", ");
                }
                columnNames.append(field.getName()); // Column name
                placeholders.append("?"); // Placeholder for value

                // Get the value for the prepared statement
                field.setAccessible(true); // Make private fields accessible
                valuesList.add(field.get(employee)); // Assuming employee object is provided
            }

            // Convert list to array
            Object[] values = valuesList.toArray(new Object[0]);

            // Log for debugging
            Log.d("InsertEmployee", "Column Names: " + columnNames.toString());
            Log.d("InsertEmployee", "Placeholders: " + placeholders.toString());
            Log.d("InsertEmployee", "Values Count: " + values.length);

            // Construct the SQL statement
            String sql = "INSERT INTO " + logintable + " (" + columnNames + ") VALUES (" + placeholders + ")";
            SQLiteStatement insertStatement = db.compileStatement(sql);

            // Bind the values to the prepared statement
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    insertStatement.bindString(i + 1, values[i].toString());
                } else {
                    insertStatement.bindNull(i + 1); // Use bindNull for null values
                }
            }
            insertStatement.executeInsert();

            // Prepare the SQL statement for Clients
            Field[] cfields = Clients.class.getDeclaredFields();
            StringBuilder ccolumnNames = new StringBuilder();
            StringBuilder cplaceholders = new StringBuilder();
            List<Object> cvaluesList = new ArrayList<>(); // Use a list for dynamic sizing

            for (Field cfield : cfields) {
                if (cfield.getName().equalsIgnoreCase("id")) {
                    continue; // Skip the ID field
                }

                if (ccolumnNames.length() > 0) {
                    ccolumnNames.append(", ");
                    cplaceholders.append(", ");
                }
                ccolumnNames.append(cfield.getName()); // Column name
                cplaceholders.append("?"); // Placeholder for value

                // Get the value for the prepared statement
                cfield.setAccessible(true); // Make private fields accessible
                cvaluesList.add(cfield.get(clients)); // Assuming clients object is provided
            }

            // Convert list to array
            Object[] cvalues = cvaluesList.toArray(new Object[0]);

            // Log for debugging
            Log.d("InsertClients", "Column Names: " + ccolumnNames.toString());
            Log.d("InsertClients", "Placeholders: " + cplaceholders.toString());
            Log.d("InsertClients", "Client Values Count: " + cvalues.length);

            // Construct the SQL statement for clients
            String csql = "INSERT INTO " + clienttable + " (" + ccolumnNames + ") VALUES (" + cplaceholders + ")";
            SQLiteStatement cinsertStatement = db.compileStatement(csql);

            // Bind the values to the prepared statement for clients
            for (int i = 0; i < cvalues.length; i++) {
                if (cvalues[i] != null) {
                    cinsertStatement.bindString(i + 1, cvalues[i].toString());
                } else {
                    cinsertStatement.bindNull(i + 1); // Use bindNull for null values
                }
            }
            cinsertStatement.executeInsert();

            db.setTransactionSuccessful(); // Mark the transaction as successful
            return null; // Indicate successful insertion
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            return e.getMessage(); // Indicate failure
        } finally {
            db.endTransaction(); // Always end the transaction
        }
    }

    private void bindValuesToStatement(SQLiteStatement insertStatement, Itemmodel item, Field[] fields) {
        int index = 1; // Start binding from 1
        for (Field field : fields) {
            try {
                field.setAccessible(true); // Access private fields
                Object value = field.get(item); // Get the value of the field
                // Check the type of value and bind it accordingly
                if (value instanceof Long) {
                    insertStatement.bindLong(index++, (Long) value); // Bind Long value
                } else if (value instanceof String) {
                    insertStatement.bindString(index++, (String) value); // Bind String value
                } else if (value instanceof Double) {
                    insertStatement.bindDouble(index++, (Double) value); // Bind Double value
                }
                // You can add more conditions for other types as necessary
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle access issues
            }
        }
    }
}
