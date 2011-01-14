package com.ultrawise.android.bank.view.account_management;

import java.util.ArrayList;
import java.util.HashMap;

import com.ultrawise.android.bank.view.transfer.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class QueryAccount extends ListActivity {
	private Button btnCoustom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.list_account);
		// ����List View
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "5560654220320266");
		map1.put("arrow", ">");
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "1122065468210030");
		map2.put("arrow", ">");
		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("name", "3322019830320266");
		map3.put("arrow", ">");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		SimpleAdapter sa = new SimpleAdapter(this, list,
				R.layout.account_management_textview_list, new String[] {
						"name", "arrow" }, new int[] { R.id.name, R.id.arrow });
		this.setListAdapter(sa);

		// ���õײ��Զ��尴ť��ʾ
		btnCoustom = (Button) this.findViewById(R.id.btnCoustom);
		btnCoustom.setText("ѡ���˻�");
		btnCoustom.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String num = "";
		String nickName = "";
		String type = "";
		Intent intent = new Intent();
		if (id == 0) {
			num = "5560654220320266";
			nickName = "���ÿ�����";
			type = "���ÿ�";
		} else if (id == 1) {
			num = "1122065468210030";
			nickName = "�ҵ�����";
			type="���";
		} else if (id == 2) {
			num = "3322019830320266";
			nickName = "�ҵĵ�һ�����ÿ�";
			type= "���ÿ�";
		}
		intent.putExtra("AccInfo", "�˺ţ�" + num + "\n" + "������" + nickName + "\n"
				+ "�˻����ͣ�" + type + "\n" + "�˻�״̬������\n�Ƿ�ǩԼ��ǩԼ\n"
				+ "�����У���������������÷��֧��\n" + "�����գ�2006/04/10");
		intent.setClass(QueryAccount.this, AccountInfo.class);
		QueryAccount.this.startActivity(intent);

	}
}
