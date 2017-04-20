package jju.xmh.inform;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InformActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final LayoutInflater layoutInflater = LayoutInflater.from(this);
		final EditText edit_text = (EditText) this
				.findViewById(R.id.edit_content);// 获取输入框
		edit_text.setWidth(200);
		Button btn_send = (Button) this.findViewById(R.id.btn_send);// 获取按钮
		btn_send.setOnClickListener(new Button.OnClickListener() {// 设置按钮监听
			public void onClick(View arg0) {
				if (edit_text.getText().toString().equals(null)
						|| edit_text.getText().toString().equals("")
						|| edit_text.getText().toString().equals("点此输入短信内容")) {
					Toast.makeText(InformActivity.this, "请输入短信内容", 3000).show();
					return;
				}
				// 弹出对话框提示输入开机密码，输入正确则继续，不正确准则返回

				final View passwordTextView = layoutInflater.inflate(
						R.layout.password_edit, null);
				new AlertDialog.Builder(InformActivity.this)
						.setTitle("请输入开机密码")
						.setView(passwordTextView)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										EditText passwordText = (EditText) passwordTextView
												.findViewById(R.id.psw);
										String password = passwordText
												.getText().toString();
										if (password.equals("22991268"))
											send();
										else
											Toast.makeText(InformActivity.this,
													"密码错误", 3000).show();
									}

									private void send() {
										// 发送短信
										String send_content = edit_text
												.getText().toString() + "【许孟豪】";// 获取Edit内容

										SmsManager sms = SmsManager
												.getDefault();// 获得SmsManager类的实例
										sms.sendTextMessage("18079241133",
												null, send_content, null, null);// 1
										sms.sendTextMessage("15949563971",
												null, send_content, null, null);// 2
										sms.sendTextMessage("15949568657",
												null, send_content, null, null);// 3
										sms.sendTextMessage("15949572198",
												null, send_content, null, null);// 4
										sms.sendTextMessage("15949588642",
												null, send_content, null, null);// 5
										sms.sendTextMessage("15949571381",
												null, send_content, null, null);// 6
										sms.sendTextMessage("18170230551",
												null, send_content, null, null);// 7
										sms.sendTextMessage("18079241060",
												null, send_content, null, null);// 8
										sms.sendTextMessage("15949560647",
												null, send_content, null, null);// 9
										sms.sendTextMessage("15949568960",
												null, send_content, null, null);// 10
										sms.sendTextMessage("15949569557",
												null, send_content, null, null);// 11
										sms.sendTextMessage("18079242875",
												null, send_content, null, null);// 12
										sms.sendTextMessage("18079247856",
												null, send_content, null, null);// 13
										sms.sendTextMessage("18170234086",
												null, send_content, null, null);// 14
										sms.sendTextMessage("18079240654",
												null, send_content, null, null);// 15
										sms.sendTextMessage("15949582865",
												null, send_content, null, null);// 16
										sms.sendTextMessage("15949589730",
												null, send_content, null, null);// 17
										sms.sendTextMessage("18270676015",
												null, send_content, null, null);// 18
										sms.sendTextMessage("18270201718",
												null, send_content, null, null);// 20
										sms.sendTextMessage("15949568375",
												null, send_content, null, null);// 21
										sms.sendTextMessage("15949560115",
												null, send_content, null, null);// 23
										sms.sendTextMessage("18979222987",
												null, send_content, null, null);// 24
										sms.sendTextMessage("18170231887",
												null, send_content, null, null);// 25
										sms.sendTextMessage("15949589212",
												null, send_content, null, null);// 27
										sms.sendTextMessage("18046723151",
												null, send_content, null, null);// 28
										sms.sendTextMessage("13064178222",
												null, send_content, null, null);// 29
										sms.sendTextMessage("18070421201",
												null, send_content, null, null);// 30
										sms.sendTextMessage("18070421024",
												null, send_content, null, null);// 31
										sms.sendTextMessage("15949519919",
												null, send_content, null, null);// 32
										sms.sendTextMessage("13979400731",
												null, send_content, null, null);// 33
										sms.sendTextMessage("15949565016",
												null, send_content, null, null);// 34
										sms.sendTextMessage("15717025864",
												null, send_content, null, null);// 35
										sms.sendTextMessage("18070202454",
												null, send_content, null, null);// 36
										sms.sendTextMessage("18079243784",
												null, send_content, null, null);// 38
										sms.sendTextMessage("15949582340",
												null, send_content, null, null);// 39
										sms.sendTextMessage("18079243512",
												null, send_content, null, null);// 40
										sms.sendTextMessage("15949564129",
												null, send_content, null, null);// 41
										sms.sendTextMessage("15949564305",
												null, send_content, null, null);// 42
										sms.sendTextMessage("15949561713",
												null, send_content, null, null);// 43
										sms.sendTextMessage("15949583128",
												null, send_content, null, null);// 44
										sms.sendTextMessage("15949580451",
												null, send_content, null, null);// 45
										sms.sendTextMessage("18170238638",
												null, send_content, null, null);// 46
										sms.sendTextMessage("15949560074",
												null, send_content, null, null);// 22

										Toast.makeText(InformActivity.this,
												"短信已发送", 3000)
												.show();
										edit_text.setText("");//清空编辑框

									}
								}).show();
			}
		});
	}
}