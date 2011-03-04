package com.ultrawise.android.bank.webservices.implement.account_query02;

import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/query")
public class AccountSevericeQuery {
	private JSONObject mJsonObj = new JSONObject();
	private int mAction;
	private String[] mValue;
	private String mReturnString;
	// 静态的功能标识
	private final static int GET_USER_NO = 101;

	/**
	 * 接收请求 1.解析json 2.解密 3.解析字符串格式 4.调用功能 5.加密 6.包装成json 7.返回json
	 * 
	 * @author hosolo
	 * @param anything
	 * @return
	 */
	@Consumes("application/x-www-form-urlencoded")
	@Path("do/")
	@Produces("application/json")
	@POST
	public JSONObject doGet(@FormParam("value") String anything) {
		// 解析json
		// 解密
		// 解析格式
		// String anything = "0101";
		mValue = anything.split(":");
		mAction = Integer.parseInt(mValue[0]);
		System.out.println("前台传入的anything=" + anything);
		List<String> list = AccountQueryManager.getInstance().getAccType();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("list=" + list.get(i));
		}
		return wrapUp(doEncode(list));
	}

	private List<String> doEncode(List<String> lstMingWen) {

		List<String> lstMiWen = new ArrayList<String>();
		if (lstMingWen.size() != 0) {
			for (String value : lstMingWen) {
				lstMiWen.add(Base64.encode(value, "utf-8"));
			}
		}
		return lstMiWen;

	}

	/**
	 * 将每个密文包装成JSON
	 * 
	 * @param lstValue
	 * @return
	 */
	private JSONObject wrapUp(List<String> lstMiWen) {
		JSONObject wrapJsonObj = new JSONObject();
		try {
			for (int i = 0; i < lstMiWen.size(); i++) {
				wrapJsonObj.put("miwen" + i, lstMiWen.get(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wrapJsonObj;
	}
}
