package com.ultrawise.android.bank.webservices;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ultrawise.android.bank.Enum.AccState;
import com.ultrawise.android.bank.Helper.Helper;
import com.ultrawise.android.bank.base.IAccSystem;
import com.ultrawise.android.bank.base.IQuery;
import com.ultrawise.android.bank.base.ITrans;
import com.ultrawise.android.bank.implement.AccSystem;
import com.ultrawise.android.bank.implement.Account;
import com.ultrawise.android.bank.implement.CurrentDeposit;

public class Action {
	private static Action mAction;
	private IQuery mAccount;
	private IAccSystem mAccSystem;
	private CurrentDeposit mCurrentDepositt;

	protected Action() {
		// mAccount = new Account();
		mAccSystem = new AccSystem();
		mCurrentDepositt = new CurrentDeposit();
	}

	/**
	 * 取得Action对象，可以用于调用不同功能
	 * 
	 * @return Action对象
	 */
	public static Action getAction() {
		if (mAction == null) {
			return new Action();
		}
		return mAction;
	}

	/**
	 * 功能号:0101
	 * 
	 * @author hosolo
	 * @return
	 */
	public JSONObject performGetAccType() {
		return Helper.wrapUp(mAccSystem.getAccType());
	}

	/**
	 * 功能号：0103
	 * 
	 * @author hosolo
	 * @param userId
	 * @param accType
	 * @param accState
	 * @return
	 */
	public JSONObject performGetAccWithNickName(String userId, String accType,
			AccState accState) {
		return Helper.wrapUp(mAccSystem.getAccWithNickName(userId, accType,
				accState));

	}

	/**
	 * 传入账户号，取得账户管理所需要得账户信息
	 * 
	 * @author 王亭
	 * @param account
	 * @return 账户信息
	 */
	public JSONObject performGetAccInfo(String account) {
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getAccInfo(account));
		return jsonObj;
	}
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param account
	 * @param password
	 * @param amtph
	 * @param amtnum
	 * @return
	 */
	public JSONObject performGetTargetPhoneInfo(String account,
			String password, String amtph, double amtnum){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getTargetPhoneInfo(account, password, amtph, amtnum));
		return jsonObj;
	}
	
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param account
	 * @param password
	 * @param amtph
	 * @param amtnum
	 * @return
	 */
	public JSONObject performGetTransfeAct(String account, String password,
			String amtph, double amtnum){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.transfeAct(account, password, amtph, amtnum));
		return jsonObj;
	}
	
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param id
	 * @return
	 */
	public JSONObject performGetComeQueryInfo(String id){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getComeQueryInfo(id));
		return jsonObj;
	}
	
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param id
	 * @return
	 */
	public JSONObject performGetListQueryInfo(String id){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getListQueryInfo(id));
		return jsonObj;
	}
	
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param serNo
	 * @param detail
	 * @return
	 */
	public JSONObject performSetDetail(String serNo, String detail){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.setDetail(serNo, detail));
		return jsonObj;
	}
	
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param id
	 * @param paymentNam
	 * @return
	 */
	public JSONObject performGetPaymentHisInfo(String paymentNam,String id){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getPaymentHisInfo(paymentNam,id));
		return jsonObj;
	}
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-24
	 * @param paymentName
	 * @param paymentAmt
	 * @param paymentActNo
	 * @param paymentActPasswd
	 * @param paymentNum
	 * @return
	 */
	public JSONObject performRecharge(String paymentName,
			double paymentAmt, String paymentActNo, String paymentActPasswd,String paymentNum){
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.recharge(paymentName, paymentAmt, paymentActNo, paymentActPasswd, paymentNum));
		return jsonObj;
	}
	/**
	 * 
	 * @author 王   亭
	 * 2011-3-28
	 * @param id
	 * @return
	 */
	public JSONObject performGetPaymentInfo(String id) {
		JSONObject jsonObj = new JSONObject();
		jsonObj = Helper.wrapUp(mCurrentDepositt.getPaymentInfo(id));
		return jsonObj;
	}
}
