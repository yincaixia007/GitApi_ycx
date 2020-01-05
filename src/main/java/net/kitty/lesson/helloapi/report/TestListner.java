package net.kitty.lesson.helloapi.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * 
* @ClassName: TestListner
* @Description: 监听器监听每次测试运行的结果，并把结果设置到ExtentTest当中，最后生成extentreport
* @author Kitty
* @date 2019年12月24日 下午7:26:18
*
 */
public class TestListner  implements ITestListener{
  //监听器中配置ExtentReports
  private static ExtentReports extent = ExtentManager.createInstance();
  //ExtentTest记录每次的测试
  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

  @Override
  public synchronized void onStart(ITestContext context) {
     
  }

  @Override
  public synchronized void onFinish(ITestContext context) {
      //结束时生成extent报告到指定文件夹,引入本地js,css避免问题
      extent.flush();
      String REPORT_PATH="report/index.html";
      Document html;
      try {
        html = Jsoup.parse(new File(REPORT_PATH), "UTF-8");
        Element linkCss=html.select("link").get(2);
        linkCss.attr("href","extent.css");
        Element scriptJs=html.select("script").get(1);
        scriptJs.attr("src", "extent.js");
        FileOutputStream fos = new FileOutputStream(REPORT_PATH, false);
        fos.write(html.html().getBytes());
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  @Override
  public synchronized void onTestStart(ITestResult result) {
      System.out.println((result.getMethod().getMethodName() + " 开始执行************************"));
      ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
      test.set(extentTest);
  }

  @Override
  public synchronized void onTestSuccess(ITestResult result) {
      System.out.println((result.getMethod().getMethodName() + " 运行通过*************************"));
      test.get().pass("Test passed");
  }

  @Override
  public synchronized void onTestFailure(ITestResult result) {
      System.out.println((result.getMethod().getMethodName() + " 运行失败**************************"));
      test.get().fail(result.getThrowable());
  }

  @Override
  public synchronized void onTestSkipped(ITestResult result) {
      System.out.println((result.getMethod().getMethodName() + " 跳过执行***************************!"));
      test.get().skip(result.getThrowable());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
      System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
  }
}
