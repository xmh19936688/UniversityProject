package com.xmh.account.dataBase;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLOpenHelper extends SQLiteOpenHelper{

	Context con;
	public SQLOpenHelper(Context context) {
		super(context, "account.db", null, 1);
	}

	/**
	 * 数据库首次建立时运行
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表
		db.execSQL("create table users(id integer primary key autoincrement,username text,password text)");
		db.execSQL("create table merchandises(id integer primary key autoincrement,fallname text,in_price double)");
		db.execSQL("create table stocks(id integer primary key autoincrement,Mid integer,amount integer,remarks text)");
		db.execSQL("create table buys(id integer primary key autoincrement,Mid integer,amount integer,in_date date DEFAULT CURRENT_DATE,remarks text)");
		db.execSQL("create table sells(id integer primary key autoincrement,Mid integer,amount integer,out_price double,out_date date DEFAULT CURRENT_DATE,remarks text)");
		//初始化数据
		db.execSQL("insert into users(username,password)values('admin','666')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
