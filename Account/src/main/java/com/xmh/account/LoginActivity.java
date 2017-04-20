package com.xmh.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xmh.account.dataBase.SQLOpenHelper;
import com.xmh.account.service.LoginService;

public class LoginActivity extends Activity{
	
	EditText et_password;
	Button btn_login;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        InitView();//初始化控件
        InitDataBase();//初始化数据库
        
    }

	/**
	 * 初始化数据库
	 */
	private void InitDataBase() {
		Application.setSQLHelper_account(new SQLOpenHelper(this));
	}

	/**
	 * 初始化控件
	 */
	private void InitView() {
		et_password=(EditText) findViewById(R.id.et_password);
		btn_login=(Button) findViewById(R.id.btn_login);
		
		btn_login.setOnClickListener(new LoginListener());
	}
	
	/**
	 * 登录按钮监听
	 * @author XMH
	 *
	 */
	class LoginListener implements OnClickListener{

		public void onClick(View buttton) {
			String password=et_password.getText().toString();
			if(new LoginService().login(password)){
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}else{
				Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
			}
		}
		
	}
}


