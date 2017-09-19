package luan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Util.Util;

public class TEST {
	
	private static Set<String> words=new HashSet<String>();
	private static Set<String> stopWords=new HashSet<String>();
	static HashMap<String,String> nlpir_diff=new HashMap<String,String>();
	static HashMap<String,String> thulac_diff=new HashMap<String,String>();
	static boolean run=false;
	static String lines=null;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
if(run){
		lines=Util.getContent(new File("diff/词典.txt"));
		//Collection<String> lines.split("\n").
		for(String str:lines.split("\n")){
			words.add(str);
		}
		
		lines=Util.getContent(new File("diff/stop.txt"));
		for(String str:lines.split("\n")){
			stopWords.add(str);
		}
		
		lines=Util.getContent(new File("diff/nlpir_diff.txt"));
		for(String str:lines.split("\n")){
			nlpir_diff.put(str.split("\t")[1], str.split("\t")[2]);
		}
		
		lines=Util.getContent(new File("diff/thulac_diff.txt"));
		for(String str:lines.split("\n")){
			//System.out.print(str.split("\t")[1]+"\t\t");
			//System.out.println(str.split("\t")[2]);
			thulac_diff.put(str.split("\t")[1], str.split("\t")[2]);
		}

		
		FileWriter pw=new FileWriter("diff/words.txt",true);
		for(Map.Entry<String,String> map:nlpir_diff.entrySet()){
			String compared=map.getKey();
			String test=map.getValue();
			if(!words.contains(compared)){
				if(stopWords.contains(compared)){
					//遍历nlpir_diff，其中属于禁止连接词的忽略
					//System.out.println(compared);
					;
				}else{
					//发现了新的词典，需要加入到word.txt文件中
					if(compared.contains(" ") || compared.contains("&")){
						;
					}else{
						System.out.println(compared+"\t"+test);
						//pw.write(compared+"\r\n");
					}
				}
			}
		}
		//pw.close();
}

		
		
		String stop=Util.getContent(new File("diff/tmp.txt"));
		for(String str:stop.split("\n")){
			//stopWords.add(str);
			stopWords.add(str);
		}
		
		lines=Util.getContent(new File("diff/words.txt"));
		for(String str:lines.split("\n")){
			thulac_diff.remove(str);
		}
		System.out.println(thulac_diff.size());
		PrintWriter pw=new PrintWriter("diff/tmp");
		for(Map.Entry<String, String> en:thulac_diff.entrySet()){
			pw.print(en.getKey()+"\t");
			pw.println(en.getValue());
		}
		pw.flush();
		pw.close();
	}

}
