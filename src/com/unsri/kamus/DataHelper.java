package com.unsri.kamus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper{
	private static String TAG="DataHelper";
	private static final String DATABASE_PATH="/data/data/com.unsri.kamus/databases/";
	private static final String DATABASE_NAME="dbKamus.sqlite";
	private static final int DATABASE_VERSION=1;
	
	private final Context context;
	private SQLiteDatabase db;
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		
	}
	
	public DataHelper(Context ctx){
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
		this.context=ctx;
	}
	
	private boolean checkDatabase(){
		File dbFile=new File(DATABASE_PATH+DATABASE_NAME);
		return dbFile.exists();
	}
	
	public void createDatabase()throws IOException{
		boolean mDatabaseExist=checkDatabase();
		if(!mDatabaseExist){
			this.getReadableDatabase();
			this.close();
			try{
				copyDatabase();
				Log.e(TAG,"databasecreated");
			}
			catch(IOException exc){
				throw new Error("ErrorCopyingDatabase");
			}
		}
	}
	
	private void copyDatabase()throws IOException{
		InputStream input=context.getAssets().open(DATABASE_NAME);
		String outFileName=DATABASE_PATH+DATABASE_NAME;
		OutputStream output=new FileOutputStream(outFileName);
		byte[] buffer=new byte[1024];
		int length;
		while((length=input.read(buffer))>0){
			output.write(buffer,0,length);
		}
		output.flush();
		output.close();
		input.close();
	}
	public boolean openDatabase()throws SQLException{
		String path=DATABASE_PATH+DATABASE_NAME;
		db=SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		return db!=null;
	}
	@Override
	public synchronized void close(){
		if(db!=null){
			db.close();
		}
		super.close();
	}

}
