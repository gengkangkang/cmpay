package com.cmpay.common.util;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月10日 下午3:27:12
 *
 */
public class Test {

	public static void main(String[] args) {
		java.lang.System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

//		String str="https://service.ms.netease.com/api/single_trade_query.htm?msg=PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48ZXBheT48dmVyc2lvbj4xLjA8L3ZlcnNpb24+PHNpZ25fdHlwZT5yc2E8L3NpZ25fdHlwZT48Z29vZHNfbmFtZT7lvannpag8L2dvb2RzX25hbWU+PGdvb2RzX3VybD5odHRwOi8vY2FpcGlhby4xNjMuY29tL29yZGVyL3ByZUJldF9zc3EuaHRtbDwvZ29vZHNfdXJsPjx0cmFkZV9hbW91bnQ+MC4wMTwvdHJhZGVfYW1vdW50PjxwbGF0Zm9ybV9pZD4yMDA4MTIyODE1UFQwMDAwMDAyNTwvcGxhdGZvcm1faWQ+PHBsYXRmb3JtX3RyYWRlX2lkPjIwMTIwNzAzMTdDUDAwMDAwMDAxPC9wbGF0Zm9ybV90cmFkZV9pZD48cGxhdGZvcm1fdXNlcl9pcD4xMjcuMC4wLjE8L3BsYXRmb3JtX3VzZXJfaXA+PHRyYWRlX3R5cGU+MTwvdHJhZGVfdHlwZT48cGxhdGZvcm1fdHJhZGVfdGltZT4yMDEyMDgwMiAxODoxMTowMDwvcGxhdGZvcm1fdHJhZGVfdGltZT48bm90aWZ5X3VybD5odHRwOi8vY2FpcGlhby4xNjMuY29tL29yZGVyL3ByZUJldF9zc3EuaHRtbDwvbm90aWZ5X3VybD48dGltZW91dD4xMjA8L3RpbWVvdXQ+PHBsYXRmb3JtX3ByaXZhdGVfZmllbGQ+dGVzdDwvcGxhdGZvcm1fcHJpdmF0ZV9maWVsZD48L2VwYXk+&msgType=xml&sign=123";
       String str="https://epay.163.com/pay_api/v1/order_pay.htm?msg=eyJhY2NvdW50SWQiOiJlYjcwY2EzNmExNDY0MmE4OGYxZGM2N2U3ZWJjNjcyNiIsImFjY291bnRUeXBlIjoib3V0ZXIiLCJjYXJkSW5mbyI6eyJiYW5rSWQiOiI5OTk4IiwiY2FyZEFjY291bnROYW1lIjoi5Y%2Bu5b2T54yrIiwiY2FyZE5vIjoiNjY2NjEyMzEyMzEyMzExMTExIiwiY2VydE5vIjoiNDIwMTE3MTk3NTAyMjA0OTA0IiwibW9iaWxlUGhvbmUiOiIxMzUwMDAwMDAwMyJ9LCJjaGFsbGVuZ2VUeXBlIjoibm9uZSIsImRpcmVjdGx5UGF5Ijp0cnVlLCJvcmRlckluZm9zIjpbeyJjdXJyZW5jeVR5cGUiOiIwMCIsImdvb2RzTmFtZSI6Iue9keaYk%2BaUr%2BS7mOWFheWAvCIsImdvb2RzVHlwZSI6IjEwMDIiLCJnb29kc1VybCI6Imh0dHBzOi8vd3d3LmNtaWludi5jb20vaW5kZXguaHRtbCIsIm5vdGlmeVVybCI6Imh0dHBzOi8vd3d3LmNtaWludi5jb20vIiwicGxhdGZvcm1UcmFkZUlkIjoiODRjYThhN2U0YjA0NDBhMmIyYzBiMzVmYzI4NDcwNDYiLCJwbGF0Zm9ybVRyYWRlVGltZSI6IjIwMTYxMTEwMDYxNTE3Iiwic2VsbGVySWQiOiJhcGlAd3liLjE2My5jb20iLCJ0aW1lb3V0IjoiIiwidHJhZGVBbW91bnQiOjEwMH1dLCJwYXlNZXRob2QiOiJxdWlja3BheSIsInBsYXRmb3JtSWQiOiIyMDE2MDgzMDEzUFQyMTUzMDIzNiIsInJpc2tJbmZvIjoiIiwidXNlcklwIjoiMTI3LjAuMC4xIn0%3D&sign=308205ab06092a864886f70d010702a082059c30820598020101310e300c06082a864886f70d02050500300b06092a864886f70d010701a08203df308203db308202c3a00302010202146dfe52cd6037dcc2a92ce1a966a4a645dff8968c300d06092a864886f70d0101050500304f3119301706035504030c10e7bd91e69893e5ae9d5573657220434131123010060355040b0c09e59586e58aa1e983a8311e301c060355040a0c15e7bd91e69893e5ae9de69c89e99990e585ace58fb8301e170d3136303932333036333735305a170d3139303932333036333735305a307a3133303106092a864886f70d010901162432303136303833303133505432313533303233364063612e657061792e3136332e636f6d310c300a06035504030c0361706931123010060355040b0c09e59586e58aa1e983a83121301f060355040a0c18e7bd91e69893e5ae9de69c89e69c89e99990e585ace58fb830820122300d06092a864886f70d01010105000382010f003082010a0282010100a5e742f27d066e97c69bf515f1ce13db2ad94f01f0eb38b21efe5b7d0c3f9d3dcdae3794c9369e57e79e998526e366ddd40a4411ebca20a6be0629023c73a88fde73fcab672c0e15df1059f25ed421e6a6a76c207569415ffed86f3b30a75e77ef9b3b3edd8bd77f027cedccc7a1247ec5f54aad265c5870078561200c3e0a53eb22454339e5d479ae8eb0fb99ebf06a41db2842145c5ad12e602883";
		try {
			String res=HttpClientUtil.postSSLwithCA(str, "", "UTF-8", "UTF-8");
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
