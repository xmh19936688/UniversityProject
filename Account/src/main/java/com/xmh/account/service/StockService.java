package com.xmh.account.service;

import com.xmh.account.bean.Goods;
import com.xmh.account.dataBase.GoodsDB;

import java.util.List;

public class StockService {

	public List<Goods> stock(){
		List<Goods> resultlist=new GoodsDB().stock();
		
		return resultlist;
	}
	
	public List<Goods> stock(String key) {
		List<Goods> resultlist=new GoodsDB().stock(key);
		
		return resultlist;
	}
	
	public boolean updateRemarks(Goods goods){
		boolean result=false;
		if(new GoodsDB().updateStockRemarks(goods)){
			result=true;
		}
		return result;
	}
	
	public boolean deleteGoods(Goods goods){
		boolean result=false;
		if(new GoodsDB().deleteGoods(goods)){
			result=true;
		}
		return result;
	}
}
