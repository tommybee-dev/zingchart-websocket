package websocket.dychart;

//import java.util.Random;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataGenUtil {
	
	private static final Random _randomGenerator = new Random();
	
	
	public static String getRandomMessage()
	{
		String jsonString = "{\"scale-x\":%s, \"plot0\" :[%s, %s], \"plot1\" :[%s, %s], \"plot2\" :[%s, %s], \"plot3\" :[%s, %s]}";
    	String unixTime = DataGenUtil.getUnixTimeStr3();
    	
    	Numbers numbers = tryNextStaticsNum();
		
    	//broadcast(String.format(jsonString, unixTime, unixTime, DataGenUtil.getRandomNumberStr()));
    	
    	return String.format(jsonString, unixTime, 
    		unixTime, numbers.getNum1(), //plot0
    		unixTime, numbers.getNum2(), //plot1
    		unixTime, numbers.getNum3(), //plot2
    		unixTime, numbers.getNum4()  //plot3
    		);
	}
	
	private static final AtomicInteger orderIds = new AtomicInteger(0);

	private static final ConcurrentHashMap<Integer, Numbers> numbers = new ConcurrentHashMap<>();
	
	public static final class Numbers
	{
		final String num1;
		final String num2;
		final String num3;
		final String num4;
		
		public Numbers(String num1, String num2, String num3, String num4)
		{
			this.num1 = num1;
			this.num2 = num2;
			this.num3 = num3;
			this.num4 = num4;
		}
		
		String getNum1(){return num1;}
		String getNum2(){return num2;}
		String getNum3(){
			return String.valueOf(Integer.valueOf(num3) + getRandomNumberStrInTen());
		}
		String getNum4(){
			return String.valueOf(Integer.valueOf(num3) - getRandomNumberStrInTen());
		}
		
		int getRandomNumberStrInTen()
		{
			Random randomGenerator = new Random();
			return randomGenerator.nextInt(10);
		}
	}
	
	private static void tryInitStatistics() {
		if (numbers.size() == 0) {
			String data1[]= 
			{
				"32.94333333", "33.58594203", "34.22855072", "34.87115942", "35.51376812",
				"36.15637681", "36.79898551", "37.4415942", "38.0842029", "38.72681159",
				"39.36942029", "40.01202899", "40.65463768", "41.29724638", "41.93985507",
				"42.58246377", "43.22507246", "43.86768116", "44.51028986", "45.15289855",
				"45.79550725", "46.43811594", "47.08072464", "47.72333333"
			};
			String data2[] =
			{
				"30", "30", "31", "32", "33", 
				"39", "39", "40", "40", "40",
				"40", "41", "41", "41", "42",
				"42", "42", "43", "43", "45",
				"46", "48",	"49", "51"

			};
			String data3[] =
			{
				"30", "30", "31", "32", "33", 
				"39", "39", "40", "40", "40",
				"40", "41", "41", "41", "42",
				"42", "42", "43", "43", "45",
				"46", "48",	"49", "51"

			};
			String data4[] =
			{
				"30", "30", "31", "32", "33", 
				"39", "39", "40", "40", "40",
				"40", "41", "41", "41", "42",
				"42", "42", "43", "43", "45",
				"46", "48",	"49", "51"

			};
			
			int cnt = 0;
			for(String datum: data1)
			{
				numbers.put(orderIds.getAndIncrement(), new DataGenUtil.Numbers(datum, data2[cnt], data3[cnt], data4[cnt]));
				cnt++;
			}
		}
	}

	public static Numbers tryNextStaticsNum() {
		tryInitStatistics();
		return numbers.remove(orderIds.decrementAndGet());
	}
	
	public static String getRandomMessage2()
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