package com.example.loransmubarikyproj.Class;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import static com.example.loransmubarikyproj.DataBase.TablesString.CartTable.*;



public class Cart implements SqlInterface{

    private int cartid;
    private String uid;
    private int pid;
    private int amount;

    public Cart(String uid, int pid,int amount) {

        this.uid=uid;
        this.pid=pid;
        this.amount = amount;
    }
    public Cart(int cartid,String uid, int pid,int amount) {
        this.cartid=cartid;
        this.uid=uid;
        this.pid=pid;
        this.amount = amount;
    }

    public Cart() {

    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Cursor SelectByUserAndProductId(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_ID,
                COLUMN_USER_ID,
                COLUMN_AMOUNT
        };
        String selection = COLUMN_PRODUCT_ID + " = ? AND " +COLUMN_USER_ID+" = ?";
        String[] selectionArgs = new String[]{pid+"",uid};

        Cursor c = db.query(
                TABLE_CART,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null  );
        return c;
    }
    public Cursor SelectByUserId(SQLiteDatabase db,String id) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_ID,
                COLUMN_USER_ID,
                COLUMN_AMOUNT
        };
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {id};

        Cursor c = db.query(
                TABLE_CART,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null  );
        return c;
    }
    @Override
    public long Add(SQLiteDatabase db) {
        Cursor c = SelectByUserAndProductId(db);
        if(c.moveToFirst()){
            amount = c.getInt(c.getColumnIndexOrThrow(COLUMN_AMOUNT));
            return UpdateQuantity(db,c.getInt(c.getColumnIndexOrThrow(_ID)));
        }else{
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCT_ID, pid);
            values.put(COLUMN_USER_ID,uid);
            values.put(COLUMN_AMOUNT, amount);
// Insert the new row, returning the primary key value of the new row
            return db.insert(TABLE_CART, null, values);
        }
    }

    private int UpdateQuantity(SQLiteDatabase db,int id) {
        amount++;
        return Update(db,id);
    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {id+""};
// Issue SQL statement.
        return db.delete(TABLE_CART, selection, selectionArgs);
    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, pid);
        values.put(COLUMN_USER_ID,uid);
        values.put(COLUMN_AMOUNT, amount);
// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };

        return  db.update(
                TABLE_CART,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_PRODUCT_ID,
                COLUMN_USER_ID,
                COLUMN_AMOUNT
        };
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";
        Cursor c = db.query(TABLE_CART,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        return c;
    }
}
