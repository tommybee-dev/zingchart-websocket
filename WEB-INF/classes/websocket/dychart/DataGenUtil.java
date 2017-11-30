package websocket.dychart;

//import java.util.Random;
import java.util.*;


public class DataGenUtil {
	
	private static final Random _randomGenerator = new Random();
	
	public static String getRandomMessage()
	{
		String jsonString = "{\"scale-x\":%s, \"plot0\" :[%s, %s], \"plot1\" :[%s, %s], \"plot2\" :[%s, %s], \"plot3\" :[%s, %s]}";
    	String unixTime = DataGenUtil.getUnixTimeStr3();
    
    	//broadcast(String.format(jsonString, unixTime, unixTime, DataGenUtil.getRandomNumberStr()));
    	
    	return String.format(jsonString, unixTime, 
    		unixTime, DataGenUtil.getRandomNumberStr(), //plot0
    		unixTime, DataGenUtil.getRandomNumberStr(), //plot1
    		unixTime, DataGenUtil.getRandomNumberStr(), //plot2
    		unixTime, DataGenUtil.getRandomNumberStr()  //plot3
    		);
	}
	
	public static String getUnixTimeStr3()
	{
		long t2 = new Date().getTime();
		return String.valueOf(t2);
	}
	
	
	public static String getUnixTimeStr2()
	{
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.getTimeInMillis() / 1000);
	}
	
	public static String getUnixTimeStr()
	{
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	public static String getRandomNumberStr()
	{
		Random randomGenerator = new Random();
		return String.valueOf(randomGenerator.nextInt(100));
	}
	
	
	public static long getUnixTime()
	{
		return System.currentTimeMillis() / 1000;
	}
	
	public static int getRandomNumber()
	{
		return _randomGenerator.nextInt(100);
	}
	
  public static void main(String args[]) {
    long L = System.currentTimeMillis() / 1000;
    System.out.println(L);
    
    
    Random randomGenerator = new Random();
    for (int idx = 1; idx <= 10; ++idx){
      int randomInt = randomGenerator.nextInt(100);
      System.out.println("Generated : " + randomInt);
    }
  }  
}