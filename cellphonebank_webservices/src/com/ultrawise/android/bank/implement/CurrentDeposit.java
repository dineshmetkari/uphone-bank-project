package com.ultrawise.android.bank.implement;

import java.util.Date;
import java.util.HashMap;

import com.sun.org.apache.commons.collections.StaticBucketMap;
import com.ultrawise.android.bank.Enum.EOperation;
import com.ultrawise.android.bank.Enum.TableName;
import com.ultrawise.android.bank.Helper.Helper;
import com.ultrawise.android.bank.base.IAccSystem;
import com.ultrawise.android.bank.base.ITrans;
import com.ultrawise.android.bank.base.IUpdate;
import com.ultrawise.bank.implement.dao.DataAccessModel;

public class CurrentDeposit extends Account implements ITrans, IUpdate {

	public HashMap<String, String> getPaymentHisInfo(String id) {
		HashMap<String, String> paymentHisInfo = new HashMap<String, String>();

		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("paymentform", "id", id);
		if (temp != null) {
			paymentHisInfo.put("paymentNam", temp.get("name"));
			paymentHisInfo.put("paymentAmt", temp.get("damout"));
			paymentHisInfo.put("paymentTim", temp.get("date"));
			paymentHisInfo.put("paymentSer", temp.get("dunum"));
			paymentHisInfo.put("paymentAct", temp.get("account"));
		}
		return paymentHisInfo;
	}

	public HashMap<String, String> getPaymentInfo(String userid, String id) {
		HashMap<String, String> paymentInfo = new HashMap<String, String>();

		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("pendingform", "userid", userid,
						"id", id);
		if (temp != null) {
			paymentInfo.put("paymentNam", temp.get("name"));
			paymentInfo.put("paymentAmt", temp.get("damout"));
			paymentInfo.put("charger", temp.get("charger"));
			paymentInfo.put("concordat", temp.get("dunum"));
			paymentInfo.put("deadline", temp.get("dulimit"));
		}
		return paymentInfo;
	}

	public HashMap<String, String> getRechargeInfo(String paymentName,
			String paymentActNo, double paymentAmt, String paymentPhoneNum) {
		HashMap<String, String> recharfeInfo = new HashMap<String, String>();

		return recharfeInfo;
	}

	public HashMap<String, String> getTargetPhoneInfo(String account,
			String password, String amtph, double amtnum) {
		IAccSystem accSystem = new AccSystem();
		HashMap<String, String> targetPhoneInfo = new HashMap<String, String>();
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", account);
		if (password.equals(accInfo.get("actpwd"))) {
			HashMap<String, String> userInfo = DataAccessModel.newInstances()
					.createQueryTools().query("userInfo", "phnum", amtph);
			if (userInfo != null) {
				double balance = Double.parseDouble(accInfo.get("balance"));
				if (balance >= amtnum) {
					targetPhoneInfo.put("cost", accSystem.getCost("转账"));
					targetPhoneInfo.put("username", userInfo.get("userName"));
				} else {
					targetPhoneInfo.put("error", "您的余额不足");
				}
			} else {
				targetPhoneInfo.put("error", "对方手机号没有开通手机银行");
			}
		} else {
			targetPhoneInfo.put("error", "密码错误");
		}
		return targetPhoneInfo;
	}

	public HashMap<String, String> recharge(String paymentName,
			double paymentAmt, String paymentActNo, String paymentActPasswd,
			String paymentNum, String operator) {
		HashMap<String, String> recharge = new HashMap<String, String>();
		// 判断账号是否已经激活
		boolean isActive = acctIsActive(paymentActNo);
		if (!isActive) {
			// 账号没有激活，则激活账号
			DataAccessModel.newInstances().createUpdataTools().updata("accout",
					"orderid:" + paymentActNo, "activation", "1");
		}
		// 根据账号 获取账号的信息
		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", paymentActNo);
		// 判断输入的密码是否正确
		if (paymentActPasswd.equals(temp.get("actpwd"))) {
			// 获取账号的余额
			double balance = Double.parseDouble(temp.get("balance"));
			balance -= paymentAmt;
			// 根据缴费名称获取缴费的合同号
			HashMap<String, String> temp1 = DataAccessModel.newInstances()
					.createQueryTools().query("patype", "name", paymentName);
			String serNo = temp1.get("dunum");
			// 插入一条充值记录
			DataAccessModel.newInstances().createInsertTools().insertThree(
					"rechargeform", "id:" + String.valueOf(rechargeId++), "userid:" + temp.get("userid"),
					"name:" + paymentName, "credit:" + paymentAmt,
					"creqqnum:" + paymentNum,
					"date:" + Helper.getCurrentTime(), "creno:" + serNo,
					"operator:" + operator, "account:" + paymentActNo);
			// 更新账号的余额
			DataAccessModel.newInstances().createUpdataTools()
					.updata("accout", "orderid", paymentActNo, "balance",
							String.valueOf(balance));
			recharge.put("serNo", serNo);
			recharge.put("result", "true");
		} else {
			recharge.put("error", "密码错误");
		}
		return recharge;
	}

