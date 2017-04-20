package jju.xmh.mylessons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class LessonAdapter extends BaseAdapter{
	
	//定义Item布局
	private LayoutInflater layoutInflater;
	int itemLayout;
	//定义数据集
	List<Map<String,Object>> listData;
	Context context;
	
	// 构造器
	public LessonAdapter(Context context, int itemLayout,
			List<Map<String, Object>> list) {
		this.itemLayout = itemLayout;
		this.listData = list;
		layoutInflater = LayoutInflater.from(context);
		this.context = context;// ///
	}

	//获取总数
	public int getCount() {
		return listData.size();
	}

	//获取该项
	public Object getItem(int arg0) {
		return listData.get(arg0);
	}

	//获取该项ID
	public long getItemId(int arg0) {
		return listData.get(arg0).hashCode();
	}

	//获取View
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		//获取itemView
		View itemView=layoutInflater.inflate(itemLayout, null);
		//获取数据
		Map<String,Object> map=listData.get(arg0);
		//获取控件
		ImageView image=(ImageView)itemView.findViewById(R.id.image);
		TextView name=(TextView)itemView.findViewById(R.id.lessonName);
		TextView add=(TextView)itemView.findViewById(R.id.lessonAddress);
		//设置控件属性
		image.setImageResource((Integer) map.get("imageID"));
		name.setText(map.get("lessonName").toString());
		add.setText(map.get("lessonAddress").toString());
		return itemView;
	}

}
