package jju.xmh.mylessons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LessonSQLOpenHelper extends SQLiteOpenHelper{

	public LessonSQLOpenHelper(Context context) {
		//构造器
		super(context,"SQLite_lesson.db",null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//数据库首次建立时运行
		//创建表
		db.execSQL("create table lessons(_id integer primary key autoincrement,early integer,single integer,day integer,time integer,name text,address text)");
		//添加数据
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(1,2,1,1,'ARM','文友楼620')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(0,2,1,1,'嵌入应用','文友楼621')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(2,2,1,3,'系统结构','文友楼620')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(2,2,2,1,'软件工程','文友楼620')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(1,2,2,2,'ARM','文友楼621')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(0,2,2,2,'嵌入应用','文友楼621')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(1,2,3,1,'ARM','文友楼614')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(0,0,3,1,'嵌入应用','文友楼614')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(2,1,3,2,'软件工程','实验楼603')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(1,0,4,1,'ARM','实验楼707')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(1,1,4,1,'ARM','文友楼620')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(0,2,4,2,'嵌入应用','实验楼707')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(0,0,5,1,'嵌入应用','实验楼707')");
		db.execSQL("insert into lessons(early,single,day,time,name,address)values(2,2,5,2,'系统结构','文友楼614')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//数据库版本号不一致时更新操作
	}

}
