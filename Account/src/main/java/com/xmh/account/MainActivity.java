package com.xmh.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity{
	
	Button btn_buy;
	Button btn_sell;
	Button btn_stock;
	Button btn_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		InitView();
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		//获取控件
		btn_buy=(Button) findViewById(R.id.btn_buy);
		btn_sell=(Button) findViewById(R.id.btn_sell);
		btn_stock=(Button) findViewById(R.id.btn_stock);
		btn_password=(Button) findViewById(R.id.btn_password);
		//添加事件
		btn_buy.setOnClickListener(new OnBuyListener());
		btn_sell.setOnClickListener(new OnSellListener());
		btn_stock.setOnClickListener(new OnStockListener());
		btn_password.setOnClickListener(new OnPasswordListener());
	}
	
	/**
	 * 进货按钮监听
	 * @author XMH
	 *
	 */
	class OnBuyListener implements OnClickListener{

		public void onClick(View button) {
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, BuyActivity.class);
			startActivity(intent);
		}
		
	}
	
	/**
	 * 销售按钮监听
	 * @author XMH
	 *
	 */
	class OnSellListener implements OnClickListener{

		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, SellActivity.class);
			startActivity(intent);
		}
		
	}
	
	/**
	 * 库存按钮监听
	 * @author XMH
	 *
	 */
	class OnStockListener implements OnClickListener{

		public void onClick(View button) {
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, StockActivity.class);
			startActivity(intent);
		}
		
	}
	
	/**
	 * 修改密码按钮监听
	 * @author XMH
	 *
	 */
	class OnPasswordListener implements OnClickListener{

		public void onClick(View button) {
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, SetPasswordActivity.class);
			startActivity(intent);
		}
		
	}
}
