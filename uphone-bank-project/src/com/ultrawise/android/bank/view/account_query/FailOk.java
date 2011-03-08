package com.ultrawise.android.bank.view.account_query;

import com.ultrawise.android.bank.view.transfer.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class FailOk extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.payment_result_one);
	        

	       TextView tvflag = (TextView)findViewById(R.id.tv_paymentdl_flag);
	    	TextView tvshow = (TextView)findViewById(R.id.tv_paymentdl_info);
	    	Button btnok = (Button)findViewById(R.id.btn_paymentdl_ok);
	    	
	        Intent receive_intent = getIntent();
	      String flag = receive_intent.getStringExtra("flag");
	        String info = receive_intent.getStringExtra("info");
	     tvflag.setText(flag);
	        tvshow.setText(info);
	        btnok.setText("确定");
	        btnok.setOnClickListener(new BtnOkCL());
	 }
	 class BtnOkCL implements OnClickListener{
			public void onClick(View v) {
				
				FailOk.this.finish();
			}
	    }
}