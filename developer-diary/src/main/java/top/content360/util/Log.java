package top.content360.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static final boolean log = true;
	
	public static void log(String str){
		if(log){
			System.out.println(getTimeStr() + str);
		}
	}
	
	public static String getTimeStr(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = formatter.format(currentTime);
	    return dateString + "-----";
	}
}
