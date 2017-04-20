package com.xmh.account.dataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xmh.account.Application;
import com.xmh.account.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsDB {

	private SQLOpenHelper SQLHelper_account;
	private SQLiteDatabase SQL_account;
	
	public GoodsDB(){
		SQLHelper_account=Application.getSQLHelper_account();
		SQL_account=SQLHelper_account.getReadableDatabase();
	}
	
	/**
	 * 买进
	 * @param goods 商品
	 * @return
	 */
	public boolean buy(Goods goods){
		boolean result=false;
		try{
			int id=0;
			Cursor cursor;
			//查询该商品是否存在与商品表
			cursor=SQL_account.rawQuery("select id from merchandises where (fallname='"+goods.getFallname()+"')and(in_price="+goods.getIn_price()+")", null);
			if(cursor.moveToFirst()){
				id=Integer.parseInt( cursor.getString(0));
			}
			//该商品不存在于商品表
			if(id==0){
				SQL_account.execSQL("insert into merchandises(fallname,in_price)values('"+goods.getFallname()+"',"+goods.getIn_price()+")");
				cursor=SQL_account.rawQuery("select id from merchandises where (fallname='"+goods.getFallname()+"')and(in_price="+goods.getIn_price()+")", null);
				if(cursor.moveToFirst()){
					id=Integer.parseInt( cursor.getString(0));
				}
			}
			
			//查询该商品是否存在与库存表
			cursor=SQL_account.rawQuery("select id from stocks where(Mid="+id+")",null);
			if(cursor.moveToFirst()){
				if(Integer.parseInt(cursor.getString(0))!=0){
					//存在则更新
					SQL_account.execSQL("update stocks set amount=amount+"+goods.getAmount()+" where Mid="+id);
				}else{
					//不存在则插入
					SQL_account.execSQL("insert into stocks(Mid,amount)values("+id+","+goods.getAmount()+")");
				}
			}else{
				//不存在则插入
				SQL_account.execSQL("insert into stocks(Mid,amount)values("+id+","+goods.getAmount()+")");
			}
			
			//添加到进货表（进货记录）
			SQL_account.execSQL("insert into buys(Mid,amount,remarks)values("+id+","+goods.getAmount()+",'"+goods.getRemarks()+"')");
			
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean sell(Goods goods){
		boolean result=false;
		try{
			int id=0;
			Cursor cursor;
			//查询商品表有无此商品
			cursor=SQL_account.rawQuery("select id from merchandises where (fallname='"+goods.getFallname()+"')", null);
			if(cursor.moveToFirst()){
				id=Integer.parseInt(cursor.getString(0));
			}else{
				return result;
			}
			//商品表无此商品
			if(id==0){
				return result;
			}
			
			//查询库存表该商品是否存在
			cursor=SQL_account.rawQuery("select id,amount from stocks where(Mid="+id+")",null);
			if(cursor.moveToFirst()){
				//判断是否有库存
				if(Integer.parseInt(cursor.getString(1))-goods.getAmount()>=0){
					//更新库存表
					SQL_account.execSQL("update stocks set amount=amount-"+goods.getAmount()+" where Mid="+id);
					//插入售货表
					SQL_account.execSQL("insert into sells(Mid,amount,out_price,remarks)values("+id+","+goods.getAmount()+","+goods.getOut_price()+",'"+goods.getRemarks()+"')");
				}else{
					return result;
				}
			}else{
				return result;
			}
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 当前库存
	 * @return
	 */
	public List<Goods> stock(){
		List<Goods> resultlist=new ArrayList<Goods>();
		
		Cursor cursor;
		cursor=SQL_account.rawQuery("select stocks.id,merchandises.fallname,stocks.amount,merchandises.in_price,stocks.remarks from merchandises,stocks where (stocks.Mid=merchandises.id)", null);
		while(cursor.moveToNext()){
			Goods goods=new Goods();
			goods.setId(Integer.parseInt(cursor.getString(0)));
			goods.setFallname(cursor.getString(1));
			goods.setAmount(Integer.parseInt(cursor.getString(2)));
			goods.setIn_price(Double.parseDouble(cursor.getString(3)));
			goods.setRemarks(cursor.getString(4));
			resultlist.add(goods);
		}
		return resultlist;
	}
	
	public List<Goods> stock(String key) {
		List<Goods> resultlist=new ArrayList<Goods>();
		Cursor cursor;
		cursor=SQL_account.rawQuery("select stocks.id,merchandises.fallname,stocks.amount,merchandises.in_price,stocks.remarks from merchandises,stocks where (stocks.Mid=merchandises.id)and(merchandises.fallname like '%"+key+"%')", null);
		while(cursor.moveToNext()){
			Goods goods=new Goods();
			goods.setId(Integer.parseInt(cursor.getString(0)));
			goods.setFallname(cursor.getString(1));
			goods.setAmount(Integer.parseInt(cursor.getString(2)));
			goods.setIn_price(Double.parseDouble(cursor.getString(3)));
			goods.setRemarks(cursor.getString(4));
			resultlist.add(goods);
		}
		return resultlist;
	}
	
	public boolean updateStockRemarks(Goods goods){
		boolean result=false;
		try{
			SQL_account.execSQL("update stocks set remarks='"+goods.getRemarks()+"' where id="+goods.getId());
			
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateBuyRemarks(Goods goods) {
		boolean result=false;
		try{
			SQL_account.execSQL("update buys set remarks='"+goods.getRemarks()+"' where id="+goods.getId());
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateSellRemarks(Goods goods) {
		boolean result=false;
		try{
			SQL_account.execSQL("update sells set remarks='"+goods.getRemarks()+"' where id="+goods.getId());
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteGoods(Goods goods) {
		boolean result=false;
		try{
			String sql="select id from merchandises where(fallname='"+goods.getFallname()+"')and(in_price="+goods.getIn_price()+")";
			sql="delete from stocks where Mid=("+sql+")";
			SQL_account.execSQL(sql);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
