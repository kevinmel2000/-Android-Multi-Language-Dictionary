package com.unsri.kamus;


import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	
	private final Context context;
	private DataHelper dbHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		this.context=ctx;
		dbHelper=new DataHelper(context);
	}
	public DBAdapter createDatabase()throws SQLException{
		try{
			dbHelper.createDatabase();
		}catch(IOException exc){
			throw new Error("unable to create database");
		}
		return this;
	}
	public DBAdapter open() throws SQLException{
		try{
			dbHelper.openDatabase();
			dbHelper.close();
			db=dbHelper.getReadableDatabase();
		}
		catch(SQLException sqle){
			throw sqle;
		}
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}	
	
	
	public Cursor getAlldata(String field,String input){
		try{
			String sql="select * from kamus where "+field+"='"+input+"'";
			Cursor cur=db.rawQuery(sql, null);
			if(cur!=null){
				cur.moveToNext();				
			}
			return cur;
		}catch(SQLException sqle){
			throw sqle;
		}
	}
}
