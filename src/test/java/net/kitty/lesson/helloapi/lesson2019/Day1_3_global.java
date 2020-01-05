package net.kitty.lesson.helloapi.lesson2019;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.corba.se.impl.orbutil.GetPropertyAction;

import net.kitty.lesson.helloapi.util.HttpUtil;
import net.kitty.lesson.helloapi.util.JSONPathUtil;
import net.kitty.lesson.helloapi.util.JdbcDataUtil;
import net.kitty.lesson.helloapi.util.PropertyUtil;

public class Day1_3_global extends BaseTest {
	
	int memberId;
	
	@Test(description="测试HttpUtil Get请求参数转化")
	public void testListUserByParam() throws ClientProtocolException, IOException {
		String path="/javamall/shop/admin/member!memberlistJson.do";
		//新建Map，注意是java.util.map，导入内容要注意
		Map<String,String> queryMap=new HashMap();
		queryMap.put("page", "1");
		queryMap.put("rows", "10");
		queryMap.put("sort", "member_id");
		queryMap.put("order", "desc");
		//调用工具类，把Map转化为paramStr，构建请求参数对应的url内容
		String paramStr=HttpUtil.getQueryParam(queryMap);
		System.out.println(paramStr);
		
		HttpGet request=new HttpGet(host+path+paramStr);	
		//把setUp方法中已经赋值的Cookie头添加到接口请求中
		request.addHeader(header);
		//调用setUp方法中已经创建好的客户端，执行请求
		HttpResponse response=client.execute(request);
		//EntityUtils解析响应体并打印出json内容
		String respnseStr=EntityUtils.toString(response.getEntity());
		System.out.println("响应："+respnseStr);
		//使用JsonPath工具类提取出Total值，此处有一个运行时动态类型的转化，T->int，注意观察响应报文里字段的类型
		int apiTotal= JSONPathUtil.extract(respnseStr, "$.total");
		System.out.println("接口返回的用户总数:"+apiTotal);
		
		//获取数据库的用户总数，和接口的用户总数做断言
		String sql="select count(*) from es_member;";
		Object[][] data=JdbcDataUtil.getData(conn, sql);
		long dbTotal=(long) data[0][0];
		System.out.println("数据库返回的用户总数:"+dbTotal);
		Assert.assertEquals(apiTotal, dbTotal);
		//生产删除用户id
		memberId= JSONPathUtil.extract(respnseStr, "$.rows[0].member_id");
		System.out.println("获取到可用的member-id："+memberId);
	}
	
	
	
	
}
