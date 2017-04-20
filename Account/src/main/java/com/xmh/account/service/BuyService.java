package com.xmh.account.service;

import com.xmh.account.bean.Goods;
import com.xmh.account.dataBase.GoodsDB;
import com.xmh.account.dataBase.TotalDB;

import java.util.Date;
import java.util.List;

public class BuyService {
	
	public boolean buy(Goods goods){
		boolean result=false;
		try{
			if(new GoodsDB().buy(goods)){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public List<Goods> buyListDay(Date date){
		List<Goods> list=new TotalDB().buyListDay(date);
		return list;
	}
	
	public List<Goods> buyListDay(Date date, String key) {
		List<Goods> list=new TotalDB().buyListDay(date,key);
		return list;
	}
	
	public List<Goods> buyListMonth(Date date){
		List<Goods> list=new TotalDB().buyListMonth(date);
		return list;
	}
	
	public List<Goods> buyListMonth(Date date, String key) {
		List<Goods> list=new TotalDB().buyListMonth(date,key);
		return list;
	}
	
	public List<Goods> buyList(){
		List<Goods> list=new TotalDB().buyList();
		return list;
	}
	
	public List<Goods> buyList(String key) {
		List<Goods> list=new TotalDB().buyList(key);
		return list;
	}
	
	public List<Goods> buyListMonth(){
		List<Goods> list=null;
		return list;
	}

	public boolean updateRemarks(Goods goods) {
		boolean result=false;
		try{
			if(new GoodsDB().updateBuyRemarks(goods)){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
