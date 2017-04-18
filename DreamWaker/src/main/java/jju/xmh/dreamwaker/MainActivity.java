package jju.xmh.dreamwaker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import jju.xmh.dreamwaker.AlarmReceiver;
import jju.xmh.dreamwaker.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //获取按钮
        final ImageButton ibtn=(ImageButton)this.findViewById(R.id.ibtn);//获取闹钟按钮
        final ImageButton ibca=(ImageButton)this.findViewById(R.id.ibca);//获取日程按钮
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        //闹钟
        ibtn.setOnClickListener(new ImageButton.OnClickListener()
        {//设置按钮监听
            public void onClick(View arg0)
            {//显示对话框
                final EditText time=new EditText(MainActivity.this);//定义编辑框控件
                time.setSingleLine();
                new AlertDialog.Builder(MainActivity.this)//定义闹钟
                        .setTitle("设置闹钟")
                        .setView(time)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {//确定之后
                                try
                                {//尝试类型转换
                                    int pass= Integer.decode(time.getText().toString());
                                    Date now=new Date();//创建并获取当前时刻
                                    Date next=new Date();//创建提醒时刻
                                    next.setMinutes(pass+now.getMinutes());//计算提醒时刻
                                    timeGo(next);//计时功能
                                }
                                catch(Exception e)
                                {//异常处理
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setMessage("请输入正确时间")
                                            .show();
                                }
                            }
                        })
                        .show();
            }
        });
        //日程
        ibca.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View arg0)
            {//点击图片
                final View ca_date = layoutInflater.inflate(R.layout.calender_date, null);//获取日期设置界面
                final View ca_time = layoutInflater.inflate(R.layout.calender_time, null);//获取时间设置界面
                new AlertDialog.Builder(MainActivity.this)//定义日程
                        .setTitle("设置日期")
                        .setView(ca_date)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {//确定之后
                                final Date next=new Date();//创建提醒时刻
                                DatePicker date=(DatePicker)ca_date.findViewById(R.id.date);//获取对话框中的DatePicker
                                next.setMonth(date.getMonth());//获取并设置Month
                                next.setDate(date.getDayOfMonth());//获取并设置Day
                                //二级对话框
                                new AlertDialog.Builder(MainActivity.this)//定义日程
                                        .setTitle("设置时间")
                                        .setView(ca_time)
                                        .setPositiveButton("确定",new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                TimePicker time=(TimePicker)ca_time.findViewById(R.id.time);//获取对话框中的TimePicker
                                                next.setHours(time.getCurrentHour());//获取并设置Hour
                                                next.setMinutes(time.getCurrentMinute());//获取并设置Minute
                                                timeGo(next);//开始计时
                                            }
                                        })
                                        .show();
                            }
                        })
                        .show();
            }
        });
    }
    public void timeGo(Date next)
    {//计时功能
        AlarmManager am=(AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);//定义闹钟管理
        Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);//定义事件
        intent.putExtra("music",true);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        Calendar c=Calendar.getInstance();//定义Calendar
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY,next.getHours());
        c.set(Calendar.MINUTE,next.getMinutes());
        am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        Toast.makeText(MainActivity.this ,"闹钟设置成功" , 5000).show();//设置提示
    }
}