	public boolean setDetail(String serNo, String detail) {
		return DataAccessModel.newInstances().createUpdataTools().updata(
				"transfers", "id", serNo, "description", detail);
	}

	public HashMap<String, String> transfeAct(String account, String password,
			String amtph, double amtnum) {
		HashMap<String, String> transInfo = new HashMap<String, String>();
		// 转出人 的 账号信息
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", account);
		// 转出人的信息
		HashMap<String, String> outuser = DataAccessModel.newInstances()
				.createQueryTools().query("userInfo", "userid",
						accInfo.get("userid"));
		
		// 转入人的信息
		HashMap<String, String> userInfo = DataAccessModel.newInstances()
				.createQueryTools().query("userInfo", "phnum", amtph);
		String userid = userInfo.get("userid");
		// 转入人的账号信息
		HashMap<String, String> accInfo1 = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "userid", userid);
		if(accInfo1== null || accInfo1.get("userid")== null){
			transInfo.put("result", "账号不存在");
			return transInfo;
		}
		// 判断转出人输入的密码是否正确
		if (password.equals(accInfo.get("actpwd"))) {
			double balance = transfer(amtnum, accInfo, outuser, userInfo,
					accInfo1);
			transInfo.put("result", "转账成功");
			transInfo.put("balance", String.valueOf(balance));
		} else {
			transInfo.put("result", "密码错误");
		}

