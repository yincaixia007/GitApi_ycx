package net.kitty.lesson.helloapi.lesson2019;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import net.kitty.lesson.helloapi.util.EncryptionUtil;
import net.kitty.lesson.helloapi.util.JSONPathUtil;
import net.kitty.lesson.helloapi.util.JdbcDataUtil;
import net.kitty.lesson.helloapi.util.TimeUtil;

public class Demo5_time extends BaseTest{
	@Test
	public void testAddUser() throws ClientProtocolException, IOException, ParseException {

		String path = "/javamall/shop/admin/member!saveMember.do";
		

		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		String id = RandomStringUtils.random(6, "123456789");
		NameValuePair p1 = new BasicNameValuePair("member.uname", "muhe" + id);
		String password="YCXycx1988!@#";
		NameValuePair p2 = new BasicNameValuePair("member.password", password);
		
		String bir="1988-06-22";
		NameValuePair p3=new BasicNameValuePair("birthday", bir);
		
		postData.add(p1);
		postData.add(p2);
		postData.add(p3);
		
		

		HttpEntity postEntity = new UrlEncodedFormEntity(postData);
		HttpPost postRequest = new HttpPost(host + path);
		postRequest.setEntity(postEntity);
		postRequest.addHeader(header);

		HttpResponse response = client.execute(postRequest);
		String apiResponse=EntityUtils.toString(response.getEntity());
		
		String apiId=JSONPathUtil.extract(apiResponse, "$.id");
		System.out.println("新增用户是 ："+apiId);
		
		String query="select birthday,password from es_member where member_id="+apiId;
		
		Object[][] dbdata=JdbcDataUtil.getData(conn, query);
		
		Long dbbir=(Long)dbdata[0][0];
		String dbpassword=(String)dbdata[0][1];
		
		System.out.println("数据库读出的生日："+dbbir);
		System.out.println("数据库读出的生密码："+dbpassword);
		
		//String unixBir=TimeUtil.getDateByUnix(dbbir*1000);
		//System.out.println("unixBir :"+unixBir);
		
		Long realbir=TimeUtil.getUnixDateWithSecond(bir);
		System.out.println("真实时间是 ："+realbir);
		assertEquals(dbbir, realbir);
		
		
		String md5password=EncryptionUtil.md5(password);
		System.out.println(md5password);
		assertEquals(md5password, dbpassword);

}
}