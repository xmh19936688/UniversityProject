package jju.xmh.dreamwaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.io.IOException;

import jju.xmh.dreamwaker.ShakeListener.OnShakeListener;

public class AlarmReceiver extends BroadcastReceiver 
{
	Context context;
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Log.e("am i here","am i here");
		this.context=context;
		playRing();//播放闹钟
	}
	private void playRing() 
	{
		MediaPlayer player=new MediaPlayer();//定义闹钟
		try{
			AssetFileDescriptor fileDescriptor = context.getAssets().openFd("ring.mp3");
			player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());//配置铃声路径
			final AudioManager audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM)!=0){
				player.setAudioStreamType(AudioManager.STREAM_ALARM);
				player.prepare();
			}
		}catch(IllegalStateException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		player.setLooping(true);//设置循环播放
		player.start();//播放闹钟
		Vibrator vibrator;//定义振动器
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {1000,1000,1000,1000}; 
		vibrator.vibrate(pattern, 1);//震动打开
		cancle(player,vibrator);
	}
	private void cancle(final MediaPlayer player, final Vibrator vibrator) 
	{
		ShakeListener shakeListener = new ShakeListener(context);//创建一个对象
		shakeListener.setOnShakeListener(new OnShakeListener()
		{
			public void onShake() 
			{
				try {
					player.stop();//关闭铃声
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					player.release();//释放资源
					vibrator.cancel();//关闭振动

				}
			}
		});
	}
}
