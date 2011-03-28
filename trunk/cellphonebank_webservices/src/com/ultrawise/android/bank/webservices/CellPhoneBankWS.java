package com.ultrawise.android.bank.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ultrawise.android.bank.Enum.AccState;
import com.ultrawise.android.bank.Enum.EOperation;
import com.ultrawise.log.Log;

@Path("cellphone")
public class CellPhoneBankWS {
	Logger log = Log.getInstance().getLogger();// 日志对象

	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	@POST
	@Path("do")
	public JSONObject doPost(@FormParam("params") String params) {
		String[] arrayParams = params.split(":");
		// /* 数据校验，参数是否为空 */
		// if (arrayParams.length == 0) {
		// log.error("Array params length is 0");
		// throw new IllegalArgumentException(">Array params length is 0");
		// }

		/* 获取枚举功能号 */
		EOperation eo = EOperation.getEoperation(arrayParams[0]);
		// /* 数据校验，判断功能号是否找不到 */
		// if (eo == null) {
		// log.error("Enum operation isn't find,pelse check the form param");
		// throw new NullPointerException(
		// ">I can't find Enum operation,so it is null");
		// }

		/* 根据第一个参数获得得功能号进行功能选择 */
		Action action = Action.getAction();
		switch (eo) {

		case GET_ACC_INFO:
			//查询账号信息
			return action.performGetAccInfo(arrayParams[1]);

		case GET_TRARGETPHONEINFO:
			// 转账信息
			return action.performGetTargetPhoneInfo(arrayParams[1],
					arrayParams[2], arrayParams[3], Double.parseDouble(arrayParams[4]));
		
		case GET_TRANSFEACT:
			//确认转账
			return action.performGetTransfeAct(arrayParams[1],
					arrayParams[2], arrayParams[3], Double.parseDouble(arrayParams[4]));
		
		case GET_LISTQUERYINFO:
			//明细信息
			return action.performGetListQueryInfo(arrayParams[1]);
			
		case GET_COMEQUERYINFO:
			//来账信息
			return action.performGetComeQueryInfo(arrayParams[1]);
			
		case GET_SET_DETAIL:
			//设置描述信息
			return action.performSetDetail(arrayParams[1], arrayParams[2]);
		
		case GET_PAYMENTHISINFO:
			//查看某条历史缴费记录的详细信息
			return action.performGetPaymentHisInfo(arrayParams[1], arrayParams[2]);
			
		case GET_RECHARGE:
			//确认缴费
			return action.performRecharge(arrayParams[1], Double.parseDouble(arrayParams[2]), 
					arrayParams[3], arrayParams[4], arrayParams[4]);
		case GET_PAYMENTINFO:
			//查看某条待缴费的详细信息
			return action.performGetPaymentInfo(arrayParams[1]);
		default:
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject().put("404", 404);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObj;
		}
	}
}