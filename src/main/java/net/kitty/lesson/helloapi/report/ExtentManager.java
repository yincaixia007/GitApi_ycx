package net.kitty.lesson.helloapi.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
  private static String reportLocation;
  private static ExtentReports extent;
  
  public static ExtentReports getInstance() {
      if (extent == null)
          createInstance();
      return extent;
  }
  
  public static ExtentReports createInstance() {
      //指明报告生成路径,/表示绝对路径，没有/表示相对路径（相对于系统根目录）
      reportLocation="report/index.html";
      System.out.println("extentreport的报告路径:"+reportLocation);
      ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportLocation);
      
      //指明Report的主题，编码和属性等...
      htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
      htmlReporter.config().setChartVisibilityOnOpen(true);
      htmlReporter.config().setTheme(Theme.STANDARD);
      htmlReporter.config().setDocumentTitle("ApiTestReport");
      htmlReporter.config().setEncoding("utf-8");
      htmlReporter.config().setReportName("ApiTestReport"); 
      //设置CDN(可能存在CDN无法联网加载样式的问题)
      htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

      //设置htmlReport至ExtentsReport
      extent = new ExtentReports();
      extent.attachReporter(htmlReporter);
      return extent;
  }

  
}
