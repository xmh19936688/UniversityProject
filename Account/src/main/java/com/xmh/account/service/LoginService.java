package com.xmh.account.service;

import com.xmh.account.dataBase.UserDB;

public class LoginService {
	
	/**
	 * 验证登录
	 * @param password 密码
	 * @return 登录结果
	 */
	public boolean login(String password){
		boolean result=false;
		if(password.equals(new UserDB().findUser())){
			result=true;
		}
		return result;
	}
}
