package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Util {
	
	
	private static Scanner sc=null;
	private static PrintWriter pw=null;
	private static Set<String> set=new HashSet<String>();
	/**
	 * 
	 * <p>Title:递归得到某一路径下的所有文件集合</p>
	 * <p>Description：</p>
	 * <p>Company：17zuoye</p>
	 * @author Administrator
	 * @data
	 */
	public static void getFile(File file,Set set) {
		if(file.isDirectory()){
			File childDir[]=file.listFiles();
			for(File dir:childDir){
				getFile(dir,set);
			}
		}else if(file.isFile()){
			set.add(file);
		}
	}
	
	public static String getContent(File file){
		String content="";
		try {
			Scanner sc=new Scanner(file);
			while(sc.hasNext()){
				content+=sc.nextLine()+"\n";
			}
		//	System.out.println(content);
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public static void distinct(File file) {
		setScanner(file);
		//File filecopy=new File(file.getAbsoluteFile()+"_copy");
		//setPrintWrinter(filecopy);
		try {
			set.clear();
			while(sc.hasNext()){
				set.add(sc.next());
			}
			
			setPrintWrinter(file);
			for(String words:set){
				pw.println(words);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
		sc=null;
		pw.close();
		pw=null;
		/*if(file.delete()){
			filecopy.renameTo(file);
		}*/
	}
	

	private static void setScanner(File file) {
		try {
			sc=new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void setPrintWrinter(File file) {
		try {
			pw=new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
