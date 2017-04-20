package com.xmh.account.dataBase;


import com.xmh.account.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDB {
	
	private SQLOpenHelper SQLHelper_account;
	private SQLiteDatabase SQL_account;
	
	public UserDB(){
		SQLHelper_account=Application.getSQLHelper_account();
		SQL_account=SQLHelper_account.getReadableDatabase();
	}
	
	
    public String findUser(){
    	String result=null;
    	Cursor cursor;
		cursor=SQL_account.rawQuery("select password from users where (username='admin')", null);
		if(cursor.moveToFirst()){
			result=cursor.getString(0);
		}
    	return result;
    }
    
    public boolean setUser(String password){
    	boolean result=false;
    	try{
    		SQL_account.execSQL("update users set password='"+password+"' where username='admin'");
    		result=true;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
}
