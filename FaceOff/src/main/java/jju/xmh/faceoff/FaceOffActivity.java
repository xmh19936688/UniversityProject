package jju.xmh.faceoff;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import jju.xmh.faceoff.ProximityListener.OnProximityListener;

public class FaceOffActivity extends Activity {
	
    private int now;
    int total_picture;
    ImageView pic;
    ProximityListener proximityListener;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pic=(ImageView)this.findViewById(R.id.image);
        pic.setBackgroundResource(R.drawable.a);
        now = 1;
        total_picture=8;
        proximityListener = new ProximityListener(this);//创建一个对象
        proximityListener.setOnProximityListener(new OnProximityListener()
        {
        	public void onProximity() 
        	{
				Intent intent= getIntent();
				int value = intent.getIntExtra("max",total_picture);
				int i=now;
				while(i==now)
				{
				 	i= Integer.valueOf((int) (Math.random()*value));//生成随机数
				}
				switch(i)
				{
					case 1:pic.setBackgroundResource(R.drawable.a);now=i;break;
					case 2:pic.setBackgroundResource(R.drawable.b);now=i;break;
					case 3:pic.setBackgroundResource(R.drawable.c);now=i;break;
					case 4:pic.setBackgroundResource(R.drawable.d);now=i;break;
					case 5:pic.setBackgroundResource(R.drawable.e);now=i;break;
					case 6:pic.setBackgroundResource(R.drawable.f);now=i;break;
					case 7:pic.setBackgroundResource(R.drawable.g);now=i;break;
					case 8:pic.setBackgroundResource(R.drawable.h);now=i;break;
				}
			}
		});
    }
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			this.finish();
		}
		return false;
	}
	
}