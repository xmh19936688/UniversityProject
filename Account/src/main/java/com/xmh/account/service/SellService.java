package com.xmh.account.service;

import com.xmh.account.bean.Goods;
import com.xmh.account.dataBase.GoodsDB;
import com.xmh.account.dataBase.TotalDB;

import java.util.Date;
import java.util.List;

public class SellService {

	public boolean sell(Goods goods){
		boolean result=false;
		try{
			if(new GoodsDB().sell(goods)){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public List<Goods> sellListDay(Date date) {
		List<Goods> list=new TotalDB().sellListDay(date);
		return list;
	}
	
	public List<Goods> sellListDay(Date date, String key) {
		List<Goods> list=new TotalDB().sellListDay(date,key);
		return list;
	}

	public List<Goods> sellListMonth(Date date) {
		List<Goods> list=new TotalDB().sellListMonth(date);
		return list;
	}
	
	public List<Goods> sellListMonth(Date date, String key) {
		List<Goods> list=new TotalDB().sellListMonth(date,key);
		return list;
	}

	public List<Goods> sellList() {
		List<Goods> list=new TotalDB().sellList();
		return list;
	}
	
	public List<Goods> sellList(String key) {
		List<Goods> list=new TotalDB().sellList(key);
		return list;
	}

	public boolean updateRemarks(Goods goods) {
		boolean result=false;
		try{
			if(new GoodsDB().updateSellRemarks(goods)){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}