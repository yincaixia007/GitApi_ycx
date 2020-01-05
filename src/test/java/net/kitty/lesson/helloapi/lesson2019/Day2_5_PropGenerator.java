package net.kitty.lesson.helloapi.lesson2019;

import org.testng.annotations.Test;
import net.kitty.lesson.helloapi.util.Props;

public class Day2_5_PropGenerator {
   
  @Test(description="描述")
  public void test123(){
    Props.put("prop1", "prop1Value");
  }
   
}
