package jju.xmh.faceoff;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximityListener implements SensorEventListener
{

	private SensorManager sensorManager;
	private Sensor sensor;
	private OnProximityListener onProximityListener;
	private Context context;
	
	public ProximityListener(Context c) 
	{
		context = c;//获得监听对象
		start();
	}
	
	private void start() 
	{
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if(sensorManager != null) 
		{
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);//获得距离传感器
		}
		if(sensor != null) 
		{
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);//注册监听
		}
	}
	public void stop() 
	{
		sensorManager.unregisterListener(this);//取消注册监听
	}
	public interface OnProximityListener 
	{
		public void onProximity();
	}
	public void setOnProximityListener(OnProximityListener listener) //设置监听事件
	{
		onProximityListener = listener;
	}
	public void onAccuracyChanged(Sensor arg0, int arg1) //传感器精度改变时触发
	{
		
	}

	public void onSensorChanged(SensorEvent event) //传感器参数改变时触发
	{
		if(event.values[0]==0.0)//靠近手机触发
		onProximityListener.onProximity();
	}

}
