package com.xmh.account;


import java.util.Date;

import com.xmh.account.bean.Goods;
import com.xmh.account.service.BuyService;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuyActivity extends Activity{

	EditText et_fallname;
	EditText et_amount;
	EditText et_in_price;
	EditText et_remarks;
	Button btn_buy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy);
		
		InitView();
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		//初始化控件
		et_fallname=(EditText) findViewById(R.id.et_fallname);
		et_amount=(EditText) findViewById(R.id.et_amount);
		et_in_price=(EditText) findViewById(R.id.et_in_price);
		et_remarks=(EditText) findViewById(R.id.et_remarks);
		btn_buy=(Button) findViewById(R.id.btn_buy);
		//设置监听
		btn_buy.setOnClickListener(new OnBuyListener());
	}
	
	/**
	 * 检查输入
	 * @return
	 */
	private boolean checkInput() {
		if(et_fallname.getText().toString()==null||et_fallname.getText().toString().equals(""))
			return false;
		if(et_amount.getText().toString()==null||et_amount.getText().toString().equals(""))
			return false;
		if(et_in_price.getText().toString()==null||et_in_price.getText().toString().equals(""))
			return false;
		
		return true;
	}
	
	/**
	 * 买进按钮监听
	 * @author XMH
	 *
	 */
	class OnBuyListener implements OnClickListener{

		public void onClick(View button) {
			if(!checkInput()){
				Toast.makeText(BuyActivity.this, "请输入完整信息", Toast.LENGTH_LONG).show();
				return;
			}
			Goods goods=new Goods();
			goods.setFallname(et_fallname.getText().toString());
			goods.setAmount( Integer.parseInt( et_amount.getText().toString()));
			goods.setDate(new Date());
			goods.setIn_price(Double.parseDouble(et_in_price.getText().toString()));
			goods.setTotal(goods.getAmount()*goods.getIn_price());
			goods.setRemarks(et_remarks.getText().toString());
			if(new BuyService().buy(goods)){
				Toast.makeText(BuyActivity.this, "进货成功", Toast.LENGTH_LONG).show();
				BuyActivity.this.finish();
			}
		}
	}
}
