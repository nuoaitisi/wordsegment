package Util;

public class Log {
	
	private static int sum=0;
	private static int index=0;
	
	public static void setStart(int size) {
		// TODO Auto-generated method stub
		sum=size;
		index=0;
	}
	
	public static void tip(){
		System.out.println((index++)+"/"+sum);
	}

}
