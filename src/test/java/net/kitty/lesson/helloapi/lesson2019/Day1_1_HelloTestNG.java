package net.kitty.lesson.helloapi.lesson2019;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
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

import net.kitty.lesson.helloapi.util.HttpUtil;
import net.kitty.lesson.helloapi.util.JSONPathUtil;
import net.kitty.lesson.helloapi.util.JdbcDataUtil;

/**
 * 
* @ClassName: HelloApi
* @Description: Get请求展示接口测试流程
* @author Kitty
* @date 2019年12月23日 下午3:55:57
*
 */
public class Day1_1_HelloTestNG {
	
	 CloseableHttpClient  client;
	 Header header;
	 String host;
	 int memberid;
	 Connection conn;
	@BeforeMethod
	public  void setUp() {
		client= HttpClients.createDefault();
		host="http://192.168.3.11:8080";
		String name="Cookie";
	    String value="JSESSIONID=66F1B4C8ACF7C1B31FBF9459B0BB22B7";
	    header=new BasicHeader(name, value);
	    String url="jdbc:mysql://192.168.3.11:3306/javamall?useUnicode=true&characterEncoding=utf8";
	    String username="root";
	    String password="123456";
	    conn=JdbcDataUtil.getConn(url, username, password);
		
	}
	
	@AfterMethod
	public  void tearDown() throws IOException {
		client.close();
		
		
	}
  
	
	//@Test
  public  void test() throws ParseException, IOException{

    //创建请求
    HttpGet request=new HttpGet("http://192.168.3.11:8080/javamall/admin/backendUi!main.do");
    
    String name="Cookie";
    String value="JSESSIONID=66F1B4C8ACF7C1B31FBF9459B0BB22B7";
    Header header=new BasicHeader(name, value);
    
    request.addHeader(header);
    
    //发起请求接受响应
    HttpResponse response=client.execute(request);
    //打印响应status，响应头，响应行
    StatusLine status= response.getStatusLine();
    int statusCode=status.getStatusCode();
    System.out.println("statusCode:"+statusCode);
    Header[] headers= response.getAllHeaders();
    System.out.println("heades:"+headers.toString());
    HttpEntity responseEntity=response.getEntity();
    String strResult=EntityUtils.toString(responseEntity);
    System.out.println("response:"+strResult);
    
  }
	
	
	//@Test
	public void testListUser() throws ClientProtocolException, IOException {
		
		
		String path="/javamall/shop/admin/member!memberlistJson.do?";
		String param="page=1&rows=10&sort=member_id&order=desc";
		HttpGet request=new HttpGet(host+path+param);
		
		request.addHeader(header);
		
		HttpResponse response=client.execute(request);
		System.out.println("响应："+EntityUtils.toString(response.getEntity()));
		
		
	}
	@Test(priority=1)
	public void testListUserByParam() throws ClientProtocolException, IOException {
		String path="/javamall/shop/admin/member!memberlistJson.do";
		Map<String, String> map=new HashMap<String, String>();
		map.put("page", "1");
		map.put("rows", "10");
		map.put("sort", "member_id");
		map.put("order", "desc");
		
		String param=HttpUtil.getQueryParam(map);
		System.out.println("param:"+param);
		
        HttpGet request=new HttpGet(host+path+param);
		
		request.addHeader(header);
		
		HttpResponse response=client.execute(request);
		
		String responseStr=EntityUtils.toString(response.getEntity());
		System.out.println("响应："+responseStr);
		
		
		int total=JSONPathUtil.extract(responseStr, "$.total");
		System.out.println("用户总数是："+total);
		
	
		String sql="select count(*) from es_member";
		Object[][] data=JdbcDataUtil.getData(conn, sql);
		long dbtotal=(long)data[0][0];
		assertEquals(total, dbtotal);
		
		
		
		
		
		//memberid=JSONPathUtil.extract(responseStr, "$.rows[0].member_id");
		//System.out.println("用户id是："+memberid);
	}
	
	//@Test(priority=2)
	public void deleteuser() throws ClientProtocolException, IOException {
		String params=String.valueOf(memberid);
		String path="/javamall/shop/admin/member!delete.do";
		Map<String, String> map=new HashMap<String, String>();
		map.put("member_id", params);
		String param=HttpUtil.getQueryParam(map);
		
        HttpGet request1=new HttpGet(host+path+param);
		
		request1.addHeader(header);
		
		HttpResponse response1=client.execute(request1);
		
		System.out.println(request1);
		
		String responseStr1=EntityUtils.toString(response1.getEntity());
		System.out.println("响应1："+responseStr1);
		String str=JSONPathUtil.extract(responseStr1, "$.message");
		Assert.assertEquals(str, "删除成功");
		
		
		String sql="select count(*) from es_member where member_id="+memberid;
		Object[][] data=JdbcDataUtil.getData(conn, sql);
		//assertNull(data);
		
		assertEquals((long)data[0][0], 0);
		
	}

}

