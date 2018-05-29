package com.example.cyl.alltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, DbContent.DATABASE_NAME, null, DbContent.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createNameTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        destroyNameTable(db);
        onCreate(db);
    }

    //建表
    private void createNameTable(SQLiteDatabase db) {
        db.execSQL(DbContent.NameTable.CREATE_NAME_TABLE);
    }

    //删表
    private void destroyNameTable(SQLiteDatabase db) {
        db.execSQL(DbContent.NameTable.DROP_NAME_TABLE);
    }

    //获取数据库
    public synchronized SQLiteDatabase getDb() {
        return this.getWritableDatabase();
    }

    /*//获取数据库
    public SQLiteDatabase getNameDb() {
        if (mDatabase == null) {
            synchronized (this) {
                if (mDatabase == null) {
                    mDatabase = super.getWritableDatabase();
                }
            }
        }
        return mDatabase;
    }

    SQLiteDatabase mDatabase;*/

    //###########################################数据库的 增删改查 ##############################################

    /**
     * 插入一条数据
     * @param table
     * @param values
     * @return
     */
    public boolean insert(String table, ContentValues values) {
        Log.d(TAG, "Insert data to flash DB. table:" + table);
        if (values == null || TextUtils.isEmpty(table)) {
            return false;
        }
        SQLiteDatabase db = this.getDb();
        if (db == null) {
            return false;
        }
        try {
            return db.insert(table, null, values) >= 0;
        } catch (Exception e) {
            Log.e(TAG, "Insert data to client fail!", e);
            return true;
        }
    }

    /**
     * Batch insert new data.
     */
    public int bulkInsert(String table, ContentValues[] values) {
        Log.d(TAG, "Batch insert data to flash DB. table:" + table);
        if (values == null || TextUtils.isEmpty(table)) {
            return -1;
        }
        SQLiteDatabase db = this.getDb();
        if (db == null) {
            return -1;
        }
        int numValues = values.length;
        try {
            // start transaction
            db.beginTransaction();
            try {
                for (int i = 0; i < numValues; i++) {
                    if (values[i] != null) {
                        db.insert(table, null, values[i]);
                    }
                }
                // Marks the current transaction as successful
                db.setTransactionSuccessful();
            } finally {
                // Commit or rollback by flag of transaction
                db.endTransaction();
            }
        } catch (Exception e) {
            Log.e(TAG, "BulkUpdate fail! table: " + table, e);
            return -1;
        }
        return numValues;
    }

    /**
     * 删除
     * @param table
     * @param selection
     * @param selectionArgs
     * @return
     *
     */
    public int delete(String table, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "Delete data on flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return -1;
        }
        SQLiteDatabase db = this.getDb();
        if (db == null) {
            return -1;
        }
        try {
            return db.delete(table, selection, selectionArgs);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Delete fail!", e);
            return -1;
        }
    }

    /**
     * 更新
     * @param table
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    public int update(String table, ContentValues values,
                      String selection, String[] selectionArgs) {
        int result = 0;
        Log.d(TAG, "Update data on flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return -1;
        }
        SQLiteDatabase db = this.getDb();
        if (db == null) {
            return -1;
        }
        if (values == null && selection != null) {
            // execute a SQL
            db.execSQL(selection);
            return 1;
        } else if (values == null) {
            return -1;
        } else {
            result = db.update(table, values, selection, selectionArgs);
        }
        return result;
    }

    /**
     * 查找数据
     * @param table
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public Cursor query(String table, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query data from flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return null;
        }
        SQLiteDatabase db = this.getDb();
        if (db == null) {
            return null;
        }
        Cursor queryResult = null;
        try {
            queryResult = db.query(table, projection, selection, selectionArgs,
                    null, null, sortOrder,null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Query SQL error:" + e.getMessage(), e);
        } catch (SQLiteException e) {
            Log.e(TAG, "Query database error!", e);
        } catch (SQLException e) {
            Log.e(TAG, "Query fail!", e);
        }
        return queryResult;
    }
}