		return transInfo;
	}
	
	public HashMap<String, String> transfeActToAct(String account,
			String password, String amtAct, double amtnum) {
		HashMap<String, String> transInfo = new HashMap<String, String>();
		// 转出人 的 账号信息
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", account);
		// 转出人的信息
		HashMap<String, String> outuser = DataAccessModel.newInstances()
				.createQueryTools().query("userInfo", "userid",
						accInfo.get("userid"));
		// 转入人的账号信息
		HashMap<String, String> accInfo1 = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", amtAct);
		if(accInfo1 == null || accInfo1.get("orderid")== null){
			transInfo.put("result", "账号不存在");
			return transInfo;
		}
		// 转入人的信息
		HashMap<String, String> userInfo = DataAccessModel.newInstances()
				.createQueryTools().query("userInfo", "userid", accInfo1.get("userid"));
		// 判断转出人输入的密码是否正确
		if (password.equals(accInfo.get("actpwd"))) {
			double balance = transfer(amtnum, accInfo, outuser, userInfo,
					accInfo1);
			transInfo.put("result", "转账成功");
			transInfo.put("balance", String.valueOf(balance));
			return transInfo;
		} else {
			transInfo.put("result", "密码错误");
			return transInfo;
		}
		
		
	}

	/**
	 * 转账时 修改 两个账号中的余额
	 * 
	 * @author 王 亭 2011-4-2
	 * @param account
	 * @param amtnum
	 * @param accInfo
	 * @param outuser
	 * @param userInfo
	 * @param accInfo1
	 * @return
	 */

	public static double transfer(double amtnum,
			HashMap<String, String> accInfo, HashMap<String, String> outuser,
			HashMap<String, String> userInfo, HashMap<String, String> accInfo1) {
		double balance = Double.parseDouble(accInfo.get("balance"));
		double balance1 = Double.parseDouble(accInfo1.get("balance"));
		balance1 += amtnum;
		DataAccessModel.newInstances().createInsertTools().insertThree(
				"transfers", "id:" + String.valueOf(transferId++), "userid:" + accInfo.get("userid"),
				"sequence:tf00005", "outsub:" + outuser.get("userName"),
				"intsub:" + userInfo.get("userName"),
				"inant:" + accInfo1.get("orderid"),
				"outant:" + accInfo.get("orderid"),
				"inphone:" + userInfo.get("phnum"), "amount:" + amtnum,
				"date:" + Helper.getCurrentTime(),
				"outphone:" + outuser.get("phnum"), "incity:手机转出",
				"inbank:中国工商银行", "inidnum:" + userInfo.get("panum"),
				"description:手机转出");
		balance -= amtnum;
		DataAccessModel.newInstances().createUpdataTools().updata("accout",
				"orderid", accInfo.get("orderid"), "balance",
				String.valueOf(balance));
		DataAccessModel.newInstances().createUpdataTools().updata("accout",
				"userid", userInfo.get("userid"), "balance",
				String.valueOf(balance1));
		return balance;
	}

	public HashMap<String, String> getComeQueryInfo(String type, String id) {
		HashMap<String, String> comeQueryInfo = new HashMap<String, String>();
		HashMap<String, String> temp = null;
		// 根据不同的来账的类型 在不同的表中查找来账记录
		if ("汇款".equals(type)) {
			temp = DataAccessModel.newInstances().createQueryTools().query(
					"remit", "id", id);
		} else {
			temp = DataAccessModel.newInstances().createQueryTools().query(
					"transfers", "id", id);
		}
		// 根据账号 得到 账号的类型id
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid",
						temp.get("outant"));
		// 根据账号id 得到 账号的类型名称
		HashMap<String, String> accType = DataAccessModel.newInstances()
				.createQueryTools()
				.query("paypal", "id", accInfo.get("actype"));
		comeQueryInfo.put("来账时间", temp.get("date"));
		comeQueryInfo.put("来账类型", type);
		comeQueryInfo.put("来账金额", temp.get("amount"));
		comeQueryInfo.put("付款人姓名", temp.get("outsub"));
		comeQueryInfo.put("付款账号", temp.get("outant"));
		comeQueryInfo.put("付款账号种类", accType.get("tyname"));
		comeQueryInfo.put("描述", temp.get("description"));
		return comeQueryInfo;
	}

	public HashMap<String, String> getListQueryInfo(String type, String id) {
		HashMap<String, String> listQueryInfo = new HashMap<String, String>();
		HashMap<String, String> temp = null;
		// 根据不同的来账的类型 在不同的表中查找来账记录
		if ("收入".equals(type)) {
			temp = DataAccessModel.newInstances().createQueryTools().query(
					"remit", "id", id);
		} else {
			temp = DataAccessModel.newInstances().createQueryTools().query(
					"transfers", "id", id);
		}
		// 根据账号 得到 账号的类型id
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid",
						temp.get("outant"));
		listQueryInfo.put("交易时间", temp.get("date"));
		listQueryInfo.put("交易账号", temp.get("outant"));
		listQueryInfo.put("交易金额", temp.get("amount"));
		listQueryInfo.put("余额", accInfo.get("balance"));
		listQueryInfo.put("描述", temp.get("description"));
		return listQueryInfo;
	}

	@Override
	public HashMap<String, String> getAccInfo(String acc) {
		HashMap<String, String> accInfo = new HashMap<String, String>();
		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", acc);
		HashMap<String, String> accTypeHashMap = DataAccessModel.newInstances()
				.createQueryTools().query("paypal", "id", temp.get("actype"));
		if (temp != null) {
			accInfo.put("账户", temp.get("orderid"));
			accInfo.put("账户别名", temp.get("aliss"));
			accInfo.put("账户类型", accTypeHashMap.get("tyname"));
			accInfo.put("币种", temp.get("montype"));
			accInfo.put("余额", temp.get("balance"));
			accInfo.put("账户状态", "预约换卡");
			accInfo.put("是否绑定", temp.get("bind").equals("1")?"是":"否");
			accInfo.put("开户行", temp.get("openbank"));
			accInfo.put("开户日", temp.get("opendate"));
		}
		return accInfo;
	}

	@Override
	public boolean acctIsActive(String paymentActNo) {
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", paymentActNo);
		if ("1".equals(accInfo.get("activation"))) {
			return true;
		}
		return false;
	}
	
	public boolean verifyPassword(String account,String password) {
		HashMap<String, String> accHashMap = DataAccessModel.newInstances().createQueryTools().query("accout", "orderid",account);
		if (password.equals(accHashMap.get("actpwd"))) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getNickName(String acc) {
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", acc);
		return accInfo.get("aliss");
	}

	@Override
	public HashMap<String, String> getOrderInfo(String acc) {
		HashMap<String, String> orderInfo = new HashMap<String, String>();
		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("appointmentform", "orderid", acc);
		if (temp != null) {
			orderInfo.put("账号", temp.get("orderid"));
			orderInfo.put("账户别名", temp.get("aliss"));
			orderInfo.put("更换原因", temp.get("reason"));
			orderInfo.put("领卡网点", temp.get("net"));
			orderInfo.put("网点地址", temp.get("netaddress"));
			orderInfo.put("工本费用", temp.get("cost"));
		} else {
			orderInfo.put("not find", "该账号没有预约信息");
		}
		return orderInfo;
	}

	public boolean deleAcc(String accNo) {
		// TODO Auto-generated method stub
		return DataAccessModel.newInstances().createUpdataTools().updata(
				"accout", "orderid", accNo, "delete", "1");
	}

	public boolean lossRegister(String accNo) {
		// TODO Auto-generated method stub
		return DataAccessModel.newInstances().createUpdataTools().updata(
				"accout", "orderid", accNo, "loss", "1");
	}

	public boolean setActActive(String accNo, String accPwd) {
		HashMap<String, String> accInfo = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", accNo);
		if (accPwd.equals(accInfo.get("actpwd"))) {
			return DataAccessModel.newInstances().createUpdataTools().updata(
					"accout", "orderid", accNo, "activation", "1");
		} else {
			return false;
		}
	}

	public boolean setBind(String accNo,String password) {
		// TODO Auto-generated method stub
		HashMap<String, String> accHashMap = DataAccessModel.newInstances().createQueryTools().query("accout", "orderid",accNo);
		if (password.equals(accHashMap.get("actpwd"))) {
			return DataAccessModel.newInstances().createUpdataTools().updata(
					"accout", "orderid", accNo, "bind", "1");
		}
		return false;
	}

	public boolean setNickName(String accNo, String name) {
		// TODO Auto-generated method stub
		return DataAccessModel.newInstances().createUpdataTools().updata(
				"accout", "orderid", accNo, "aliss", name);
	}

	public boolean setOrderCard(String accNo, String aliss, String reason,
			String net, String netaddress, double cost) {
		// TODO Auto-generated method stub
		DataAccessModel.newInstances().createInsertTools().insertThree(
				"appointmentform", "orderid:" + accNo, "aliss:" + aliss,
				"reason:" + reason, "net:" + net, "netaddress:" + netaddress,
				"cost:" + String.valueOf(cost));
		return DataAccessModel.newInstances().createUpdataTools().updata(
				"accout", "orderid", accNo, "orderstate", "1");
	}

	/**
	 * 缴费格式cd:0210:水费:30:110:123456:运营商
	 */
	public HashMap<String, String> payment(String paymentName,
			double paymentAmt, String paymentActNo, String paymentActPasswd,
			String charger) {
		HashMap<String, String> payment = new HashMap<String, String>();
		boolean isActive = acctIsActive(paymentActNo);
		if (!isActive) {
			DataAccessModel.newInstances().createUpdataTools().updata("accout",
					"orderid" + paymentActNo, "activation", "1");
		}
		HashMap<String, String> temp = DataAccessModel.newInstances()
				.createQueryTools().query("accout", "orderid", paymentActNo);
		
		if (paymentActPasswd.equals(temp.get("actpwd"))) {
			double balance = Double.parseDouble(temp.get("balance"));
			balance -= paymentAmt;
			HashMap<String, String> temp1 = DataAccessModel.newInstances()
					.createQueryTools().query("pendingform", "name",
							paymentName);
			String serNo = temp1.get("dunum");
			if(serNo==null){
				serNo="sT"+String.valueOf(Math.random()).substring(2, 10);//产生一串随机数
				System.out.println("流水号为:"+serNo);
			}
//			System.out.println("|"+temp.get("actpwd")+"|-----");//获得密码
			
			DataAccessModel.newInstances().createInsertTools().insertThree(
					"paymentform", "id:"+String.valueOf(paymentId++), "userid:" + temp.get("userid"),
					"name:" + paymentName, "dunum:" + serNo,
					"damout:" + paymentAmt, "date:" + Helper.getCurrentTime(),
					"charger:" + charger, "account:" + paymentActNo);
			DataAccessModel.newInstances().createUpdataTools()
					.updata("accout", "orderid", paymentActNo, "balance",
							String.valueOf(balance));
			payment.put("serNo", serNo);
			payment.put("result", "true");
		} else {
			payment.put("error", "密码错误");
		}
		return payment;
	}

}
