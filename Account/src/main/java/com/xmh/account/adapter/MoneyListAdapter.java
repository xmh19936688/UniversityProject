package com.xmh.account.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xmh.account.R;
import com.xmh.account.bean.Goods;

import java.util.List;

public class MoneyListAdapter extends BaseAdapter{

	Context context;
	List<Goods>list;
	
	public MoneyListAdapter(Context context,List<Goods> list){
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
		if(convertView!=null){
			holder=(ViewHolder) convertView.getTag();
		}else{
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.moneylist_item, group,false);
			holder.tv_id=(TextView) convertView.findViewById(R.id.tv_id);
			holder.tv_date=(TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_fallname=(TextView) convertView.findViewById(R.id.tv_fallname);
			holder.tv_amount=(TextView) convertView.findViewById(R.id.tv_amount);
			holder.tv_in_price=(TextView) convertView.findViewById(R.id.tv_in_price);
			holder.tv_out_price=(TextView) convertView.findViewById(R.id.tv_out_price);
			holder.tv_money=(TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_total=(TextView) convertView.findViewById(R.id.tv_total);
			holder.tv_remarks=(TextView) convertView.findViewById(R.id.tv_remarks);
			convertView.setTag(holder);
		}
		holder.tv_id.setText(list.get(index).getId().toString());
		holder.tv_date.setText(DateFormat.format("yyyy-MM-dd", list.get(index).getDate()));
		holder.tv_fallname.setText(list.get(index).getFallname());
		holder.tv_amount.setText(list.get(index).getAmount().toString());
		holder.tv_in_price.setText(list.get(index).getIn_price().toString());
		holder.tv_out_price.setText(list.get(index).getOut_price().toString());
		holder.tv_money.setText(list.get(index).getMoney().toString());
		holder.tv_total.setText(list.get(index).getTotal().toString());
		holder.tv_remarks.setText(list.get(index).getRemarks());
		
		return convertView;
	}
	
	public static class ViewHolder {

		//Item中的控件
		TextView tv_id;
		TextView tv_date;
		TextView tv_fallname;
		TextView tv_amount;
		TextView tv_in_price;
		TextView tv_out_price;
		TextView tv_money;
		TextView tv_total;
		TextView tv_remarks;
	}

}
