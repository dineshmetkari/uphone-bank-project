package com.ultrawise.android.bank.view.account_management;

import com.ultrawise.android.bank.view.transfer.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccount extends Activity {
	private Button btnConfirm;
	private Button btnCoustom;
	private EditText etAccount;
	private EditText etCoustomPwd;
	private TextView tvClassFirst;
	private TextView tvClassSecond;
	private TextView tvClassThrid;
	private TextView tvClassFour;
	Intent intent;
	private int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.add_account);

		btnConfirm = (Button) this.findViewById(R.id.btnAddAccConfrim);
		etAccount = (EditText) this.findViewById(R.id.etAcc);
		etCoustomPwd = (EditText) this.findViewById(R.id.etCoustomPwd);
		// Confirm to add account
		btnConfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Eject dialog
				new AlertDialog.Builder(AddAccount.this)
						.setTitle("ȷ�϶Ի���")
						.setMessage("�����˻���")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// Turn to AccountInfo
										flag = 1;// done
										Toast.makeText(AddAccount.this, "�����˻��ɹ�", Toast.LENGTH_SHORT).show();
										finish();
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// cancel
										flag = -1;
										//finish();
									}
								}).show();
				// done
				if (flag == 1) {
					intent = AddAccount.this.getIntent();
					/**
					 * ҵ���ܣ���ʱ������
					 */
					/*
					String strAccount = etAccount.getText().toString();
					String strCoustomPwd = etCoustomPwd.getText().toString();
					
					intent.putExtra("addAccNum", strAccount);
					intent.putExtra("CoustomPwd", strCoustomPwd);
					*/
					intent.setClass(AddAccount.this, AccountInfo.class);
					AddAccount.this.startActivity(intent);
				}
			}
		});
		
		//���ò㼶��ϵ
		tvClassFirst = (TextView)this.findViewById(R.id.class_first);
		tvClassFirst.setText("�ֻ�����>");
		tvClassFirst.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 intent = AddAccount.this.getIntent();
//				 intent.setClass(AddAccount.this, AccountInfo.class);
//				 AddAccount.this.startActivity(intent);
			}
		});
		tvClassSecond = (TextView)this.findViewById(R.id.class_second);
		tvClassSecond.setText("�˻�����>");
		tvClassSecond.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 intent = AddAccount.this.getIntent();
				 intent.setClass(AddAccount.this, AccountManagement.class);
				 AddAccount.this.startActivity(intent);
			}
		});
		tvClassThrid = (TextView)this.findViewById(R.id.class_third);
		tvClassThrid.setText("�˻���Ϣ>");
		tvClassThrid.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 intent = AddAccount.this.getIntent();
				 intent.setClass(AddAccount.this, AccountInfo.class);
				 AddAccount.this.startActivity(intent);
			}
		});
		tvClassFour = (TextView)this.findViewById(R.id.class_four);
		tvClassFour.setText("�����˻�");

		tvClassFirst.setVisibility(View.VISIBLE);
		tvClassSecond.setVisibility(View.VISIBLE);
		tvClassThrid.setVisibility(View.VISIBLE);
		tvClassFour.setVisibility(View.VISIBLE);

		// ���õײ��Զ��尴ť��ʾ
		btnCoustom = (Button) this.findViewById(R.id.btnCoustom);
		btnCoustom.setText("�����˻�");
		btnCoustom.setVisibility(View.VISIBLE);
	}
	
}