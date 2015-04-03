package it.tids.seed.util;

public class ParseUtil {
	
	private ParseUtil(){};
	
	private static boolean privateIsBlank(Object check){				
		return (check == null || (check.toString()).equals("0") || (check.toString()).trim().equals(""));
	}
	
   public static boolean isBlank(Object check){
	   return privateIsBlank(check);
   }
   
   public static boolean isNotBlank(Object check){
	   return !privateIsBlank(check);
   }
}