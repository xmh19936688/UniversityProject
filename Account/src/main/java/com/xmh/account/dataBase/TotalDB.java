package com.xmh.account.dataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import com.xmh.account.Application;
import com.xmh.account.bean.Goods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TotalDB {

	private SQLOpenHelper SQLHelper_account;
	private SQLiteDatabase SQL_account;
	
	public TotalDB(){
		SQLHelper_account=Application.getSQLHelper_account();
		SQL_account=SQLHelper_account.getReadableDatabase();
	}
	
	public Double totalBuy(int limit){
		Double total=0.0;
		
		try{
			String sql="";
			//根据条件生成sql语句
			if(limit==Application.LIMIT_DAY){
				sql="select sum(merchandises.in_price*buys.amount) from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date == '"+DateFormat.format("yyyy-MM-dd", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_MONTH){
				sql="select sum(merchandises.in_price*buys.amount) from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date like '"+DateFormat.format("yyyy-MM%", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_TOTAL){
				sql="select sum(merchandises.in_price*buys.amount) from buys,merchandises where(buys.Mid==merchandises.id)";
			}else{
				return total;
			}
			//执行sql语句查询结果
			Cursor cursor;
			cursor=SQL_account.rawQuery(sql, null);
			if(cursor.moveToFirst()){
				total=Double.parseDouble(cursor.getString(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return total;
	}
	
	public Double totalSell(int limit){
		Double total=0.0;
		
		try{
			String sql="";
			//根据条件生成sql语句
			if(limit==Application.LIMIT_DAY){
				sql="select sum(out_price*amount) from sells where(out_date == '"+DateFormat.format("yyyy-MM-dd", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_MONTH){
				sql="select sum(out_price*amount) from sells where(out_date like '"+DateFormat.format("yyyy-MM%", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_TOTAL){
				sql="select sum(out_price*amount) from sells";
			}else{
				return total;
			}
			//执行sql语句查询结果
			Cursor cursor;
			cursor=SQL_account.rawQuery(sql, null);
			if(cursor.moveToFirst()){
				total=Double.parseDouble(cursor.getString(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return total;
	}
	
	public Double totalMoney(int limit){
		Double total=0.0;
		
		try{
			String sql="";
			//根据条件生成sql语句
			if(limit==Application.LIMIT_DAY){
				sql="select sum((sells.out_price-merchandises.in_price)*sells.amount) from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date == '"+DateFormat.format("yyyy-MM-dd", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_MONTH){
				sql="select sum((sells.out_price-merchandises.in_price)*sells.amount) from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date like '"+DateFormat.format("yyyy-MM%", new Date()).toString()+"')";
			}else if(limit==Application.LIMIT_TOTAL){
				sql="select sum((sells.out_price-merchandises.in_price)*sells.amount) from sells,merchandises where(sells.Mid==merchandises.id)";
			}else{
				return total;
			}
			//执行sql语句查询结果
			Cursor cursor;
			cursor=SQL_account.rawQuery(sql, null);
			if(cursor.moveToFirst()){
				total=Double.parseDouble(cursor.getString(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return total;
	}

	public List<Goods> buyListDay(Date date){
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> buyListDay(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> buyListMonth(Date date){
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> buyListMonth(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)and(buys.in_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> buyList(){
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> buyList(String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select buys.id,buys.in_date,merchandises.fallname,buys.amount,merchandises.in_price,buys.remarks from buys,merchandises where(buys.Mid==merchandises.id)and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setIn_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getIn_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> sellListDay(Date date) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> sellListDay(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> sellListMonth(Date date) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> sellListMonth(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> sellList() {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> sellList(String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks from sells,merchandises where(sells.Mid==merchandises.id)and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setTotal(goods.getOut_price()*goods.getAmount());
				goods.setRemarks(cursor.getString(5));
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> moneyListDay(Date date) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> moneyListDay(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date=='"+DateFormat.format("yyyy-MM-dd", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> moneyListMonth(Date date) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> moneyListMonth(Date date, String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)and(sells.out_date like '"+DateFormat.format("yyyy-MM%", date).toString()+"')and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

	public List<Goods> moneyList() {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}
	
	public List<Goods> moneyList(String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		try{
			Cursor cursor;
			cursor=SQL_account.rawQuery("select sells.id,sells.out_date,merchandises.fallname,sells.amount,sells.out_price,sells.remarks,merchandises.in_price from sells,merchandises where(sells.Mid==merchandises.id)and(merchandises.fallname like '%"+key+"%')", null);
			while(cursor.moveToNext()){
				Goods goods=new Goods();
				goods.setId(Integer.parseInt(cursor.getString(0)));
				goods.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(1)));
				goods.setFallname(cursor.getString(2));
				goods.setAmount(Integer.parseInt(cursor.getString(3)));
				goods.setOut_price(Double.parseDouble(cursor.getString(4)));
				goods.setRemarks(cursor.getString(5));
				goods.setIn_price(Double.parseDouble(cursor.getString(6)));
				goods.setMoney(goods.getOut_price()-goods.getIn_price());
				goods.setTotal(goods.getMoney()*goods.getAmount());
				resultlist.add(goods);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;
	}

}
