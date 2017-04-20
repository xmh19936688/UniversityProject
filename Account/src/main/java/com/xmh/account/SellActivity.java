package com.xmh.account;

import java.util.Date;

import com.xmh.account.bean.Goods;
import com.xmh.account.service.SellService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SellActivity extends Activity{

	EditText et_fallname;
	EditText et_amount;
	EditText et_out_price;
	EditText et_remarks;
	Button btn_sell;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sell);
		
		InitView();
	}

	/**
	 * 初始化控件	
	 */
	private void InitView() {
		//初始化控件
		et_fallname=(EditText) findViewById(R.id.et_fallname);
		et_amount=(EditText) findViewById(R.id.et_amount);
		et_out_price=(EditText) findViewById(R.id.et_out_price);
		et_remarks=(EditText) findViewById(R.id.et_remarks);
		btn_sell=(Button) findViewById(R.id.btn_sell);
		//设置监听
		btn_sell.setOnClickListener(new OnSellListener());
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
		if(et_out_price.getText().toString()==null||et_out_price.getText().toString().equals(""))
			return false;
		
		return true;
	}
	
	/**
	 * 售出按钮监听
	 * @author XMH
	 *
	 */
	class OnSellListener implements OnClickListener{

		public void onClick(View button) {
			if(!checkInput()){
				Toast.makeText(SellActivity.this, "请输入完整信息", Toast.LENGTH_LONG).show();
				return;
			}
			Goods goods=new Goods();
			goods.setFallname(et_fallname.getText().toString());
			goods.setAmount(Integer.parseInt(et_amount.getText().toString()));
			goods.setDate(new Date());
			goods.setOut_price(Double.parseDouble(et_out_price.getText().toString()));
			goods.setRemarks(et_remarks.getText().toString());
			if(new SellService().sell(goods)){
				Toast.makeText(SellActivity.this, "销售成功", Toast.LENGTH_LONG).show();
				SellActivity.this.finish();
			}else{
				Toast.makeText(SellActivity.this, "库存不足或没有该商品", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}

}
