package com.ultrawise.android.bank.view.transfer;

import com.ultrawise.android.bank.view.transfer.R;
import com.ultrawise.android.bank.view.transfer.TransferActivity.BtnCancleCL;
import com.ultrawise.android.bank.view.transfer.TransferActivity.BtnHelpCL;
import com.ultrawise.android.bank.view.transfer.TransferActivity.BtnMainCL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TransPhConfirm extends Activity {
    /** Called when the activity is first created. */
	private Button btn_main = null;
	private Button btn_help = null;
	private Button btn_now = null;
	Intent transcon_intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_ph_confirm);
        
        Button btnok = (Button)findViewById(R.id.btn_cfm_ok);
    	Button btncancle = (Button)findViewById(R.id.btn_cfm_cancle);
        TextView tvtransacc = (TextView)findViewById(R.id.tv_transcon_acc);
        TextView tvtransbal = (TextView)findViewById(R.id.tv_transcon_balance);
        TextView tvtransinput = (TextView)findViewById(R.id.tv_transcon_inputnum);
        TextView tvtransamount = (TextView)findViewById(R.id.tv_transcon_amount);
        
        transcon_intent = getIntent();
        String transacc = transcon_intent.getStringExtra("selectedacc");
        String transbal = transcon_intent.getStringExtra("password");
        String transpas = transcon_intent.getStringExtra("password");
        String transinput = transcon_intent.getStringExtra("inputnum");
        String transamount = transcon_intent.getStringExtra("amount");
        
        tvtransacc.setText(transacc);
        tvtransinput.setText(transinput);
        tvtransamount.setText(transamount);
        
        //btn_main.setOnClickListener(new BtnMainCL());
        //btn_help.setOnClickListener(new BtnHelpCL());
        
        btnok.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		Intent transconok_intent = new Intent();
        		transconok_intent.putExtra("flag", "success");
        		transconok_intent.putExtra("info", "The transfered is successful");
        		transconok_intent.setClass(TransPhConfirm.this, TransResult.class);
        		startActivity(transconok_intent);
        	}
        });
        btncancle.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		Intent transconcancle_intent = new Intent();
        		transconcancle_intent.putExtra("flag", "failed");
        		transconcancle_intent.putExtra("info", "The transfer is canceled");
        		transconcancle_intent.setClass(TransPhConfirm.this, TransResult.class);
        		startActivity(transconcancle_intent);
        	}
        });
    }
    /*class BtnMainCL implements OnClickListener{
    	public void onClick(View v){
    		Intent transmain_intent = new Intent();
    		transmain_intent.putExtra("flag", "failed");
    		transmain_intent.putExtra("info", "The transfer is canceled");
    		transmain_intent.setClass(TransPhConfirm.this, TransResult.class);
    		startActivity(transmain_intent);
    	}
    }
    class BtnHelpCL implements OnClickListener{
    	public void onClick(View v){
    		Intent transhelp_intent = new Intent();
    		transhelp_intent.putExtra("flag", "failed");
    		transhelp_intent.putExtra("info", "The transfer is canceled");
    		transhelp_intent.setClass(TransPhConfirm.this, TransResult.class);
    		startActivity(transhelp_intent);
    	}
    }*/
}