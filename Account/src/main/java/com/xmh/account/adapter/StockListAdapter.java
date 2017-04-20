package com.xmh.account.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xmh.account.R;
import com.xmh.account.bean.Goods;

import java.util.List;

public class StockListAdapter extends BaseAdapter{
	
	Context context;
	List<Goods> list;
	
	public StockListAdapter(Context context,List<Goods> list){
		this.context=context;
		this.list=list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int index) {
		return list.get(index);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int index, View convertView, ViewGroup group) {
		final ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.stocklist_item, group, false);
			holder.tv_id=(TextView) convertView.findViewById(R.id.tv_id);
			holder.tv_fallname=(TextView) convertView.findViewById(R.id.tv_fallname);
			holder.tv_amount=(TextView) convertView.findViewById(R.id.tv_amount);
			holder.tv_in_price=(TextView) convertView.findViewById(R.id.tv_in_price);
			holder.tv_remarks=(TextView) convertView.findViewById(R.id.tv_remarks);
			convertView.setTag(holder);
		}
		holder.tv_id.setText(list.get(index).getId().toString());
		holder.tv_fallname.setText(list.get(index).getFallname());
		holder.tv_amount.setText(list.get(index).getAmount().toString());
		holder.tv_in_price.setText(list.get(index).getIn_price().toString());
		holder.tv_remarks.setText(list.get(index).getRemarks());
		//若库存少于2则红色显示
		if(list.get(index).getAmount()<2){
			holder.tv_amount.setTextColor(Color.RED);
		}else{
			holder.tv_amount.setTextColor(Color.GREEN);
		}
		
		return convertView;
	}
	
	public static class ViewHolder {

		//Item中的控件
		TextView tv_id;
		TextView tv_fallname;
		TextView tv_amount;
		TextView tv_in_price;
		TextView tv_remarks;
	}

}
