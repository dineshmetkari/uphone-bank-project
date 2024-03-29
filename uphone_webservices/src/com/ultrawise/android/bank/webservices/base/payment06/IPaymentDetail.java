package com.ultrawise.android.bank.webservices.base.payment06;

public interface IPaymentDetail {
	//获取待缴费项目的名称和缴费金额
	public String getPaymentNameAndMoney(String userNo,String paymentName);
	
	//获取某条待缴费项目的详细信息
	public String getPaymentDetail(String userNo,String paymentName,String month);
}
