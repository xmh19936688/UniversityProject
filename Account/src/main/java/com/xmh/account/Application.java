package com.xmh.account;

import com.xmh.account.dataBase.SQLOpenHelper;

public class Application {
	
	public static final int LIMIT_DAY=1;
	public static final int LIMIT_MONTH=2;
	public static final int LIMIT_TOTAL=3;

	public static SQLOpenHelper SQLHelper_account;

	public static SQLOpenHelper getSQLHelper_account() {
		return SQLHelper_account;
	}

	public static void setSQLHelper_account(SQLOpenHelper sQLHelper_account) {
		SQLHelper_account = sQLHelper_account;
	}
	
}
