package websocket.dychart;

import java.util.*;



public class CodeTest {
  public static void main(String args[]) {
    long L = System.currentTimeMillis() / 1000;
    System.out.println(L);
    
    Date now = new Date();      
	Long longTime = new Long(now.getTime()/1000);
	System.out.println(longTime.intValue());

    Random randomGenerator = new Random();
    for (int idx = 1; idx <= 10; ++idx){
      int randomInt = randomGenerator.nextInt(100);
      System.out.println("Generated : " + randomInt);
    }
  }  
}