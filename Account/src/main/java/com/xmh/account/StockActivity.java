package com.xmh.account;

import java.util.ArrayList;
import java.util.List;

import com.xmh.account.adapter.StockListAdapter;
import com.xmh.account.bean.Goods;
import com.xmh.account.service.StockService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StockActivity extends Activity{

	//明细选择按钮
	Button btn_today_total;
	Button btn_month_total;
	Button btn_total;
	//列表信息
	ListView lv_stock;
	StockListAdapter adapter;
	//搜索相关
	TextView tv_fallname;
	EditText et_key;
	//item控件
	LinearLayout ll_remarks_input;
	EditText et_remarks;
	TextView tv_remarks;
	TextView tv_id;
	String remarks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock);
		
		InitView();
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		//初始化控件
		btn_today_total=(Button) findViewById(R.id.btn_today_total);
		btn_month_total=(Button) findViewById(R.id.btn_month_total);
		btn_total=(Button) findViewById(R.id.btn_total);
		lv_stock=(ListView) findViewById(R.id.lv_stock);
		tv_fallname=(TextView) findViewById(R.id.tv_fallname);
		et_key=(EditText) findViewById(R.id.et_key);
		//设置监听
		btn_today_total.setOnClickListener(new OnTotalListener());
		btn_month_total.setOnClickListener(new OnTotalListener());
		btn_total.setOnClickListener(new OnTotalListener());
		lv_stock.setOnItemClickListener(new OnStoclListItemClickListener());
		lv_stock.setOnItemLongClickListener(new OnStoclListItemLongClickListener());
		tv_fallname.setOnClickListener(new OnFallnameClickListener());
		et_key.addTextChangedListener(new OnKeyChangedListener());
		//绑定数据
		List<Goods> stocklist=new StockService().stock();
		adapter=new StockListAdapter(this, stocklist);
		lv_stock.setAdapter(adapter);
	}
	
	/**
	 * 关键字改变监听
	 * @author XMH
	 *
	 */
	class OnKeyChangedListener implements TextWatcher{

		public void afterTextChanged(Editable view) {
			
		}

		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void onTextChanged(CharSequence key, int arg1, int arg2,
				int arg3) {
			lv_stock.setAdapter(new StockListAdapter(StockActivity.this, new StockService().stock(key.toString())));
		}
		
	}
	
	/**
	 * 全名点击监听
	 * @author XMH
	 *
	 */
	class OnFallnameClickListener implements OnClickListener{

		public void onClick(View view) {
			view.setVisibility(View.GONE);
			et_key.setVisibility(View.VISIBLE);
			et_key.requestFocus();
		}
		
	}
	
	/**
	 * 日结监听
	 * @author XMH
	 *
	 */
	class OnTotalListener implements OnClickListener{

		public void onClick(View view) {
			int limit=0;//标记日结、月结、总结
			Button button=(Button)view;
			switch (button.getId()) {
			case R.id.btn_today_total:
				limit=Application.LIMIT_DAY;
				break;
			case R.id.btn_month_total:
				limit=Application.LIMIT_MONTH;
				break;
			case R.id.btn_total:
				limit=Application.LIMIT_TOTAL;
				break;
			}
			Intent intent=new Intent();
			intent.setClass(StockActivity.this, TotalActivity.class);
			intent.putExtra("limit", limit);
			startActivity(intent);
		}
		
	}
	 
	/**
	 * 列表项点击监听
	 * @author XMH
	 *
	 */
	class OnStoclListItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> arg0, View view, int index,
				long arg3) {
			//如果已存在输入框（已点击某一行）
			if(!(ll_remarks_input==null)){
				ll_remarks_input.setVisibility(View.GONE);
			}
			//获取控件
			ll_remarks_input=(LinearLayout) view.findViewById(R.id.ll_remarks_input);
			et_remarks=(EditText) view.findViewById(R.id.et_remarks);
			et_remarks.setLongClickable(false);
			tv_remarks=(TextView) view.findViewById(R.id.tv_remarks);
			tv_id=(TextView) view.findViewById(R.id.tv_id);
			//显示备注输入区
			et_remarks.setText(tv_remarks.getText());
			ll_remarks_input.setVisibility(View.VISIBLE);
			
			//设置按钮监听
			Button btn_sure=(Button) view.findViewById(R.id.btn_sure_remarks);
			btn_sure.setOnClickListener(new OnSureRemarksListener());
			//获取焦点
			et_remarks.requestFocus();
		}
		
	}
	
	class OnStoclListItemLongClickListener implements OnItemLongClickListener{

		public boolean onItemLongClick(AdapterView<?> arg0, View view,
				int arg2, long arg3) {
			TextView tv_fallname=(TextView) view.findViewById(R.id.tv_fallname);
			TextView tv_in_price=(TextView) view.findViewById(R.id.tv_in_price);
			final String fallname=tv_fallname.getText().toString();
			final Double in_price=Double.parseDouble(tv_in_price.getText().toString());
			//弹出确认对话框
			AlertDialog.Builder dialog=new AlertDialog.Builder(StockActivity.this);
			dialog.setTitle("删除商品信息");
			//android.R.drawable.ic_dialog_info
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			dialog.setMessage("确定删除进价为“"+in_price+"”商品“"+fallname+"”吗？");
			dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) {
					//删除
					Goods goods=new Goods();
					goods.setFallname(fallname);
					goods.setIn_price(in_price);
					if(new StockService().deleteGoods(goods)){
						Toast.makeText(StockActivity.this, "已删除该商品库存信息，该商品进销信息被保留", Toast.LENGTH_LONG).show();
						//更新界面
						lv_stock.setAdapter(new StockListAdapter(StockActivity.this, new StockService().stock()));
					}
				}
			});
			dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int arg1) {
					//取消
					 dialog.cancel();
				}
			});
			dialog.create().show();
			//对话框返回确认则删除该商品数据
			return false;
		}
		
	}
	
	/**
	 * √按钮监听
	 * @author XMH
	 *
	 */
	class OnSureRemarksListener implements OnClickListener{

		public void onClick(View button) {
			
			remarks=et_remarks.getText().toString();
			tv_remarks.setText(remarks);
			//空间中id是stock的id
			Goods goods=new Goods();
			goods.setId(Integer.parseInt(tv_id.getText().toString()));
			goods.setRemarks(remarks);
			if(new StockService().updateRemarks(goods)){
				Toast.makeText(StockActivity.this, "已更新备注", Toast.LENGTH_LONG).show();
			}
			ll_remarks_input.setVisibility(View.GONE);
			//更新数据
			adapter=new StockListAdapter(StockActivity.this, new StockService().stock());
			lv_stock.setAdapter(adapter);
		}
		
	}
	
	/**
	 * 返回键监听
	 */
	@Override
	public void onBackPressed() {
		//如果批准驳回按钮可视，则隐藏按钮，否则执行返回事件
		if(ll_remarks_input!=null){
			if(ll_remarks_input.getVisibility()==View.VISIBLE){
				ll_remarks_input.setVisibility(View.GONE);
				return;
			}
		}
		if(et_key!=null){
			if(et_key.getVisibility()==View.VISIBLE){
				et_key.setVisibility(View.GONE);
				tv_fallname.setVisibility(View.VISIBLE);
				//更新列表
				lv_stock.setAdapter(new StockListAdapter(this, new StockService().stock()));
				return;
			}
		}
		super.onBackPressed();
	}
}
