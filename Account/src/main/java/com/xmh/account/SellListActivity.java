package com.xmh.account;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xmh.account.StockActivity.OnFallnameClickListener;
import com.xmh.account.StockActivity.OnKeyChangedListener;
import com.xmh.account.adapter.SellListAdapter;
import com.xmh.account.adapter.StockListAdapter;
import com.xmh.account.bean.Goods;
import com.xmh.account.service.SellService;
import com.xmh.account.service.StockService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SellListActivity extends Activity{

	//日期选择
	Button btn_back;
	Button btn_find;
	Button btn_forward;
	LinearLayout ll_list_select;
	//日期定向
	EditText et_year;
	EditText et_month;
	EditText et_day;
	Button btn_sure_date;
	LinearLayout ll_date_select;
	//列表相关
	ListView lv_sell;
	SellListAdapter adapter;
	//备注输入
	LinearLayout ll_remarks_input;
	EditText et_remarks;
	TextView tv_remarks;
	TextView tv_id;
	String remarks;
	//搜索相关
	TextView tv_fallname;
	EditText et_key;
	//基本信息
	int limit;
	List<Goods> selllist;
	Date currentDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selllist);
		
		Intent intent=getIntent();
		limit=intent.getExtras().getInt("limit");
		currentDate=new Date();
		
		InitView();
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		//初始化控件
		btn_back=(Button) findViewById(R.id.btn_back);
		btn_find=(Button) findViewById(R.id.btn_find);
		btn_forward=(Button) findViewById(R.id.btn_forward);
		lv_sell=(ListView) findViewById(R.id.lv_sell);
		ll_list_select=(LinearLayout) findViewById(R.id.ll_list_select);
		
		et_year=(EditText) findViewById(R.id.et_year);
		et_month=(EditText) findViewById(R.id.et_month);
		et_day=(EditText) findViewById(R.id.et_day);
		btn_sure_date=(Button) findViewById(R.id.btn_sure_date);
		ll_date_select=(LinearLayout) findViewById(R.id.ll_date_select);
		
		tv_fallname=(TextView) findViewById(R.id.tv_fallname);
		et_key=(EditText) findViewById(R.id.et_key);
		//设置监听
		btn_back.setOnClickListener(new OnBackListener());
		btn_find.setOnClickListener(new OnFindListener());
		btn_forward.setOnClickListener(new OnForwardListener());
		btn_sure_date.setOnClickListener(new OnDateSureListener());
		lv_sell.setOnItemClickListener(new OnSellListItemClickListener());
		
		tv_fallname.setOnClickListener(new OnFallnameClickListener());
		et_key.addTextChangedListener(new OnKeyChangedListener());
		//显示数据
		showList();
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
			showList(key.toString());
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
	 * 更新界面
	 */
	private void showList() {
		switch(limit){
		case Application.LIMIT_DAY:
			selllist=new SellService().sellListDay(currentDate);
			btn_find.setText(DateFormat.format("yyyy-MM-dd", currentDate));
			break;
		case Application.LIMIT_MONTH:
			selllist=new SellService().sellListMonth(currentDate);
			btn_find.setText(DateFormat.format("yyyy-MM", currentDate));
			break;
		case Application.LIMIT_TOTAL:
			selllist=new SellService().sellList();
			btn_back.setVisibility(View.GONE);
			btn_find.setVisibility(View.GONE);
			btn_forward.setVisibility(View.GONE);
			break;
		}
		lv_sell.setAdapter(new SellListAdapter(this, selllist));
	}
	
	private void showList(String key) {
		switch(limit){
		case Application.LIMIT_DAY:
			selllist=new SellService().sellListDay(currentDate,key);
			btn_find.setText(DateFormat.format("yyyy-MM-dd", currentDate));
			break;
		case Application.LIMIT_MONTH:
			selllist=new SellService().sellListMonth(currentDate,key);
			btn_find.setText(DateFormat.format("yyyy-MM", currentDate));
			break;
		case Application.LIMIT_TOTAL:
			selllist=new SellService().sellList(key);
			btn_back.setVisibility(View.GONE);
			btn_find.setVisibility(View.GONE);
			btn_forward.setVisibility(View.GONE);
			break;
		}
		lv_sell.setAdapter(new SellListAdapter(this, selllist));
	}
	
	/**
	 * back按钮监听
	 * @author XMH
	 *
	 */
	class OnBackListener implements OnClickListener{

		public void onClick(View view) {
			Calendar c=Calendar.getInstance();
			c.setTime(currentDate);
			switch(limit){
			case Application.LIMIT_DAY:
				//计算昨天日期
				c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
				break;
			case Application.LIMIT_MONTH:
				//计算上个月日期
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
				break;
			case Application.LIMIT_TOTAL:
				Toast.makeText(SellListActivity.this, "当前为全部信息", Toast.LENGTH_LONG).show();
				break;
			}
			currentDate=c.getTime();
			showList();
		}
		
	}
	
	/**
	 * forward按钮监听
	 * @author XMH
	 *
	 */
	class OnForwardListener implements OnClickListener{

		public void onClick(View view) {
			Calendar c=Calendar.getInstance();
			c.setTime(currentDate);
			switch(limit){
			case Application.LIMIT_DAY:
				//计算昨天日期
				c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
				break;
			case Application.LIMIT_MONTH:
				//计算上个月日期
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
				break;
			case Application.LIMIT_TOTAL:
				Toast.makeText(SellListActivity.this, "当前为全部信息", Toast.LENGTH_LONG).show();
				break;
			}
			currentDate=c.getTime();
			showList();
		}
	}
	
	/**
	 * 查询监听
	 * @author XMH
	 *
	 */
	class OnFindListener implements OnClickListener{

		public void onClick(View view) {
			ll_list_select.setVisibility(View.GONE);
			ll_date_select.setVisibility(View.VISIBLE);
			et_year.setText(btn_find.getText().toString().split("-")[0]);
			et_month.setText(btn_find.getText().toString().split("-")[1]);
			if(limit==Application.LIMIT_MONTH){
				et_day.setVisibility(View.GONE);
			}else{
				et_day.setText(btn_find.getText().toString().split("-")[2]);
			}
		}
		
	}
	
	/**
	 * 确认日期监听
	 * @author XMH
	 *
	 */
	class OnDateSureListener implements OnClickListener{

		public void onClick(View view) {
			ll_date_select.setVisibility(View.GONE);
			ll_list_select.setVisibility(View.VISIBLE);
			Calendar c=Calendar.getInstance();
			int year=Integer.parseInt(et_year.getText().toString());
			int month=Integer.parseInt(et_month.getText().toString());
			if(limit==Application.LIMIT_DAY){
				int day=Integer.parseInt(et_day.getText().toString());
				c.set(year, month-1, day);
			}else{
				c.set(year, month-1, 15);
			}
			currentDate=c.getTime();
			
			showList();
		}
		
	}
	
	class OnSellListItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> arg0, View view, int index,
				long arg3) {
			//如果已存在输入框（已点击某一行）
			if(!(ll_remarks_input==null)){
				ll_remarks_input.setVisibility(View.GONE);
			}
			//获取控件
			ll_remarks_input=(LinearLayout) view.findViewById(R.id.ll_remarks_input);
			et_remarks=(EditText) view.findViewById(R.id.et_remarks);
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
	
	class OnSureRemarksListener implements OnClickListener{

		public void onClick(View view) {
			remarks=et_remarks.getText().toString();
			tv_remarks.setText(remarks);
			//空间中id是stock的id
			Goods goods=new Goods();
			goods.setId(Integer.parseInt(tv_id.getText().toString()));
			goods.setRemarks(remarks);
			if(new SellService().updateRemarks(goods)){
				Toast.makeText(SellListActivity.this, "已更新备注", Toast.LENGTH_LONG).show();
			}
			ll_remarks_input.setVisibility(View.GONE);
			showList();
		}
		
	}
	
	/**
	 * 返回键监听
	 */
	@Override
	public void onBackPressed() {
		//如果批准驳回按钮可视，则隐藏按钮，否则执行返回事件
		if(ll_date_select!=null){
			if(ll_date_select.getVisibility()==View.VISIBLE){
				ll_date_select.setVisibility(View.GONE);
				ll_list_select.setVisibility(View.VISIBLE);
				return;
			}
		}
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
				showList();
				return;
			}
		}
		super.onBackPressed();
	}

}
