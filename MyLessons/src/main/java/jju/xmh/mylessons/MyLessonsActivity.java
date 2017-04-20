package jju.xmh.mylessons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jju.xmh.mylessons.ShakeListener.OnShakeListener;

public class MyLessonsActivity extends Activity implements OnTouchListener,
		OnGestureListener {
	// 定义常量标识
	private static final int SEEK = 0x10;
	private static final int HELP = 0x11;
	private static final int EXIT = 0x12;
	private static final int DEL = 0x13;
	// 建立手势识别器
	GestureDetector detector = new GestureDetector(this);
	// 建立SQLOpenHelper
	LessonSQLOpenHelper SQLHelper_lesson;
	// 建立SQLiteDatabase
	SQLiteDatabase SQL_lesson;
	// 建立并获取当前日期
	Date today = new Date();
	// 定义学期初日期
	Date BEGIN = new Date(114,1,9);//设置起始日期为2014年2月9日
	// 定义学期中点
	Date MID;
	// 定义日期控件
	TextView view_date;
	// 定义主屏
	View main;
	// 定义ListView
	ListView list_lesson;
	// 获取item View
	LayoutInflater layoutInflater;
	View view_item;
	// 定义数据集
	List<Map<String, Object>> list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//初始化期中时间
		Calendar c=Calendar.getInstance();
		c.set(2014,2,30);
		MID = c.getTime();
		// 设置手势识别器
		detector.setIsLongpressEnabled(true);
		// 实例化数据库
		SQLHelper_lesson = new LessonSQLOpenHelper(this);
		SQL_lesson = SQLHelper_lesson.getReadableDatabase();
		// 实例化第一行信息
		view_date = (TextView) this.findViewById(R.id.date);
		// 实例化主屏
		main = (View) this.findViewById(R.id.main);
		// 实例化view_item
		layoutInflater = LayoutInflater.from(this);
		view_item = layoutInflater.inflate(R.layout.list_item, null);
		// 实例化list_lesson
		list_lesson = (ListView) this.findViewById(R.id.list_lessons);
		// 显示内容
		showlist(today, list_lesson);

		// 设置主屏可触控
		main.setOnTouchListener(this);
		main.setLongClickable(true);
		list_lesson.setOnTouchListener(this);
		list_lesson.setLongClickable(true); // 定义晃动监听对象
		shake_init();
	}

	// 晃动监听
	private void shake_init() {
		ShakeListener shakeListener = new ShakeListener(this);
		shakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				today = new Date();
				showlist(today, list_lesson);
			}
		});
	}

	// 显示内容
	private void showlist(Date today, ListView list_lessons) {
		// 日期转义
		String[] lessons = dateTurnToLesson(today);
		// 实例化list
		list = new ArrayList<Map<String, Object>>();
		// 将四条数据放入list
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> itemData = new HashMap<String, Object>();
			switch (i) {
			case 0:
				itemData.put("imageID", R.drawable.icon_lesson1);
				break;
			case 1:
				itemData.put("imageID", R.drawable.icon_lesson2);
				break;
			case 2:
				itemData.put("imageID", R.drawable.icon_lesson3);
				break;
			case 3:
				itemData.put("imageID", R.drawable.icon_lesson4);
				break;
			}
			itemData.put("lessonName", lessons[i * 2] + "");
			itemData.put("lessonAddress", lessons[i * 2 + 1] + "");
			list.add(itemData);
		}
		LessonAdapter adapter = new LessonAdapter(this, R.layout.list_item,
				list);
		list_lesson.setAdapter(adapter);
	}

	// 日期转义
	private String[] dateTurnToLesson(Date today) {
		// 识别日期
		int early;
		int single;
		int day;
		// 是否上学期
		if (today.before(MID))
			early = 1;
		else
			early = 0;
		// 是否单周
		double temp= (today.getTime()-BEGIN.getTime())/1000/3600/24/7;
		if (temp%2 == 1)
			single = 1;
		else
			single = 0;
		// 获取星期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println(early+","+single+","+day+","+(int)temp);
		//设置日期格式
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
		view_date.setText(sfd.format(today)+"\t星期"+day+"\t"+"第"+(int)temp+"周");
		// 返回课程信息
		String[] lessons = new String[8];
		// 定义查询游标
		Cursor cursor;
		cursor = SQL_lesson.rawQuery(
				"select name,address from lessons where (early=" + early
						+ " or early=2) and (single=" + single
						+ " or single=2) and day=" + day + " and time=1", null);
		if (cursor.moveToFirst()) {
			lessons[0] = "课程：" + cursor.getString(0);
			lessons[1] = "地点：" + cursor.getString(1);
		} else {
			lessons[0] = "此时不上课";
			lessons[1] = "";
		}
		cursor = SQL_lesson.rawQuery(
				"select name,address from lessons where (early=" + early
						+ " or early=2) and (single=" + single
						+ " or single=2) and day=" + day + " and time=2", null);
		if (cursor.moveToFirst()) {
			lessons[2] = "课程：" + cursor.getString(0);
			lessons[3] = "地点：" + cursor.getString(1);
		} else {
			lessons[2] = "此时不上课";
			lessons[3] = "";
		}
		cursor = SQL_lesson.rawQuery(
				"select name,address from lessons where (early=" + early
						+ " or early=2) and (single=" + single
						+ " or single=2) and day=" + day + " and time=3", null);
		if (cursor.moveToFirst()) {
			lessons[4] = "课程：" + cursor.getString(0);
			lessons[5] = "地点：" + cursor.getString(1);
		} else {
			lessons[4] = "此时不上课";
			lessons[5] = "";
		}
		cursor = SQL_lesson.rawQuery(
				"select name,address from lessons where (early=" + early
						+ " or early=2) and (single=" + single
						+ " or single=2) and day=" + day + " and time=4", null);
		if (cursor.moveToFirst()) {
			lessons[6] = "课程：" + cursor.getString(0);
			lessons[7] = "地点：" + cursor.getString(1);
		} else {
			lessons[6] = "此时不上课";
			lessons[7] = "";
		}
		cursor.close();
		return lessons;
	}

	// 菜单键监听
	public boolean onCreateOptionsMenu(Menu menu) {
		// 增加菜单项
		menu.add(0, SEEK, 0, "选择日期").setIcon(R.drawable.icon_seek);
		menu.add(0, HELP, 0, "帮助信息").setIcon(R.drawable.icon_info);
		menu.add(0, DEL, 0, "恢复课表").setIcon(R.drawable.icon_del);
		menu.add(0, EXIT, 0, "退出程序").setIcon(R.drawable.icon_exit);
		return super.onCreateOptionsMenu(menu);
	}

	// 不同菜单项的不同动作
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SEEK:
			final View v_date = layoutInflater.inflate(R.layout.date, null);// 获取日期设置界面
			new AlertDialog.Builder(this)
					// 定义日程
					.setTitle("设置日期")
					.setView(v_date)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									DatePicker date = (DatePicker) v_date
											.findViewById(R.id.date);// 获取对话框中的DatePicker
									today.setMonth(date.getMonth());// 获取并设置Month
									today.setDate(date.getDayOfMonth());// 获取并设置Day
									showlist(today, list_lesson);
								}
							}).show();
			break;
		case HELP:
			// 跳转help界面
			Intent intent = new Intent(MyLessonsActivity.this, Help.class);
			startActivityForResult(intent, 0);
			break;
		case DEL:
			// 删除数据库文件
			delSQLite();
			break;
		case EXIT:
			// 关闭窗口
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 删除数据库
	private void delSQLite() {
		TextView warn = new TextView(this);
		warn.setText("确定删除当前课表并恢复初始课表吗？");

		new AlertDialog.Builder(this)
				// 定义日程
				.setTitle("删除课程表").setView(warn)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// 确定之后
						SQL_lesson.close();
						deleteDatabase("SQLite_lesson.db");
						// 二级对话框
						TextView del_ok = new TextView(MyLessonsActivity.this);
						del_ok.setText("课程表已删除，请重新打开");
						new AlertDialog.Builder(MyLessonsActivity.this)
								// 定义日程
								.setTitle("回复课程表")
								.setView(del_ok)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												// 关闭窗口
												MyLessonsActivity.this.finish();
											}
										}).show();
					}
				}).show();

	}

	// 返回键监听
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 关闭窗口
			this.finish();
		}
		return false;
	}

	// 滑动监听
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > 10 && Math.abs(velocityX) > 200) {
			Calendar c =Calendar.getInstance();
			c.setTime(today);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
			today=c.getTime();
			showlist(today, list_lesson);
		}
		if (e2.getX() - e1.getX() > 10 && Math.abs(velocityX) > 200) {
			Calendar c =Calendar.getInstance();
			c.setTime(today);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
			today=c.getTime();
			showlist(today, list_lesson);
		}
		return false;
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		detector.onTouchEvent(event);
		return false;
	}

}