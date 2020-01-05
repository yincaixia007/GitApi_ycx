package net.kitty.lesson.helloapi.lesson2019;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mysql.fabric.xmlrpc.Client;
import com.mysql.fabric.xmlrpc.base.Member;

import net.kitty.lesson.helloapi.util.ExcelDataUtil;

public class Demo4_Post extends BaseTest {

	@Test(dataProvider="dp")
	public void testAddUser(String provinceId,String provinceName,String cityId,String cityName,String regionId,String regionName) throws ClientProtocolException, IOException {

		String path = "/javamall/shop/admin/member!saveMember.do";
		

		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		String id = RandomStringUtils.random(2, "123456789");
		NameValuePair p1 = new BasicNameValuePair("member.uname", "muhe" + id);
		NameValuePair p2 = new BasicNameValuePair("member.password", "muhe");

		String num = RandomStringUtils.random(8, "0123456789");
		String phone = "182" + num;

		NameValuePair p3 = new BasicNameValuePair("member.mobile", phone);

		System.out.println("uname" + id);
		System.out.println("phone" + phone);

		postData.add(p1);
		postData.add(p2);
		postData.add(p3);
		postData.add(new BasicNameValuePair("province_id", provinceId));
		postData.add(new BasicNameValuePair("province", provinceName));
		postData.add(new BasicNameValuePair("city_id", cityId));
		postData.add(new BasicNameValuePair("city", cityName));
		postData.add(new BasicNameValuePair("region_id", regionId));
		postData.add(new BasicNameValuePair("region", regionName));
		
		
		System.out.println("参数："+provinceName+cityName+regionName);

		HttpEntity postEntity = new UrlEncodedFormEntity(postData);
		HttpPost postRequest = new HttpPost(host + path);
		postRequest.setEntity(postEntity);
		postRequest.addHeader(header);

		HttpResponse response = client.execute(postRequest);
		System.out.println("响应是:" + EntityUtils.toString(response.getEntity()));
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(){
		

		return ExcelDataUtil.readData("/excel/userInfo.xls", "user",1,1,0,0);
		
	}

	//@Test
	public void readExcel() {

		Object data[][] = ExcelDataUtil.readData("/excel/userInfo.xls", "user");
		printData(data);

	}

	public void printData(Object data[][]) {
		
		for(int i=0;i<data.length;i++) {
			for(int j=0;j<data[i].length;j++) {
				System.out.println(data[i][j]);
				
			}
			
		}
		
		}
}
