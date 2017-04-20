package com.xmh.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xmh.account.dataBase.UserDB;
import com.xmh.account.service.LoginService;

public class SetPasswordActivity extends Activity{

	EditText et_oldpassword;
	EditText et_newpassword;
	EditText et_surepassword;
	Button btn_setpassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpassword);
		
		InitView();
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		//初始化控件
		et_oldpassword=(EditText) findViewById(R.id.et_oldpassword);
		et_newpassword=(EditText) findViewById(R.id.et_newpassword);
		et_surepassword=(EditText) findViewById(R.id.et_surepassword);
		btn_setpassword=(Button) findViewById(R.id.btn_setpassword);
		//设置监听
		btn_setpassword.setOnClickListener(new SetPasswordListener());
	}

	/**
	 * 设置监听
	 * @author XMH
	 *
	 */
	class SetPasswordListener implements OnClickListener{

		public void onClick(View arg0) {
			
			String oldpassword=et_oldpassword.getText().toString();
			String newpassword=et_newpassword.getText().toString();
			String surepassword=et_surepassword.getText().toString();
			if(new LoginService().login(oldpassword)){
				if(newpassword.equals(surepassword)){
					new UserDB().setUser(newpassword);
					Toast.makeText(getApplicationContext(), "密码修改成功，请重新进入系统", Toast.LENGTH_LONG);
					Intent intent=new Intent();
					intent.setClass(SetPasswordActivity.this, LoginActivity.class);
					startActivity(intent);
					SetPasswordActivity.this.finish();
				}else{
					Toast.makeText(getApplicationContext(), "两次输入的新密码不一致", Toast.LENGTH_LONG);
				}
			}else{
				Toast.makeText(getApplicationContext(), "原密码错误", Toast.LENGTH_LONG);
			}
			
		}
		
	}
}
