package com.xmh.account.service;

import com.xmh.account.dataBase.TotalDB;

public class TotalService {
	
	public Double totalBuy(int limit){
		Double total=0.0;
		
		total=new TotalDB().totalBuy(limit);
		
		return total;
	}
	
	public Double totalSell(int limit){
		Double total=0.0;
		
		total=new TotalDB().totalSell(limit);
		
		return total;
	}
	
	public Double totalMoney(int limit){
		Double total=0.0;
		
		total=new TotalDB().totalMoney(limit);
		
		return total;
	}

}
