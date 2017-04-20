package com.xmh.account.service;

import com.xmh.account.bean.Goods;
import com.xmh.account.dataBase.GoodsDB;
import com.xmh.account.dataBase.TotalDB;

import java.util.Date;
import java.util.List;

public class MoneyService {

	public List<Goods> moneyListDay(Date date) {
		List<Goods> list=new TotalDB().moneyListDay(date);
		return list;
	}
	
	public List<Goods> moneyListDay(Date date, String key) {
		List<Goods> list=new TotalDB().moneyListDay(date,key);
		return list;
	}

	public List<Goods> moneyListMonth(Date date) {
		List<Goods> list=new TotalDB().moneyListMonth(date);
		return list;
	}
	
	public List<Goods> moneyListMonth(Date date, String key) {
		List<Goods> list=new TotalDB().moneyListMonth(date,key);
		return list;
	}

	public List<Goods> moneyList() {
		List<Goods> list=new TotalDB().moneyList();
		return list;
	}
	
	public List<Goods> moneyList(String key) {
		List<Goods> list=new TotalDB().moneyList(key);
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
