package com.xmh.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xmh.account.service.TotalService;

public class TotalActivity extends Activity{
	
	Button btn_total_out;
	Button btn_total_in;
	Button btn_total_money;
	int limit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.total);
		//初始化控件
		InitView();
		//获取限制
		Intent intent=getIntent();
		Bundle bundle = intent.getExtras();
		limit=bundle.getInt("limit");
		//更新界面
		btn_total_out.setText("货款："+new TotalService().totalBuy(limit).toString()+"￥");
		btn_total_in.setText("收入："+new TotalService().totalSell(limit).toString()+"￥");
		btn_total_money.setText("盈利："+new TotalService().totalMoney(limit).toString()+"￥");
		
	}

	private void InitView() {
		//获取控件
		btn_total_out=(Button) findViewById(R.id.btn_total_out);
		btn_total_in=(Button) findViewById(R.id.btn_total_in);
		btn_total_money=(Button) findViewById(R.id.btn_total_money);
		//添加监听
		btn_total_out.setOnClickListener(new OnTotalListListener());
		btn_total_in.setOnClickListener(new OnTotalListListener());
		btn_total_money.setOnClickListener(new OnTotalListListener());
	}
	
	/**
	 * totallist按钮监听
	 * @author XMH
	 *
	 */
	class OnTotalListListener implements OnClickListener{

		public void onClick(View view) {
			Button button=(Button)view;
			Intent intent=new Intent();
			intent.putExtra("limit", limit);
			
			switch (button.getId()) {
			case R.id.btn_total_out:
				intent.setClass(TotalActivity.this, BuyListActivity.class);
				break;
			case R.id.btn_total_in:
				intent.setClass(TotalActivity.this, SellListActivity.class);
				break;
			case R.id.btn_total_money:
				intent.setClass(TotalActivity.this, MoneyListActivity.class);
				break;
			}
			
			startActivity(intent);
			TotalActivity.this.finish();
		}
		
	}

}
