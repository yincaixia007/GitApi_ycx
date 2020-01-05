package net.kitty.lesson.helloapi.lesson2019;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 * 
* @ClassName: HelloApi
* @Description: Get请求展示接口测试流程
* @author Kitty
* @date 2019年12月23日 下午3:55:57
*
 */
public class Day1_1_HelloGet {
  
  public static void main(String[] args) throws IOException{
    //创建CloseableClient
    CloseableHttpClient  client= HttpClients.createDefault();
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
    client.close();
  }

}
