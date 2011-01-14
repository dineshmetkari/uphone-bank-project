package com.ultrawise.android.bank.view.account_management;

import com.ultrawise.android.bank.view.transfer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddAccount extends Activity {
	private Button btnConfrim;
	private Button btnCoustom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.add_account);
		btnConfrim = (Button)this.findViewById(R.id.btnAddAccConfrim);
		btnConfrim.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				//�����Ի�����ʾ�û��Ѿ��ɹ����
				Intent intent = new Intent();
				intent.setClass(AddAccount.this, AccountInfo.class);
				//AddAccount.this.startActivity(intent);
			}
		});
		//���õײ��Զ��尴ť��ʾ
		btnCoustom = (Button)this.findViewById(R.id.btnCoustom);
		btnCoustom.setText("�����˻�");
		btnCoustom.setVisibility(View.VISIBLE);
	}

}
