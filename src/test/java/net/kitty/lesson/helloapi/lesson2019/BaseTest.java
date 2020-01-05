package net.kitty.lesson.helloapi.lesson2019;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.kitty.lesson.helloapi.util.JdbcDataUtil;
import net.kitty.lesson.helloapi.util.PropertyUtil;

public class BaseTest {
	  //创建类变量，所有方法都可以使用
		CloseableHttpClient client;
		//创建服务器变量
		String host;
		//创建公用的Cookie头变量
		Header header;
		//数据库连接对象
		Connection conn;
		
		@BeforeMethod
		public void setUp() {
			Properties httpProp=PropertyUtil.getProperties("/http.properties");
			host=httpProp.getProperty("http.url");
			System.out.println("Host信息："+host);
			String cookieValue=httpProp.getProperty("http.cookie");
			System.out.println("Cookie信息"+cookieValue);
			header=new BasicHeader("Cookie", "JSESSIONID="+cookieValue);
			//初始化客户端
			client= HttpClients.createDefault();
			
	        Properties dbProp= PropertyUtil.getProperties("/db.properties");
			String url=dbProp.getProperty("jdbc.url");
			String username=dbProp.getProperty("jdbc.username");
			String password=dbProp.getProperty("jdbc.password");
		    conn=JdbcDataUtil.getConn(url, username, password);
		}
		

		@AfterMethod
		public void tearDown() throws IOException {
			//关闭客户端连接
			client.close();
			JdbcDataUtil.closeConn(conn);
		}
}
