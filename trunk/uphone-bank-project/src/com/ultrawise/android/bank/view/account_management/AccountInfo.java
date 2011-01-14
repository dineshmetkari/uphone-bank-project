package com.ultrawise.android.bank.view.account_management;

import java.util.ArrayList;
import java.util.HashMap;

import com.ultrawise.android.bank.view.transfer.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInfo extends ListActivity {
	private TextView tvAccInfo;
	private Button btnCoustom;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tvAccInfo = (TextView) this.findViewById(R.id.tvAccInfo);
		Intent intent = getIntent();
		if (intent.getStringExtra("AccInfo") != null) {
			tvAccInfo.setText(intent.getStringExtra("AccInfo"));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.account_info);
		// �˻���Ϣ
		tvAccInfo = (TextView) this.findViewById(R.id.tvAccInfo);
		tvAccInfo.setText("�˺ţ�440301198810282152\n" + "�������ҵĴ��\n"
				+ "�˻����ͣ����ڴ���һ�ۣ�����\n" + "�˻�״̬������\n�Ƿ�ǩԼ��δǩԼ\n"
				+ "�����У���������������÷��֧��\n" + "�����գ�2006/07/09");
		//
		
		
		// ����List View
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "��ѯ�����˻�");
		map1.put("arrow", ">");
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "�����˻�");
		map2.put("arrow", ">");
		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("name", "ɾ���˻�");
		map3.put("arrow", ">");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		SimpleAdapter sa = new SimpleAdapter(this, list,
				R.layout.account_management_textview_list, new String[] {
						"name", "arrow" }, new int[] { R.id.name, R.id.arrow });
		this.setListAdapter(sa);
		
		//���õײ��Զ��尴ť��ʾ
		btnCoustom = (Button)this.findViewById(R.id.btnCoustom);
		btnCoustom.setText("�˻���Ϣ");
		btnCoustom.setVisibility(View.VISIBLE);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (id == 0) {
			//��ѯ�˻�
			Intent intent = new Intent();
			intent.setClass(AccountInfo.this, QueryAccount.class);
			AccountInfo.this.startActivity(intent);
		}else if(id==1){
			//�����˻�
			Intent intent = new Intent();
			intent.setClass(AccountInfo.this, AddAccount.class);
			AccountInfo.this.startActivity(intent);
		}else if(id==2){
			//ɾ���˻�
		}
	}

}
