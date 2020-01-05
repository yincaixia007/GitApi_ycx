package net.kitty.lesson.helloapi.lesson2019;

import org.testng.annotations.Test;
import net.kitty.lesson.helloapi.util.Props;

public class Day2_5_PropConsumer {

  @Test
  public void test(){
    String prop1=Props.get("prop1");
    System.out.println("跨类获取的props："+prop1);
  }
}
